package uk.co.lastresorts.charcoalmod;

public class OrientationAndLevel {
	public int orientation, level;
	
	//Orientation: 0 is North, 1 is East, 2 is South, 3 is West. -1 implies unknown.
	//Level: 1 is bottom, 2 is middle, 3 is top (given in y coordinate).
	
	public OrientationAndLevel(int orientation, int level) {
		this.orientation = orientation;
		this.level = level;
	}
}
