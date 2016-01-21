package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bbs.beans.Comment;
import bbs.beans.Post;
import bbs.exception.NoRowsUpdatedRuntimeException;
import bbs.exception.SQLRuntimeException;

public class PostDao {
	
	public void insert(Connection connection, Post post){
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO postmessage (");
			sql.append(" user_id");
			sql.append(", title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUE (");
			sql.append("?"); //user_id
			sql.append(",?"); //title
			sql.append(",?"); //text
			sql.append(",?"); //category
			sql.append(",CURRENT_TIMESTAMP"); //insert_d
			sql.append(",CURRENT_TIMESTAMP"); //update_d
			sql.append(");");
			
			ps = connection.prepareStatement(sql.toString());
			
			ps.setInt(1, post.getUser_id());
			ps.setString(2, post.getTitle());
			ps.setString(3, post.getText());
			ps.setString(4, post.getCategory());
			
			ps.executeUpdate();
		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally{
			close(ps);
		}
		
	}

	public void deletePost(Connection connection, Post post){
		PreparedStatement ps = null;
		try{
			String sql = "DELETE FROM postmessage WHERE id = ?;";
			ps = connection.prepareStatement(sql.toString());
		
			ps.setInt(1, post.getId());

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
	
	public void update(Connection connection, Comment comment){
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE postmessage SET ");
			sql.append(" insert_date = insert_date ,");
			sql.append(" update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?;");
			
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, comment.getMessage_id());

			ps.executeUpdate();

		} catch(SQLException e){
			throw new SQLRuntimeException(e);
		} finally{
			close(ps);
		}
	}
}
