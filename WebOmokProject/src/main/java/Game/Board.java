package Game;

public class Board {
	private static String[][] map;
	private static final int SIZE = 19;
    public String currentPlayer;
	
	 public Board() {
		 String[][] map;
		 map = new String[SIZE][SIZE];
		      for (int row = 0; row < SIZE; row++) {
		          for (int col = 0; col < SIZE; col++) {
		               map[row][col] = ".";
		            }
		        }
	    }

	 
	 public void print() {
	        for (int row = 0; row < SIZE; row++) {
	        	System.out.printf("%2s",row);
	            for (int col = 0; col < SIZE; col++) {
	            	System.out.print(" "+ map[row][col]);
	            }
	            System.out.println();
	       	 	if(row == SIZE-1) {
	       	 		System.out.print("   ");
	       	 		for(int i = 0; i < SIZE; i++) {
	           	 		System.out.print((char)(i+65));
	           	 		System.out.print(" ");
	       	 		}
	       	 	System.out.println();
	       	 	}
	        }
	    }
	 
	 public boolean play(int x, int y, String stone) {
		    // 이미 돌이 놓인 자리인지, 좌표가 범위 내인지 검사
		    if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && map[x][y].equals(".")) {
		        map[x][y] = stone; // "B" 또는 "W"를 보드에 놓음
		        return true;
		    }
		    return false;
		}

	 
	 public String isBlackTurn(String currentBlackPlayer, String currentWhitePlayer) {
		  
		    if (currentBlackPlayer.equals("black_player")) {
		        // '흑' 턴으로 변경
		    	this.currentPlayer = currentBlackPlayer;
		        return "black_player";
		    } else {
		        // '백' 턴으로 변경
		    	this.currentPlayer = currentWhitePlayer;
		        return "white_player";
		}
	 }
	 
	
		public static boolean checkOmok(int x, int y, String player) {
		    // 오목을 이루는데 필요한 돌의 수
		    final int OMOK_COUNT = 5;

		    // 가로, 세로, 대각선 방향의 연속된 돌을 세는데 사용될 카운터
		    int count;

		    // 가로 방향 체크
		    count = 1;
		    // 왼쪽 방향
		    for (int i = x - 1; i >= 0 && map[y][i].equals(player); i--) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }
		    // 오른쪽 방향
		    for (int i = x + 1; i < map[y].length && map[y][i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 수직 방향 체크
		    count = 1;
		    // 위쪽 방향
		    for (int i = y - 1; i >= 0 && map[i][x].equals(player); i--) {
		        count++;
		    }
		    // 아래쪽 방향
		    for (int i = y + 1; i < map.length && map[i][x].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 대각선 (좌상향) 방향 체크
		    count = 1;
		    for (int i = 1; x - i >= 0 && y - i >= 0 && map[y - i][x - i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }
		    // 대각선 (우하향) 방향 체크
		    for (int i = 1; x + i < map[0].length && y + i < map.length && map[y + i][x + i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 대각선 (우상향) 방향 체크
		    count = 1;
		    for (int i = 1; x + i < map[0].length && y - i >= 0 && map[y - i][x + i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }
		    // 대각선 (좌하향) 방향 체크
		    for (int i = 1; x - i >= 0 && y + i < map.length && map[y + i][x - i].equals(player); i++) {
		        count++;
		        if (count == OMOK_COUNT) return true;
		    }

		    // 모든 방향에 대해 오목이 아니면 false 반환
		    return false;
		}
		
		 public static boolean checkSix(int x, int y, String player) {
			    // 육목을 이루는데 필요한 돌의 수
			    final int SIX_COUNT = 6;

			    // 가로, 세로, 대각선 방향의 연속된 돌을 세는데 사용될 카운터
			    int count;

			    // 수평 방향 체크 (왼쪽 + 오른쪽)
			    count = 1;
			    for (int i = x - 1; i >= 0 && map[y][i].equals(player); i--) count++;
			    for (int i = x + 1; i < map[0].length && map[y][i].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 수직 방향 체크 (위 + 아래)
			    count = 1;
			    for (int i = y - 1; i >= 0 && map[i][x].equals(player); i--) count++;
			    for (int i = y + 1; i < map.length && map[i][x].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 대각선 (좌상향 + 우하향) 체크
			    count = 1;
			    for (int i = 1; x - i >= 0 && y - i >= 0 && map[y - i][x - i].equals(player); i++) count++;
			    for (int i = 1; x + i < map[0].length && y + i < map.length && map[y + i][x + i].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 대각선 (우상향 + 좌하향) 체크
			    count = 1;
			    for (int i = 1; x + i < map[0].length && y - i >= 0 && map[y - i][x + i].equals(player); i++) count++;
			    for (int i = 1; x - i >= 0 && y + i < map.length && map[y + i][x - i].equals(player); i++) count++;
			    if (count == SIX_COUNT) return true;

			    // 모든 방향에 대해 육목이 아니면 false 반환
			    return false;
			}
		 	
		 

		 


		  public static boolean checkThree(int x, int y, String player, String[][] board, int directionX, int directionY) {
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

			            if (nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length) break;

			            if ("".equals(map[ny][nx]) || map[ny][nx] == null) {
			                if (i == 0 || i == 3) open = true;
			                if (i > 0) break; // 돌 시퀀스 이후 열린 공간이 있어야 하므로
			            } else if (map[ny][nx].equals(player)) {
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


			public static boolean checkDoubleThree(int x, int y, String player) {
			    // 삼이 형성되는지 여부를 방향별로 체크하고 삼의 개수를 카운트
			    int threes = 0;

			    // 수평 방향으로 삼을 확인
			    if (checkThree(x, y, player, map, 1, 0)) {
			        threes++;
			    }

			    // 수직 방향으로 삼을 확인
			    if (checkThree(x, y, player, map, 0, 1)) {
			        threes++;
			    }

			    // 대각선 (좌상향 + 우하향) 방향으로 삼을 확인
			    if (checkThree(x, y, player, map, 1, 1)) {
			        threes++;
			    }

			    // 대각선 (우상향 + 좌하향) 방향으로 삼을 확인
			    if (checkThree(x, y, player, map, 1, -1)) {
			        threes++;
			    }

			    // 삼이 두 개 이상이면 쌍삼으로 간주
			    return threes >= 2;
			}

	 
}
