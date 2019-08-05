package com.igor.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
	
	public SessionFilter() {
		
	}
	
	@Override
    public void init(FilterConfig fConfig) throws ServletException {
		
	}
	
	//Check if there is no session - return to the Login page
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
//		String url = ((HttpServletRequest)request).getRequestURI();////
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		// if there is no session - return to the Login HTML form if the user name or password are incorrect
		if (session == null){ ////.getAttribute("facade")////|| url.lastIndexOf("login.html") == -1////
			((HttpServletResponse)response).sendRedirect("login.html");
    	} else{
    		// else - pass the request along the filter chain
    		chain.doFilter(request, response);
    	}
		
	}
	
	@Override
	public void destroy() {
		
	}

}
