package protocolData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;

public class DBConnect {
	
	public static Connection makeConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";
		Statement stmt = null;
		// 호스트, 포트 주소를 문자열 변수 url로 묶는다
		Connection con = null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
//			System.out.println("Oracle client 데이터 베이스 연결 대기중..");
			con = DriverManager.getConnection(url, "omok123", "omok123");
			
//			System.out.println("Oracle client 데이터 베이스 연결 성공.");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버를 찾지 못했습니다.");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 실패");
		}
		return con;
	}
}
