<?php

//should be changed to production api url
$server_url = "https://api.tevox.com/";
$endpoint = "cdrs/retrieve";
//Replace with access_key of account. Can be found in 'General Settings page'
$access_key="XXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX";

//Create a request object
$data = array(
    'access_key'=>$access_key,
    'page'=> 1, // Retrieve cdr from page 1
    'page_size' => 10, // limit of cdrs 
    'start_date_from' => '2017-01-01T09:00:00.000Z', // Retrieve cdrs of calls that were started after given timestamp
    'start_date_to' => '2017-11-01T00:00:00.000Z' // Retrieve cdrs of calls that were started before given timestamp
);

echo 'Making request to Tevox api to retrieve cdr. Request details:'."\r\n";
echo 'Access key : '.$data['access_key']."\r\n";
echo 'Page(integer value starting from 1) : '.$data['page']."\r\n";
echo 'Page to retrieve:(integer value starting from 1) : '.$data['page']."\r\n";



// Setup cURL
$ch = curl_init($server_url.$endpoint);
curl_setopt_array($ch, array(
    CURLOPT_POST => TRUE,
    CURLOPT_RETURNTRANSFER => TRUE,
    CURLOPT_HTTPHEADER => array(
        'Content-Type: application/json' //Set appropriate content-type header. 
    ),
    CURLOPT_POSTFIELDS => json_encode($data)
));

// Send the request
$response = curl_exec($ch);

// Check for errors
if($response === FALSE){
    die(curl_error($ch));
}

// Decode the response
$responseData = json_decode($response, TRUE);


echo "\r\n";
echo "Receive response from API"."\r\n";
echo 'For selected range there are : '.$responseData['total_items'].' cdrs'."\r\n";

if($responseData['total_items']==0) {
    die();
}
echo "\r\n";


$items = $responseData['items'];
echo "Response contain following cdrs:"."\r\n";
echo "--------------------------"."\r\n";

//Iterate over items(cdrs) in response object
foreach ($items as $cdr) {
    echo "UUID : ",$cdr['uuid']."\r\n";
    echo "Call Type : ",$cdr['call_type']."\r\n";
    echo "Duration : ",$cdr['duration']." seconds "."\r\n";
    echo "Cost : ".$cdr['cost']." EUR"."\r\n";
    echo "--------------------------"."\r\n";
}

?>