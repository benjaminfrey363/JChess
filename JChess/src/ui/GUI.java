package ui;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

import logic.Board;
import logic.Logic;
import logic.Logic.Coord;
import logic.Piece;

public class GUI {
    private Board gameBoard;
    private JFrame gameFrame;
    private JPanel chessPanel;
    private JButton[][] chessButtons;
    private Logic logic;

    public GUI(Logic logic) {
    	this.logic = logic;
        gameBoard = logic.gameBoard;
        gameFrame = new JFrame("Chess");
        chessPanel = new JPanel();
        chessPanel.setLayout(new GridLayout(Board.DIM,Board.DIM));
        chessButtons = new JButton[Board.DIM][Board.DIM];
        for (int i = 0; i < Board.DIM; ++i) {
            for (int j = 0; j < Board.DIM; ++j) {
            	chessButtons[i][j] = new JButton();
                chessPanel.add(chessButtons[i][j]);
                BoardButtonListener listener = new BoardButtonListener(i, j);
                chessButtons[i][j].addActionListener(listener);
            }
        }
        gameFrame.getContentPane().add(chessPanel, BorderLayout.CENTER);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	gameFrame.pack();
    	gameFrame.setVisible(true);
    	
    	updateBoard();
    }
    
    
    // Register a BoardButtonListener for each board button. Will
    // listen for buttons being pressed, calls a method in logic
    // if so.
    private class BoardButtonListener implements ActionListener {
    	// Coordinates of button
    	private int x; private int y;
    	
    	public BoardButtonListener(int x, int y) {
    		this.x = x; this.y = y;
    	}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			logic.reactToBoardButtonPressed(x, y);
		}
    
    }
    
    
    // Updates button text according to current board placement.
    public void updateBoard() {
    	for (int i = 0; i < Board.DIM; ++i) {
    		for (int j = 0; j < Board.DIM; ++j) {
    			if (gameBoard.boardArray[i][j] != null) {
    				chessButtons[i][j].setText(gameBoard.boardArray[i][j].tag);
    			} else {
    				chessButtons[i][j].setText("");
    			}
    		}
    	}
    }
    
    
    // Highlight possible moves based on current contents of currentMoves array.
    public void displayPossibleMoves() {
    	// Turn all buttons green.
    	for (Coord move : logic.currentMoves) {
    		chessButtons[move.x][move.y].setBackground(Color.GREEN);
    	}
    }
    
    
    // Un-highlight currentMoves.
    public void removePossibleMoves() {
    	// Turn all buttons back to gray.
    	for (Coord move : logic.currentMoves) {
    		chessButtons[move.x][move.y].setBackground(null);
    	}
    }
    

}
