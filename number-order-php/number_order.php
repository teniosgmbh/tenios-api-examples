<?php

$server_url = "https://api.tenios.com/";

$order_number_endpoint = "number/order";

//Replace with access_key of account. Can be found in 'General Settings page'
$access_key="XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXX";

//Replace with verification id
$verification_id = '3d47943f-e899-45db-a361-ee006da83665';

//Create a request object
$request = array(
    'access_key'=>$access_key, //access_key from General Settings page
    'verification_id' => $verification_id,
    'number_type' => 'GEOGRAPHICAL'
);

echo 'Making request to TENIOS api to order number.'."\r\n";

$response_data = post($server_url.$order_number_endpoint, $request);
echo "Received response from API"."\r\n";

$id = $response_data['order_id'];
echo "Number order was successfully created. Order id : ".$id."\r\n";
echo "\r\n";

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
