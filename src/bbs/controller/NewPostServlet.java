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

import org.apache.commons.lang.StringUtils;

import bbs.beans.Post;
import bbs.beans.User;
import bbs.service.PostService;

@WebServlet(urlPatterns = {"/newpost"})
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    		request.getRequestDispatcher("newpost.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		
		Post entryPost = getEntryPost(request);
		request.setAttribute("entryPost", entryPost);
		
		if(isValid(entryPost, messages) == true){
			
			User user = (User) session.getAttribute("loginUser");
			
			Post post = new Post();
			post.setTitle(request.getParameter("title"));
			post.setCategory(request.getParameter("category"));
			post.setText(request.getParameter("text"));
			post.setUser_id(user.getId());
			
			new PostService().register(post);
			
			response.sendRedirect("./");
		}else {			
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("newpost.jsp").forward(request, response);
		}
		
	}

	private Post getEntryPost(HttpServletRequest request) 
			throws IOException, ServletException{
		
		Post entryPost = new Post();
		
		entryPost.setTitle(request.getParameter("title"));
		entryPost.setCategory(request.getParameter("category"));
		entryPost.setText(request.getParameter("text"));
		return entryPost;
	}


	private boolean isValid(Post entryPost, List<String> messages) {
		String title = entryPost.getTitle();
		String category = entryPost.getCategory();
		String text = entryPost.getText();
		
		if(StringUtils.isEmpty(title) == true){
			messages.add("件名を入力してください");
		}
		if (title.length() > 50){
			messages.add("件名は50文字以下で入力してください");
		}
		if(StringUtils.isEmpty(category) == true){
			messages.add("カテゴリーを入力してください");
		}
		if (category.length() > 10){
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if(StringUtils.isEmpty(text) == true){
			messages.add("本文を入力してください");
		}
		if (text.length() > 1000){
			messages.add("本文は1000文字以下で入力してください");
		}
		
		if(messages.size() == 0){
			return true;
		} else {
			return false;
		}
	}

}
