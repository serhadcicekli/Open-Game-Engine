package gameSystem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
public class GameRuntime {
	Random rand = new Random();
	public float axisH;
	public float axisV;
	public float axisHraw;
	public float axisVraw;
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZ = 1f;
	BasicShapes basicShapes = new BasicShapes();
	public final int screenWidth = 800;
	public final int screenHeight = 600;
	public int FPS = 60;
	// Initialization code here:
	GameObject player;
	GameObject floor;
	GameObject floor2;
	GameObject wall;
	GameObject box;
	Trigger jumpTrigger;
	public void gameStart(Graphics2D g2d,GameController gc) {
		jumpTrigger = gc.newTrigger("jumpTest");
		box = gc.newGameObject("box");
		floor = gc.newGameObject("floor");
		floor2 = gc.newGameObject("floor2");
		player = gc.newGameObject("player");
		wall = gc.newGameObject("wall");
		box.width = 40;
		box.height = 60;
		box.xPos = -100;
		box.mass = 0.1f;
		box.damping = 0.5f;
		box.addShape(0, basicShapes.Rect(40, 60, Color.blue));
		box.physicsEnabled = true;
		player.width = 20;
		player.height = 50;
		player.addShape(0, basicShapes.Rect(20, 50, Color.green));
		player.physicsEnabled = true;
		player.mass = 0.1f;
		player.damping = 1;
		floor.width = 800;
		floor.height = 50;
		floor.yPos = -275;
		floor.addShape(0, basicShapes.Rect(800, 50, Color.white));
		floor.physicsEnabled = true;
		floor.fixed = true;
		floor2.width = 800;
		floor2.height = 50;
		floor2.xPos = 800;
		floor2.yPos = -275;
		floor2.addShape(0, basicShapes.Rect(800, 50, Color.red));
		floor2.physicsEnabled = true;
		floor2.fixed = true;
		wall.width = 40;
		wall.height = 120;
		wall.addShape(0, basicShapes.Rect(40, 120, Color.white));
		wall.physicsEnabled = true;
		wall.fixed = true;
		wall.xPos = 380;
		wall.yPos = -190;
		jumpTrigger.attach(player);
		jumpTrigger.yOffset = -25;
		jumpTrigger.width = 20;
		jumpTrigger.height = 10;
	}
	public void update(Graphics2D g2d,GameController gc) {
		
		if(jumpTrigger.collidingObjectCount > 0 && axisVraw > 0) {
			player.yv = 7;
			player.yPos += 2;
		}
		player.xv = axisH * 10;
		cameraX += (((float)Math.floor((player.xPos + 400)/800)*800) - cameraX) / 10;
	}
}
