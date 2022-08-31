public class RemoteControlTest {
	// Our client object!!
	public static void main(String[] args){
		SimpleRemoteControl remote = new SimpleRemoteControl();
			// Our command object
		Light light = new Light();
			// Our reciever object
		LightOnCommand lightOn = new LightOnCommand(light);
			// Passing our command object to reciever, decoupled
	
		// For our garage
		GarageDoor garageDoor = new GarageDoor();
		GarageDoorOpenCommand garageOpen = new GarageDoorOpenCommand(garageDoor);
		remote.setCommand(lightOn);
			// Command passed to invoker
		remote.buttonWasPressed();
			// Simulating button being pressed i.e. invokation

		// For the garage reciever
		
		remote.setCommand(garageOpen);
		remote.buttonWasPressed();

	}
}
