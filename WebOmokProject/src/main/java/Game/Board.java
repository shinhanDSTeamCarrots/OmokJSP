package Game;

public class Board {
	private String[][] map;
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
	 
	 public String[][] getMap() {
		    return map;
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
	 
	
	

	 
}
