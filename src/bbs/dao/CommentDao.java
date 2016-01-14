package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bbs.beans.Comment;
import bbs.exception.SQLRuntimeException;

public class CommentDao {
	
	public void insert(Connection connection, Comment comment){
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comment (");
			sql.append(" user_id");
			sql.append(", message_id");
			sql.append(", text");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUE (");
			sql.append("?"); //user_id
			sql.append(",?"); //m_id
			sql.append(",?"); //text
			sql.append(",CURRENT_TIMESTAMP"); //insert_d
			sql.append(",CURRENT_TIMESTAMP"); //update_d
			sql.append(");");
			
			ps = connection.prepareStatement(sql.toString());
			
			ps.setInt(1, comment.getUser_id());
			ps.setInt(2, comment.getMessage_id());
			ps.setString(3, comment.getText());
			
			ps.executeUpdate();			
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally{
			close(ps);
		}
	}
}