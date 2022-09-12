import java.util.ArrayList;
import java.util.Random;
import java.lang.Math.*;

class BadKey {
  public Random gen;
  private int e;
  private int q;
  private int d;
  private int p;
  public int limit;
  
  public BadKey(int limit) {
    this.gen = new Random();
    this.limit = limit;
    this.e = pickPrime(2,limit);
    int[] pnq = this.find_p_q();
    this.p = pnq[0];
    this.q = pnq[1];
    this.d = findD();
  }
  
  public int pickPrime(int start, int limit){
    int candidate = 2;
    boolean prime = false;
    while(!prime){
      candidate = gen.nextInt(limit);
      prime = true;
      if(candidate < start){
        prime = false;
        continue;
      }
      for(int check = 2; check < candidate; check++){
        if(candidate % check == 0){
          prime = false;
        }
      }
    }
    return candidate;
  }
  
  public int gcd(int a, int b){
    if(b == 0){
      return a;
    }
    return gcd(b, a % b);
  }
  
  public int inverseModBrute(int a, int b) {
    int index = 0;
    while (a * index % b != 1) {
      index++;
    }
    return index;
  }


  public int[] find_p_q(){
    boolean found = false;
    while(!found){
       p = this.pickPrime(2,e);
       q = this.pickPrime(2,e);
       int totient = (p-1)*(q-1);
       if(this.gcd(e, totient) == 1){
         found = true;
       }
    }
    int[] results = {p,q};
    return results;
  }

  public int findD(){
    int totient = (p-1)*(q-1);
    return inverseModBrute(e, totient);
  }
  
  public char encrypt(char data){
    int n = p * q;
    int numeric = (int) data;
    System.out.println(Character.toString(data) + numeric);
    int resultNum = (int) Math.ceil(Math.pow(numeric,e) % n);
    char result = (char) resultNum;
    System.out.println(Character.toString(result) + resultNum);
    return result;
  }

  
  public char decrypt(char data){
    int n = p * q;
    int numeric = (int) data;
    int resultNum = (int) Math.ceil(Math.pow(numeric,d) % n);
    char result = (char) resultNum;
    return result;
  }
}

class User {
  private BadKey key1;

  public User() {
    this.key1 = new BadKey(10000);
  }

  public String encrypt(String data) {
    String result = "";
    System.out.println("Encrypting \"" + data + "\" for uploading...");
    for (int index = 0; index < data.length(); index++) {
      char next = data.charAt(index);
      char nextResult = key1.encrypt(next);
      result += Character.toString(nextResult);
    }
    System.out.println("Converted to: " + result);
    return result;
  }

  public String decrypt(String data) {
    String result = "";
    for (int index = 0; index < data.length(); index++) {
      char next = data.charAt(index);
      char nextResult = key1.decrypt(next);
      result += Character.toString(nextResult);
    }
    return result;
  }
}

class Cloud {
  public ArrayList<String> uploads;

  public Cloud() {
    this.uploads = new ArrayList<String>();
  }

  public String read(User user, int index) {
    String unparsed = uploads.get(index);
    return user.decrypt(unparsed);

  }

  public void upload(User user, String data) {
    uploads.add(user.encrypt(data));
  }
}

class Main {
  public static void main(String[] args) {
    Cloud testCloud = new Cloud();
    User me = new User();
    testCloud.upload(me, "I can see this!");
    System.out.println(testCloud.read(me, 0));
  }
}
