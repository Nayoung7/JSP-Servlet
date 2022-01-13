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
			if (rs.next()) { // 회원가입된 정보가 DB에 있는 경우
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

	// 회원정보 수정
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
