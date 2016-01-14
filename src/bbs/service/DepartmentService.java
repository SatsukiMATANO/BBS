package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Department;
import bbs.dao.DepartmentDao;

public class DepartmentService {
	public List<Department> getDepartment() {
		
		List<Department> ret = new ArrayList<>();

		Connection connection = null;
		try {
			connection = getConnection();

			DepartmentDao departmentDao = new DepartmentDao();
			List<Department> department = departmentDao.getDepartment(connection);
			ret.addAll(department);
			
			commit(connection);
			
			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
