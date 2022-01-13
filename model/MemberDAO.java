package com.message.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	private int cnt;
	private String sql;

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
	public int memberJoin(MemberDTO member) {

		connect();

		sql = "insert into web_member values(?,?,?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getM_email());
			psmt.setString(2, member.getM_pw());
			psmt.setString(3, member.getM_tel());
			psmt.setString(4, member.getM_address());

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
	public MemberDTO memberLogin(String email, String pw) {

		connect();

		MemberDTO member = null;
		String m_tel = "";
		String m_address = "";

		sql = "select m_tel, m_address from web_member where m_email = ? and m_pw = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, email);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) { // ȸ�����Ե� ������ DB�� �ִ� ���
				m_tel = rs.getString("m_tel");
				m_address = rs.getString("m_address");
				member = new MemberDTO(email, null, m_tel, m_address);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return member;
	}

	// ȸ������ ����
	public int memberUpdate(MemberDTO member) {

		connect();

		sql = "update web_member set m_pw = ?, m_tel = ?, m_address = ? where m_email = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getM_pw());
			psmt.setString(2, member.getM_tel());
			psmt.setString(3, member.getM_address());
			psmt.setString(4, member.getM_email());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
}
