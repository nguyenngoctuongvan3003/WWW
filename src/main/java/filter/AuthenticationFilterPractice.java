package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationFilterPractice extends HttpFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// cho phep truy cap vao cac thong tin va tai nguyen cua ung dung web
	private ServletContext context;
	
	public AuthenticationFilterPractice() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	// goi khi filter bi huy, trong truong hop nay thì chưa làm gì cả
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
//		super.destroy();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// chuyển đổi thành đối tượng http để có thể sử dụng các phương thức http cụ thể
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		
		//uri bao gồm url protocol://host:port/path?query#fragment (cụ thể) và urn:namespace:resource (không cụ thể)
		String uri= req.getRequestURI();
		
		//ghi log uri
		this.context.log("Resquest Resource:"+uri);
		
		//lấy session nếu có, không tạo mới nếu không có
		HttpSession session= req.getSession(false);
		
		//ktra session có tồn tại hay k, uri không phải html hoặc đăng nhập thì được chuyển hướng đến login.html
		if(session==null && !(uri.endsWith("html")|| uri.endsWith("login"))) {
			this.context.log("1111111111 Unauthorized access request");
			res.sendRedirect("login.html");
		}else {
			//nếu k thỏa if thì truyền yêu cầu và phản hồi qua các Filter tiếp theo hoặc Servlet
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.context=filterConfig.getServletContext();
		this.context.log("AuthenticatiionFilter duoc khoi tao");
	}
}
