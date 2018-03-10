
package january;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame implements ActionListener {

	private JButton btn;

	public Menu() {
		btn = new JButton("Next Window");
		btn.addActionListener(this);
		super.add(btn);
		super.setTitle("Tic Tac Toe");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);
		super.setSize(800, 500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btn) {
			dispose();
			new TicTacToe();
		}
	}

}
