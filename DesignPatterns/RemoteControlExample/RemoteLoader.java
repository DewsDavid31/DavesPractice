public class RemoteLoader {
	public static void main (String [] args) {
		RemoteControl remoteControl = new RemoteControl();



		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		CeilingFan ceilingFan = new CeilingFan("Living Room");
		GarageDoor garageDoor = new GarageDoor("");
		Stereo stereo = new Stereo("Living Room");
		TV tv = new TV("Living Room");
		Hottub hottub = new Hottub();

		LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
		LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenRoomLightOff = new LightOffCommand(kitchenLight);
		TVOnCommand tvOn = new TVOnCommand(tv);
		HottubOnCommand hottubOn = new HottubOnCommand(hottub);	
		HottubOffCommand hottubOff = new HottubOffCommand(hottub);
		TVOffCommand tvOff = new TVOffCommand(tv);
				
		CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
		CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
		
		CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(ceilingFan);
		CeilingFanMediumCommand ceilingFanMedium = new CeilingFanMediumCommand(ceilingFan);
		CeilingFanLowCommand ceilingFanLow = new CeilingFanLowCommand(ceilingFan);

		GarageDoorOpenCommand garageDoorUpCommand = new GarageDoorOpenCommand(garageDoor);
		GarageDoorCloseCommand garageDoorDownCommand = new GarageDoorCloseCommand(garageDoor);

		StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
		StereoOffCommand stereoOff = new StereoOffCommand(stereo);

		remoteControl.setCommand(0,livingRoomLightOn, livingRoomLightOff);
		remoteControl.setCommand(1,kitchenLightOn, kitchenRoomLightOff);
		remoteControl.setCommand(2,ceilingFanOn, ceilingFanOff);
		remoteControl.setCommand(3,stereoOnWithCD, stereoOff);
		remoteControl.setCommand(4,ceilingFanHigh, ceilingFanOff);
		remoteControl.setCommand(5,ceilingFanMedium, ceilingFanOff);
		remoteControl.setCommand(6,ceilingFanLow, ceilingFanOff);

		System.out.println(remoteControl);

		remoteControl.onButtonWasPushed(0);
		remoteControl.offButtonWasPushed(0);
		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);
		
		remoteControl.onButtonWasPushed(1);
		remoteControl.offButtonWasPushed(1);
		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);
		
		remoteControl.onButtonWasPushed(2);
		remoteControl.offButtonWasPushed(2);
		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);
		
		remoteControl.onButtonWasPushed(3);
		remoteControl.offButtonWasPushed(3);
		System.out.println(remoteControl);
// Testing new cielingFan modes!!
		remoteControl.onButtonWasPushed(4);
		remoteControl.offButtonWasPushed(4);
		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);

		remoteControl.onButtonWasPushed(5);
		remoteControl.offButtonWasPushed(5);
		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);

		remoteControl.onButtonWasPushed(6);
		remoteControl.offButtonWasPushed(6);
		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);
		
//Macro command example
		Command[] partyOn = {livingRoomLightOn, stereoOnWithCD, tvOn, hottubOn};
		Command[] partyOff = {livingRoomLightOff,stereoOff,tvOff,hottubOff};

		MacroCommand partyOnMacro = new MacroCommand(partyOn);
		MacroCommand partyOffMacro = new MacroCommand(partyOff);

		remoteControl.setCommand(0,partyOnMacro,partyOffMacro);
		System.out.println("Testing part macro");
		System.out.println(remoteControl);
		remoteControl.onButtonWasPushed(0);
		remoteControl.offButtonWasPushed(0);
		remoteControl.undoButtonWasPushed();

	}
}
		
