public class CeilingFan {
	
	public static final int HIGH = 3;
	public static final int MEDIUM = 2;
	public static final int LOW = 1;
	public static final int OFF = 0;
	boolean on;
	int speed;
	String name;	
	
	CeilingFan(String name){
		this.name = name;
		this.speed = 0;
		this.on = false;
	}
	
	public void on(){
		on = true;
		System.out.println("The fan in the "+ name  +" clicks on");
	}
	
	public void off(){
		on = false;
		setSpeed(OFF);
		System.out.println("The fan in the " + name  +  " clicks off");	
	}
	
	
	public void setSpeed(int newSpeed){
		this.speed = newSpeed;
		System.out.println("The "+ name  +" fan whirs up to " +
					 getSpeed() + " rotations per second");
	}

	public void low(){
		if(!on){
			on();
		}
		setSpeed(LOW);
	}
	
	public void medium(){
		if(!on){
			on();
		}
		setSpeed(MEDIUM);
	}

	
	public void high(){
		if(!on){
			on();
		}
		setSpeed(HIGH);
	}

	public int getSpeed(){
		return speed;
	}
}
