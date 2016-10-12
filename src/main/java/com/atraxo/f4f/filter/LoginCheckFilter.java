package com.atraxo.f4f.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.model.user.User;

public class LoginCheckFilter extends AbstractFilter implements Filter {
	private List<String> allowedURIs;

	private static final Logger LOGGER = Logger.getLogger(LoginCheckFilter.class);

	private UserFacade userFacade;


	public UserFacade getUserFacade() {
		if ( userFacade == null){
			userFacade = new UserFacade();
		}
		return userFacade;
	}



	public void init(FilterConfig filterConfig) throws ServletException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START init()");
		}

		if(allowedURIs == null){
			allowedURIs = new ArrayList<>();
			allowedURIs.add(filterConfig.getInitParameter("loginActionURI"));

			allowedURIs.add("/Food4Fuel/");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/theme.css.xhtml");

			allowedURIs.add("/Food4Fuel/javax.faces.resource/css/sentinel-layout.css.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/css/font-icon-layout.css.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/css/core-layout.css.xhtml");

			allowedURIs.add("/Food4Fuel/javax.faces.resource/jquery/jquery.js.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/jquery/query-plugins.js.xhtml");

			allowedURIs.add("/Food4Fuel/javax.faces.resource/primefaces.css.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/primefaces.js.xhtml");

			allowedURIs.add("/Food4Fuel/javax.faces.resource/images/LOGO.svg.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/images/login-back.svg.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/images/access-denied.svg.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/images/error-face.svg.xhtml");

			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/sentinel.woff.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/sentinel.ttf.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/titilliumweb-semibolditalic-webfont.woff.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/titilliumweb-semibolditalic-webfont.ttf.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/titilliumweb-regular-webfont.woff.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/titilliumweb-regular-webfont.ttf.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/fonts/fontawesome-webfont.woff2.xhtml");

			allowedURIs.add("/Food4Fuel/javax.faces.resource/fa/font-awesome.css.xhtml");
			allowedURIs.add("/Food4Fuel/javax.faces.resource/js/layout.js.xhtml");

			// write all the XHTML files here
			allowedURIs.add("/Food4Fuel/pages/login.xhtml");
			allowedURIs.add("/Food4Fuel/pages/404.xhtml");
			allowedURIs.add("/Food4Fuel/pages/error.xhtml");
			allowedURIs.add("/Food4Fuel/pages/access-denied.xhtml");
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END init()");
		}

	}



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START doFilter()");
		}

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			doLogin(response, req);
			return;
		}

		User user = (User) session.getAttribute("user");
		if (user == null && !allowedURIs.contains(req.getRequestURI())) {
			accessDenied(request, response, req);
			return;
		}

		chain.doFilter(request, response);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END doFilter()");
		}
	}



	public void destroy() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START destroy()");
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END destroy()");
		}

	}

}
