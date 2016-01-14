package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Category;
import bbs.beans.UserPost;
import bbs.exception.SQLRuntimeException;

public class UserPostDao {

	public List<UserPost> getUserPost(Connection connection, int num) {
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_post ");
			sql.append("ORDER BY insert_date DESC limit " + num);
			
			ps = connection.prepareStatement(sql.toString());
			
			ResultSet rs = ps.executeQuery();
			List<UserPost> ret = toUserPostList(rs);
			return ret;
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
		
	}
	
	private List<UserPost> toUserPostList(ResultSet rs)
		throws SQLException{
		
		List<UserPost> ret = new ArrayList<UserPost>();
		try{
			while(rs.next()){
				int message_id = rs.getInt("message_id");
				int branch_id = rs.getInt("branch_id");
				int department_id = rs.getInt("department_id");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				
				UserPost post = new UserPost();
				post.setMessage_id(message_id);
				post.setBranch_id(branch_id);
				post.setDepartment_id(department_id);
				post.setName(name);
				post.setTitle(title);
				post.setText(text);
				post.setCategory(category);
				post.setInsert_date(insertDate);
				
				ret.add(post);
			}
			return ret;
		} finally {
			close(rs);
			
		}
	}
	
	public List<UserPost> getDatePost(Connection connection, int num, 
			String start_date, String end_date, String category) {
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_post ");
			sql.append("WHERE");
			sql.append(" insert_date >= ?");
			sql.append(" AND");
			sql.append(" insert_date <= ?");
			if(category != ""){
				sql.append(" AND category = ?");
			}
			sql.append(" ORDER BY insert_date DESC limit " + num);
			
			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, start_date);
			ps.setString(2, end_date);
			if(category != ""){
				ps.setString(3, category);
			}
			
			ResultSet rs = ps.executeQuery();
			List<UserPost> ret = toUserPostList(rs);
			return ret;
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Category> getCategory(Connection connection) {
		
		PreparedStatement ps = null;
		try{
			String sql = "SELECT distinct category FROM user_post";
			
			ps = connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			List<Category> ret = toCategoryList(rs);
			return ret;
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
		
	}

	private List<Category> toCategoryList(ResultSet rs)
		throws SQLException{
		
		List<Category> ret = new ArrayList<Category>();
		try{
			while(rs.next()){
				String category = rs.getString("category");
				
				Category categorySet = new Category();
				categorySet.setCategory(category);
				
				ret.add(categorySet);
			}
			return ret;
		} finally {
			close(rs);
			
		}
	}

}
