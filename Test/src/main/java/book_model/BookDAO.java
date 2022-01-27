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

	// DB���� ���
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "hr";

			conn = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			// OracleDriver Ŭ������ �ش� ��ġ�� ���� ���(ojdbc6.jar ������ or ��ο�Ÿ)
			// �ذ�å : WEB-INF > lib > ojdbc6.jar ����
			e.printStackTrace();
		} catch (SQLException e) {
			// DB���� ������ ��Ȯ���� ���� ���(url or user or password�� ��Ÿ�� �ִ� ���)
			e.printStackTrace();
		}
	}

	// DB���� ����
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
	
	
	// ȸ������ ���
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
				// SQL������ �߸� �ۼ��Ǿ��� ���
				// psmt ��ü�� �߸��� �ε������� �ۼ����� ���
				// ���̺��� ���� ���
				e.printStackTrace();
			} finally {
				// try~catch���� ����� �� �ݵ�� �����ϴ� ����
				close();
			}
			return cnt;
		}
		
		// �α��� ���
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
				if (rs.next()) { // ȸ�����Ե� ������ DB�� �ִ� ���
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
