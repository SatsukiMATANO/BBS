package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import bbs.beans.User;
import bbs.exception.NoRowsUpdatedRuntimeException;
import bbs.exception.SQLRuntimeException;

public class UserDao {
	
	public User getUser(Connection connection, String login_id,
			String password) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user WHERE (login_id = ? )");
			sql.append(" AND password = ?");
			sql.append(" AND stoped = 0");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, login_id);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	
	public List<User> allUser(Connection connection){
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user;";
			
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			List<User> ret = toUserList(rs);
			
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	
	public List<User> toUserList(ResultSet rs) throws SQLException{
		
		List<User> ret = new ArrayList<>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String login_id = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branch_id = rs.getInt("branch_id"); 
				int department_id = rs.getInt("department_id");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");
				int stoped = rs.getInt("stoped");
				
				User user = new User();
				user.setId(id);
				user.setLogin_id(login_id);
				user.setPassword(password);
				user.setName(name);
				user.setBranch_id(branch_id);
				user.setDepartment_id(department_id);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);
				user.setStoped(stoped);
				
				ret.add(user);
			}	
				return ret;
		} finally{
			close(rs);
		}
	}
	
	public void insert(Connection connection, User user){
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO user (");
			sql.append("login_id");
			sql.append(",password");
			sql.append(",name");
			sql.append(",branch_id");
			sql.append(",department_id");
			sql.append(",insert_date");
			sql.append(",update_date");
			sql.append(",stoped");
			sql.append(") VALUES (");
			sql.append("?"); //login_id
			sql.append(",?"); //password
			sql.append(",?"); //name
			sql.append(",?"); //bra_id
			sql.append(",?"); //dep_id
			sql.append(",CURRENT_TIMESTAMP"); //insert_date
			sql.append(",CURRENT_TIMESTAMP"); //update_date
			sql.append(",0"); //デフォルト値0＝利用可能
			sql.append(");");
			
			ps = connection.prepareStatement(sql.toString());
			
			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranch_id());
			ps.setInt(5, user.getDepartment_id());
			
			ps.executeUpdate();
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	
	//ユーザー編集用SQL
	public void upDate(Connection connection, User user){
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user SET");
			sql.append(" login_id = ?");//1
			sql.append(", name = ?");//2
			sql.append(", branch_id = ?");//3
			sql.append(", department_id = ?");//4
			if(StringUtil.isEmpty(user.getPassword()) == false){
				sql.append(", password = ?"); //5
				sql.append(", update_date = CURRENT_TIMESTAMP");
				sql.append(" WHERE");
				sql.append(" id = ?;"); //6
			}else{
				sql.append(", update_date = CURRENT_TIMESTAMP");
				sql.append(" WHERE");
				sql.append(" id = ?;"); //5
			}
						
			ps = connection.prepareStatement(sql.toString());
			
			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranch_id());
			ps.setInt(4, user.getDepartment_id());
			
			if(StringUtil.isEmpty(user.getPassword()) == false){
				ps.setString(5, user.getPassword());
				ps.setInt(6, user.getId());
			} else {
				ps.setInt(5, user.getId());
			}
			
			
			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	
	//ユーザー停止・運用のUPDATE
	public void statusUpdate(Connection connection, User user){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user SET");
			sql.append(" stoped = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?;");	
			
			ps = connection.prepareStatement(sql.toString());
		
			ps.setInt(1, user.getStoped());
			ps.setInt(2, user.getId());

			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	
	public void deleteUser(Connection connection, User user){
		PreparedStatement ps = null;
		try{
			String sql = "DELETE FROM user WHERE id = ?;";
			ps = connection.prepareStatement(sql.toString());
		
			ps.setInt(1, user.getId());

			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}		
	}
	
	public List<User> checkLoginId(Connection connection, String login_id){
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE login_id = ?;";
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, login_id);
			
			ResultSet rs = ps.executeQuery();
			List<User> ret = toUserList(rs);
			if (ret.isEmpty() == true) {
				return null;
			} else if (2 <= ret.size()) {
				return null;
			} else {
				return ret;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	

}

