package com.atraxo.f4f.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.atraxo.f4f.util.WebPage;

/**
 * @author vhojda
 *
 */
public class AbstractFilter {

	private static final Logger LOGGER = Logger.getLogger(AbstractFilter.class);
	
	public AbstractFilter() {
		super();
	}

	/**
	 * @param response
	 * @param req
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doLogin(ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START doLogin()");
		}
		
		//with redirect
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(req.getContextPath() + WebPage.PAGE_LOGIN);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END doLogin()");
		}

	}
	
	/**
	 * @param request
	 * @param response
	 * @param req
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START accessDenied()");
		}

		RequestDispatcher rd = req.getRequestDispatcher(WebPage.PAGE_ACCESS_DENIED);
		rd.forward(request, response);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END accessDenied()");
		}

	}
}
