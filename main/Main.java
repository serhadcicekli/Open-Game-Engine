package main;
import javax.swing.JFrame;
import gameSystem.Sound;
import gameSystem.Visual;
public class Main {
	public static void main(String[] args) {
		new Sound("main/Start.wav", true).play();
		JFrame window = new JFrame();
		window.setIconImage(new Visual("main/Icon.png",true).image);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.addWindowListener(new WindowSystem());
		window.setResizable(false);
		window.setTitle("Game Engine");
		window.setVisible(true);
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		gamePanel.startGameThread();

	}
}
