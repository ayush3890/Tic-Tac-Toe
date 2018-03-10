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

public class TicTacToe_V2 extends JFrame implements ActionListener {

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

	private class pastHistory {

		public pastHistory(int r, int c) {
			this.row = r;
			this.col = c;
			this.fun(r, c);
		}

		private int row;
		private int col;
		private ArrayList<ArrayList<pair>> boardCond = new ArrayList<>();
		private int l;
		private int m;

		public void fun(int r, int c) {
			// vertical
			l = 0;
			m = this.col;
			ArrayList<pair> temp = new ArrayList<>();
			while (l < 3) {
				if (l == this.row && m == this.col) {
					l++;
					continue;
				}
				temp.add(new pair(l, m));
				if (board[l][m] != currentPlayer)
					boardCond.add(temp);
				l++;
			}

			// horizontal
			l = this.row;
			m = 0;
			temp = new ArrayList<>();

			while (m < 3) {
				if (l == this.row && m == this.col) {
					m++;
					continue;
				}
				temp.add(new pair(l, m));
				if (board[l][m] != currentPlayer)
					boardCond.add(temp);
				m++;
			}
			// diag 1
			temp = new ArrayList<>();
			if (r == c) {
				l = 0;
				m = 0;
				while (l < 3) {
					if (l == this.row && m == this.col) {
						l++;
						m++;
						continue;
					}
					temp.add(new pair(l, m));
					if (board[l][m] != currentPlayer)
						boardCond.add(temp);
					l++;
					m++;
				}
			}
			// diag 2
			temp = new ArrayList<>();
			if (r + c == 2) {
				l = 2;
				m = 0;
				while (l >= 0) {
					if (l == this.row && m == this.col) {
						l--;
						m++;
						continue;
					}
					temp.add(new pair(l, m));
					if (board[l][m] != currentPlayer)
						boardCond.add(temp);
					l--;
					m++;
				}
			}
		}

	}

	private class pair {
		public pair(int r, int c) {
			this.row = r;
			this.col = c;
		}

		@Override
		public String toString() {
			return "(" + this.row + ":" + this.col + ") ";
		}

		public int row;
		public int col;
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

	// =============================================
	// Versus Computer Data Member's
	Stack<pastHistory> userSeqStack = new Stack<>();
	Stack<pastHistory> compSeqStack = new Stack<>();
	// =============================================

	public TicTacToe_V2() {
		GridLayout layout = new GridLayout(6, 3);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 2, 30);

		// setting up label for player 1
		player1Score = new JLabel("Player");
		player1Score.setFont(font);
		super.add(player1Score);

		// setting up label for player arrow
		playerArrow = new JButton("<->");
		playerArrow.setFont(font);
		super.add(playerArrow);

		// setting up label for player 2
		player2Score = new JLabel("Computer");
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
	}

	// Helper Function's
	private void initialRandomization() {
		int random = (int) (Math.random() * 2 + 1);
		// currentPlayer = random;
		currentPlayer = 1;
		changeArrow();
	}

	private void changeArrow() {
		if (currentPlayer == 1) {
			playerArrow.setText("<- 1");
		} else {
			playerArrow.setText("Computer ->");
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
		String s = "Computer";
		if (currentPlayer == 1) {
			s = "PLayer";
		}
		playerArrow.setText("yay " + s + " won");
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

	private void displayCond(ArrayList<ArrayList<pair>> arr) {
		for (int i = 0; i < arr.size(); i++) {
			for (int j = 0; j < arr.get(i).size(); j++) {
				System.out.print(arr.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int k = 0;
		currentPlayer = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton temp = buttons.get(k);
				if (e.getSource() == temp)
					versus_comp_user_turn_Update(i, j, temp);
				k++;
			}
		}
	}

	private void versus_comp_user_turn_Update(int i, int j, JButton temp) {
		// Currently The User's Turn Is Going and this function update's the
		// database
		// (i)
		board[i][j] = currentPlayer;

		// (ii)
		sequenceStack.push(new node(i, j, currentPlayer, temp));

		// (iii)
		userSeqStack.push(new pastHistory(i, j));
		
		// (iv)
		temp.setText("X");
		
		// (v)
		temp.setEnabled(false);
		
		// (vi)
		swapPlayers();

		versus_comp_comp_turn_update();
	}

	private void versus_comp_comp_turn_update() {
		pastHistory user_stack_seq_peek = null;
		if (userSeqStack.size() != 0) {
			user_stack_seq_peek = userSeqStack.peek();
//			displayCond(user_stack_seq_peek.boardCond);
		}

		pastHistory comp_stack_seq_peek = null;
		if (compSeqStack.size() != 0) {
			comp_stack_seq_peek = compSeqStack.peek();
//			displayCond(comp_stack_seq_peek.boardCond);
		}

		if (comp_stack_seq_peek != null) {
			ArrayList<pair> list = which_pair_to_be_returned(comp_stack_seq_peek);
			if (list.size() == 1) {
				// computer will win
				pair p = list.get(0);
				function(p);
				end();
				System.out.println("End's");
				return;
			} else {
				pair p = list.get(0);
				function(p);
			}
		}

		if (user_stack_seq_peek != null) {
			ArrayList<pair> list = which_pair_to_be_returned(user_stack_seq_peek);
			if (list.size() == 1) {
				// player can win
				pair p = list.get(0);
				function(p);
			} else {
				pair p = list.get(0);
				function(p);
			}
		}
		
	}

	private ArrayList<pair> which_pair_to_be_returned(pastHistory pH) {
		ArrayList<pair> p = null;
		for (int i = 0; i < pH.boardCond.size(); i++) {
			p = pH.boardCond.get(i);
			if (p.size() == 1) {
				return p;
			}
		}

		return p;
	}

	private void function(pair p) {
		int r = p.row;
		int c = p.col;
		board[r][c] = currentPlayer;
		compSeqStack.push(new pastHistory(r, c));
		JButton btnToBeChan = buttons.get(3 * r + c);
		sequenceStack.push(new node(r, c, currentPlayer, btnToBeChan));
		btnToBeChan.setEnabled(false);
		btnToBeChan.setText("O");
		
		swapPlayers();
	}
}
