public class Light{


	boolean on;
	String name;
	Light(String name){
		this.name = name;
		on = false;
	}
	public void on(){
		System.out.println("ooh, the "+ name + " light is on!");
		on = true;
	}
	
	public void off(){
		System.out.println("Drat, the "+ name +" light went out...");
		on = false;
	}
}
