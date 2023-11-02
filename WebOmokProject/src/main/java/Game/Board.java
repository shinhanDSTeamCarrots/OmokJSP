package Game;

public class Board {
	private char[][] grid;
	public static final int SIZE = 19;
    public static final char BLACK = 'B';
    public static final char WHITE = 'W';
	
	 public Board() {
	        grid = new char[SIZE][SIZE];
	        for (int i = 0; i < SIZE; i++) {
	            for (int j = 0; j < SIZE; j++) {
	                grid[i][j] = '.';
	            }
	        }
	    }
	 
	  public boolean put(int x, int y, char stone) {
	        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && grid[x][y] == '.') {
	            grid[x][y] = stone;
	            return true;
	        }
	        return false;
	    }
}
