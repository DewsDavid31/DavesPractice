public class NoQuarterState implements State {
        GumballMachine gumballMachine;

        public NoQuarterState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        public void insertQuarter(){
            System.out.println("1 Credit Inserted");
            gumballMachine.setState(gumballMachine.getHasQuarterState());    
        }
        public void ejectQuarter(){
            System.out.println("Quarter clinks into the coin bucket");
        }
        public void turnCrank() {
            System.out.println("The crank won't budge");
        }
        public void dispense(){
            System.out.println("A clink noise is made");   
        }


}