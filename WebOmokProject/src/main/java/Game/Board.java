package Game;

import org.json.JSONObject;
import java.util.Stack;
public class Board {
	private int[][] map;
	private static final int SIZE = 19;
	public int currentPlayer; // 현재 플레이어의 돌 정의
	private static Board sboard;
	private Stack<Stroke> stack = new Stack<>();

	// 싱글턴 패턴
	public static Board getStaticBoard() {
		if (sboard == null)
			sboard = new Board();
		return sboard;
	}

	// 팩토리 패턴 응용
	public Board setPlayer(int val) {
		// clamp
		int v = Math.max(Math.min(1, val), 0);
		this.currentPlayer = v;
		return this;
	}

	private Board() {
		map = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				map[row][col] = 0;
			}
		}
	}

	public boolean myStoneplay(int x, int y) {
		// 이미 돌이 놓인 자리인지, 좌표가 범위 내인지 검사
		if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && map[x][y] == 0) {
			// 전성욱 String 기반에서 int 기반으로 변경 B 는 1 W 는 2 로 통일
			map[x][y] = currentPlayer; // "B" 또는 "W"를 보드에 놓음
			stack.add(new Stroke(-1,currentPlayer,x,y));
			return true;
		}
		return false;
	}

	public void Opponentplay(int x, int y) {
		// 상대가 놓은 돌을 놓는다
		// 상대의 돌은 무조건 내 돌이 아닐테니
		int opponentPlayer = (currentPlayer == 0 ? 1 : 0);
		map[x][y] = opponentPlayer;
		stack.add(new Stroke(-1,opponentPlayer,x,y));
	}

	// 오목 체크 함수
	public boolean checkOmok(int x, int y) {
		// 오목을 이루는데 필요한 돌의 수
		final int OMOK_COUNT = 5;

		// 가로, 세로, 대각선 방향의 연속된 돌을 세는데 사용될 카운터
		int count;

		// 가로 방향 체크
		count = 1;
		// 왼쪽 방향
		for (int i = x - 1; i >= 0 && map[y][i] == currentPlayer; i--) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}
		// 오른쪽 방향
		for (int i = x + 1; i < map[y].length && map[y][i] == currentPlayer; i++) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}

		// 수직 방향 체크
		count = 1;
		// 위쪽 방향
		for (int i = y - 1; i >= 0 && map[i][x] == currentPlayer; i--) {
			count++;
		}
		// 아래쪽 방향
		for (int i = y + 1; i < map.length && map[i][x] == currentPlayer; i++) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}

		// 대각선 (좌상향) 방향 체크
		count = 1;
		for (int i = 1; x - i >= 0 && y - i >= 0 && map[y - i][x - i] == currentPlayer; i++) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}
		// 대각선 (우하향) 방향 체크
		for (int i = 1; x + i < map[0].length && y + i < map.length && map[y + i][x + i] == currentPlayer; i++) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}

		// 대각선 (우상향) 방향 체크
		count = 1;
		for (int i = 1; x + i < map[0].length && y - i >= 0 && map[y - i][x + i] == currentPlayer; i++) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}
		// 대각선 (좌하향) 방향 체크
		for (int i = 1; x - i >= 0 && y + i < map.length && map[y + i][x - i] == currentPlayer; i++) {
			count++;
			if (count == OMOK_COUNT)
				return true;
		}

		// 모든 방향에 대해 오목이 아니면 false 반환
		return false;
	}

	// 육목 체크
	public boolean checkSix(int x, int y) {
		// 육목을 이루는데 필요한 돌의 수
		final int SIX_COUNT = 6;

		// 가로, 세로, 대각선 방향의 연속된 돌을 세는데 사용될 카운터
		int count;

		// 수평 방향 체크 (왼쪽 + 오른쪽)
		count = 1;
		for (int i = x - 1; i >= 0 && map[y][i] == currentPlayer; i--)
			count++;
		for (int i = x + 1; i < map[0].length && map[y][i] == currentPlayer; i++)
			count++;
		if (count == SIX_COUNT)
			return true;

		// 수직 방향 체크 (위 + 아래)
		count = 1;
		for (int i = y - 1; i >= 0 && map[i][x] == currentPlayer; i--)
			count++;
		for (int i = y + 1; i < map.length && map[i][x] == currentPlayer; i++)
			count++;
		if (count == SIX_COUNT)
			return true;

		// 대각선 (좌상향 + 우하향) 체크
		count = 1;
		for (int i = 1; x - i >= 0 && y - i >= 0 && map[y - i][x - i] == currentPlayer; i++)
			count++;
		for (int i = 1; x + i < map[0].length && y + i < map.length && map[y + i][x + i] == currentPlayer; i++)
			count++;
		if (count == SIX_COUNT)
			return true;

		// 대각선 (우상향 + 좌하향) 체크
		count = 1;
		for (int i = 1; x + i < map[0].length && y - i >= 0 && map[y - i][x + i] == currentPlayer; i++)
			count++;
		for (int i = 1; x - i >= 0 && y + i < map.length && map[y + i][x - i] == currentPlayer; i++)
			count++;
		if (count == SIX_COUNT)
			return true;

		// 모든 방향에 대해 육목이 아니면 false 반환
		return false;
	}

	// 쌍삼 체크
	public boolean checkThree(int x, int y, int directionX, int directionY) {
		int[][] directions = { { directionX, directionY }, { -directionX, -directionY } };

		int stones = 0;
		boolean openStart = false, openEnd = false;

		for (int dir = 0; dir < 2; dir++) {
			int dx = directions[dir][0];
			int dy = directions[dir][1];
			stones = 0;
			boolean open = false;

			for (int i = (dir == 0 ? 0 : 1); i <= 3; i++) {
				int nx = x + dx * i;
				int ny = y + dy * i;

				if (nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length)
					break;

				if (map[ny][nx] == 0) {
					if (i == 0 || i == 3)
						open = true;
					if (i > 0)
						break; // 돌 시퀀스 이후 열린 공간이 있어야 하므로
				} else if (map[ny][nx] == currentPlayer) {
					stones++;
				} else {
					break;
				}
			}

			if (dir == 0 && open)
				openStart = true; // 첫 번째 방향에서 열린 끝을 확인
			if (dir == 1 && open)
				openEnd = true; // 두 번째 방향에서 열린 끝을 확인
		}

		return stones == 3 && openStart && openEnd;
	}

	// 쌍삼
	public boolean checkDoubleThree(int x, int y) {
		// 삼이 형성되는지 여부를 방향별로 체크하고 삼의 개수를 카운트
		int threes = 0;

		// 수평 방향으로 삼을 확인
		if (checkThree(x, y, 1, 0)) {
			threes++;
		}

		// 수직 방향으로 삼을 확인
		if (checkThree(x, y, 0, 1)) {
			threes++;
		}

		// 대각선 (좌상향 + 우하향) 방향으로 삼을 확인
		if (checkThree(x, y, 1, 1)) {
			threes++;
		}

		// 대각선 (우상향 + 좌하향) 방향으로 삼을 확인
		if (checkThree(x, y, 1, -1)) {
			threes++;
		}

		// 삼이 두 개 이상이면 쌍삼으로 간주
		return threes >= 2;
	}
	
	public String WinLog() {
		//win 로그에 추가
		stack.add(new Stroke(1, 0, 0, 0));
		StringBuilder sb = new StringBuilder();
		stack.stream().map(t ->sb.append(t.ToString()) );
		return sb.toString();
	}
	public String SurrenderLog() {
		stack.add(new Stroke(2, 0, 0, 0));
		StringBuilder sb = new StringBuilder();
		stack.stream().map(t ->sb.append(t.ToString()) );
		return sb.toString();
	}
}

class Stroke{
	private int player;
	private int row;
	private int col;
	private int systemtype;
	public Stroke(int sys, int player, int row, int col) {
		this.systemtype = sys;
		this.player = player;
		this.row = row;
		this.col = col;
	}
	public JSONObject ToJsonObject() {
		StringBuilder sb = new StringBuilder();
		if(systemtype == -1) {
			sb.append("{\"player\":\"");
			sb.append((player == 1 ? "B" : "W"));
			sb.append("\",\"row\":\"");
			sb.append(row);
			sb.append("\",\"col\":\"");
			sb.append(col);
			sb.append("\"}");
		}else if(systemtype == 1) {//오목 승리
			sb.append("{\"type\":\"VIC\"}");
		}else if(systemtype == 2) {//상대의 항복
			sb.append("{\"type\":\"SURRENDER\"}");
		}else if(systemtype == 3) {//무르기
			sb.append("{\"type\":\"UNDO\"}");
		}
		JSONObject json = new JSONObject(sb.toString());
		return json;
	}
	public String ToString() {
		StringBuilder sb = new StringBuilder();
		if(systemtype == -1) {
			sb.append((player == 1 ? "B" : "W"));
			sb.append(",");
			sb.append(row);
			sb.append(",");
			sb.append(col);
			sb.append(";");
		}else if(systemtype == 1) {//오목 승리
			sb.append("VICTORY;");
		}else if(systemtype == 2) {//(상대의)항복
			sb.append("SURRENDER;");
		}else if(systemtype == 3) {//무르기
			sb.append("UNDO;");
		}
		return sb.toString();
	}
}
