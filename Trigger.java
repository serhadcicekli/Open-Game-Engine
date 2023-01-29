package gameSystem;

public class Trigger {
	public float xPos = 0;
	public float xOffset = 0;
	public float yPos = 0;
	public float yOffset = 0;
	public float width = 0;
	public float height = 0;
	public int collidingObjectCount = 0;
	boolean isAttached = false;
	public GameObject attachedGameObject;
	public void attach(GameObject gObject) {
		if(gObject != null) {
			isAttached = true;
			attachedGameObject = gObject;
		}
		else {
			isAttached = false;
			attachedGameObject = null;
		}
	}
	public void detach() {
		isAttached = false;
		attachedGameObject = null;
	}
	public void reLoc() {
		if(isAttached) {
			xPos = attachedGameObject.xPos + xOffset;
			yPos = attachedGameObject.yPos + yOffset;
		}
		else {
			xPos = xOffset;
			yPos = yOffset;
		}
	}
}
