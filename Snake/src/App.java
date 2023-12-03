import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public App() {
		
		JFrame frame = new JFrame();
		
		frame.setPreferredSize(new Dimension(500, 600));
		
		JPanel menu = new JPanel();	
		setPreferredSize(new Dimension(500, 100));
		frame.add(menu);
		
		JButton start = new JButton("Play");
		menu.add(start);
		
		JButton again = new JButton("Play Again");
		menu.add(again);
		

		GamePanel gamePanel = new GamePanel();
		
		/**JLabel scoreLabel = new JLabel("Score: " + gamePanel.getScore());
		scoreLabel.setForeground(Color.RED);
		menu.add(scoreLabel);**/

		frame.add(gamePanel, BorderLayout.SOUTH);	
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Snake");
		
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					gamePanel.start();	
					gamePanel.requestFocus();
					gamePanel.setFocusable(true);
			}
        });
		
		again.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gamePanel.isRunning()) {
				    gamePanel.stop();
				    }
					gamePanel.clear();	
					gamePanel.start();
					gamePanel.requestFocus();
					gamePanel.setFocusable(true);
			}
        });
		
	}

	public static void main(String[] args) {
		
		new App();
	}
	
}
