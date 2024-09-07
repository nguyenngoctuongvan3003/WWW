package session;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServletPractice  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// static: phụ thuộc vào class chứ k phải đối tượng
	//final chỉ cho gán giá chỉ 1 lần duy nhất
	private final String userID="admin";
	private final String pasword="password";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//lấy giá trị từ yêu http post
		String user=req.getParameter("userName");
		String pwd=req.getParameter("password");
		
		if(userID.equals(user)&&pasword.equals(pwd)) {
			HttpSession session=req.getSession();
			//luu userID vao session
			session.setAttribute("userIdSession", userID);
			//thoi gian het han session la 30 phut, het han la dang nhap lai
			session.setMaxInactiveInterval(30*60);
			
			
			Cookie cookieUserName= new Cookie("userIdCookie",userID);
			cookieUserName.setMaxAge(30*60);
			resp.addCookie(cookieUserName);
			
			resp.sendRedirect("loginSucess.jsp");
		}else {
			//tao Rd để chuyển tiếp
			 RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("login.html");
			 dispatcher.forward(req, resp);
		}
		
		
		
	}


}
