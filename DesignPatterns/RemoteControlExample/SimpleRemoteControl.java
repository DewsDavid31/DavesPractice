public class SimpleRemoteControl {
// This is our command object!!
	Command slot;

	public SimpleRemoteControl(){}

	public void setCommand(Command command) {
	slot = command;
	}
	
	public void buttonWasPressed() {
		slot.execute();
	}
}
