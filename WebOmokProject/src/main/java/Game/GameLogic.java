	package Game;
	
	public class GameLogic {
		private Board board;
		private static final int SIZE = 19;
		private String[][] map2;
		 
		
		public boolean checkOmok(int x, int y, String player, String[][] board) {
		    // 오목을 이루는데 필요한 돌의 수
		    final int OMOK_COUNT = 5;

		    // 가로, 세로, 대각선 방향의 연속된 돌을 세는데 사용될 카운터
		    int count;

		    // 가로 방향 체크
		    count = 1;
		    // 왼쪽 방향
		    for (int i = x - 1; i >= 0 && board[y][i].equals(player); i--) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }
		    // 오른쪽 방향
		    for (int i = x + 1; i < board[y].length && board[y][i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 수직 방향 체크
		    count = 1;
		    // 위쪽 방향
		    for (int i = y - 1; i >= 0 && board[i][x].equals(player); i--) {
		        count++;
		    }
		    // 아래쪽 방향
		    for (int i = y + 1; i < board.length && board[i][x].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 대각선 (좌상향) 방향 체크
		    count = 1;
		    for (int i = 1; x - i >= 0 && y - i >= 0 && board[y - i][x - i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }
		    // 대각선 (우하향) 방향 체크
		    for (int i = 1; x + i < board[0].length && y + i < board.length && board[y + i][x + i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 대각선 (우상향) 방향 체크
		    count = 1;
		    for (int i = 1; x + i < board[0].length && y - i >= 0 && board[y - i][x + i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }
		    // 대각선 (좌하향) 방향 체크
		    for (int i = 1; x - i >= 0 && y + i < board.length && board[y + i][x - i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 모든 방향에 대해 오목이 아니면 false 반환
		    return false;
		}

	
	
		  public boolean checkSix(int x, int y, String player, String[][] board) {
			    // 육목을 이루는데 필요한 돌의 수
			    final int SIX_COUNT = 6;

			    // 가로, 세로, 대각선 방향의 연속된 돌을 세는데 사용될 카운터
			    int count;

			    // 수평 방향 체크 (왼쪽 + 오른쪽)
			    count = 1;
			    for (int i = x - 1; i >= 0 && board[y][i].equals(player); i--) count++;
			    for (int i = x + 1; i < board[0].length && board[y][i].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 수직 방향 체크 (위 + 아래)
			    count = 1;
			    for (int i = y - 1; i >= 0 && board[i][x].equals(player); i--) count++;
			    for (int i = y + 1; i < board.length && board[i][x].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 대각선 (좌상향 + 우하향) 체크
			    count = 1;
			    for (int i = 1; x - i >= 0 && y - i >= 0 && board[y - i][x - i].equals(player); i++) count++;
			    for (int i = 1; x + i < board[0].length && y + i < board.length && board[y + i][x + i].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 대각선 (우상향 + 좌하향) 체크
			    count = 1;
			    for (int i = 1; x + i < board[0].length && y - i >= 0 && board[y - i][x + i].equals(player); i++) count++;
			    for (int i = 1; x - i >= 0 && y + i < board.length && board[y + i][x - i].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 모든 방향에 대해 육목이 아니면 false 반환
			    return false;
			}


		  public boolean checkThree(int x, int y, String player, String[][] board, int directionX, int directionY) {
			    int[][] directions = {{directionX, directionY}, {-directionX, -directionY}};

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

			            if (nx < 0 || nx >= board.length || ny < 0 || ny >= board[0].length) break;

			            if ("".equals(board[ny][nx]) || board[ny][nx] == null) {
			                if (i == 0 || i == 3) open = true;
			                if (i > 0) break; // 돌 시퀀스 이후 열린 공간이 있어야 하므로
			            } else if (board[ny][nx].equals(player)) {
			                stones++;
			            } else {
			                break;
			            }
			        }

			        if (dir == 0 && open) openStart = true; // 첫 번째 방향에서 열린 끝을 확인
			        if (dir == 1 && open) openEnd = true; // 두 번째 방향에서 열린 끝을 확인
			    }

			    return stones == 3 && openStart && openEnd;
			}


			public boolean checkDoubleThree(int x, int y, String player, String[][] board) {
			    // 삼이 형성되는지 여부를 방향별로 체크하고 삼의 개수를 카운트
			    int threes = 0;

			    // 수평 방향으로 삼을 확인
			    if (checkThree(x, y, player, board, 1, 0)) {
			        threes++;
			    }

			    // 수직 방향으로 삼을 확인
			    if (checkThree(x, y, player, board, 0, 1)) {
			        threes++;
			    }

			    // 대각선 (좌상향 + 우하향) 방향으로 삼을 확인
			    if (checkThree(x, y, player, board, 1, 1)) {
			        threes++;
			    }

			    // 대각선 (우상향 + 좌하향) 방향으로 삼을 확인
			    if (checkThree(x, y, player, board, 1, -1)) {
			        threes++;
			    }

			    // 삼이 두 개 이상이면 쌍삼으로 간주
			    return threes >= 2;
			}

	
	}
