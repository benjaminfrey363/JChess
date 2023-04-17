package logic;

import java.util.ArrayList;

import universal.Coord;

// Facade of logic - all interaction between the logic
// module and the rest of the software is achieved using these methods.
public interface LogicFacadeInterface {

	// UI indicates to logic that a piece has been chosen to move, return ArrayList of
	// coords representing possible moves this piece can make.
	// NOTE: GUI DOES NOT NEED TO TRACK ANY INFORMATION ABOUT TYPES OF PIECES/TEAMS.
	// THIS METHOD WILL RETURN AN EMPTY ARRAYLIST IF CALLED ON AN ENEMY PIECE, SO THIS
	// METHOD CAN BE CALLED ON ALL PIECES WITHOUT CHECKING TEAM.
	// GUI DOESN'T EVEN NEED TO KNOW WHICH CELLS ARE OCCUPIED.
	public ArrayList<Coord> getMoves(Coord loc);
	
	// UI indicates to logic that a possible move has been chosen, piece should be moved accordingly.
	public void movePiece(Coord oldLoc, Coord newLoc);
	
}
