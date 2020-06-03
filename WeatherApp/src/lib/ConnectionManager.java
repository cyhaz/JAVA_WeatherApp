package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 중복된 데이터베이스 연결 및 해제코드의 재사용성을 높이기 위해 별도의 클래스로 정의
public class ConnectionManager {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	
	// 데이터베이스 접속
	public Connection getConnection() {
		Connection con=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	// 데이터베이스 자원 해제 
	// 윈도우 창 닫을 때 호출 -> con 반납
	public void closeDB(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// DML(insert, update, delete)만 수행한 경우 -> pstmt만 반납 
	public void closeDB(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// select문인 경우 -> pstmt, rs 반납
	public void closeDB(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
