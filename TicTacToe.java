package january;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TicTacToe extends JFrame implements ActionListener {

	private class node {

		private node(int r, int c, int currentP, JButton btn) {
			this.nRow = r;
			this.nCol = c;
			this.playerPlayed = currentP;
			this.boardCondition = board;
			this.btn = btn;
		}

		public String toString() {
			return "[" + this.nRow + "," + this.nCol + "](" + this.playerPlayed + ")";
		}

		int nRow;
		int nCol;
		JButton btn;
		int playerPlayed;
		int[][] boardCondition;
	}

	// declaration of labels
	private JLabel player1Score;
	private JLabel player2Score;

	// declaration of buttons:- (i)undo button (ii)replay button (iii)reset
	// button
	private JButton undoBtn;
	private JButton replayBtn;
	private JButton resetBtn;

	// declaration of buttons:- (i)twoPlayer (ii)versusComp (iii)Player arrow
	private JButton twoPlayer;
	private JButton versusComp;
	private JButton playerArrow;

	// grid buttons
	ArrayList<JButton> buttons = new ArrayList<JButton>();

	// score of player's
	private int scoreP1;
	private int scoreP2;

	// which player's turn [1 == player one] [2 == player two]
	private int currentPlayer;

	// actual board
	int[][] board = new int[3][3];

	// movesStack
	Stack<node> sequenceStack = new Stack<>();

	public TicTacToe() {
		GridLayout layout = new GridLayout(6, 3);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 2, 30);

		// setting up label for player 1
		player1Score = new JLabel("Player 1");
		player1Score.setFont(font);
		super.add(player1Score);

		// setting up label for player arrow
		playerArrow = new JButton("<->");
		playerArrow.setFont(font);
		super.add(playerArrow);

		// setting up label for player 2
		player2Score = new JLabel("Player 2");
		player2Score.setFont(font);
		super.add(player2Score);

		// setting up buttons

		// (i) undo btn:-
		undoBtn = new JButton("Undo");
		undoBtn.setFont(font);
		undoBtn.addActionListener(this);
		super.add(undoBtn);

		// (ii) replay btn:-
		replayBtn = new JButton("Replay");
		replayBtn.setFont(font);
		replayBtn.addActionListener(this);
		super.add(replayBtn);

		// (iii) reset btn:-
		resetBtn = new JButton("Reset");
		resetBtn.setFont(font);
		resetBtn.addActionListener(this);
		super.add(resetBtn);

		// grid
		// *********************************************************
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons.add(new JButton("G" + i + j));
			}
		}

		for (int i = 0; i < buttons.size(); i++) {
			JButton temp = buttons.get(i);
			temp.setText("");
			temp.setFont(font);
			temp.addActionListener(this);
			super.add(temp);
		}
		// *********************************************************

		// (iv) two player btn:-
		twoPlayer = new JButton("Two Player");
		twoPlayer.setFont(font);
		twoPlayer.addActionListener(this);
		super.add(twoPlayer);

		// (v) versus computer btn:-
		versusComp = new JButton("Against Computer");
		versusComp.setFont(font);
		versusComp.addActionListener(this);
		super.add(versusComp);

		// Basics
		super.setTitle("Tic Tac Toe");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);
		super.setSize(800, 500);

		reset();
	}

	// Helper Function's

	private void initialRandomization() {
		int random = (int) (Math.random() * 2 + 1);
		currentPlayer = random;
		changeArrow();
	}

	private void changeArrow() {
		if (currentPlayer == 1) {
			playerArrow.setText("<- 1");
		} else {
			playerArrow.setText("2 ->");
		}
	}

	private int swapPlayers() {
		if (currentPlayer == 1) {
			currentPlayer = 2;
		} else {
			currentPlayer = 1;
		}
		changeArrow();
		return currentPlayer;
	}

	// player 1 == X and player 2 == O
	private boolean checkForWin(int row, int col) {
		int count = 0;
		int r = row, c = 0;
		// horizontal
		while (c < 3) {
			if (board[row][c] == currentPlayer) {
				count++;
			}
			c++;
		}
		if (count == 3) {
			return true;
		}
		r = 0;
		c = col;
		count = 0;
		// vertical
		while (r < 3) {
			if (board[r][col] == currentPlayer) {
				count++;
			}
			r++;
		}
		if (count == 3) {
			return true;
		}

		// main diagonal [Diagonal 1](i = j)
		count = 0;
		if (row == col) {
			int i = 0;
			while (i < 3) {
				if (board[i][i] == currentPlayer) {
					count++;
				}
				i++;
			}
			if (count == 3) {
				return true;
			}
		}

		// secondary diagonal [Diagonal 2](i + j = 2)
		count = 0;
		if (col + row == 2) {
			r = 2;
			c = 0;
			while (r > -1) {
				if (board[r][c] == currentPlayer) {
					count++;
				}
				r--;
				c++;
			}
		}

		return count == 3 ? true : false;
	}

	private void reset() {
		for (int i = 0; i < buttons.size(); i++) {
			JButton temp = buttons.get(i);
			temp.setEnabled(true);
			temp.setText("");
		}
		sequenceStack = new Stack<>();
		board = new int[3][3];
		initialRandomization();
	}

	private void undo() {
		if (sequenceStack.size() != 0) {
			node deletedNode = sequenceStack.pop();
			deletedNode.btn.setEnabled(true);
			deletedNode.btn.setText("");
			board[deletedNode.nRow][deletedNode.nCol] = 0;
			swapPlayers();
		}
	}

	private void end() {
		playerArrow.setText("yay " + currentPlayer + " won");
	}

	private void display() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		int k = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton temp = buttons.get(k);
				if (e.getSource() == temp) {
					twoPlayerUpdateDataBase(temp, i, j);
				}
				k++;
			}
		}

		if (e.getSource() == twoPlayer) {
			reset();
		}

		if (e.getSource() == undoBtn) {
			undo();
		}

		if (e.getSource() == resetBtn) {
			reset();
		}

		if (e.getSource() == versusComp) {
			playerArrow.setText("Against Comp.");
		}

	}

	// Two Player Mode
	public void twoPlayerUpdateDataBase(JButton temp, int r, int c) {
		board[r][c] = currentPlayer;
		temp.setText(currentPlayer == 1 ? "X" : "O");
		sequenceStack.push(new node(r, c, currentPlayer, temp));
		temp.setEnabled(false);
		if (checkForWin(r, c)) {
			end();
			return;
		}
		if (sequenceStack.size() == 9) {
			playerArrow.setText("Draw");
			end();
			return;
		}
		swapPlayers();
	}

}