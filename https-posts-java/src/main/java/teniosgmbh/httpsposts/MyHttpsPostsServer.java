package teniosgmbh.httpsposts;

import com.google.common.io.ByteStreams;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.google.common.io.ByteStreams.toByteArray;

public class MyHttpsPostsServer {

    private static Logger LOG = Logger.getLogger(MyHttpsPostsServer.class.getName());

    private final String url;
    private HttpServer httpServer;

    public MyHttpsPostsServer(String url) {
        this.url = url;
    }

    public static void main(String[] args) throws Exception {

        LogManager.getLogManager().readConfiguration(
                MyHttpsPostsServer.class.getResourceAsStream("/logging.properties"));

        if (args.length != 1) {
            LOG.info("Usage: command <your url>");
            System.exit(1);
        }

        MyHttpsPostsServer myServer = new MyHttpsPostsServer(args[0]);
        myServer.run();
    }

    private void run() throws Exception {

        // Set up the http server
        ResourceConfig rc = new ResourceConfig()
                .packages(getClass().getPackage().getName()) // Search this package for classes annotated with @Path
                .register(JacksonFeature.class)
                .register( // turn on logging of HTTP requests/responses
                        new LoggingFeature(LOG, Level.INFO, Verbosity.PAYLOAD_ANY, 1000))
                ;

        // Start the http(s) server

        if (url.startsWith("https")) {

            /* If you run this server in HTTPS mode, you MUST exchange the keystore_server_example with your own
            *  keystore. Also adjust the key store password, and the key password to match your keystore.*/

            SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
            sslContextConfigurator.setKeyStoreBytes(toByteArray(getClass().getResourceAsStream("/keystore_server_example")));
            sslContextConfigurator.setKeyStorePass("123456");
            sslContextConfigurator.setKeyPass("changeme");

            httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(url), rc, true,
                    new SSLEngineConfigurator(sslContextConfigurator).setClientMode(false).setNeedClientAuth(false));
        } else {

            /* If we run on http (e.g. behind an Apache httpd server, which can handle SSL for us), then we don't need an SSL context */

            httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(url), rc);
        }


        LOG.info("Server running at " + url + "/...");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                synchronized (MyHttpsPostsServer.class) {
                    MyHttpsPostsServer.this.stop();
                    LOG.info("Server stopped.");
                }
            }
        });
    }

    public void stop() {
        LOG.info("Stopping Grizzly HTTP server...");
        if (httpServer != null)
            httpServer.shutdown();
        LOG.info("Grizzly HTTP server stopped.");
    }
}
