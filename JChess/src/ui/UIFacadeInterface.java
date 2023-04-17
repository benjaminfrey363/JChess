package ui;

import universal.Coord;

public interface UIFacadeInterface {

	// Display menu with game options.
	public void startMenu();
	
	// Indicate to UI that game is starting, initialize and display board.
	public void startGame();
	
	// Logic indicates to UI that a piece has been moved.
	public void movePiece(Coord oldLoc, Coord newLoc);
	
	// Logic indicates to UI that a team has won.
	public void victory(char team);
		
}
