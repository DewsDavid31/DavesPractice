public class TV{
	boolean on;
	String name;
	TV(String name){
		this.name = name;
		on = false;
	}
	
	public void on(){
		this.on = true;
		System.out.println("The "+ name + " tv blips on");
	}

	public void off(){
		System.out.println("The "+ name + " tv blips off");
		this.on = true;
	}
}
