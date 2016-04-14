package org.reactor.transport.http.config;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author grabslu
 */
public class ConfigContentHandler extends AbstractHandler {

    private static final String CONTENT_TYPE = "application/javascript; charset=utf-8";
    private static final String TEMPLATE_FILE = "config/config.js";

    private final ConfigContent configContent;

    public ConfigContentHandler(ConfigContent configContent) {
        this.configContent = configContent;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(SC_OK);

        PrintWriter out = response.getWriter();
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        Mustache mustache = mustacheFactory.compile(TEMPLATE_FILE);
        mustache.execute(out, configContent);

        baseRequest.setHandled(true);
    }
}
