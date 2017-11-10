# Retrieve CDR in PHP

Test script, represent ability to retrieve historical info aboud call for specific range of time

Requirements:
- PHP 5.4 (or higher version)

## Test:
1. First of all you need to have active account in TENIOS system. You need to copy your personal Access Key, it can be found in General Setting page in TENIOS portal.
2. Open `retrieve_cdrs.php` file with text editor.
3. Replace value of ``$access_key`` with Access Key from step 1.
4. Execute via command line ``php retrieve_cdrs.php``
5. Output will show call records for your account for specified range of time