public class RemoteControl {
	Command[] onCommands;
	Command[] offCommands;
	Command undoCommand;
	public RemoteControl() {
		onCommands = new Command[7]; // Seven programmable slots for now
		offCommands = new Command[7]; 
		Command undoCommand;
		Command noCommand = new NoCommand();
			for(int i = 0; i < 7; i++) {
				onCommands[i] = noCommand;
				offCommands[i] = noCommand;
			}
			undoCommand = noCommand;
		}
		

		public void setCommand(int slot, Command onCommand, Command offCommand){
			onCommands[slot] = onCommand;
			offCommands[slot] = offCommand;
		}

		public void onButtonWasPushed(int slot) {
			System.out.println("You press button " + slot);
			onCommands[slot].execute();
			undoCommand = onCommands[slot];
		}
	
		public void offButtonWasPushed(int slot) {
			System.out.println("You press off button " + slot);
			offCommands[slot].execute();
			undoCommand = offCommands[slot];
		}

		public void undoButtonWasPushed(){
			System.out.println("You press the undo button ");
			undoCommand.undo();
		}

		public String toString() {
			StringBuffer stringBuff = new StringBuffer();
			stringBuff.append("\n------ Remote Control ------\n");
			for (int i = 0; i < onCommands.length; i++){
				stringBuff.append("|[slot " + i + " ]" +
					 onCommands[i].getClass().getName() +
					 "\t" + offCommands[i].getClass().getName() + "|\n");
			}
			stringBuff.append("| [undo button] |\n");
			return stringBuff.toString();
		}
}
