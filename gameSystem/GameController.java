package gameSystem;

import java.util.ArrayList;
import java.util.List;
public class GameController {
	public float timeScale = 1f;
	public List<Trigger> triggers = new ArrayList<>();
	public List<String> triggerNames = new ArrayList<>();
	public List<Spring> gameSprings = new ArrayList<>();
	public List<GameObject> gameObjects = new ArrayList<>();
	public List<String> goNameStrings = new ArrayList<>();
	public List<String> gsNameStrings = new ArrayList<>();
	List<GameObject> peGameObjects = new ArrayList<>();
	public int screenW;
	public int screenH;
	public Trigger newTrigger(String nameString) {
		triggerNames.add(nameString);
		triggers.add(new Trigger());
		return triggers.get(triggers.size() - 1);
	}
	public GameObject newGameObject(String nameString) {
		gameObjects.add(new GameObject(nameString));
		gameObjects.get(gameObjects.size() - 1).screenWidth = screenW;
		gameObjects.get(gameObjects.size() - 1).screenHeight = screenH;
		goNameStrings.add(nameString);
		return gameObjects.get(gameObjects.size() - 1);
	}
	public Spring newSpring(String nameString,GameObject A,GameObject B) {
		gameSprings.add(new Spring(nameString, A, B));
		gsNameStrings.add(nameString);
		return gameSprings.get(gameSprings.size() - 1);
	}
	List<GameObject> peA = new ArrayList<>();
	List<GameObject> peB = new ArrayList<>();
	float pBase;
	float Acxv;
	float Bcxv;
	float Acyv;
	float Bcyv;
	public int collisionUpdateTicks = 40;
	int cuTicks = 6000;
	float lerp(float x,float y,float a) {
		return ((1 - a) * x) + (a * y);
	}
	boolean testLayers(List<Integer> Acol,List<Integer> Bcol) {
		boolean temp = false;
		if(Acol.size() > Bcol.size()) {
			for(int i = 0;i < Bcol.size();i++) {
				if(Acol.contains(Bcol.get(i))) {
					temp = true;
					i = Bcol.size();
				}
			}
		}
		else {
			for(int i = 0;i < Acol.size();i++) {
				if(Bcol.contains(Acol.get(i))) {
					temp = true;
					i = Acol.size();
				}
			}
		}
		return temp;
	}
	public void physicsUpdate(float atfl) {

		cuTicks += 1;
		if(cuTicks >= collisionUpdateTicks) {
			peGameObjects.clear();
			for (GameObject gObject : gameObjects) {
				if(gObject.physicsEnabled == true) {
					peGameObjects.add(gObject);
						
				}
			}
			peA.clear();
			peB.clear();
			for(int i = 0; i < peGameObjects.size(); i++) {
				for (int j = 0; j < peGameObjects.size() - (i + 1); j++) {
					peA.add(peGameObjects.get(i));
				}
				peB.addAll(peGameObjects.subList((i + 1), peGameObjects.size()));
			}
			cuTicks = 0;
			
		}
		
		for(int i = 0; i < peA.size(); i++) {
			if(((peA.get(i).width + peB.get(i).width) / 2 > Math.abs(peA.get(i).xPos - peB.get(i).xPos)) && ((peA.get(i).height + peB.get(i).height) / 2 > Math.abs(peA.get(i).yPos - peB.get(i).yPos)) && testLayers(peA.get(i).physicLayers, peB.get(i).physicLayers)) {
				peA.get(i).isColliding = true;
				peB.get(i).isColliding = true;
				
				//  ((peA.get(i).width + peB.get(i).width) / 2 - Math.abs(peA.get(i).xPos - peB.get(i).xPos))
				//  ((peA.get(i).height + peB.get(i).height) / 2 - Math.abs(peA.get(i).yPos - peB.get(i).yPos))
				//x base (A < B): ((peA.get(i).xPos + (peA.get(i).width / 2)) + (peB.get(i).xPos - (peB.get(i).width / 2))) / 2
				//x base (A > B): ((peA.get(i).xPos - (peA.get(i).width / 2)) + (peB.get(i).xPos + (peB.get(i).width / 2))) / 2
				//y base (A < B): ((peA.get(i).yPos + (peA.get(i).height / 2)) + (peB.get(i).yPos - (peB.get(i).height / 2))) / 2
				//y base (A > B): ((peA.get(i).yPos - (peA.get(i).height / 2)) + (peB.get(i).yPos + (peB.get(i).height / 2))) / 2
				if((!peA.get(i).fixed) && (!peB.get(i).fixed)) {
					Acxv = peA.get(i).xv;
					Acyv = peA.get(i).yv;
					Bcxv = peB.get(i).xv;
					Bcyv = peB.get(i).yv;
				/*
				tempxv = peA.get(i).xv;
				tempyv = peA.get(i).yv;
				peA.get(i).xv = peB.get(i).xv * (1 - (peA.get(i).damping + peB.get(i).damping) / 2);
				peA.get(i).yv = peB.get(i).yv * (1 - (peA.get(i).damping + peB.get(i).damping) / 2) - atfl;
				peB.get(i).xv = tempxv;
				peB.get(i).yv = tempyv - atfl;
				*/
				// x velocity base: ((peA.get(i).xv + peB.get(i).xv) / 2)
				// y velocity base: ((peA.get(i).yv + peB.get(i).yv) / 2)
				// x base:((peA.get(i).xPos) + (peB.get(i).xPos)) / 2
				if(((peA.get(i).width + peB.get(i).width) / 2 - Math.abs(peA.get(i).xPos - peB.get(i).xPos)) < ((peA.get(i).height + peB.get(i).height) / 2 - Math.abs(peA.get(i).yPos - peB.get(i).yPos))) {

					peA.get(i).xv = lerp(Bcxv, Acxv * (1 - peA.get(i).damping), peA.get(i).mass / (peA.get(i).mass + peB.get(i).mass));
					peB.get(i).xv = lerp(Acxv, Bcxv * (1 - peB.get(i).damping), peB.get(i).mass / (peA.get(i).mass + peB.get(i).mass));
					peA.get(i).yv = peA.get(i).yv * (1 - peA.get(i).surfacefriction);
					peB.get(i).yv = peB.get(i).yv * (1 - peB.get(i).surfacefriction);
				if(peA.get(i).xPos < peB.get(i).xPos) {
					pBase = ((peA.get(i).xPos + (peA.get(i).width / 2)) + (peB.get(i).xPos - (peB.get(i).width / 2))) / 2;
					peA.get(i).xPos = pBase - (peA.get(i).width / 2) - 1;
					peB.get(i).xPos = pBase + (peB.get(i).width / 2) + 1;
				}
				else {
					pBase = ((peA.get(i).xPos - (peA.get(i).width / 2)) + (peB.get(i).xPos + (peB.get(i).width / 2))) / 2;
					peA.get(i).xPos = pBase + (peA.get(i).width / 2) + 1;
					peB.get(i).xPos = pBase - (peB.get(i).width / 2) - 1;
				}
				}
				else {

					peA.get(i).yv = lerp(Bcyv, Acyv * (1 - peA.get(i).damping), peA.get(i).mass / (peA.get(i).mass + peB.get(i).mass));
					peB.get(i).yv = lerp(Acyv, Bcyv * (1 - peB.get(i).damping), peB.get(i).mass / (peA.get(i).mass + peB.get(i).mass));
					peA.get(i).xv = peA.get(i).xv * (1 - peA.get(i).surfacefriction);
					peB.get(i).xv = peB.get(i).xv * (1 - peB.get(i).surfacefriction);
				if(peA.get(i).yPos < peB.get(i).yPos) {
					pBase = ((peA.get(i).yPos + (peA.get(i).height / 2)) + (peB.get(i).yPos - (peB.get(i).height / 2))) / 2;
					peA.get(i).yPos = pBase - (peA.get(i).height / 2) - 1;
					peB.get(i).yPos = pBase + (peB.get(i).height / 2) + 1;
				}
				else {
					pBase = ((peA.get(i).yPos - (peA.get(i).height / 2)) + (peB.get(i).yPos + (peB.get(i).height / 2))) / 2;
					peA.get(i).yPos = pBase + (peA.get(i).height / 2) + 1;
					peB.get(i).yPos = pBase - (peB.get(i).height / 2) - 1;
				}
				}
				// y base:((peA.get(i).yPos + peB.get(i).yPos) / 2)
			}else {
				if(!(peA.get(i).fixed && peB.get(i).fixed)) {
					if(peA.get(i).fixed && (!peB.get(i).fixed)) {
						if(((peA.get(i).width + peB.get(i).width) / 2 - Math.abs(peA.get(i).xPos - peB.get(i).xPos)) < ((peA.get(i).height + peB.get(i).height) / 2 - Math.abs(peA.get(i).yPos - peB.get(i).yPos))) {
							if(peA.get(i).xPos < peB.get(i).xPos) {
								peB.get(i).xPos = peA.get(i).xPos + (peA.get(i).width / 2) + (peB.get(i).width / 2);
							}
							else {
								peB.get(i).xPos = peA.get(i).xPos - (peA.get(i).width / 2) - (peB.get(i).width / 2);
							}
							peB.get(i).xv = peB.get(i).xv * (1 - peB.get(i).damping) * -1;
							peB.get(i).yv = peB.get(i).yv * (1 - peB.get(i).surfacefriction);
						}else {
							if(peA.get(i).yPos < peB.get(i).yPos) {
								peB.get(i).yPos = peA.get(i).yPos + (peA.get(i).height / 2) + (peB.get(i).height / 2) - 1;
							}
							else {
								peB.get(i).yPos = peA.get(i).yPos - (peA.get(i).height / 2) - (peB.get(i).height / 2);
							}
							peB.get(i).yv = (peB.get(i).yv * (1 - peB.get(i).damping) * -1);
							peB.get(i).xv = peB.get(i).xv * (1 - peB.get(i).surfacefriction);
						}
						
						
					}
					if(peB.get(i).fixed && (!peA.get(i).fixed)) {
						if(((peA.get(i).width + peB.get(i).width) / 2 - Math.abs(peA.get(i).xPos - peB.get(i).xPos)) < ((peA.get(i).height + peB.get(i).height) / 2 - Math.abs(peA.get(i).yPos - peB.get(i).yPos))) {
							if(peA.get(i).xPos < peB.get(i).xPos) {
								peA.get(i).xPos = peB.get(i).xPos - (peB.get(i).width / 2) - (peA.get(i).width / 2) - 1;
							}
							else {
								peA.get(i).xPos = peB.get(i).xPos + (peB.get(i).width / 2) + (peA.get(i).width / 2);
							}
							peA.get(i).xv = peA.get(i).xv * (1 - peA.get(i).damping) * -1;
							peA.get(i).yv = peA.get(i).yv * (1 - peA.get(i).surfacefriction);
						}else {
							if(peA.get(i).yPos < peB.get(i).yPos) {
								peA.get(i).yPos = peB.get(i).yPos - (peB.get(i).height / 2) - (peA.get(i).height / 2);
							}
							else {
								peA.get(i).yPos = peB.get(i).yPos + (peB.get(i).height / 2) + (peA.get(i).height / 2);
							}
							peA.get(i).yv = peA.get(i).yv * (1 - peA.get(i).damping) * -1;
							peA.get(i).xv = peA.get(i).xv * (1 - peA.get(i).surfacefriction);
						}
						
						
					}
				}
			}
			}
		}
																																																			//peA	peGameObjects.get(j)
																																																			//peB 	triggers.get(i)
		for(int i = 0; i < triggers.size(); i++) {
			triggers.get(i).reLoc();
			triggers.get(i).collidingObjectCount = 0;
			for(int j = 0; j < peGameObjects.size(); j++) {
				if(peGameObjects.get(j) != triggers.get(i).attachedGameObject) {
				if(((peGameObjects.get(j).width + triggers.get(i).width) / 2 > Math.abs(peGameObjects.get(j).xPos - triggers.get(i).xPos)) && ((peGameObjects.get(j).height + triggers.get(i).height) / 2 > Math.abs(peGameObjects.get(j).yPos - triggers.get(i).yPos)) && testLayers(peGameObjects.get(j).physicLayers, triggers.get(i).physicLayers)) {
					triggers.get(i).collidingObjectCount += 1;
				}
				}
			}
		}
	}
	public GameObject getGameObject(String goName) {
		if(goNameStrings.contains(goName)) {
			if(gameObjects.get(goNameStrings.indexOf(goName)) != null) {
			return gameObjects.get(goNameStrings.indexOf(goName));
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	public Trigger getTrigger(String goName) {
		if(triggerNames.contains(goName)) {
			if(triggers.get(triggerNames.indexOf(goName)) != null) {
			return triggers.get(triggerNames.indexOf(goName));
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	public Spring getSpring(String goName) {
		if(gsNameStrings.contains(goName)) {
			if(gameSprings.get(gsNameStrings.indexOf(goName)) != null) {
			return gameSprings.get(gsNameStrings.indexOf(goName));
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
}
