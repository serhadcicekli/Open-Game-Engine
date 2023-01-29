package gameSystem;

import java.awt.Color;

public class BasicShapes {
	public Shape Rect(float w,float h,Color defColor) {
		return new Shape("rect", 0, 0, w, h, defColor);
	}
	public Shape Rect(float x,float y,float w,float h,Color defColor) {
		return new Shape("rect", x, y, w, h, defColor);
	}
	
}
