package com.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
	private int m_num;
	private String m_sendName;
	private String m_receiveEmail;
	private String m_content;
	private String m_sendDate;
}
