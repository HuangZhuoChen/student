package com.situ.student.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String method = httpServletRequest.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			EnhancedRequest enhancedRequest = new EnhancedRequest(httpServletRequest);
			chain.doFilter(enhancedRequest, response);
		} else if ("post".equalsIgnoreCase(method)) {
			request.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
	}
}

class EnhancedRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	public EnhancedRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	@Override
	public String getParameter(String name) {
		String parameter = request.getParameter(name);//还是乱码
		if (parameter != null && !"".equals(parameter)) {
			try {
				parameter = new String(parameter.getBytes("iso8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return parameter;
	}
}


