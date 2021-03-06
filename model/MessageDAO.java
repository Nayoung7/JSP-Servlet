package com.message.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageDAO {

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

	// 전송한 메세지 저장하는 기능
	public int sendMessage(MessageDTO message) {

		connect();

		sql = "insert into web_message values(num_seq.nextval,?,?,?,sysdate)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, message.getM_sendName());
			psmt.setString(2, message.getM_receiveEmail());
			psmt.setString(3, message.getM_content());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public ArrayList<MessageDTO> receiveMessage(String email) {

		connect();
		ArrayList<MessageDTO> message = new ArrayList<MessageDTO>();

		sql = "select m_num, m_sendName, m_content, m_sendDate from web_message where m_receiveEmail = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, email);

			rs = psmt.executeQuery();

			while (rs.next()) {
				
				int num = rs.getInt("m_num");
				String sendName = rs.getString("m_sendName");
				String content = rs.getString("m_content");
				String sendDate = rs.getString("m_sendDate");
				message.add(new MessageDTO(num, sendName, email, content, sendDate));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return message;
	}

}
