package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.Category;
import bbs.beans.Post;
import bbs.beans.UserPost;
import bbs.dao.PostDao;
import bbs.dao.UserPostDao;

public class PostService {
	
	public void register(Post post){
		
		Connection connection = null;
		try{
			connection = getConnection();
			
			PostDao postDao = new PostDao();
			postDao.insert(connection, post);
			
			commit(connection);
		} catch(RuntimeException e) {
			rollback(connection);
			throw e;
		} catch(Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
	
	private static final int LIMIT_NUM = 1000;
	
	public List<UserPost> getPost(){
		
		Connection connection = null;
		try{
			connection = getConnection();
			
			UserPostDao postDao = new UserPostDao();
			List<UserPost> ret = postDao.getUserPost(connection, LIMIT_NUM);
			
			commit(connection);
			return ret;
		} catch(RuntimeException e) {
			rollback(connection);
			throw e;
		} catch(Error e){
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
		
	}
	
	public void deletePost(Post post){
		Connection connection = null;
		try {
			connection = getConnection();
			
			PostDao postDao = new PostDao();
			postDao.deletePost(connection, post);

			commit(connection);
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
	
	public List<UserPost> getDatePost(String start_date,String end_date,
			String category){
		
		Connection connection = null;
		try{
			connection = getConnection();
			
			UserPostDao postDao = new UserPostDao();
			List<UserPost> ret 
				= postDao.getDatePost(connection, LIMIT_NUM,
						start_date, end_date, category);
			
			commit(connection);
			return ret;
		} catch(RuntimeException e) {
			rollback(connection);
			throw e;
		} catch(Error e){
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
	
	public List<Category> getCategory(){
		
		Connection connection = null;
		try{
			connection = getConnection();
			
			UserPostDao postDao = new UserPostDao();
			List<Category> ret = postDao.getCategory(connection);
			
			commit(connection);
			return ret;
		} catch(RuntimeException e) {
			rollback(connection);
			throw e;
		} catch(Error e){
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}		
	}

}
