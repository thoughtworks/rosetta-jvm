package rosetta.boot;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class Application {
    private Injector injector;

    public static Application boot(Arguments arguments) throws Exception {
        return new Application(arguments).boot();
    }

    private Application(Arguments arguments) {
        this.injector = Guice.createInjector(new ApplicationModule(arguments));
    }

    private Application boot() throws Exception {
        injector.getInstance(Server.class).start();
        return this;
    }

    private static class ApplicationModule extends AbstractModule {
        private final Arguments arguments;

        public ApplicationModule(Arguments arguments) {
            this.arguments = arguments;
        }

        @Override protected void configure() {
        }

        @Provides
        public Arguments arguments() {
            return arguments;
        }

        @Provides
        public Server server(Arguments arguments) throws IOException, URISyntaxException {
            Server server = new Server(arguments.port());

            HandlerCollection handlers = new HandlerCollection();

            ServletContextHandler handler = new ServletContextHandler();
            handler.setInitParameter("rackup", CharStreams.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ru"))));
            handler.setResourceBase(Files.createTempDir().getCanonicalPath());
            handler.addServlet(org.jruby.rack.RackServlet.class, "/*");

            handler.addEventListener(new org.jruby.rack.RackServletContextListener());
            handlers.setHandlers(new Handler[]{handler});
            server.setHandler(handlers);

            return server;
        }
    }
}
