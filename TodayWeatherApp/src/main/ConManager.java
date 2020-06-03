package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConManager {
	static ConManager conManagerInstance;
	Properties properties;

	String url;
	String user;
	String password;

	private ConManager() {
		properties = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream("/database/oracleInfo.db");
		try {
			properties.load(inputStream);
			url = (String) properties.get("oracle_url");
			user = (String) properties.get("oracle_user");
			password = (String) properties.get("oracle_password");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 데이터베이스 접속 및 커넥션 반환
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	// conManagerInstance 반환
	public static ConManager getInstance() {
		if (conManagerInstance == null)
			conManagerInstance = new ConManager();
		return conManagerInstance;
	}

	/* -------------------- closeDB -------------------- */
	public void closeDB(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDB(PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDB(PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* -------------------- closeStream -------------------- */
	public void closeStream(FileInputStream fis) {
		try {
			if(fis!=null) fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeStream(FileOutputStream fos) {
		try {
			if(fos!=null) fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}