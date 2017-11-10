<?php

logMessage("Received request. Request body:");
//Read and parse request body
$body = json_decode(file_get_contents('php://input'), true);

logMessage(json_encode($body));


$hangupResponse = ["blocks" => [
    ["blockType" => "HANGUP",
     "hangupCause" => "CALL_REJECTED"
     ]
    ]];
$announcementResponse = ["blocks" => [
    ["blockType" => "ANNOUNCEMENT",
     "announcementName" => "Voicemail_Ansage",
     "standardAnnouncement" => true]
    ]];
$bridgeAndAnnouncementResponse = ["blocks" => [
    ["blockType" =>"BRIDGE",
    "bridgeMode" => "SEQUENTIAL",
    "destinations" => [
        [
            "destinationType" => "SIP_USER",
            "destination" => "1003"
        ],
        [
            "destinationType" => "SIP_USER",
            "destination" => "1004"
        ]
    ]],
    ["blockType" => "ANNOUNCEMENT",
        "announcementName" => "My_Announcement",
        "standardAnnouncement" => false
    ]
    ]];


$access_key="1234";
//Check if request contains proper access key
if(!array_key_exists('accessKey', $body)  || $body['accessKey'] != $access_key) {
    returnError("Illegal access key", 403);
}
//Check if request contains request status
if(!array_key_exists('requestStatus', $body)) {
    returnError("Unexpected request status", 400);
}

switch ($body['requestStatus']) {
    case 'REQUESTING_BLOCKS':
        $variables = $body['variables'];
        $caller = $variables['caller_id_number'];
        if ("+4912345678" == $caller) {
            returnResponse($hangupResponse);
        } else {
            if($body['loopCount'] == 0) {
                returnResponse($announcementResponse);
            } else if($body['loopCount'] == 1) {
                returnResponse($bridgeAndAnnouncementResponse);
            } else {
                returnResponse(["blocks"=>[]]);
            }
        }
        break;
    case 'VALIDATION_ERRORS':
        break;
    default:
        returnError("Unexpected request status", 400);
        break;
}


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