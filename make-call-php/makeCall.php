<?php

$server_url = "https://api.tenios.com/";

$init_make_call_endpoint = "makecall/init";

$status_makecall_endpoint = "makecall/status";

//Replace with access_key of account. Can be found in 'General Settings page'
$access_key="XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXX";

//Replace with real number
$tenios_number = '+4900000000';

//Replace with real number
$destination_number = '+49123456789';
//Create a request object
$init_call_request = array(
    'access_key'=>$access_key, //access_key from General Settings page
    'destination_number' => $destination_number, // The number which will be called.
    'tenios_number' => $tenios_number, // One of your numbers at tenios.
);

echo 'Making request to TENIOS api to create call. Request details:'."\r\n";
echo 'Access key : '.$init_call_request['access_key']."\r\n";
echo 'Phone number to use in call : '.$init_call_request['tenios_number']."\r\n";
echo "\r\n";


$response_data = post($server_url.$init_make_call_endpoint, $init_call_request);
echo "Received response from API"."\r\n";

$id = $response_data['id'];
echo "Call request was successfully created. Call id : ".$id."\r\n";
echo "\r\n";


usleep(1000000); //wait a second
//Create a request object
$status_call_request = array(
    'access_key'=>$access_key,
    'id' => $id
);
//Request status of Call
$status_response_data = post($server_url.$status_makecall_endpoint, $status_call_request);
echo "Call status : ".$status_response_data['status']."\r\n";

//Wait while call status is 'requested'
$i = 0;
while($i < 3 && $status_response_data['status'] == 'REQUESTED') {
    echo "Asking for callback status"."\r\n";
    $status_response_data = post($server_url.$status_makecall_endpoint, $status_call_request);
    echo "Make call status : ".$status_response_data['status']."\r\n";
    $i = $i + 1;
    echo "\r\n";
    usleep(2000000);
}
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
