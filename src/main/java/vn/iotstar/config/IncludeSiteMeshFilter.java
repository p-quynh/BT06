package vn.iotstar.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sitemesh.DecoratorSelector;
import org.sitemesh.content.ContentProcessor;
import org.sitemesh.webapp.SiteMeshFilter;
import org.sitemesh.webapp.WebAppContext;
import org.sitemesh.webapp.contentfilter.ResponseMetaData;
import org.sitemesh.webapp.contentfilter.Selector;

import java.io.IOException;

/** SiteMesh context compatible with Tomcat 11 response wrapping. */
final class IncludeSiteMeshFilter extends SiteMeshFilter {
    IncludeSiteMeshFilter(Selector selector, ContentProcessor contentProcessor,
                          DecoratorSelector<WebAppContext> decoratorSelector,
                          boolean includeErrorPages) {
        super(selector, contentProcessor, decoratorSelector, includeErrorPages);
    }

    @Override
    protected WebAppContext createContext(String contentType, HttpServletRequest request,
                                          HttpServletResponse response, ResponseMetaData metaData) {
        ServletContext servletContext = request.getServletContext();
        return new WebAppContext(contentType, request, response, servletContext,
                getContentProcessor(), metaData, false) {
            @Override
            protected void dispatch(HttpServletRequest req, HttpServletResponse resp, String path)
                    throws ServletException, IOException {
                RequestDispatcher dispatcher = servletContext.getRequestDispatcher(path);
                if (dispatcher == null) {
                    throw new ServletException("Không tìm thấy decorator: " + path);
                }
                dispatcher.include(req, resp);
            }
        };
    }
}
