package Game;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class gamelogic
 */
@WebServlet("/gamelogic")
public class gamelogic extends HttpServlet {

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int count = 0;
		 int x = Integer.parseInt(request.getParameter("x"));
	     int y = Integer.parseInt(request.getParameter("y"));	
        
		 HttpSession session = request.getSession();
		 Board board = (Board) session.getAttribute("board");
	        if (board == null) {
	            board = new Board();
	            session.setAttribute("board", board);
	        }
	        
	        
	
	}

}
