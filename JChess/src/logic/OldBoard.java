package logic;

public class OldBoard {
    public static int DIM = 8;
    public OldPiece boardArray[][] = new OldPiece[DIM][DIM];

    // Initialize board array.
    // Black begins in row boardArray[0][-], white begins
    // in row boardArray[6][-].
    public OldBoard() {
    	for (int i = 0; i < OldBoard.DIM; ++i) {
    		for (int j = 0; j < OldBoard.DIM; ++j) {
    			boardArray[i][j] = new OldPiece(i, j);
    		}
    	}
    	
        boardArray[0][0].tag = "BT1"; boardArray[0][7].tag = "BT2";
        boardArray[0][1].tag = "BN1"; boardArray[0][6].tag = "BN2";
        boardArray[0][2].tag = "BB1"; boardArray[0][5].tag = "BB2";
        boardArray[0][3].tag = "BQ"; boardArray[0][4].tag = "BK";

        for (int i = 0; i < DIM; ++i) boardArray[1][i].tag = "BP" + (8 - i);

        for (int i = 2; i < DIM - 2; ++i) {
            for (int j = 0; j < DIM; ++j) {
            	// Null out muddle of the board.
                boardArray[i][j] = null;
            }
        }

        for (int i = 0; i < DIM; ++i) boardArray[6][i].tag = "WP" + (i + 1);

        boardArray[7][0].tag = "WT1"; boardArray[7][7].tag = "WT2";
        boardArray[7][1].tag = "WN1"; boardArray[7][6].tag = "WN2";
        boardArray[7][2].tag = "WB1"; boardArray[7][5].tag = "WB2";
        boardArray[7][3].tag = "WQ"; boardArray[7][4].tag = "WK";
    }
    
    
    // Copy constructor.
    public OldBoard(OldBoard b) {
    	for (int i = 0; i < OldBoard.DIM; ++i) {
    		for (int j = 0; j < OldBoard.DIM; ++j) {
    			if (b.boardArray[i][j] == null) this.boardArray[i][j] = null;
    			else this.boardArray[i][j] = new OldPiece(b.boardArray[i][j]);
    		}
    	}
    }
}
