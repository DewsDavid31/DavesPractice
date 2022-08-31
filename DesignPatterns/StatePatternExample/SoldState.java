public class SoldState implements State {
        GumballMachine gumballMachine;

        public SoldState(GumballMachine gumballMachine){
            this.gumballMachine = gumballMachine;
        }

        public void insertQuarter(){
            System.out.println("Your Quarter isn't able to fit");
        }
        public void ejectQuarter(){
            System.out.println("The gumball bounces a little, nothing else happens");
        }
        public void turnCrank() {
            System.out.println("The crank wiggles and the gumball bounces a little");
	 }
        public void dispense(){
            System.out.println("The gumball spirals out of the machine into your hand");
            if (gumballMachine.getCount() <= 0){
                System.out.println("The machine is silent, lacking gumballs");
                gumballMachine.setState(gumballMachine.getSoldOutState());
            }
        }


}
