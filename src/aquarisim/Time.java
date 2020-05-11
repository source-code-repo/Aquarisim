package aquarisim;

public class Time {
	// Originally 0.65
	private static float MULTIPLE = 0.65f;
	private float time = 0;
	public Time(float time) {
		this.time = time * MULTIPLE;
	}
	
	private float getTime() {
		return time;
	}
	
	public boolean longerThan(Time otherTime) {
		return (this.time > otherTime.getTime()) ? true : false;
	}
	
	public void add(float toAdd) {
		this.time += toAdd * MULTIPLE;
	}
	
	public void time(float toSet) {
		this.time = toSet  * MULTIPLE;
	}
	
	public float getFloat() {
		return this.time;
	}
	
	@Override
	public String toString() {
		return Float.toString(this.time);
	}

	public void time(Time newTime) {
		this.time = newTime.getFloat();
	}
	
	public static float convert(float inputTime) {
		return inputTime * MULTIPLE;
	}
}
