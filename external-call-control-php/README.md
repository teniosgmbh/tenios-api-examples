# External Call Control in PHP

Requirements:
--
- PHP version > 5.4
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

This script will make a few test requests to our http handler implementation. Test data which will be in request is close to the real one.

Portal setup
--
 * Open routing Plan page for number you want to configure
 * Enter your <domain_name/path> into 'Tagret' field
 * Fill other fields
 * Save Routing plan
