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

import bbs.beans.Category;
import bbs.beans.Comment;
import bbs.beans.Post;
import bbs.beans.User;
import bbs.beans.UserComment;
import bbs.beans.UserPost;
import bbs.service.CommentService;
import bbs.service.PostService;

@WebServlet(urlPatterns = {"/index.jsp"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<UserComment> comment = new CommentService().getComment();
		List<Category> categorys = new PostService().getCategory();	
		
		String start_date = "2016-01-01"; //デフォルト
		String end_date = "2100-12-31";
		String select_cate = "";
		
		if(StringUtils.isEmpty(request.getParameter("start_date")) == false ){
			start_date = request.getParameter("start_date");
			request.setAttribute("start_date", start_date);
		}
		if(StringUtils.isEmpty(request.getParameter("end_date")) == false){
			end_date = request.getParameter("end_date");
			request.setAttribute("end_date", end_date);
		}
		if(StringUtils.isEmpty(request.getParameter("s_category")) == false){
			select_cate = request.getParameter("s_category");
			request.setAttribute("select", select_cate);
		}
		List<UserPost> post =
				new PostService().getDatePost(start_date, end_date, select_cate);
		
		User user = (User) session.getAttribute("loginUser");
		request.setAttribute("loginUser", user);
		request.setAttribute("posts", post);
		request.setAttribute("categorys", categorys);
		request.setAttribute("comments", comment);
		request.getRequestDispatcher("./home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		if(request.getParameter("text") != null){
			List<String> messages = new ArrayList<String>();
			HttpSession session = request.getSession();
			Comment entryComment = getEntryComment(request);
			request.setAttribute("entryComment", entryComment);
		
			if(isValid(entryComment, messages) == true){
			
				User user = (User) session.getAttribute("loginUser");
				
				Comment comment = new Comment();
				comment.setText(request.getParameter("text"));
				comment.setUser_id(user.getId());
				comment.setMessage_id(Integer.parseInt(request.getParameter("message_id")));
				
				new CommentService().register(comment);
				new CommentService().update(comment);
				
				response.sendRedirect("./");
			}else {
				List<UserPost> post = new PostService().getPost();
				List<UserComment> comment = new CommentService().getComment();
				
				request.setAttribute("posts", post);
				request.setAttribute("comments", comment);
				request.setAttribute("entryComment", entryComment);
				session.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("home.jsp").forward(request, response);
			
			}
		}
		
		if(request.getParameter("delete") != null){
			Post post = new Post();
			String delete = request.getParameter("delete");
			if(delete.equals("delete")){
				post.setId(Integer.parseInt(request.getParameter("id")));
				new PostService().deletePost(post);
				response.sendRedirect("./");
			}
		}
	}
	
	private Comment getEntryComment(HttpServletRequest request) 
			throws IOException, ServletException{
		
		Comment entryComment = new Comment();
		
		entryComment.setText(request.getParameter("text"));
		return entryComment;
	}
	
	private boolean isValid(Comment entryComment, List<String> messages) {
		String text = entryComment.getText();
		
		if(StringUtils.isEmpty(text) == true){
			messages.add("コメントが入力されていません。");
		}
		if (text.length() > 500){
			messages.add("コメントは500文字以下で入力してください。");
		}
		
		if(messages.size() == 0){
			return true;
		} else {
			return false;
		}
	}
}