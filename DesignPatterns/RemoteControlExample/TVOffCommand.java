public class TVOffCommand implements Command{
	TV tv;
	
	TVOffCommand(TV tv){
		this.tv = tv;
	}
	
	public void execute(){
		tv.off();
	}
	
	public void undo(){
		tv.on();
	}
}
