public class HasQuarterState implements State {
        GumballMachine gumballMachine;

        public HasQuarterState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        public void insertQuarter(){
            System.out.println("Your Quarter fumbles on inserted one");    
        }
        public void ejectQuarter(){
            gumballMachine.setState(gumballMachine.getNoQuarterState());
            System.out.println("Your inserted quarter clinks out into you hand");
        }
        public void turnCrank() {
            gumballMachine.setState(gumballMachine.getSoldState());    
            System.out.println("You hear the machine crank");
	    gumballMachine.dispense();
        }
        public void dispense(){
            System.out.println("The machine clinks and the machine does nothing");
        }


}
