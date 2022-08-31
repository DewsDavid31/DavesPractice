public class Hottub{
	boolean on;

	Hottub(){
		on = false;
	}

	public void on(){
		this.on = true;
		System.out.println("Hot Tub is bubbling");
	}
	
	public void off(){
		this.on = false;	
		System.out.println("Hot Tub has shut off");
	}
}
