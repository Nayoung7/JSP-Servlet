package book_controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book_model.BookDAO;
import book_model.BookDTO;

@WebServlet("/JoinCon")
public class JoinCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
	
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nick = request.getParameter("nick");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		
		BookDAO dao = new BookDAO();
		
		int cnt = dao.insert_member(new BookDTO(id, pw, nick, email, tel));
		
		if(cnt > 0) {
			response.sendRedirect("Question4_Login.jsp");
		}else {
			response.sendRedirect("Question4_Join.jsp");
		}
		
	}

}
