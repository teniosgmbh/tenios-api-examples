<?php
//Script to test if implementation of http post endpoint is correct
//Developed for testing purposes


$server_url = "http://localhost:8000/";

$endpoint = "index.php";

//Create a request object
//Example is close to real request body
$test_http_request_body = array(
    'requestType'=> 'CALL_START',
    'customerNumber' => 200000, 
    'accessKey' => '1234',
    'variables' => [
        "destination_number" => "+4912345678",
        "answered_time" => "0",
        "caller_id_number" => "+4989444444444"
    ]
    
);

echo "Sending test request to httppost endpoint"."\r\n";
$response_data = post($server_url.$endpoint, $test_http_request_body);
echo "Received response. Please check output log of your index.php script"."\r\n";

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