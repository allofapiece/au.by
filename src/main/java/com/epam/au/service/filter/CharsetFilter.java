package com.epam.au.service.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final String ENCODING_PARAMETER = "pageEncoding";
    private String encoding;

    public void init(FilterConfig fConfig) throws ServletException {
        encoding = fConfig.getInitParameter(ENCODING_PARAMETER);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);

        chain.doFilter(request, response);
    }
}