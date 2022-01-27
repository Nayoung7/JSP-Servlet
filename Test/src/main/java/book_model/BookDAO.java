package book_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class BookDAO {
	
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	String sql;
	int cnt;

	// DB연결 기능
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "hr";

			conn = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			// OracleDriver 클래스가 해당 위치에 없는 경우(ojdbc6.jar 미포함 or 경로오타)
			// 해결책 : WEB-INF > lib > ojdbc6.jar 저장
			e.printStackTrace();
		} catch (SQLException e) {
			// DB연결 정보가 정확하지 않을 경우(url or user or password에 오타가 있는 경우)
			e.printStackTrace();
		}
	}

	// DB연결 종료
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	// 회원가입 기능
		public int insert_member(BookDTO member) {

			connect();

			sql = "insert into book_member values(?,?,?,?,?)";
			

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, member.getId());
				psmt.setString(2, member.getPw());
				psmt.setString(3, member.getNick());
				psmt.setString(4, member.getEmail());
				psmt.setString(5, member.getTel());

				cnt = psmt.executeUpdate();

			} catch (SQLException e) {
				// SQL문장이 잘못 작성되었을 경우
				// psmt 객체로 잘못된 인덱스값을 작성했을 경우
				// 테이블이 없는 경우
				e.printStackTrace();
			} finally {
				// try~catch문이 실행된 후 반드시 실행하는 구문
				close();
			}
			return cnt;
		}
		
		// 로그인 기능
		public BookDTO login_member(String id, String pw) {

			connect();

			BookDTO member = null;
			String nick = "";

			sql = "select nick from book_member where id = ? and pw = ?";

			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, id);
				psmt.setString(2, pw);

				rs = psmt.executeQuery();
				if (rs.next()) { // 회원가입된 정보가 DB에 있는 경우
					nick = rs.getString("nick");
					member = new BookDTO(id, pw, nick, null, null);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
			return member;
		}
}
