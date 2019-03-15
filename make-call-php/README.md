# MakeCall API method in PHP

Test script, represent ability to create calls in TENIOS system

Requirements:
- PHP 5.4 (or higher version)

## Test:
1. First of all you need to have active account in TENIOS system. You need to copy your personal Access Key, it can be found in General Setting page in TENIOS portal.
2. You will need to have "Callbacks" feature enabled. Please contact support if you want enable this feature. Also please tell support that you are willing to use 'Callback' feature with API
3. Open `makeCall.php` file with text editor.
4. Replace value of ``$access_key`` with Access Key from step 1.
5. Replace value of ``$destination_number`` with number you want to call.
6. Replace value of ``$tenios_number`` with your tenios number.
7. Execute via command line ``php makeCall.php``
8. Output will show that new call request was created in TENIOS system.