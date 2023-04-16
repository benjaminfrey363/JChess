package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ui.GUI;

public class Logic {
	
	// Simple board coordinate class.
	public class Coord {
		public int x; public int y;
		public Coord(int x, int y) {this.x = x; this.y = y;}
	}
	
	public static char OPPCOL = 'B';
	
	// Associated GUI.
	public GUI gui;
	// Game board lets us look up which piece is in a specific position
	public Board gameBoard;
	// pieceMap lets us find the position of any piece (by finding instance
	// of piece class)
	public HashMap<String, Piece> pieceMap;
	// Logic can be in two states (for now) - either waiting for a piece to be selected or waiting for a move to be chosen.
	public Boolean choosePiece = true;
	// Current chosen piece.
	public Piece currentPiece;
	// Array to track possible moves once a piece has been selected.
	public ArrayList<Coord> currentMoves;
	// Booleans to indicate if either white or black has won.
	public Boolean whiteWon = false;
	public Boolean blackWon = false;
	// Boolean to track turn - initialized to white.
	public boolean whiteTurn = true;
	
	public Logic() {
		gameBoard = new Board();
		// Next, construct a hash map String tags -> pieces so that we can
		// look up the location of a specific piece...
		pieceMap = new HashMap<String, Piece>();
		for (int i = 0; i < Board.DIM; ++i) {
			for (int j = 0; j < Board.DIM; ++j) {
				// Would have probably been easier to construct piece map first, but what can you do
				if (gameBoard.boardArray[i][j] != null) pieceMap.put(gameBoard.boardArray[i][j].tag, gameBoard.boardArray[i][j]);
			}
		}
		currentMoves = new ArrayList<Coord>();
		gui = new GUI(this);
	}

	// React to board button being pressed.
	public void reactToBoardButtonPressed(int x, int y) {
		
		// Only react to button pressed if it is white (player) turn
		if (whiteTurn) {
		
			if (choosePiece) {
				
				// Logic is in choosePiece state. Determine what piece was selected and
				// display moves, then switch to move select state.
				
				// Added - selected piece must also be white.
				if (gameBoard.boardArray[x][y] != null && gameBoard.boardArray[x][y].tag.charAt(0) == 'W') {
					currentPiece = gameBoard.boardArray[x][y];
					currentMoves = moveOptions(gameBoard.boardArray[x][y]);
				}
				
				// Display possible moves on user interface.
				gui.displayPossibleMoves();
				
				choosePiece = false;
			
			} else {
				
				// If choosePiece is false, then we're currently waiting for a move to be selected.
				// check if pressed button corresponds to any moves in the currentMoves array. If so,
				// then move piece. Otherwise, cancel previous piece selection.
				
				for (Coord move : currentMoves) {
					if (move.x == x && move.y == y) {
						// Piece is moved to x, y. Check for a check mate.
						movePiece(currentPiece, x, y);
						checkForCheckmate(currentPiece);
						if (whiteWon == true) gui.whiteVictory();
						else {
							// Change turn.
							whiteTurn = false;
							// Call opponentMove method...
							opponentMove();
							break;
						}
					}
				}
				
				gui.removePossibleMoves();
				// Update board (may not do anything)
				gui.updateBoard();
				currentPiece = null;
				currentMoves = new ArrayList<Coord>();
	
				choosePiece = true;
				
			}
		}
		
	}
	
	
	//////////////////////////
	// MOVE OPTIONS METHODS //
	//////////////////////////
	
	// Move option method for each piece type. Takes an argument of type piece (assumes
	// correct piece type) and returns an ArrayList of coords representing the possible
	// moves which can be made.
	
	
	// Returns move options for an arbitrary piece.
	private ArrayList<Coord> moveOptions(Piece p) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		
		if (p.tag.charAt(1) == 'P') {
			// Pawn - display move options accordingly.
			options.addAll(pawnMoveOptions(p));
		} else if (p.tag.charAt(1) == 'T') {
			// Tower - display move options accordingly.
			options.addAll(towerMoveOptions(p));
		} else if (p.tag.charAt(1) == 'N') {
			// Knight - display move options accordingly.
			options.addAll(knightMoveOptions(p));
		} else if (p.tag.charAt(1) == 'B') {
			// Bishop - display move options accordingly.
			options.addAll(bishopMoveOptions(p));
		} else if (p.tag.charAt(1) == 'Q') {
			// Queen - display move options accordingly.
			options.addAll(queenMoveOptions(p));
		} else if (p.tag.charAt(1) == 'K') {
			// King - display move options accordingly.
			options.addAll(kingMoveOptions(p));
		}
		
		return options;
		
	}
	
	
	private ArrayList<Coord> pawnMoveOptions(Piece pawn) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		
		if (pawn.tag.charAt(0) == 'B') {
			// black pawn. First check if moving down is possible.
			if (pawn.x < Board.DIM - 1 && gameBoard.boardArray[pawn.x + 1][pawn.y] == null) options.add(new Coord(pawn.x + 1, pawn.y));
			
			// next, check for attack diagonals:
			if (pawn.x < Board.DIM - 1 && pawn.y < Board.DIM - 1 && gameBoard.boardArray[pawn.x + 1][pawn.y + 1] != null && gameBoard.boardArray[pawn.x + 1][pawn.y + 1].tag.charAt(0) == 'W') {
				options.add(new Coord(pawn.x + 1, pawn.y + 1));
			}
			if (pawn.x < Board.DIM - 1 && pawn.y > 0 && gameBoard.boardArray[pawn.x + 1][pawn.y - 1] != null && gameBoard.boardArray[pawn.x + 1][pawn.y - 1].tag.charAt(0) == 'W') {
				options.add(new Coord(pawn.x + 1, pawn.y - 1));
			}
			
		} else {
			// white pawn. First check if moving up is possible.
			if (pawn.x > 0 && gameBoard.boardArray[pawn.x - 1][pawn.y] == null) options.add(new Coord(pawn.x - 1, pawn.y));
			
			// next, check for attack diagonals:
			if (pawn.x > 0 && pawn.y > 0 && gameBoard.boardArray[pawn.x - 1][pawn.y - 1] != null && gameBoard.boardArray[pawn.x - 1][pawn.y - 1].tag.charAt(0) == 'B') {
				options.add(new Coord(pawn.x - 1, pawn.y - 1));
			}
			if (pawn.x > 0 && pawn.y < Board.DIM - 1 && gameBoard.boardArray[pawn.x - 1][pawn.y + 1] != null && gameBoard.boardArray[pawn.x - 1][pawn.y + 1].tag.charAt(0) == 'B') {
				options.add(new Coord(pawn.x - 1, pawn.y + 1));
			}
			
		}
		
		return options;
	}
	
	
	private ArrayList<Coord> towerMoveOptions(Piece tower) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		
		char colour = tower.tag.charAt(0);
		
		// DOWN:
		int i = 1;
		while (tower.x + i < Board.DIM) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[tower.x + i][tower.y] != null && gameBoard.boardArray[tower.x + i][tower.y].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(tower.x + i, tower.y));
			++i;
		}
		// UP:
		i = 1;
		while (tower.x - i >= 0) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[tower.x - i][tower.y] != null && gameBoard.boardArray[tower.x - i][tower.y].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(tower.x - i, tower.y));
			++i;
		}
		// LEFT:
		i = 1;
		while (tower.y - i >= 0) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[tower.x][tower.y - i] != null && gameBoard.boardArray[tower.x][tower.y - i].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(tower.x, tower.y - i));
			++i;
		}
		// RIGHT:
		i = 1;
		while (tower.y + i < Board.DIM) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[tower.x][tower.y + i] != null && gameBoard.boardArray[tower.x][tower.y + i].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(tower.x, tower.y + i));
			++i;
		}
		
		return options;
	}
	
	
	private ArrayList<Coord> knightMoveOptions(Piece knight) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		
		// Check all possible "L" moves in any direction. Add to options array as long as cell not occupied by friendly piece.
		
		// Up left - consider (x - 2, y - 1)
		if (knight.x > 1 && knight.y > 0 && (gameBoard.boardArray[knight.x - 2][knight.y - 1] == null || gameBoard.boardArray[knight.x - 2][knight.y - 1].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x - 2, knight.y - 1));
		}
		// Up right - consider (x - 2, y + 1)
		if (knight.x > 1 && knight.y < Board.DIM - 1 && (gameBoard.boardArray[knight.x - 2][knight.y + 1] == null || gameBoard.boardArray[knight.x - 2][knight.y + 1].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x - 2, knight.y + 1));
		}
		// Right up - consider (x - 1, y + 2)
		if (knight.x > 0 && knight.y < Board.DIM - 2 && (gameBoard.boardArray[knight.x - 1][knight.y + 2] == null || gameBoard.boardArray[knight.x - 1][knight.y + 2].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x - 1, knight.y + 2));
		}
		// Right down - consider (x + 1, y + 2)
		if (knight.x < Board.DIM - 1 && knight.y < Board.DIM - 2 && (gameBoard.boardArray[knight.x + 1][knight.y + 2] == null || gameBoard.boardArray[knight.x + 1][knight.y + 2].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x + 1, knight.y + 2));
		}
		// Down right - consider (x + 2, y + 1)
		if (knight.x < Board.DIM - 2 && knight.y < Board.DIM - 1 && (gameBoard.boardArray[knight.x + 2][knight.y + 1] == null || gameBoard.boardArray[knight.x + 2][knight.y + 1].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x + 2, knight.y + 1));
		}
		// Down left - consider (x + 2, y - 1)
		if (knight.x < Board.DIM - 2 && knight.y > 0 && (gameBoard.boardArray[knight.x + 2][knight.y - 1] == null || gameBoard.boardArray[knight.x + 2][knight.y - 1].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x + 2, knight.y - 1));
		}
		// Left down - consider (x + 1, y - 2)
		if (knight.x < Board.DIM - 1 && knight.y > 1 && (gameBoard.boardArray[knight.x + 1][knight.y - 2] == null || gameBoard.boardArray[knight.x + 1][knight.y - 2].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x + 1, knight.y - 2));
		}
		// Left up - consider (x - 1, y - 2)
		if (knight.x > 0 && knight.y > 1 && (gameBoard.boardArray[knight.x - 1][knight.y - 2] == null || gameBoard.boardArray[knight.x - 1][knight.y - 2].tag.charAt(0) != knight.tag.charAt(0))) {
			options.add(new Coord(knight.x - 1, knight.y - 2));
		}
		
		return options;
	}
	

	private ArrayList<Coord> bishopMoveOptions(Piece bishop) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		char colour = bishop.tag.charAt(0);
		
		// Check all possible diagonal moves.
		
		// First, down and right...
		int i = 1;
		while (bishop.x + i < Board.DIM && bishop.y + i < Board.DIM) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[bishop.x + i][bishop.y + i] != null && gameBoard.boardArray[bishop.x + i][bishop.y + i].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(bishop.x + i, bishop.y + i));
			++i;
		}
		
		// Next, down and left...
		i = 1;
		while (bishop.x + i < Board.DIM && bishop.y - i >= 0) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[bishop.x + i][bishop.y - i] != null && gameBoard.boardArray[bishop.x + i][bishop.y - i].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(bishop.x + i, bishop.y - i));
			++i;
		}
		
		// Next, up and left...
		i = 1;
		while (bishop.x - i >= 0 && bishop.y - i >= 0) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[bishop.x - i][bishop.y - i] != null && gameBoard.boardArray[bishop.x - i][bishop.y - i].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(bishop.x - i, bishop.y - i));
			++i;
		}
		
		// Next, up and right...
		i = 1;
		while (bishop.x - i >= 0 && bishop.y + i < Board.DIM) {
			// cell is in range of board. Do not add if occupied by a friendly piece. Else, add.
			if (gameBoard.boardArray[bishop.x - i][bishop.y + i] != null && gameBoard.boardArray[bishop.x - i][bishop.y + i].tag.charAt(0) == colour) break;
			// At this point, either cell not occupied or occupied by enemy. Add to array.
			options.add(new Coord(bishop.x - i, bishop.y + i));
			++i;
		}
		
		return options;
	}	
	
	
	private ArrayList<Coord> queenMoveOptions(Piece queen) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		// Combination of tower moves and bishop moves.
		options.addAll(towerMoveOptions(queen));
		options.addAll(bishopMoveOptions(queen));
		return options;
	}
	
	
	private ArrayList<Coord> kingMoveOptions(Piece king) {
		ArrayList<Coord> options = new ArrayList<Coord>();
		char colour = king.tag.charAt(0);
		// iterate over all possible king moves.
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (king.x + i < Board.DIM && king.x + i >= 0 && king.y + j < Board.DIM && king.y + j >= 0) {
					// valid cell. If not occupied by friendly piece, add to array.
					if (gameBoard.boardArray[king.x + i][king.y + j] == null || gameBoard.boardArray[king.x + i][king.y + j].tag.charAt(0) != colour) {
						options.add(new Coord(king.x + i, king.y + j));
					}
				}
			}
		}
		
		
		return options;
	}
	
	
	// Move piece to specified location (p should initially still have old coordinates)
	private void movePiece(Piece p, int x, int y) {
		// Update piece map if moved to location is not null (killing enemy piece)
		if (gameBoard.boardArray[x][y] != null) {
			pieceMap.remove(gameBoard.boardArray[x][y].tag);
		}
		// Update gameBoard.
		gameBoard.boardArray[p.x][p.y] = null;
		gameBoard.boardArray[x][y] = p;
		// change coordinates of piece.
		p.x = x; p.y = y;
	}
	
	
	// Check for a check mate given the current board configurations, and given that the
	// piece passed as argument is the most recent piece to have moved.
	private void checkForCheckmate(Piece p) {
		// Check possible moves that the piece p could now make from its new location.
		ArrayList<Coord> moves = new ArrayList<Coord>();
		if (p.tag.charAt(1) == 'P') moves.addAll(pawnMoveOptions(p));
		else if (p.tag.charAt(1) == 'T') moves.addAll(towerMoveOptions(p));
		else if (p.tag.charAt(1) == 'N') moves.addAll(knightMoveOptions(p));
		else if (p.tag.charAt(1) == 'B') moves.addAll(bishopMoveOptions(p));
		else if (p.tag.charAt(1) == 'Q') moves.addAll(queenMoveOptions(p));
		else if (p.tag.charAt(1) == 'K') moves.addAll(kingMoveOptions(p));
		
		// Check for a collision with the king.
		for (Coord move : moves) {
			if (p.tag.charAt(0) == 'W') {
				if (move.x == pieceMap.get("BK").x && move.y == pieceMap.get("BK").y) {
					whiteWon = true;
					// Implement full check mate system later.
				}
			} else {
				if (move.x == pieceMap.get("WK").x && move.y == pieceMap.get("WK").y) {
					blackWon = true;
					// Implement full check mate system later.
				}
			}
		}
	}
	
	
	// Randomly move a black piece.
	private void opponentMove() {
		
		// A move option contains a piece to move and a coord to move to.
		class MoveOption {
			public Piece piece; public Coord coord;
			public MoveOption(Piece piece, Coord coord) {this.piece = piece; this.coord = coord;}
		}
		
		ArrayList<MoveOption> options = new ArrayList<MoveOption>();
		Piece pieceToMove = null;
		for (Piece p : pieceMap.values()) {
			// Add all possible black moves to list of possible moves.
			if (p.tag.charAt(0) == OPPCOL) {
				for (Coord move : moveOptions(p)) {
					options.add(new MoveOption(p, move));
				}
			}
		}
		
		// Now have an ArrayList of possible moves. Choose one randomly.
		MoveOption move;
		Random ran = new Random();
		move = options.get(ran.nextInt(options.size()));
		movePiece(move.piece, move.coord.x, move.coord.y);
		
		checkForCheckmate(move.piece);
		
		// Call blackVictory method if black has check mate.
		if (blackWon == true) gui.blackVictory();
		
		whiteTurn = true;
		
	}
	
	
	public static void main(String args[]) {
		Logic l = new Logic();
	}
	
}
