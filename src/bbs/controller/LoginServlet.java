package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		
		LoginService loginService = new LoginService();
		User user = loginService.login(login_id, password);
		
		HttpSession session = request.getSession();
		if(user != null){
			
			session.setAttribute("loginUser", user);
			response.sendRedirect("./");
		} else {
			
			List<String> messages = new ArrayList<>();
			messages.add("ログインに失敗しました。\n再度ログインIDとパスワードを入力してください。");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("login.jsp");
		}
	}

}
