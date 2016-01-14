package bbs.exception;

import java.sql.SQLException;

public class SQLRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SQLRuntimeException(SQLException e) {
		super(e);
	}
}

