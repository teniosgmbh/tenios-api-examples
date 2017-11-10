# Callback in PHP

Test script, represent ability to create callbacks in TENIOS system and track their status

Requirements:
- PHP 5.4 (or higher version)

## Test:
1. First of all you need to have active account in TENIOS system. You need to copy your personal Access Key, it can be found in General Setting page in TENIOS portal.
2. You will need to have "Callbacks" feature enabled. Please contact support if you want enable this feature. Also please tell support that you are willing to use 'Callback' feature with API
3. After you get access to "Callback" feature, create Callback configuration. To do this open "Callbacks" page and click on "Add new configuration" button. Fill form. Click 'Save' button
4. Copy 'Configuration ID' of new callback configuration.
5. Open `callback.php` file with text editor.
6. Replace value of ``$access_key`` with Access Key from step 1.
7. Replace value of ``$callback_configuration_id`` with configuration id from step 4.
8. Replace value of ``$callback_number`` with number you want to call with callback.
9. Execute via command line ``php callback.php``
10. Output will show that new callback was created in TENIOS system. Also script will perform 3 checks of Callback status