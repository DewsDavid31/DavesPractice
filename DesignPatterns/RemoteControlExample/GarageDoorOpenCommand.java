public class GarageDoorOpenCommand implements Command {
	GarageDoor garageDoor;
	

	GarageDoorOpenCommand(GarageDoor garageDoor){
		this.garageDoor = garageDoor;
	}
	public void execute(){
		garageDoor.up();
		garageDoor.lightOn();
	}
	public void undo(){
		garageDoor.down();
		garageDoor.lightOff();	
	}
}
