package battleship;

//@{}
public class Board {
	private int areaRowsWidth = 5;
	private int areaColsHeight = 5;
	
	public Board() {
		this(5, 5);
	}
	
	public Board(int areaRowsWidth, int areaColsHeight) {
		this.areaRowsWidth = areaRowsWidth;
		this.areaColsHeight = areaColsHeight;
	}
	
	public int getAreaRowsWidth() {
		return this.areaRowsWidth;
	}
	
	public int getAreaColsHeight() {
		return this.areaColsHeight;
	}
}
