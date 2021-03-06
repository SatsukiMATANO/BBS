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

@WebServlet(urlPatterns={"/userentry"})
public class UserEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		List<Branch> branchs = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		
		request.setAttribute("branchs", branchs);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("userentry.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=utf-8");

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		
		User entryUser = getEntryUser(request);
		request.setAttribute("entryUser", entryUser);

		if (isValid(request, entryUser, messages) == true) {
			new UserService().register(entryUser);
			response.sendRedirect("./usermanagement"); //管理画面へ戻る
			
		} else {
			
			List<Branch> branchs = new BranchService().getBranch();
			List<Department> departments = new DepartmentService().getDepartment();

			session.setAttribute("errorMessages", messages);
			request.setAttribute("branchs", branchs);
			request.setAttribute("departments", departments);
			request.getRequestDispatcher("userentry.jsp").forward(request, response);
		}
	}

	private User getEntryUser(HttpServletRequest request)
		throws IOException, ServletException{

		User entryUser =  new User();
	
		entryUser.setLogin_id(request.getParameter("login_id"));
		entryUser.setPassword(request.getParameter("password"));
		entryUser.setPasswordcheck(request.getParameter("passwordcheck"));
		entryUser.setName(request.getParameter("name"));
		String branch_id = request.getParameter("branch_id");
		String department_id = request.getParameter("department_id");
		if(branch_id != null && department_id != null){
			entryUser.setBranch_id(Integer.parseInt(branch_id));
			entryUser.setDepartment_id(Integer.parseInt(department_id));
		}
		return entryUser;
	}
	
	private boolean isValid(HttpServletRequest req, User entryUser, List<String> messages) {
		String name = entryUser.getName();
		String login_id = entryUser.getLogin_id();
		String password = entryUser.getPassword();
		String passwordcheck = entryUser.getPasswordcheck();
		String branch_id = req.getParameter("branch_id");
		String department_id = req.getParameter("department_id");
		
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
			if (checkLoginId != null){
				messages.add("ログインIDが使用されています。");
			}

		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください。");
		} else {
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
		}
		if (StringUtils.isEmpty(passwordcheck) == true){
			messages.add("パスワードの確認を入力してください。");
		} else if (!passwordcheck.equals(password)){
			messages.add("パスワードの確認がパスワードと異なります。再度入力してください。");
		}
		if (branch_id.equals("99")){
			messages.add("支店を選択してください。");
		}
		if (department_id == null){
			messages.add("部署・役職を選択してください。");
		} else if (department_id.equals("99")){
			messages.add("部署・役職を選択してください。");
		}
			

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
