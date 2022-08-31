public class GarageDoor{
	boolean up;
	Light light;
	boolean stopped;
	String name;
	GarageDoor(String name){
		this.name = name;
		light = new Light("Garage Light");
		this.up = false;
		this.stopped = false;
	}

	public void up(){
		this.up = true;
		stopped = false;
		System.out.println(name + " Garage Door is open");
	}
	
	public void down(){
		this.up = false;
		stopped = false;
		System.out.println(name +" Garage Door is closed");
	}

	public void stop(){
		stopped = true;
		System.out.println("The "+ name + " garage door is stuck in place");
	}
	
	public void lightOn(){
		this.light.on();
	}

	public void lightOff(){
		this.light.off();
	}
}
