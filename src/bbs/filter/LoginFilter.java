package bbs.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import bbs.service.UserService;


@WebFilter({"/*"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		List<String> messages = new ArrayList<String>();
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		StringBuffer requestUrl = ((HttpServletRequest)request).getRequestURL();
		String requestUrlStr = requestUrl.toString();
		String[] urlParts = requestUrlStr.split("/");
		String method = urlParts[urlParts.length -1];
		
		//loginサーブレットはフィルターを除外する
		if(!method.equals("login") && !method.equals("BBS.css")){
			User loginUser = (User) session.getAttribute("loginUser");
			
			
			if(loginUser == null){
				((HttpServletResponse)response).sendRedirect("./login");
				return;
			}
			User user = new UserService().getUser(loginUser.getId());
			if(user.getStoped() == 1){
				messages.add("アカウントが停止しています。");
				session.setAttribute("errorMessages", messages);
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
