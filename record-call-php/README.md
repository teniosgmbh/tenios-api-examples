# Recording API client example in PHP

Test script, represent ability to start/stop recording for specific call in TENIOS system

Requirements:
- PHP 5.4 (or higher version)

## Test:
1. First of all you need to have active account in TENIOS system. You need to copy your personal Access Key, it can be found in General Setting page in TENIOS portal.
2. Open `record-call.php` file with text editor.
3. Replace value of ``$access_key`` with Access Key from step 1.
5. Replace value of ``$call_uuid`` with call uuid you want to start/stop recording.
6. Execute via command line ``php record-call.php``
