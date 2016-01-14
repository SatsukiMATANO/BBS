package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Branch;
import bbs.exception.SQLRuntimeException;

public class BranchDao {
	public List<Branch> getBranch(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM bbs.m_branch;";
			
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			List<Branch> ret = toBranchList(rs);
			
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	
	public List<Branch> toBranchList(ResultSet rs) throws SQLException{
		
		List<Branch> ret = new ArrayList<>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String branchname = rs.getString("branchname");
				
				Branch branch = new Branch();
				branch.setId(id);
				branch.setBranchname(branchname);
				
				ret.add(branch);
			}	
			return ret;
		} finally{
			close(rs);
		}
	}
}
