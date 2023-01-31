package gameSystem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
public class GameObject {
	List<Shape> shapes = new ArrayList<>();
	List<Integer> shapeID = new ArrayList<Integer>();
	public List<Integer> physicLayers = new ArrayList<>();
	public float mass = 1;
	public boolean isColliding = false;
	public boolean fixed = false;
	public boolean physicsWorldLimit = false;
	public boolean physicsEnabled = false;
	public float airfriction = 0.01f;
	public float surfacefriction = 0.2f;
	public float damping = 0.4f;
	public float xv;
	public float yv;
	public String goName;
	public float xPos = 0;
	public float yPos = 0;
	public float width = 0;
	public float height = 0;
	public int screenWidth;
	public int screenHeight;
	/*
    Source Image                       Destination panel
	sx1, sy1      
	+---------------+---------+        +-----------------------------+
	|               |         |        |                             |
	| region to     |         |        | dx1, dy1                    |
	|        draw   |         |        |    +----------+             |    
	|               |         |        |    |          |             |
	+---------------+         |        |    |          |             | 
	|           sx2, sy2      |        |    +----------+             |     
	|                         |        |            dx2, dy2         |
	|                         |        |                             |
	+-------------------------+        +-----------------------------+
*/
	public void addPhysicLayer(int layerID) {
		if(!physicLayers.contains(layerID)) {
			physicLayers.add(layerID);
		}
	}
	public void removePhysicLayer(int layerID) {
		if(physicLayers.contains(layerID)) {
			physicLayers.remove(physicLayers.indexOf(layerID));
		}
	}
	public void autoMass() {
		mass = width * height / 2500;
	}
	public void addShape(int ID,Shape shape) {
		if(!shapeID.contains(ID)) {
			shapeID.add(ID);
			shapes.add(shape);
		}
	}
	public void removeShape(int ID) {
		if(shapeID.contains(ID)) {
			shapes.remove(shapeID.indexOf(ID));
			shapeID.remove(shapeID.indexOf(ID));
		}
	}
	public Shape getShape(int ID) {
		if(shapeID.contains(ID)) {
			return shapes.get(shapeID.indexOf(ID));
		}
		else {
			return null;
		}
	}
	public GameObject(String name) {
		physicLayers.add(0);
		goName = name;
	}
	/*
	public void loadImageFromResource(String imagepathString) {
		try {
			goImage = ImageIO.read(getClass().getResource(imagepathString));
			haveImage = true;
			sourceX = 0;
			sourceY = 0;
			sourceW = goImage.getWidth();
			sourceH = goImage.getHeight();
			width = goImage.getWidth();
			height = goImage.getHeight();
		} catch (IOException e) {
			haveImage = false;
		}
	}
	*/
	
	public void drawThis(Graphics2D g2ds,int screenW,int screenH,float cx,float cy,float cz) {
		for (Shape shapei : shapes) {
			shapei.renderSelf(g2ds, cx, cy, xPos, yPos, screenW, screenH,cz);
		}
		/*
		if(haveImage) {
			g2ds.drawImage(goImage
					, (int)((xPos - (int)cx) + (screenW / 2) - (width / 2))		//x1
					, (int)((screenH / 2) - (yPos - (int)cy) - (height / 2))	//y1
					, (int)((xPos - (int)cx) + (screenW / 2) - (width / 2)) + (int)width		//x2
					, (int)((screenH / 2) - (yPos - (int)cy) - (height / 2)) + (int)height//y2
					, sourceX			//sx1
					, sourceY			//sy1
					, sourceX + sourceW			//sx2
					, sourceY + sourceH			//sy2
					, null);
			}
		else {
			g2ds.setColor(color);
			g2ds.fillRect(
					(int)((xPos - (int)cx) + (screenW / 2) - (width / 2))
						 ,(int)((screenH / 2) - (yPos - (int)cy) - (height / 2))
						 ,(int)width
						 ,(int)height
						 );
		}
		*/
	}
	float lerp(float x,float y,float a) {
		return ((1 - a) * x) + (a * y);
	}
	float oldG;
	float antiFlickeryv;
	public void fixedTick(float gravity,float timeScale) {
		if(oldG != gravity) {

			if(gravity >= 0) {
				antiFlickeryv = (float) Math.sqrt(Math.abs(gravity));
			}
			else {
				antiFlickeryv = (float) Math.sqrt(Math.abs(gravity)) * -1;
			}
		}
		if(physicsEnabled) {
		if(!fixed) {
		xPos += xv * timeScale;
		yPos += yv * timeScale;
		yv += gravity * timeScale;
		xv = lerp(xv, xv * (1 - airfriction), timeScale) ;
		yv = lerp(yv, yv * (1 - airfriction), timeScale) ;
		}
		
		if(physicsWorldLimit) {
		if(Math.abs(yPos) > ((screenHeight / 2) - (height / 2))) {
			if(yPos >= 0) {
				yPos = ((screenHeight / 2) - (height / 2));
			}
			else {
				yPos = -1 * ((screenHeight / 2) - (height / 2));
			}
			
			yv = (float) (yv * (1 - damping) * -1) + antiFlickeryv;
			xv = xv * (1 - surfacefriction);
		}
		
		if(Math.abs(xPos) > (screenWidth / 2) - (width / 2)) {
			if(xPos >= 0) {
				xPos = (screenWidth / 2) - (width / 2);
			}
			else {
				xPos = -1 * ((screenWidth / 2) - (width / 2));
			}
			yv = yv * (1 - surfacefriction);
			xv = xv * (1 - damping) * -1;
		}
		}
		}
	}
}
