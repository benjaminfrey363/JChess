package logic;

import universal.Coord;

//A move option contains a piece to move and a coord to move to.
public class OldMoveOption {
	public OldPiece piece; public Coord coord;
	public OldMoveOption(OldPiece piece, Coord coord) {this.piece = piece; this.coord = coord;}
}
