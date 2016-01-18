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

import bbs.beans.Branch;
import bbs.beans.Department;
import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.DepartmentService;
import bbs.service.UserService;

@WebServlet(urlPatterns={"/usersetting"})
public class UserSettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String settingId = request.getParameter("user_id");
		try{
			int setUser = Integer.parseInt(settingId);
			User editUser = new UserService().getUser(setUser);
			if(editUser == null){
				response.sendRedirect("./usermanagement");
				return;
			}
			
			request.setAttribute("editUser", editUser);
			
			List<Branch> branchs = new BranchService().getBranch();
	    	List<Department> departments = new DepartmentService().getDepartment();
	    	
	    	request.setAttribute("branchs", branchs);
	    	request.setAttribute("departments", departments);
			request.getRequestDispatcher("usersetting.jsp").forward(request, response);
			
		} catch (NumberFormatException e){
			response.sendRedirect("./usermanagement");
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		
		User editUser = getEditUser(request);
		request.setAttribute("editUser", editUser);

		if (isValid(editUser, messages) == true) {

			new UserService().upDate(editUser);
			response.sendRedirect("./usermanagement"); //管理画面へ戻る
			
		} else {
			
			List<Branch> branchs = new BranchService().getBranch();
	    	List<Department> departments = new DepartmentService().getDepartment();

			session.setAttribute("errorMessages", messages);
			request.setAttribute("branchs", branchs);
	    	request.setAttribute("departments", departments);
	    	request.getRequestDispatcher("usersetting.jsp").forward(request, response);
		}
	}

    	private User getEditUser(HttpServletRequest request)
		throws IOException, ServletException{

		User editUser =  new User();
		
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		editUser.setLogin_id(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setPasswordcheck(request.getParameter("passwordcheck"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setDepartment_id(Integer.parseInt(request.getParameter("department_id")));
		return editUser;
	}
	
	private boolean isValid(User editUser, List<String> messages) {
		String name = editUser.getName();
		String login_id = editUser.getLogin_id();
		String password = editUser.getPassword();
		String passwordcheck = editUser.getPasswordcheck();
		
		int setId = editUser.getId();
		User check = new UserService().getUser(setId);
		List<User> checkLoginId = new UserService().checkLoginId(login_id);
		
		
		//nullチェック
		if (StringUtils.isEmpty(name) == true ) {
			messages.add("社員氏名を入力してください。");
		}
		if (name.length() > 10){
			messages.add("社員氏名は10文字以内で入力してください。");
		}
		if (StringUtils.isEmpty(login_id) == true) {
			messages.add("ログインIDを入力してください。");
		} else {
			if (!login_id.matches("^[a-zA-Z0-9]+$")){
				messages.add("ログインIDに半角英数字以外が含まれています。");
			}
			if (login_id.length() > 20){
				messages.add("ログインIDは20文字以内で入力してください。");
			}
			if (login_id.length() < 6){
				messages.add("ログインIDは6文字以上で入力してください。");
			}
			
			System.out.println("check" + check.getLogin_id());
			System.out.println("edit" + login_id);
			if(!check.getLogin_id().equals(login_id)){
				if (checkLoginId != null){
					messages.add("すでに登録されているログインIDを入力しています。"
							+ "異なるログインIDを入力してください。");
				}
			}
		}
		if (StringUtils.isEmpty(password) == false) {
			if (password.length() > 255){
				messages.add("パスワードは255文字以内で入力してください。");
			}
			if (password.length() < 6){
				messages.add("パスワードは6文字以上で入力してください。");
			}
			if (!password.matches("(?!^[^0-9]*$)(?!^[^A-Za-z]*$)"
					+ "(?!^[^(\\!-\\/|:-@|\\[-`|{-~]*$)^([\\!-~]+)$" )){
				messages.add("パスワードは半角英数字・記号をそれぞれ必ず含んでください。");
			}
			
			if(StringUtils.isEmpty(passwordcheck) == true){
				messages.add("確認用のパスワードを入力してください。");
			}
			
			if (StringUtils.isEmpty(passwordcheck) == false){
				if (!passwordcheck.equals(password)){
					messages.add("確認用のパスワードがパスワードと異なります。再度入力してください。");
				}
			}
		}
		

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
