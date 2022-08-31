public class GarageDoorCloseCommand implements Command {
	GarageDoor garageDoor;	

	GarageDoorCloseCommand(GarageDoor garageDoor){
		this.garageDoor = garageDoor;
	}
	
	public void execute(){
		garageDoor.down();	
		garageDoor.lightOff();
	}
	
	public void undo(){
		garageDoor.up();
	}
}
