package gameSystem;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Visual{
	public Image image;
	public Visual(String resString) {
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + resString));
		} catch (IOException e) {
			image = null;
		}
	}
	public Visual(String resString,boolean rac) {
		if(rac) {
			try {
				image = ImageIO.read(getClass().getClassLoader().getResource(resString));
			} catch (IOException e) {
				image = null;
			}
		}
		else {
			try {
				image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + resString));
			} catch (IOException e) {
				image = null;
			}
		}

	}
}
