<?php
//Script to test if implementation of exaternal call control endpoint is correct
//Developed for testing purposes


$server_url = "http://localhost:8000/";

$endpoint = "index.php";

//Create a request object
//Example is close to real request body
$loop_count_1_request_body = array(
    'requestType'=> 'EXTERNAL_CALL_CONTROL',
    'customerNumber' => 200000, 
    'accessKey' => '1234',
    'loopCount' => '0',
    "requestStatus" => 'REQUESTING_BLOCKS',
    'blocksProcessingResult' => null,
    'variables' => [
        "destination_number" => "+4912345678",
        "answered_time" => "0",
        "caller_id_number" => "+4989444444444"
    ]
);

echo "Sending test request to external call control endpoint"."\r\n";
$response_data = post($server_url.$endpoint, $loop_count_1_request_body);
echo "Received response. Please check output log of your index.php script"."\r\n";
if($response_data['blocks'][0]['blockType'] == 'ANNOUNCEMENT') {
    echo "Response is correct."."\r\n";
}

$loop_count_2_request_body = array(
    'requestType'=> 'EXTERNAL_CALL_CONTROL',
    'customerNumber' => 200000, 
    'accessKey' => '1234',
    'loopCount' => '1',
    "requestStatus" => 'REQUESTING_BLOCKS',
    'blocksProcessingResult' => null,
    'variables' => [
        "destination_number" => "+4912345678",
        "answered_time" => "0",
        "caller_id_number" => "+4989444444444"
    ]
);

echo "Sending test request to external call control endpoint"."\r\n";
$response_data = post($server_url.$endpoint, $loop_count_2_request_body);
echo "Received response. Please check output log of your index.php script"."\r\n";
if($response_data['blocks'][0]['blockType'] == 'BRIDGE') {
    echo "Response is correct."."\r\n";
}

$loop_count_3_request_body = array(
    'requestType'=> 'EXTERNAL_CALL_CONTROL',
    'customerNumber' => 200000, 
    'accessKey' => '1234',
    'loopCount' => '2',
    "requestStatus" => 'REQUESTING_BLOCKS',
    'blocksProcessingResult' => null,
    'variables' => [
        "destination_number" => "+4912345678",
        "answered_time" => "0",
        "caller_id_number" => "+4989444444444"
    ]
);

echo "Sending test request to external call control endpoint"."\r\n";
$response_data = post($server_url.$endpoint, $loop_count_3_request_body);
echo "Received response. Please check output log of your index.php script"."\r\n";
if(count($response_data['blocks']) == 0) {
    echo "Response is correct."."\r\n";
}


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