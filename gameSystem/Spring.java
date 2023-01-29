package gameSystem;

public class Spring{
	public String goName;
	public float xOffset;
	public float yOffset;
	public GameObject A;
	public GameObject B;
	public float factor = 60;
	public Spring(String name,GameObject ao,GameObject bo) {
		A = ao;
		B = bo;
		goName = name;
	}
	public void fixedTick(float gravity) {
		if(A.xPos < B.xPos) {
			if(!A.fixed) {
				A.xv += ((B.xPos - xOffset) - A.xPos) / factor;	
			}
			if(!B.fixed) {
				B.xv += ((A.xPos + xOffset) - B.xPos) / factor;	
			}
		}
		else {
			if(!A.fixed) {
				A.xv += ((B.xPos + xOffset) - A.xPos) / factor;	
			}
			if(!B.fixed) {
				B.xv += ((A.xPos - xOffset) - B.xPos) / factor;	
			}
		}
		if(A.yPos < B.yPos) {
			if(!A.fixed) {
				A.yv += ((B.yPos - yOffset) - A.yPos) / factor;	
			}
			if(!B.fixed) {
		B.yv += ((A.yPos + yOffset) - B.yPos) / factor;
			}
		}
		else {
			if(!A.fixed) {
				A.yv += ((B.yPos + yOffset) - A.yPos) / factor;
			}
			if(!B.fixed) {
				B.yv += ((A.yPos - yOffset) - B.yPos) / factor;
			}
		}
	}
}
