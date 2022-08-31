public class CeilingFanOnCommand implements Command {
	CeilingFan ceilingFan;

	public CeilingFanOnCommand(CeilingFan ceilingFan){
		this.ceilingFan = ceilingFan;
	}
	
	public void execute(){
		ceilingFan.on();
		ceilingFan.setSpeed(1);
	}
	public void undo(){
		ceilingFan.off();
		ceilingFan.setSpeed(0);
	}
}
