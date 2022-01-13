package com.message.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public int messageSend(MessageDTO message) {
		
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
		}
		
		close();
		
		return cnt;
	}

}
