package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gameSystem.GameController;
import gameSystem.GameObject;
import gameSystem.GameRuntime;
import gameSystem.KeySystem;
import gameSystem.Spring;
public class GamePanel extends JPanel implements Runnable{
	
	KeySystem keySystem = new KeySystem();
	private static final long serialVersionUID = -3773031973775166541L;
	GameRuntime goRuntime = new GameRuntime();
	final int screenWidth = goRuntime.screenWidth;
	final int screenHeight = goRuntime.screenHeight;
	int FPS = goRuntime.FPS;
	Thread gameThread;
	GameController goController = new GameController();
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keySystem);
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	
	public void run() {
		setFocusable(true);
		requestFocus(); 
		double drawInterval = 1000000000/FPS;
		while(gameThread != null) {
			if(!paused) {
			repaint();
			}
			try {
				Thread.sleep((long)drawInterval/1000000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	float cameraX;
	float cameraY;
	int cnt = 0;
	boolean paused = false;
	int fixedTickCount = 0;
	int fixedWaitScale = 1;
	boolean fixedTick = false;
	boolean initialTick = true;
	boolean firstTick = true;
	boolean intro = true;
	int introTick = 0;
	float physGravity = -0.4f;
	float antiFlicker = (float)Math.sqrt(Math.abs(physGravity));
	float aH;
	float aV;
	float aHr;
	float aVr;
	BufferedImage introImage;
	float lerp(float x,float y,float a) {
		return ((1 - a) * x) + (a * y);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(initialTick) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			        RenderingHints.VALUE_ANTIALIAS_ON);
			goController.screenW = screenWidth;
			goController.screenH = screenHeight;
			try {
				introImage = ImageIO.read(getClass().getResource("/main/LoadScreen.png"));
			} catch (IOException e) {
				introImage = null;
			}
			initialTick = false;
		}
		else {
			if(intro) {
				if(introImage != null) {
					g2.drawImage(introImage, (screenWidth / 2) - 400, (screenHeight / 2) - 300, (screenWidth / 2) + 400, (screenHeight / 2) + 300, 0, 0, 800, 600, null);
					introTick += 1;
					if(introTick > FPS * 3) {
						intro = false;
					}
				}
			}
			else {
				if(firstTick) {
					goRuntime.gameStart(g2, goController);
					firstTick = false;
				}
				else{
					if(fixedTick) {
					fixedTick = false;
					}
					fixedTickCount += 1;
					if(fixedTickCount >= fixedWaitScale) {
						fixedTick = true;
					}
					cameraX = goRuntime.cameraX;
					cameraY = goRuntime.cameraY;
					aHr = 0;
					aVr = 0;
					if(keySystem.dkey) {
						aHr += 1;
					}
					if(keySystem.akey) {
						aHr -= 1;
					}
					if(keySystem.wkey) {
						aVr += 1;
					}
					if(keySystem.skey) {
						aVr -= 1;
					}
					aH += (aHr - aH) / 8 * goController.timeScale;
					aV += (aVr - aV) / 8 * goController.timeScale;
					goRuntime.axisH = aH;
					goRuntime.axisV = aV;
					goRuntime.axisHraw = aHr;
					goRuntime.axisVraw = aVr;
					goRuntime.update(g2,goController);
					goController.physicsUpdate(antiFlicker);
					for (GameObject itemObject : goController.gameObjects) {
						if(fixedTick) {
							itemObject.fixedTick(physGravity,goController.timeScale);
						}
						itemObject.drawThis(g2,screenWidth,screenHeight,cameraX,cameraY,goRuntime.cameraZ);
					}
					for(Spring itemSpring : goController.gameSprings) {
						if(fixedTick) {
							itemSpring.fixedTick(physGravity);
						}
					}
				}
			}
		}
		

		g2.dispose();
	}
}
