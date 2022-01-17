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

	// ������ �޼��� �����ϴ� ���
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
