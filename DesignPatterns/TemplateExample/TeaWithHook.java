import java.io.*;
public class TeaWithHook extends CaffeineBeverageWithHook {
	public void brew() {
		System.out.println("steepin the tea...");
	}
	
	public void addCondiments(){
		System.out.println("Adding lemon to tea...");
	}
	
	public boolean customerWantsCondiments(){
		String answer = getUserInput();
	
		if(answer.toLowerCase().startsWith("y")) {
			return true;
		}else{
			return false;
		}
	}

	private String getUserInput() {
		String answer = null;
		
		System.out.println("Would you lemon with your tead {y/n}? ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
			answer = in.readLine();
		} catch (IOException ioe) {
			System.err.println("IO error reading answer");
		}
		if (answer == null){
			return "no";
		}
		return answer;
	}
}
