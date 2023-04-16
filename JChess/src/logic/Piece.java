package logic;

public class Piece {
	public String tag;
	// Horizontal and vertical coordinates.
	public int x;
	public int y;
	public Piece(int x, int y) {
		// For all piece coordinates, x represents row and y
		// represents column.
		this.x = x; this.y= y;
	}
	public Piece(int x, int y, String tag) {
		this.x = x; this.y = y; this.tag = tag;
	}
}
