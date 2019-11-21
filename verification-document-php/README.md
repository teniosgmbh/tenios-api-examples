# Verification document creation in PHP

Test script, represent ability to create verification document in TENIOS system

Requirements:
- PHP 5.4 (or higher version)
- If you are using apache, make sure that mod-php is installed. On Debian derivatives 
  you can install it by ``apt install libapache2-mod-php``.


## Test:
1. First of all you need to have active account in TENIOS system. You need to copy your personal Access Key, it can be found in General Setting page in TENIOS portal.
2. Open `verification_document.php` file with text editor.
3. Replace value of ``$access_key`` with Access Key from step 1.
4. Execute via command line ``php verification_document.php``
5. Output will show Id of verification document in TENIOS system
