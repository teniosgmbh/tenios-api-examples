# HTTPS POSTs at beginning/end of call and on each block in PHP

Requirements:
--
-PHP version > 5.4
- If you are using apache, make sure that mod-php is installed. On Debian derivatives 
  you can install it by ``apt install libapache2-mod-php``.
- Domain name
- HTTPS certificate for domain name

External Call Control will work only with proper configured HTTPS endpoint, so in order to use this feature additional setup is required.

For example Apache Server 2.4 is used with php engine, here is a link how to configure virtualhost and https connectivity:

[SSL/TLS Strong Encryption: How-To](https://httpd.apache.org/docs/2.4/ssl/ssl_howto.html)


For development purpose
--
To start php listener execute via command line:

```php -S localhost:<PORT>```

To test implementation you can use ``test.php`` file. Before executing script please update `$server_url` variable in script.

```php test.php```

This script will make a test request to our implementation. Test data which will be in request is close to the real one.

Portal setup
--
 * Open routing Plan page for number you want to configure
 * Expand 'Advanced Options' section
 * Enable checkbox on 'Post on routingplan start'
 * Fill input with your domain name and path
 * Save Routing plan
