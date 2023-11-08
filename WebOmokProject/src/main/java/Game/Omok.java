package Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import Rank.LogVO;
import Rank.RankDAO;


@WebServlet("/omok/*")
public class Omok extends HttpServlet {
	// 나중에 ajax 양방향 통신을 위해 json.simpple-1.1.1.jar 라이브러리 가져올 것
	
	RankDAO rankDao;
	
	@Override
	@SuppressWarnings("unchecked")//랭킹 가져오기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   int WinCount = Integer.parseInt(request.getParameter("Win"));
	   int LoseCount = Integer.parseInt(request.getParameter("Lose"));
	 
	   
	   if(rankDao == null)
		   rankDao = new RankDAO();
	   try {
		   LogVO logvo = new LogVO();
		   logvo.setWinner_no(WinCount);
		   logvo.setLoser_no(LoseCount);
		   rankDao.addLog(logvo);
		   
	   } catch (Exception e) {
		   
	   }
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
		//1. 내가 돌을 가져왓을때
		if(command.equals("my.do")) {
			myStoneSet(request, response);
		} //2. 상대가 돌을 놓았을때
		else if (command.equals("opp.do")) {
			oppStoneSet(request,response);
		}
		
		
	}
	
	
	//내가 돌을 놓았을 때
	private void myStoneSet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 // JSP로 부터 x값을 가져옴. JSP에 <input type="text" name="id"> 있었다면
		 // 서블릿에서 request.getParameter("y_input") 와 같은 방식으로, 클라이언트가 입력한 ID가 뭐였는지 알 수 있다
		 int x = Integer.parseInt(request.getParameter("x_row")); 
		 int y = Integer.parseInt(request.getParameter("y_col")); 
		 
		 // 
	     String WhoIsPlayer = request.getParameter("Gameplayer");
	     
	     // 여러 요청에 걸쳐 게임 상태를 서버에 저장하고 검색하기 위해 HttpSession을 사용 (상태유지 행위).
		 HttpSession session = request.getSession();	
		 
		 session.setAttribute("sessionPlayer", WhoIsPlayer);
		
		 
		 // Board 객체를 생성.. 하지만 하지만 board에 아무런 데이터가 없다면 if 조건문을 타고 객체를 만든다.. 다음 실행때는 if문 안타고 getAttibute 호출해서 "board" 속성 가져옴..
		 Board board = (Board) session.getAttribute("board");  
	        if (board == null) {
	            board = new Board();
	            session.setAttribute("board", board);  // 서블릿은 데이터를 JSP에 보내줘야함. 그런데 어떻게??
	            									   // session.setAttribute("board", board);로 속성을 집어넣고
	            									   // JSP에서 <% String strStone = (String)request.getAttribute("board"); %>로 속성을 얻는다.
	        }
	        
	        // 현재 플레이어
	        // String currentPlayer = board.isBlackTurn(black, white);
	        
	        // 검은 플레이어면 이면 "B", 흰 플레이어면 "W"를 사용한다.
	        String stone = WhoIsPlayer.equals("1") ? "B" : "W"; 
	        
	        //  이 메소드는 일반적으로 현재 플레이어가 (x, y) 위치에 돌을 놓으려고 시도
		    boolean success = board.play(x, y, stone); 
		    
		    boolean Omok = false;    // 오목 여부
		    boolean Six = false;     // 육목 여부
		    boolean DoubleThree = false; // 쌍삼 여부
		    String message = "";

		    // 돌을 놓은 후의 상태 체크
		    if (success) {
		    	Omok = Board.checkOmok(x, y, stone);
		    	Six = Board.checkSix(x, y, stone);
		    	DoubleThree = Board.checkDoubleThree(x, y, stone);
		        
		        if (Omok) {
		            message = "W";
		        } else if (Six) {
		            message = "6X";
		                                                                                                                           
		            // 육목이면 성공을 false로 변경
		            success = false; 
		        } else if (DoubleThree) {
		            message = "3X";
		            
		            // 쌍삼이면 성공을 false로 변경
		            success = false; 
		        } else {
		        	// 이것도 저것도 아니면 다음 메세지 출력
		            message = "돌O";  
		        }
		    } else {
		    	// success가 실패이면 다음 메세지 출력
		        message = "돌X";  
		    }
		    
		    // JSON 객체 생성 및 세팅
		    JSONObject jsonResponse = new JSONObject();
		    
		    // boolean을 문자열로 변환하여 넣음
		    jsonResponse.put("success", String.valueOf(success));
		    jsonResponse.put("message", message);
		    if (success) {
		        jsonResponse.put("currentPlayer", WhoIsPlayer);
		    }
	        
	        // 응답 타입을 JSON으로 설정
	        response.setContentType("application/json");
	        
	        //응답 문자열의 인코딩을 UTF-8로 설정함
	        response.setCharacterEncoding("UTF-8");

	        // PrintWriter는 JSON 응답 전송하기 위함
	        PrintWriter out = response.getWriter();
	        out.print(jsonResponse.toString());
	        
	        // 버퍼에 있는 모든 출력 데이터를 클라이언트로 즉시 전송하고 버퍼를 비움
	        out.flush();
	}
	
	
	private void oppStoneSet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		// JSP로 부터 x값을 가져옴. JSP에 <input type="text" name="id"> 있었다면
		// 서블릿에서 request.getParameter("y_input") 와 같은 방식으로, 클라이언트가 입력한 ID가 뭐였는지 알 수 있다
		int x = Integer.parseInt(request.getParameter("x_row")); 
		int y = Integer.parseInt(request.getParameter("y_col")); 
		 
		String WhoIsPlayer = request.getParameter("Gameplayer");
		 
		 // 여러 요청에 걸쳐 게임 상태를 서버에 저장하고 검색하기 위해 HttpSession을 사용 (상태유지 행위).
		HttpSession session = request.getSession();	
		 
		session.setAttribute("sessionPlayer", WhoIsPlayer);
		
		 
		// Board 객체를 생성.. 하지만 하지만 board에 아무런 데이터가 없다면 if 조건문을 타고 객체를 만든다.. 다음 실행때는 if문 안타고 getAttibute 호출해서 "board" 속성 가져옴..
		Board board = (Board) session.getAttribute("board");  
		if (board == null) {
		   board = new Board();
		   session.setAttribute("board", board);  // 서블릿은 데이터를 JSP에 보내줘야함. 그런데 어떻게??
		   											// session.setAttribute("board", board);로 속성을 집어넣고
		    									   // JSP에서 <% String strStone = (String)request.getAttribute("board"); %>로 속성을 얻는다.
		}
		
		// 현재 플레이어
		// String currentPlayer = board.isBlackTurn(black, white);
		
		// 검은 플레이어면 이면 "B", 흰 플레이어면 "W"를 사용한다.
		String stone = WhoIsPlayer.equals("1") ? "B" : "W"; 
		
		//지금은 상대 플레이어 -> 상대는 무조건 성공을 했다는 가정
	    board.play(x, y, stone); 
		
	}
}
