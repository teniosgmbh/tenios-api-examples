<?php

$server_url = "https://api.tenios.com/";

$init_callback_endpoint = "callback/init";
$status_callback_endpoint = "callback/status";
//Replace with access_key of account. Can be found in 'General Settings page'
$access_key="XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXX";

//Replace with real configuration id. It can be found after callback configuration will be saved in 'Portal'
$callback_configuration_id = '7d3509a5-e650-4923-9895-XXXXXXXXXXX';

//Replace with real number
$callback_number = '+49123456789';
//Create a request object
$init_callback_request = array(
    'access_key'=>$access_key, //access_key from General Settings page
    'callback_config_id' => $callback_configuration_id, // Callback configuration id.
    'callback_number' => $callback_number, // Phonenumber to call - Partner B
    'delay' => 0 // Delay in seconds
    
);

echo 'Making request to TENIOS api to create callback. Request details:'."\r\n";
echo 'Access key : '.$init_callback_request['access_key']."\r\n";
echo 'Phone number to use in callback : '.$init_callback_request['callback_number']."\r\n";
echo "\r\n";


$response_data = post($server_url.$init_callback_endpoint, $init_callback_request);
echo "Received response from API"."\r\n";

$callback_id = $response_data['id'];
echo "Callback was succesfully created. Callback id : ".$callback_id."\r\n";
echo "\r\n";

usleep(1000000); //wait a second
//Create a request object
$status_callback_request = array(
    'access_key'=>$access_key,
    'id' => $callback_id
);
//Request status of Callback
$status_response_data = post($server_url.$status_callback_endpoint, $status_callback_request);
echo "Callback status : ".$status_response_data['status']."\r\n";

//Wait while callback status is 'requested'
$i = 0;
while($i < 3 && $status_response_data['status'] == 'REQUESTED') {
    echo "Asking for callback status"."\r\n";
    $status_response_data = post($server_url.$status_callback_endpoint, $status_callback_request);
    echo "Callback status : ".$status_response_data['status']."\r\n";
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
