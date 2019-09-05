<?php

$server_url = "https://api.tenios.com/";

$recording_start_endpoint = "record-call/start";

$recording_stop_endpoint = "record-call/stop";

//Replace with access_key of account. Can be found in 'General Settings page'
$access_key="XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXX";

//Replace with real call uuid
$call_uuid = 'ebb5ea36-de1e-4cae-bc21-d37209a0a2e2';

//Create a request object
$start_recording_request = array(
    'access_key'=>$access_key, //access_key from General Settings page
    'call_uuid' => $call_uuid // Call uuid.
);

echo 'Making request to TENIOS api to start recording. Request details:'."\r\n";
echo 'Access key : '.$start_recording_request['access_key']."\r\n";
echo 'Call UUID: '.$start_recording_request['call_uuid']."\r\n";
echo "\r\n";


$response_data = post($server_url.$recording_start_endpoint, $start_recording_request);
echo "Received response from API"."\r\n";

$id = $response_data['recording_uuid'];
echo "Call recording was successfully started. Recording id : ".$id."\r\n";
echo "\r\n";


usleep(1000000); //wait a second
//Create a request object
$stop_recording_request = array(
    'access_key'=>$access_key,
    'call_uuid' => $call_uuid,
    'recording_uuid' => $id
);
//Request status of Call
echo "Sending request to stop recording.\r\n";
$stop_recording_response_data = post($server_url.$recording_stop_endpoint, $stop_recording_request);
echo "Recording stop request status : ".(($stop_recording_response_data['success']) ? 'true' : 'false')."\r\n";

echo "Exit";

function post($url, $data) {
    $ch    = curl_init($url);
    curl_setopt_array($ch, array(
        CURLOPT_POST => TRUE,
        CURLOPT_RETURNTRANSFER => TRUE,
        CURLOPT_HTTPHEADER => array(
            'Content-Type: application/json' //Set appropriate content-type header. 
        ),
        CURLOPT_POSTFIELDS => json_encode($data)
    ));
    $response = curl_exec($ch);
    curl_close($ch);
    return json_decode($response, TRUE);
}
?>
