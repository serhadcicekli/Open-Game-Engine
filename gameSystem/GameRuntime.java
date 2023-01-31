package gameSystem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.BooleanControl;
public class GameRuntime {
	Random rand = new Random();
	public float axisH;
	public float axisV;
	public float axisHraw;
	public float axisVraw;
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZ = 1;
	public float timeScale = 1f;
	BasicShapes basicShapes = new BasicShapes();
	public final int screenWidth = 800;
	public final int screenHeight = 600;
	public int FPS = 60;
	GameObject floor;
	GameObject player;
	GameObject box;
	GameObject box2;
	GameObject txt;
	Trigger jumpTrigger;
	public void keyPressed(KeyEvent e) {
		
	}
	public void keyReleased(KeyEvent e) {
		
	}
	public void gameStart(Graphics2D g2d,GameController gc) {
		
		box = gc.newGameObject("box");
		box2 = gc.newGameObject("box2");
		player = gc.newGameObject("player");
		txt = gc.newGameObject("txt");
		jumpTrigger = gc.newTrigger("jumpTrig");
		jumpTrigger.attach(player);
		jumpTrigger.width = 20;
		jumpTrigger.height = 2;
		jumpTrigger.yOffset = -50;
		txt.addShape(0, basicShapes.Text("This is a text with physics!",-70,0, Color.white));
		txt.physicsWorldLimit = true;
		txt.width = 150;
		txt.height = 20;
		txt.physicsEnabled = true;
		box.width = 60;
		box.height = 60;
		box.addShape(0, basicShapes.Rect(60, 60, Color.red));
		box.physicsEnabled = true;
		box.physicsWorldLimit = true;
		player.addPhysicLayer(1);
		box2.yPos = -100;
		box2.width = 60;
		box2.height = 60;
		box2.addShape(0, basicShapes.Rect(60, 60, Color.blue));
		box2.physicsEnabled = true;
		box2.physicsWorldLimit = true;
		player.width = 20;
		player.height = 50;
		player.physicsEnabled = true;
		player.physicsWorldLimit = true;
		player.addShape(0, basicShapes.Rect(20, 50, Color.green));
		player.damping = 0.8f;
		player.surfacefriction = 0;
		floor = gc.newGameObject("floor");
		floor.addShape(0, basicShapes.Rect(800, 50, Color.white));
		floor.addPhysicLayer(1);
		box2.removePhysicLayer(0);
		box2.addPhysicLayer(1);
		floor.width = 800;
		floor.yPos = -1000;
		floor.height = 50;
		floor.physicsEnabled = true;
		floor.fixed = true;
		floor.physicsWorldLimit = true;

	}
	public void update(Graphics2D g2d,GameController gc) {
		player.xv = axisH * 20;
		if(axisVraw > 0 && jumpTrigger.collidingObjectCount > 0) {
			player.yv = 10;
			player.yPos += 2;
		}
	}
}
