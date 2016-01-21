package bbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;


@WebFilter({"/*"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		StringBuffer requestUrl = ((HttpServletRequest)request).getRequestURL();
		String requestUrlStr = requestUrl.toString();
		String[] urlParts = requestUrlStr.split("/");
		String method = urlParts[urlParts.length -1];
		
		//loginサーブレットはフィルターを除外する
		if(!method.equals("login") && !method.equals("BBS.css")){
			HttpSession session = ((HttpServletRequest)request).getSession();
	    	User loginUser = (User) session.getAttribute("loginUser");

	    	if(loginUser == null){
	    		((HttpServletResponse)response).sendRedirect("./login");	    		
	    		return;
	    	}
		}
		    	
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

}
