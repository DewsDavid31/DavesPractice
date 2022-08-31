public class HottubOnCommand implements Command{
	Hottub hottub;

	HottubOnCommand(Hottub hottub){
		this.hottub = hottub;
	}
	
	public void execute(){
		hottub.on();
	}

	public void undo(){
		hottub.off();
	}
}
