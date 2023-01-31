package gameSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeySystem implements KeyListener{
	public boolean wkey;
	public boolean akey;
	public boolean skey;
	public boolean active;
	public boolean dkey;
	GameRuntime gRuntime;
	public KeySystem(GameRuntime gr) {
		gRuntime = gr;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(active) {
		gRuntime.keyPressed(e);
		if(e.getKeyCode() == KeyEvent.VK_W) {
			wkey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			akey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			skey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			dkey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			wkey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			akey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			skey = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dkey = true;
		}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(active) {
		gRuntime.keyReleased(e);
		if(e.getKeyCode() == KeyEvent.VK_W) {
			wkey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			akey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			skey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			dkey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			wkey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			akey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			skey = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dkey = false;
		}
		}
		
	}

}
