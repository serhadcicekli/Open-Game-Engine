package gameSystem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Shape {
	public int shapeType = 0;
	public BufferedImage goImage;
	public boolean haveImage = false;
	public int sourceX;
	public int sourceY;
	public int sourceW;
	public int sourceH;
	public float width = 0;
	public float height = 0;
	public float xOffset = 0;
	public float yOffset = 0;
	public Color color = Color.white;
	public Shape(String shapeString,float ofsX,float ofsY,float w,float h,Color clr) {
			if(shapeString.toLowerCase() == "rect") {
				shapeType = 1;
			}
			if(shapeString.toLowerCase() == "circle") {
				shapeType = 2;
			}
			if(shapeString.toLowerCase() == "empty") {
				shapeType = 0;
			}
		color = clr;
		haveImage = false;
		width = w;
		height = h;
		xOffset = ofsX;
		yOffset = ofsY;
	}
	public Shape(String shapeString,float ofsX,float ofsY,float w,float h,int sx,int sy,int sw,int sh,BufferedImage img) {
		if(shapeString.toLowerCase() == "rect") {
			shapeType = 1;
		}
		if(shapeString.toLowerCase() == "circle") {
			shapeType = 2;
		}
		if(shapeString.toLowerCase() == "empty") {
			shapeType = 0;
		}
	haveImage = true;
	goImage = img;
	width = w;
	height = h;
	xOffset = ofsX;
	yOffset = ofsY;
	sourceX = sx;
	sourceY = sy;
	sourceW = sw;
	sourceH = sh;
}
	public void setShapeType(String shapeString) {
		if(shapeString.toLowerCase() == "rect") {
			shapeType = 1;
		}
		if(shapeString.toLowerCase() == "circle") {
			shapeType = 2;
		}
		if(shapeString.toLowerCase() == "empty") {
			shapeType = 0;
		}
	}
	
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
	public void renderSelf(Graphics2D g2ds,float camx,float camy,float centerx,float centery,int screenW,int screenH,float camz) {
		if(haveImage) {
			g2ds.drawImage(goImage
					, (int)(((centerx + xOffset - camx) * camz) + (screenW / 2) - (width / 2 * camz))		//x1
					, (int)((screenH / 2) - ((centery + yOffset - camy) * camz) - (height / 2 * camz))	//y1
					, (int)(((centerx + xOffset - camx) * camz) + (screenW / 2) + (width / 2 * camz))		//x2
					, (int)((screenH / 2) - ((centery + yOffset - camy) * camz) + (height / 2 * camz))//y2
					, sourceX			//sx1
					, sourceY			//sy1
					, sourceX + sourceW			//sx2
					, sourceY + sourceH			//sy2
					, null);
			}
		else {
			g2ds.setColor(color );
			g2ds.fillRect(
					(int)(((centerx + xOffset - camx) * camz) + (screenW / 2) - (width / 2 * camz))
						 ,(int)((screenH / 2) - ((centery + yOffset - camy) * camz) - (height / 2 * camz))
						 ,(int)(width * camz)
						 ,(int)(height * camz)
						 );
		}
	}
}
