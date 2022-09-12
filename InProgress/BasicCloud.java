import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

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
    this.e = this.pickPrime(3, limit);
    System.out.println("Picked: " + e);
    this.find_p_q();
    this.findD();
    System.out.println("Private: "+ this.p + ", " + this.q + " public: " + this.d + ", " + this.e);
  }
  
  public int pickPrime(int start, int limit){
    int candidate = 2;
    boolean prime = false;
    while(!prime){
      candidate = gen.nextInt((int) limit - 2) + 2;
      prime = true;
      if(candidate < start || candidate > limit){
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
    int index = 1;
    while (a * index % b != 1) {
      index++;
    }
    return index;
  }


  public void find_p_q(){
    boolean found = false;
    System.out.println("Generating public key...");
    while(!found){
       this.p = this.pickPrime(this.e + 1,this.limit);
       this.q = this.pickPrime(this.e + 1,this.limit);
       int totient = (p-1)*(q-1);
       if(this.gcd(e, totient) == 1){
         found = true;
       }
    }
  }

  public void findD(){
    System.out.println("Generating private key...");
    int totient = (p-1)*(q-1);
    this.d = inverseModBrute(this.e, totient);
  }
  
  public char encrypt(char data){
    int n = this.p * this.q;
    int numeric = (int) data;
    long resultNum = (int) (Math.ceil(Math.pow(numeric, (double) this.e)) % n);
    char result = (char) resultNum;
    return result;
  }

  
  public char decrypt(char data){
    int n = this.p * this.q;
    int numeric = (int) data;
    int resultNum = (int) (Math.ceil(Math.pow(numeric, (double) this.d)) % n);
    char result = (char) resultNum;
    return result;
  }
}

class User {
  private BadKey key1;

  public User(int cap) {
    this.key1 = new BadKey(cap);
  }

  public String encrypt(String data) {
    String result = "";
    System.out.println("Encrypting \"" + data + "\" for uploading...");
    for (int index = 0; index < data.length(); index++) {
      char next = data.charAt(index);
      char nextResult = this.key1.encrypt(next);
      result += nextResult;
    }
    System.out.println("Encrypted to: " + result);
    return result;
  }

  public String decrypt(String data) {
    String result = "";
    System.out.println("Decrypting \"" + data + "\" for reading...");
    for (int index = 0; index < data.length(); index++) {
      char next = data.charAt(index);
      char nextResult = this.key1.decrypt(next);
      result += nextResult;
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
    return "Decrypted to: " + user.decrypt(unparsed);

  }

  public void upload(User user, String data) {
    uploads.add(user.encrypt(data));
  }
}

class Main {
  public static void main(String[] args) {
    Cloud testCloud = new Cloud();
    User me = new User(100);
    testCloud.upload(me, "I can see this!");
    System.out.println(testCloud.read(me, 0));
  }
}
