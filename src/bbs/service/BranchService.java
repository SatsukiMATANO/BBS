package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Branch;
import bbs.dao.BranchDao;

public class BranchService {
	public List<Branch> getBranch() {
		
		List<Branch> ret = new ArrayList<>();

		Connection connection = null;
		try {
			connection = getConnection();

			BranchDao branchDao = new BranchDao();
			List<Branch> branch = branchDao.getBranch(connection);
			ret.addAll(branch);
			
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
