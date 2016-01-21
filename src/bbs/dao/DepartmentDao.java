package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Department;
import bbs.exception.SQLRuntimeException;

public class DepartmentDao {
		public List<Department> getDepartment(Connection connection) {

			PreparedStatement ps = null;
			try {
				String sql = "SELECT * FROM bbs.m_department;";
				
				ps = connection.prepareStatement(sql);

				ResultSet rs = ps.executeQuery(sql);
				List<Department> ret = toDepartmentList(rs);
				
				return ret;
			} catch (SQLException e) {
				throw new SQLRuntimeException(e);
			} finally {
				close(ps);
			}
		}
		
		public List<Department> toDepartmentList(ResultSet rs) throws SQLException{
			
			List<Department> ret = new ArrayList<>();
			try{
				while (rs.next()){
					int id = rs.getInt("id");
					int branch_id = rs.getInt("branch_id");
					String departmentname = rs.getString("departmentname");
					
					Department department = new Department();
					department.setId(id);
					department.setBranch_id(branch_id);
					department.setDepartmentname(departmentname);
					
					ret.add(department);
				}
				return ret;
			} finally{
				close(rs);
			}
		}

}
