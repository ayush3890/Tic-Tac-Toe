package january;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultPage extends JFrame implements ActionListener {

	private JLabel resultWindow;
	private JButton exitBtn;
	private JButton playAgainBtn;

	public ResultPage(int playerWon) {
		Font font = new Font("Comic Sans MS", 2, 30);
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);

		resultWindow = new JLabel("player " + playerWon + " has won");
		resultWindow.setFont(font);

		playAgainBtn = new JButton("Play Again");
		playAgainBtn.setFont(font);
		playAgainBtn.addActionListener(this);

		exitBtn = new JButton("Exit");
		exitBtn.setFont(font);
		exitBtn.addActionListener(this);

		super.setTitle("Tic Tac Toe");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);
		super.setSize(800, 500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exitBtn) {
			dispose();
		}

		if (e.getSource() == playAgainBtn) {
			dispose();
			new Menu();
		}
	}
}
