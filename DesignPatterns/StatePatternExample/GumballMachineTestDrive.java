
import java.util.Scanner;
public class GumballMachineTestDrive {
	public static void main(String [] args){
		GumballMachine gumballMachine = new GumballMachine(5);
		Scanner input = new Scanner(System.in);
		menu(0, gumballMachine, input);
	}
	
	public static int menu(int option, GumballMachine gumballMachine, Scanner input){
		switch(option){
			case 0:
				System.out.println("You stand by the gumball machine");
				System.out.println("1) insert quarter\n2) turn crank\n3) push eject\n4) refill it\n5) walk away");
				int inputOption = input.nextInt();
				return menu(inputOption, gumballMachine, input);
				
			case 1:
				gumballMachine.insertQuarter();
				return menu(0, gumballMachine, input);
			case 2:
				gumballMachine.turnCrank();
				return menu(0, gumballMachine, input);
			case 3:
				gumballMachine.ejectQuarter();
				return menu(0, gumballMachine, input);
			case 4:
			
				int amount = input.nextInt();
				gumballMachine.refill(amount);
				return menu(0, gumballMachine, input);
			case 5: 
				return 0;

			default:
				return menu(0, gumballMachine, input);
		}
	}
}

