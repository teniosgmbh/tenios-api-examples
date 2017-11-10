<?php

logMessage("Received request. Request body:");
//Read and parse request body
$body = json_decode(file_get_contents('php://input'), true);

logMessage(json_encode($body));


$access_key="1234";
//Check if request contains proper access key
if(!array_key_exists('accessKey', $body)  || $body['accessKey'] != $access_key) {
    returnError("Illegal access key", 403);
}
//Check if request contains request type
if(!array_key_exists('requestType', $body)) {
    returnError("Unexpected request type", 400);
}

logMessage("Request type: ".$body['requestType']);
logMessage("Customer number: ".$body['customerNumber']);

$variables = $body['variables'];

if(array_key_exists('destination_number', $variables)) {
    logMessage("Destination number is: ".$variables['destination_number']);
} else {
    logMessage("The request contained no destination number");
}

logMessage("Iteration over all variables:");

foreach($variables as $key => $value) {
    logMessage("Variable ".$key." = ".$value);
}


returnResponse([]);

//Helper function to log and send http response
function returnResponse(array $data, $code=200) {
    logMessage("Return response:");
    logMessage(json_encode($data));
    http_response_code($code);
    header('Content-type: application/json');
    echo json_encode($data);
}

//Helper function to return http error
function returnError($message, $code) {
    http_response_code($code);
    die($message);
}
//Helper function to log data into log file
function logMessage($message) {
    $date = new DateTime();
    $date = $date->format("y:m:d h:i:s");
    error_log($date." - ".$message."\r\n", 3, './output.log');
}

?>