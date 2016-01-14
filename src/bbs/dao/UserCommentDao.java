package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.UserComment;
import bbs.exception.SQLRuntimeException;

public class UserCommentDao {
public List<UserComment> getUserComment(Connection connection, int num) {
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_comment ");
			sql.append("ORDER BY insert_date DESC limit " + num);
			
			ps = connection.prepareStatement(sql.toString());
			
			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
		
	}
	
	private List<UserComment> toUserCommentList(ResultSet rs)
		throws SQLException{
		
		List<UserComment> ret = new ArrayList<UserComment>();
		try{
			while(rs.next()){
				String name = rs.getString("name");
				int user_id = rs.getInt("user_id");
				int message_id = rs.getInt("message_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				
				UserComment comment = new UserComment();
				comment.setName(name);
				comment.setUser_id(user_id);
				comment.setMessage_id(message_id);
				comment.setText(text);
				comment.setInsert_date(insertDate);
				
				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
