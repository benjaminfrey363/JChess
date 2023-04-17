package logic;

public class OldPiece {
	public String tag;
	// Horizontal and vertical coordinates.
	public int x;
	public int y;
	public OldPiece(int x, int y) {
		// For all piece coordinates, x represents row and y
		// represents column.
		this.x = x; this.y= y;
	}
	public OldPiece(int x, int y, String tag) {
		this.x = x; this.y = y; this.tag = tag;
	}
	// Copy constructor
	public OldPiece(OldPiece piece) {
		this.tag = new String(piece.tag);
		this.x = piece.x;
		this.y = piece.y;
	}
}
