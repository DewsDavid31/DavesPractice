public class Stereo {
	int volume;
	boolean cd;
	boolean dvd;
	boolean on;
	double radio;
	boolean radioOn;
	String name;
	Stereo(String name){
		this.name = name;
		this.volume = 0;
		this.cd = true;
		this.dvd = false;
		this.on = false;
		this.radioOn = false;
		this.radio = 97.3;
	}
	
	public void setCD(){
		cd = true;
		dvd = false;
		radioOn = false;
		System.out.println("The " + name + " stereo is set to cd");
	}

	public void setVolume(int vol){
		this.volume = vol;
		System.out.println("The volume knob of the "+ name  +" stereo is " + volume);
	}
	
	public void setDvd(){
		cd = false;
		dvd = true;
		radioOn = false;
		System.out.println("The "+ name  +" stereo is set to dvd");
	}
	
	public void setRadio(double station){
		if(!radioOn){
			radioOn = true;
			System.out.println("The "+ name  +" stereo is set to radio");
		}
		radio = station;
		cd = false;
		dvd = false;
		System.out.println("The "+ name +"  station is set to " + station);
	}
	
	public void on(){
		on = true;
	}
	public void off(){
		on = false;
	}
}
