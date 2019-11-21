# Number Order API example in PHP

Test script, represent ability to order number in TENIOS system

Requirements:
- PHP 5.4 (or higher version)
- If you are using apache, make sure that mod-php is installed. On Debian derivatives 
  you can install it by ``apt install libapache2-mod-php``.

## Test:
1. First of all you need to have active account in TENIOS system. You need to copy your personal Access Key, it can be found in General Setting page in TENIOS portal.
2. Open `number_order.php` file with text editor.
3. Replace value of ``$access_key`` with Access Key from step 1.
4. Replace value of ``$verification_id`` with verification document id.
5. Execute via command line ``php number_order.php``
6. Output will show that new number order was created in TENIOS system.
