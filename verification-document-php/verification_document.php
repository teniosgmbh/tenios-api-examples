<?php

$server_url = "https://api.tenios.com/";

$endpoint = "verification/create";
//Replace with access_key of account. Can be found in 'General Settings page'
$access_key="XXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX";

$documentContent = file_get_contents('./verification_document.pdf');

$encodedContent = base64_encode($documentContent);

//Create a request object
$data = array(
    'access_key'=>$access_key,
    'country'=>'Germany',
    'city'=>'Aarbergen',
    'zip'=>'50670',
    'street'=>'Musterstr',
    'house_number'=>'13',
    'document_type'=>'CONTRACT',
    'area_code'=>'221',
    'area_code'=>'221',
    'document_data'=>$encodedContent
);

echo "Making request to TENIOS api to create new verification document."."\r\n";

$response_data = post($server_url.$endpoint, $data);
echo "Received response from API"."\r\n";

$id = $response_data['verification_id'];
echo "Verification document was successfully created. Verification id : ".$id."\r\n";
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
    $errors = curl_error($ch);
    curl_close($ch);
    echo $errors;
    return json_decode($response, TRUE);
}

?>
