public class GumballMachine {
    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;

	State state = soldOutState;
	int count = 0;

    public int getCount(){
        return count;
    }
    public State getNoQuarterState(){
        return noQuarterState;
    }
    public State getSoldOutState(){
        return soldOutState;
    }
    public State getSoldState(){
        return soldState;
    }
    public State getHasQuarterState(){
        return hasQuarterState;
    }
    public void setState(State newState){
        this.state = newState;
    }
	public GumballMachine(int count) {
		this.hasQuarterState = new HasQuarterState(this);
		this.soldState = new SoldState(this);
		 
		this.soldOutState = new SoldOutState(this);
		this.noQuarterState = new NoQuarterState(this);

		if (count > 0){
			this.state = this.noQuarterState;
		}

		else{
			this.state = this.soldOutState;
		}

	}
	
	public void insertQuarter () {
        	state.insertQuarter();
	}
	
	public void ejectQuarter() {
		state.ejectQuarter();
	}
	
	public void turnCrank(){
		state.turnCrank();
	}

	
	public void dispense(){
		state.dispense();
	}
	
	public void refill(int gumballs){
		if(gumballs > 0){
			System.out.println( gumballs + " gumballs bounce into the machine");	
        }
        if(state == getSoldOutState()){
            state = noQuarterState;
        }
		else{
			System.out.println("A nearby guard looks at you funny, you try to do negative math with you hands, about to reach in, but can't do it fast enough");
		}
	
	}
}
		
	

