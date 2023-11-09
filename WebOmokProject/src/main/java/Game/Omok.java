package Game;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Rank.LogVO;
import Rank.RankDAO;

@WebServlet("/omok/*")
public class Omok extends HttpServlet {
	// 나중에 ajax 양방향 통신을 위해 json.simpple-1.1.1.jar 라이브러리 가져올 것
	
	RankDAO rankDao;

	@Override
	@SuppressWarnings("unchecked") // 랭킹 가져오기
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
	
		// 1. 내가 돌을 가져왓을때
		if (command.equals("sendMyInfo.do")) {
			myStoneSet(request, response);
		} // 2. 상대가 돌을 놓았을때
		else if (command.equals("sendYouInfo.do")) {
			oppStoneSet(request, response);

		} // 3. 정보 검색
		else if (command.equals("requUserInfo.do")) {
			winRateCalc(request,response);
		} // 4. 시스템 메시지 수신
		else if (command.equals("sendMemStatus.do")) {
			String type = request.getParameter("type");
			if(type.equals("SURRENDER") || type.equals("TIME")) {
				//상대의 타임아웃
				//상대의 서렌
				//이긴걸로 저장
				VictoryLogSetting(request,response,2);
				return;
			}
			if(type.equals("VICTORY")) {
				//상대의 승리 메시지
				//-> 암것도 하지 않는다.
				return;
			}
		} // 5. 확장: 물리기 시스템 - 확장 중입니다.
		else if (command.equals("undo.do")) {

		}

	}
	
	private void winRateCalc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//winRate
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		
		
		int playerNo = Integer.parseInt(request.getParameter("member_id"));
		int oppoNo = Integer.parseInt(request.getParameter(""));
		if(rankDao == null)
			rankDao = new RankDAO();
		Map<Integer, Double> playerMap;
		playerMap = rankDao.winRate(playerNo);
		Map<Integer, Double> oppoMap;
		oppoMap = rankDao.winRate(oppoNo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("playerNo", playerNo);
		jsonObject.put("winCnt", playerMap.get(1).intValue());
		jsonObject.put("loseCnt", playerMap.get(2).intValue());
		jsonObject.put("winRate", playerMap.get(3).doubleValue());
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("oppoNo", oppoNo);
		jsonObject2.put("winCnt", oppoMap.get(1).intValue());
		jsonObject2.put("loseCnt", oppoMap.get(2).intValue());
		jsonObject2.put("winRate", oppoMap.get(3).doubleValue());
		
		JSONArray arr = new JSONArray();
		arr.add(jsonObject);
		arr.add(jsonObject2);
		
		JSONObject json = new JSONObject();
		json.put("arr", arr);
		writer.print(json.toString());
		
	}
	
	//승리 로그 저장
	private void VictoryLogSetting(HttpServletRequest request, HttpServletResponse response, int wintype) {

		// int WinCount = Integer.parseInt(request.getParameter("Win"));
		// int LoseCount = Integer.parseInt(request.getParameter("Lose"));

		int winPlayerNo = Integer.parseInt(request.getParameter("Win"));
		int losePlayerNo = Integer.parseInt(request.getParameter("Lose"));
		String LogDetail;
		if(wintype == 1) {
			LogDetail = Board.getStaticBoard().WinLog();
		}else {
			LogDetail = Board.getStaticBoard().SurrenderLog();
		}
		
		if (rankDao == null)
			rankDao = new RankDAO();
		try {
			LogVO logvo = new LogVO();
			logvo.setWinner_no(winPlayerNo);
			logvo.setLoser_no(losePlayerNo);
			rankDao.addLog(logvo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 내가 돌을 놓았을 때
	private void myStoneSet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// JSP로 부터 x값을 가져옴. JSP에 <input type="text" name="id"> 있었다면
		// 서블릿에서 request.getParameter("y_input") 와 같은 방식으로, 클라이언트가 입력한 ID가 뭐였는지 알 수 있다
		int x = Integer.parseInt(request.getParameter("x_row"));
		int y = Integer.parseInt(request.getParameter("y_col"));

		String stonecolor = request.getParameter("stone");

		// 여러 요청에 걸쳐 게임 상태를 서버에 저장하고 검색하기 위해 HttpSession을 사용 (상태유지 행위).
		HttpSession session = request.getSession();

		session.setAttribute("sessionPlayer", stonecolor);

		/* Board 객체를 생성.. 하지만 하지만 board에 아무런 데이터가 없다면 if 조건문을 타고 객체를 만든다.. 다음 실행때는 if문
		// 안타고 getAttibute 호출해서 "board" 속성 가져옴..
		Board board = (Board) session.getAttribute("board");
		if (board == null) {
			board = new Board();
			session.setAttribute("board", board); // 서블릿은 데이터를 JSP에 보내줘야함. 그런데 어떻게??
													// session.setAttribute("board", board);로 속성을 집어넣고
													// JSP에서 <% String strStone = (String)request.getAttribute("board");
													// %>로 속성을 얻는다.
		}*/
		Board board = Board.getStaticBoard();

		// 현재 플레이어
		// String currentPlayer = board.isBlackTurn(black, white);

		// 검은 플레이어면 이면 "B", 흰 플레이어면 "W"를 사용한다.
		//String stone = WhoIsPlayer.equals("1") ? "B" : "W";

		// 이 메소드는 일반적으로 현재 플레이어가 (x, y) 위치에 돌을 놓으려고 시도
		boolean success = board.myStoneplay(x, y);

		boolean Omok = false; // 오목 여부
		boolean Six = false; // 육목 여부
		boolean DoubleThree = false; // 쌍삼 여부
		String message = "";

		// 돌을 놓은 후의 상태 체크
		if (success) {
			Omok = board.checkOmok(x, y);
			Six = board.checkSix(x, y);
			DoubleThree = board.checkDoubleThree(x, y);

			if (Omok) {
				message = "W";
			} else if (Six) {
				message = "6";
				// 육목이면 성공을 false로 변경
				success = false;
			} else if (DoubleThree) {
				message = "3";
				// 쌍삼이면 성공을 false로 변경
				success = false;
			}else {
				//오목도 아니고, 놓을수 없는 공간도 아님
				message = "T";
			}
			
		} else {
			// success가 실패이면 다음 메세지 출력
			message = "F";
		}
		
		
		if(message.equals("W")) {
			//오목승리 케이스
			VictoryLogSetting(request, response,1);
		}

		// JSON 객체 생성 및 세팅
		JSONObject json = new JSONObject();

		// boolean을 문자열로 변환하여 넣음
		json.put("success", String.valueOf(success));
		json.put("message", message);
		if (success) {
			json.put("currentPlayer", stonecolor);
		}

		// 응답 타입을 JSON으로 설정
		// response.setContentType("application/json");
		
		response.setContentType("text/html; charset=utf-8");

		// 응답 문자열의 인코딩을 UTF-8로 설정함
		//response.setCharacterEncoding("UTF-8");

		// PrintWriter는 JSON 응답 전송하기 위함
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		
		
		// 버퍼에 있는 모든 출력 데이터를 클라이언트로 즉시 전송하고 버퍼를 비움
		out.flush();
	}

	private void oppStoneSet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		// JSP로 부터 x값을 가져옴. JSP에 <input type="text" name="id"> 있었다면
		// 서블릿에서 request.getParameter("y_input") 와 같은 방식으로, 클라이언트가 입력한 ID가 뭐였는지 알 수 있다
		int x = Integer.parseInt(request.getParameter("x_row"));
		
		//아니 근데 x는 row가 아니라 col 에 가깝잖아 님들아
		//왜 좌표계를 꼬아가는뎅....... ㅂㄷㅂㄷ
		//제발 row랑 col로만 저장해 x랑 y 말고....
		int y = Integer.parseInt(request.getParameter("y_col"));

		/* Board 객체를 생성.. 하지만 하지만 board에 아무런 데이터가 없다면 if 조건문을 타고 객체를 만든다.. 다음 실행때는 if문
		// 안타고 getAttibute 호출해서 "board" 속성 가져옴..
		Board board = (Board) session.getAttribute("board");
		if (board == null) {
			board = new Board();
			session.setAttribute("board", board); // 서블릿은 데이터를 JSP에 보내줘야함. 그런데 어떻게??
													// session.setAttribute("board", board);로 속성을 집어넣고
													// JSP에서 <% String strStone = (String)request.getAttribute("board");
													// %>로 속성을 얻는다.
		}*/
		
		Board board = Board.getStaticBoard();

		// 지금은 상대 플레이어 -> 상대는 무조건 성공을 했다는 가정
		board.Opponentplay(x, y);

	}
}
