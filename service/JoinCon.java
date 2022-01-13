package com.message.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.message.model.MemberDAO;
import com.message.model.MemberDTO;

@WebServlet("/JoinCon")
public class JoinCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 한글 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		
		MemberDAO dao = new MemberDAO();
		
		int cnt = dao.memberJoin(new MemberDTO(email, pw, tel, address));
		
		if(cnt > 0) { // 회원가입 성공했을 때 > join_success.jsp로 이동(eamil 정보전달)
			request.setAttribute("success_data", email);
			
			// forward방식으로 페이지 이동
			RequestDispatcher dispatcher = request.getRequestDispatcher("join_success.jsp");
			dispatcher.forward(request,response);
		}else {
			response.setContentType("text/html; charset=utf-8");
	         PrintWriter out = response.getWriter();
	         out.print("<script>");
	         out.print("alert('회원가입 실패..!');");
	         out.print("location.href='main.jsp';");
	         out.print("</script>");

		}
		
		String loginEmail = request.getParameter("loginEmail");
		String loginPW = request.getParameter("loginPW");
	}

}
