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

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.permission.RoleRight;
import com.atraxo.f4f.model.user.User;


public class PagePermissionFilter extends AbstractFilter implements Filter {
	private static final Logger LOGGER = Logger.getLogger(PagePermissionFilter.class);
	
	@Override
	public void destroy() {
		//destroy
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START doFilter()");
		}
		
		List<String> accessPages = new ArrayList<>();
		
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession(true).getAttribute("user");
		
		String requestPage = req.getRequestURI().substring(4);
		
		if( user == null ){
			chain.doFilter(request, response);
			return;
		}
		
		for( RoleRight rr : user.getRole().getRights() ){
			String rightPage = rr.getRight().getCode().getWebPage();

			//add individual right pages
			if( rightPage != null ){
				accessPages.add(rightPage);

				//add request page if is contained in a group of pages for 1 right
				if( requestPage.contains(rightPage) ){
					accessPages.add(requestPage);
				}
			}
		}

		if( !accessPages.contains(requestPage) ){
			accessDenied(request, response, req);
			return;
		}

		chain.doFilter(request, response);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END doFilter()");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START init()");
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END init()");
		}
	}
}
