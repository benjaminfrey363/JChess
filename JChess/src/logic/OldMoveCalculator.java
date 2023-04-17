package logic;

import java.util.ArrayList;
import java.util.Random;

import universal.Coord;

// Static methods to calculate moves, used for enemy movement
public class OldMoveCalculator {
	
	private OldMoveCalculator() {}
	
	
	// Return all possible opponent moves.
	public static ArrayList<OldMoveOption> allMoves(OldLogic logic) {
		ArrayList<OldMoveOption> options = new ArrayList<OldMoveOption>();
		for (OldPiece p : logic.pieceMap.values()) {
			if (p.tag.charAt(0) == OldLogic.OPPCOL) {
				for (Coord move : logic.moveOptions(p)) {
					options.add(new OldMoveOption(p, move));
				}
			}
		}
		return options;
	}
	
	
	// Return random opponent move.
	public static OldMoveOption randomMove(OldLogic logic) {
		ArrayList<OldMoveOption> options = allMoves(logic);
		// Now have an ArrayList of possible moves. Choose one randomly.
		OldMoveOption move;
		Random ran = new Random();
		move = options.get(ran.nextInt(options.size()));
		return move;
	}
	
	
	// First level of enemy logic:
	// - Capture a piece if possible
	// - Move piece if any are in danger
	// - Do not make a move if it would result in piece being captured.
	public static OldMoveOption alphaIntelligence(OldLogic logic) {
		
		// Create list of all possible move options.
		ArrayList<OldMoveOption> options = new ArrayList<OldMoveOption>();
		ArrayList<OldMoveOption> coverage = new ArrayList<OldMoveOption>();
		
		for (OldMoveOption move : allMoves(logic)) {
			if (move.piece.tag.charAt(0) == OldLogic.OPPCOL) options.add(move);
			else coverage.add(move);
			// Put player moves in coverage array.
		}
		
		// FIRST STEP: See if any pieces can be captured. If so, prioritize this.
		for (OldMoveOption move : options) {
			if (logic.gameBoard.boardArray[move.coord.x][move.coord.y] != null 
					&& logic.gameBoard.boardArray[move.coord.x][move.coord.y].tag.charAt(0) == OldLogic.PLAYERCOL) {
				// Player piece can be captured.
				return move;
			}
		}
		
		// Next, see if any pieces are in danger of being captured (i.e. in range of coverage)
		
		
		return null;
	}
	
}
