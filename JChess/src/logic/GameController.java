package logic;

// Controls flow of the game.
public class GameController {
	private static Logic logic;
	
	// Play a game of chess.
	public static void playGame() {
		// Create new logic, thus creating and displaying UI.
		logic = new Logic();
		
	}
	
	
	public static void main(String args[]) {
		playGame();
	}
	

}

