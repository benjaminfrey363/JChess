package ui;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

import logic.OldBoard;
import logic.OldLogic;
import universal.Coord;
import logic.OldPiece;

public class OldGUI {
    private OldBoard gameBoard;
    private JFrame gameFrame;
    private JPanel chessPanel;
    private JButton[][] chessButtons;
    private OldLogic logic;
    
    private JFrame victoryFrame;
    private JPanel victoryPanel;
    private JLabel victoryLabel;
    private JButton victoryButton;

    public OldGUI(OldLogic logic) {
    	this.logic = logic;
        gameBoard = logic.gameBoard;
        gameFrame = new JFrame("Chess");
        chessPanel = new JPanel();
        chessPanel.setLayout(new GridLayout(OldBoard.DIM,OldBoard.DIM));
        chessButtons = new JButton[OldBoard.DIM][OldBoard.DIM];
        for (int i = 0; i < OldBoard.DIM; ++i) {
            for (int j = 0; j < OldBoard.DIM; ++j) {
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
    	for (int i = 0; i < OldBoard.DIM; ++i) {
    		for (int j = 0; j < OldBoard.DIM; ++j) {
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
    		chessButtons[move.x][move.y].setOpaque(true);
    	}
    }
    
    
    // Un-highlight currentMoves.
    public void removePossibleMoves() {
    	// Turn all buttons back to gray.
    	for (Coord move : logic.currentMoves) {
    		chessButtons[move.x][move.y].setBackground(null);
    	}
    }
    
    
    // Called if black gets check mate.
    public void blackVictory() {
    	gameFrame.setVisible(false);
    	
    	victoryFrame = new JFrame("Game over");
    	victoryPanel = new JPanel();
    	victoryPanel.setLayout(new GridLayout(2, 1));
    	victoryLabel = new JLabel("Black has won, game over.");
    	victoryPanel.add(victoryLabel);
    	victoryButton = new JButton("Exit");
    	victoryButton.addActionListener(e -> {
    		victoryFrame.setVisible(false);
    	});
    	victoryPanel.add(victoryButton);
    	victoryFrame.getContentPane().add(victoryPanel, BorderLayout.CENTER);
    	victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	victoryFrame.setVisible(true);
    }
    
    
    // Called if white gets check mate.
    public void whiteVictory() {
    	gameFrame.setVisible(false);
    	
    	victoryFrame = new JFrame("Game over");
    	victoryPanel = new JPanel();
    	victoryPanel.setLayout(new GridLayout(2, 1));
    	victoryLabel = new JLabel("White has won, congratulations!");
    	victoryPanel.add(victoryLabel);
    	victoryButton = new JButton("Exit");
    	victoryButton.addActionListener(e -> {
    		victoryFrame.setVisible(false);
    	});
    	victoryPanel.add(victoryButton);
    	victoryFrame.getContentPane().add(victoryPanel, BorderLayout.CENTER);
    	victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	victoryFrame.setVisible(true);
    }
    

}
