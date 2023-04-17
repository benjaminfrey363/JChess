package universal;
// Simple board coordinate class.
public class Coord {
	public int x; public int y;
	public Coord(int x, int y) {this.x = x; this.y = y;}
	public boolean equals(Coord c) {
		return (c.x == this.x && c.y == this.y);
	}
}