package gameSystem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Shape {
	public int shapeType = 0;
	public BufferedImage goImage;
	boolean filledR = true;
	public boolean haveImage = false;
	public boolean haveText = false;
	public int sourceX;
	public int sourceY;
	public int sourceW;
	public int sourceH;
	public float width = 0;
	public float height = 0;
	public float xOffset = 0;
	public float yOffset = 0;
	public Color color = Color.white;
	public String shapeText;
	public Shape(float ofsX,float ofsY,float w,float h,Color clr,boolean filled) {
		haveText = false;
		filledR = filled;
		color = clr;
		haveImage = false;
		width = w;
		height = h;
		xOffset = ofsX;
		yOffset = ofsY;
	}
	public Shape(float ofsX,float ofsY,float w,float h,int sx,int sy,int sw,int sh,BufferedImage img) {
	haveText = false;
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
	public Shape(float ofsX,float ofsY,Color txtColor, String shapeTxt) {
		color = txtColor;
		haveText = true;
		shapeText = shapeTxt;
		xOffset = ofsX;
		yOffset = ofsY;
	}
	void drawRect(int x1,int y1,int x2,int y2,boolean filled,Graphics2D g2d) {
		if(filled) {
			g2d.fillRect(x1, y1, x2 - x1, y2 - y1);
		}
		else {
			
			g2d.drawRect(x1, y1, x2 - x1 - 1, y2 - y1 - 1);
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
	int clamp(int a,int min,int max) {
		if(a > max) {
			return max;
		}
		else if(a < min) {
			return min;
		}
		else {
			return a;
		}
	}
	float clamp(float a,float min,float max) {
		if(a > max) {
			return max;
		}
		else if(a < min) {
			return min;
		}
		else {
			return a;
		}
	}
	public void renderSelf(Graphics2D g2ds,float camx,float camy,float centerx,float centery,int screenW,int screenH,float camz) {
		if(haveText) {
			g2ds.setColor(color);
			g2ds.drawString(shapeText, ((centerx + xOffset - camx) * camz) + (screenW / 2), (screenH / 2) - ((centery + yOffset - camy) * camz));
		}
		else{
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
			drawRect((int)clamp(((centerx + xOffset - camx) * camz) + (screenW / 2) - (width / 2 * camz),0,screenW)
					,(int)clamp((screenH / 2) - ((centery + yOffset - camy) * camz) - (height / 2 * camz),0,screenH)
					,(int)clamp(((centerx + xOffset - camx) * camz) + (screenW / 2) + (width / 2 * camz),0,screenW)
					,(int)clamp((screenH / 2) - ((centery + yOffset - camy) * camz) + (height / 2 * camz),0,screenH)
					,filledR
					, g2ds);
			/*
			g2ds.fillRect(
						  clamp((int)(((centerx + xOffset - camx) * camz) + (screenW / 2) - (width / 2 * camz)),0,screenW)
						 ,clamp((int)((screenH / 2) - ((centery + yOffset - camy) * camz) - (height / 2 * camz)),0,screenH)
						 ,(int)width
						 ,(int)height
						 );
			*/
		}
		}
	}
}
