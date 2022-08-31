public class CeilingFanMediumCommand implements Command {
	CeilingFan ceilingFan;
	int prevSpeed;
	CeilingFanMediumCommand(CeilingFan ceilingFan){
		this.ceilingFan = ceilingFan;
	}

	public void execute(){
		ceilingFan.medium();
	}

	public void undo(){
		switch(prevSpeed){
			case CeilingFan.HIGH:
				ceilingFan.high();
				break;
			case CeilingFan.MEDIUM:
				ceilingFan.medium();
				break;
			case CeilingFan.LOW:
				ceilingFan.low();
				break;
			case CeilingFan.OFF:
				ceilingFan.off();
				break;
		}
	}
}
