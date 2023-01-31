package gameSystem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BasicShapes {
	BufferedImage tempBufferedImage;
	public Shape Rect(float w,float h,Color defColor) {
		return new Shape(0, 0, w, h, defColor,true);
	}
	public Shape Rect(float x,float y,float w,float h,Color defColor) {
		return new Shape(x, y, w, h, defColor,true);
	}
	public Shape WFRect(float w,float h,Color defColor) {
		return new Shape(0, 0, w, h, defColor,false);
	}
	public Shape WFRect(float x,float y,float w,float h,Color defColor) {
		return new Shape(x, y, w, h, defColor,false);
	}
	public Shape Rect(float w,float h,String img) {
		try {
			tempBufferedImage = ImageIO.read(getClass().getClassLoader().getResource("resources/" + img));
			return new Shape(0, 0, w, h,0 ,0 ,tempBufferedImage.getWidth() ,tempBufferedImage.getHeight() ,tempBufferedImage);
		} catch (IOException e) {
			return new Shape(0, 0, w, h, Color.black, true);
		}
		
	}
	public Shape Text(String txtString,float xofs,float yofs,Color txtColor) {
		return new Shape(xofs, yofs, txtColor, txtString);
	}
	public Shape Text(String txtString,Color txtColor) {
		return new Shape(0, 0, txtColor, txtString);
	}
	public Shape Rect(float x,float y,float w,float h,String img) {
		try {
			tempBufferedImage = ImageIO.read(getClass().getClassLoader().getResource("resources/" + img));
			return new Shape(x, y, w, h,0 ,0 ,tempBufferedImage.getWidth() ,tempBufferedImage.getHeight() ,tempBufferedImage);
		} catch (IOException e) {
			return new Shape(0, 0, w, h, Color.black, true);
		}
		
	}
}
