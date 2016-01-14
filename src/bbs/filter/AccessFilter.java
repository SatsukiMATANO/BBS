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

@WebFilter({"/userentry", "/usersetting", "/usermanagement"})
public class AccessFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		List<String> messages = new ArrayList<String>();
    	HttpSession session = ((HttpServletRequest)request).getSession();
    	
    	User user = (User) session.getAttribute("loginUser");
    	if(user == null ){
    		((HttpServletResponse)response).sendRedirect("./login");
    		return;
    	}
		if(user.getDepartment_id() != 1){
			messages.add("ユーザー管理画面のアクセスが許可されていません");
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("./").forward(request, response);
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
