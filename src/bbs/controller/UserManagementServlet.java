package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Branch;
import bbs.beans.Department;
import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.DepartmentService;
import bbs.service.UserService;

@WebServlet(urlPatterns={"/usermanagement"})
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> users = new UserService().allUser();
		List<Branch> branchs = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		request.setAttribute("branchs", branchs);
		request.setAttribute("departments", departments);
		request.setAttribute("users", users);
		request.getRequestDispatcher("usermanagement.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User editUser = new User();

		if(request.getParameter("stoped") != null){
			int stoped = Integer.parseInt(request.getParameter("stoped"));
			if(stoped == 0){		
				editUser.setId(Integer.parseInt(request.getParameter("id")));
				editUser.setStoped(0);
				new UserService().statusUpdate(editUser);
				response.sendRedirect("./usermanagement");
			}
			if(stoped == 1){
				editUser.setId(Integer.parseInt(request.getParameter("id")));
				editUser.setStoped(1);
				new UserService().statusUpdate(editUser);
				response.sendRedirect("./usermanagement");
			}
		}
		
		if(request.getParameter("delete") != null){
			String delete = request.getParameter("delete");
			if(delete.equals("delete")){
				editUser.setId(Integer.parseInt(request.getParameter("id")));
				new UserService().deleteUser(editUser);
				response.sendRedirect("./usermanagement");
			}
		}
	}

}
