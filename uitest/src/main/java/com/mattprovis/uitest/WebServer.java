package com.mattprovis.uitest;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.springframework.web.WebApplicationInitializer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/*
 * By default, Jetty only searches inside of jar files for WebApplicationInitializer classes.
 * This class overrides that behaviour to tell it specifically where to find our Initializer.
 *
 * Credit for much of this class goes to stackoverflow users Stevie (http://stackoverflow.com/a/13308996) and magomarcelo (http://stackoverflow.com/a/15702616).
 */
public class WebServer {
    private final int port;
    private final String hostname;
    private final Class<? extends WebApplicationInitializer> initializer;
    private final File resourceBaseDirectory;
    private final String contextPath;

    private WebAppContext webAppContext;

    private Server server;

    public WebServer(int port, String hostname, Class<? extends WebApplicationInitializer> initializer, File resourceBaseDirectory, String contextPath) {
        this.port = port;
        this.hostname = hostname;
        this.initializer = initializer;
        this.resourceBaseDirectory = resourceBaseDirectory;
        this.contextPath = contextPath;
    }

    public void start() throws Exception {
        server = new Server();
        server.addConnector(createConnector());
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);

        server.start();
    }

    public void stop() throws Exception {
        server.stop();
        server.join();
    }

    public boolean isRunning() {
        return server.isRunning();
    }

    public WebAppContext getWebAppContext() {
        return webAppContext;
    }

    public int getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }

    private SelectChannelConnector createConnector() {
        SelectChannelConnector _connector = new SelectChannelConnector();
        _connector.setPort(port);
        _connector.setHost(hostname);
        return _connector;
    }

    private HandlerCollection createHandlers() throws IOException, URISyntaxException {
        webAppContext = new WebAppContext();
        webAppContext.setParentLoaderPriority(true);
        webAppContext.setContextPath(contextPath);
        webAppContext.setResourceBase(resourceBaseDirectory.getAbsolutePath());

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");

        if (!scratchDir.exists()) {
            if (!scratchDir.mkdirs()) {
                throw new IOException("Unable to create scratch directory: " + scratchDir);
            }
        }

        System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        webAppContext.setClassLoader(jspClassLoader);

        ServletHolder holderJsp = new ServletHolder("jsp", JspServlet.class);
        holderJsp.setInitOrder(0);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.7");
        holderJsp.setInitParameter("compilerSourceVM", "1.7");
        holderJsp.setInitParameter("keepgenerated", "true");
        webAppContext.addServlet(holderJsp, "*.jsp");
        webAppContext.addServlet(holderJsp, "*.jspf");
        webAppContext.addServlet(holderJsp, "*.jspx");

        webAppContext.setAttribute("javax.servlet.context.tempdir", scratchDir);

        webAppContext.setConfigurations(new Configuration[]{
                new WebXmlConfiguration(),
                new AnnotationConfiguration() {
                    @Override
                    public void preConfigure(WebAppContext context) throws Exception {
                        MultiMap<String> map = new MultiMap<>();
                        map.add(WebApplicationInitializer.class.getName(), initializer.getName());
                        context.setAttribute(CLASS_INHERITANCE_MAP, map);
                        _classInheritanceHandler = new ClassInheritanceHandler(map);
                    }
                }});

        List<Handler> _handlers = new ArrayList<>();

        _handlers.add(webAppContext);

        HandlerList _contexts = new HandlerList();
        _contexts.setHandlers(_handlers.toArray(new Handler[0]));

        HandlerCollection _result = new HandlerCollection();
        _result.setHandlers(new Handler[]{_contexts});

        return _result;
    }
}