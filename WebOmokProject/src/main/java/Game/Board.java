package Game;

public class Board {
	private char[][] grid;
	public static final int SIZE = 19;
    public static final char BLACK = 'B';
    public static final char WHITE = 'W';
    public char currentPlayer;
	
	 public Board() {
	        grid = new char[SIZE][SIZE];
	        for (int i = 0; i < SIZE; i++) {
	            for (int j = 0; j < SIZE; j++) {
	                grid[i][j] = '.';
	            }
	        }
	        currentPlayer = BLACK;
	    }
	 
	  public boolean put(int x, int y, char stone) {
	        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && grid[x][y] == '.') {
	            grid[x][y] = stone;
	            return true;
	        }
	        return false;
	    }
	  
	  public void switchPlayer() {         // 플레이어를 바꾸기 위한 메소드이다. 
		  if(currentPlayer == BLACK) {	   // Black 이면 White. White 이면 Black으로 바꿈
			  currentPlayer = WHITE;
		  } else {
			  currentPlayer = BLACK;
		  }
	  }
	  
	  public void getCurrentPlayer() {		// 현재 플레이어를 리턴시
		
	  }
	  
	  public boolean who(String b, String w) {
		  
		  return true;
	  }
}
