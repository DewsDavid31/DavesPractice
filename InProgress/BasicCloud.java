import java.util.ArrayList;
import java.util.Random;
import java.math.BigInteger;
import java.util.Scanner;
class BadKey {
  public Random gen;
  private int n;
  private int totient;
  private int e;
  private int q;
  private int d;
  private int p;
  public int limit;

  public BadKey(int limit) {
    this.gen = new Random();
    this.limit = limit;
    this.e = this.pickPrime(3, limit);
    this.find_p_q();
    this.findD();
  }

  public int pickPrime(int start, int limit) {
    int candidate = 2;
    boolean prime = false;
    while (!prime) {
      candidate = gen.nextInt((int) limit - 2) + 2;
      prime = true;
      if (candidate < start || candidate > limit) {
        prime = false;
        continue;
      }
      for (int check = 2; check < candidate; check++) {
        if (candidate % check == 0) {
          prime = false;
        }
      }
    }
    return candidate;
  }

  public int gcd(int a, int b) {
    if (b == 0) {
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

  public void find_p_q() {
    boolean found = false;
    System.out.println("Generating public key...");
    while (!found) {
      this.p = this.pickPrime(this.e + 1, this.limit);
      this.q = this.pickPrime(this.e + 1, this.limit);
      this.totient = (p - 1) * (q - 1);
      if (this.gcd(this.e, this.totient) == 1) {
        found = true;
        this.n = this.p * this.q;
      }
    }
  }

  public void findD() {
    System.out.println("Generating private key...");
    this.d = inverseModBrute(this.e, this.totient);
  }

  public char encrypt(char data) {
    BigInteger numeric = BigInteger.valueOf((int) data);
    BigInteger resultNum = numeric.modPow(BigInteger.valueOf(this.e), BigInteger.valueOf(this.n));
    char result = (char) resultNum.intValue();
    return result;
  }

  public char decrypt(char data) {
    int n = this.p * this.q;
    BigInteger numeric = BigInteger.valueOf(data);
    BigInteger resultNum = numeric.modPow(BigInteger.valueOf(this.d), BigInteger.valueOf(n));
    char result = (char) resultNum.intValue();
    return result;
  }
}

class User {
  private BadKey key1;
  public String name;
  public ArrayList<String> hardDrive;

  public User(String name, int cap) {
    this.key1 = new BadKey(cap);
    this.name = name;
    this.hardDrive = new ArrayList<String>();
  }

  public void write(String data) {
    this.hardDrive.add(data);
  }

  public void read() {
    System.out.println("\nOn " + this.name + "\'s Hard Drive: ");
    for (int index = 0; index < this.hardDrive.size(); index++) {
      System.out.println("File #" + index + ": " + this.decrypt(this.hardDrive.get(index)));
    }
  }

  public String encrypt(String data) {
    String result = "";
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
  public ArrayList<User> pcs;
  public ArrayList<String> blocktable;
  public Scanner scanner;
  public Cloud(Scanner scanner) {
    this.uploads = new ArrayList<String>();
    this.pcs = new ArrayList<User>();
    this.scanner = new Scanner(System.in);
  }

  public void registerUser(User newUser) {
    this.pcs.add(newUser);
  }

  public void loadBalanceRoundRobin() {
    System.out.println("\nDecentralizing files...");
    int pcSize = this.pcs.size();
    for (int index = this.uploads.size() - 1; index >= 0; index--) {
      String next = this.uploads.get(index);
      this.pcs.get(index % pcSize).write(next);
    }
    this.uploads.clear();
    System.out.println("Done!");
  }

  public String read(User user, int index) {
    String unparsed = uploads.get(index);
    return "\nDecrypted to: " + user.decrypt(unparsed);
  }

  public void fetch(User querer){
    for(int grabUser = 0; grabUser < this.pcs.size(); grabUser++){
      User nextUser = this.pcs.get(grabUser);
      int harddriveSize = nextUser.hardDrive.size();
      System.out.println(nextUser.name + " \'s harddrive: ");
      for(int item = 0; item < harddriveSize; item++){
        String decryptedStr = querer.decrypt(nextUser.hardDrive.get(item));
        if(decryptedStr.contains(querer.name)){
          System.out.println("File #" + item + ": " + decryptedStr);
        }
      }
   }
    System.out.println("Files Uploading currently: ");
    for(int grabUploads = 0; grabUploads < this.uploads.size(); grabUploads++ ){
      String decryptedStr = querer.decrypt(this.uploads.get(grabUploads));
        if(decryptedStr.contains(querer.name)){
          System.out.println("File #" + grabUploads + ": " + decryptedStr);
        }
    }
  }
  
  public void fetchAll(){
    for(int grabUser = 0; grabUser < this.pcs.size(); grabUser++){
      User nextUser = this.pcs.get(grabUser);
      int harddriveSize = nextUser.hardDrive.size();
      System.out.println(nextUser.name + " \'s harddrive: ");
      for(int item = 0; item < harddriveSize; item++){
        System.out.println("File #" + item + ": " + nextUser.hardDrive.get(item));
      }
   }
    System.out.println("Files Uploading currently: ");
    for(int grabUploads = 0; grabUploads < this.uploads.size(); grabUploads++ ){
      System.out.println("File #" + grabUploads + ": " + this.uploads.get(grabUploads));
    }
  }

  public void upload(User user, String data) {
    System.out.println("\n" + user.name + " is encrypting \"" + data + "\" for upload");
    this.uploads.add(user.encrypt(user.name + ": " + data));
  }
   public void menu(){
     System.out.println("Log into a user:");
    for(int names = 0; names < this.pcs.size(); names++){
      System.out.println(names + ". " + this.pcs.get(names).name );
    }
     System.out.print("Select a number: ");
     int option = this.scanner.nextInt();
     User us = this.pcs.get(option);
     
    System.out.println("\nWelcome to the cloud drive " + us.name + "! Pick an option");
    System.out.println("1. Read my harddrive directly");
    System.out.println("2. Ask Cloud to fetch my files");
    System.out.println("3. Look at all Cloud files");
    System.out.println("4. Write text to Cloud Upload");
    System.out.println("5. Quit");
    System.out.print("Select a number: ");
    String optionStr = this.scanner.next();
    switch(optionStr){
      case "1": us.read();
         this.menu();
      case "2": this.fetch(us);
         this.menu();
      case "3": this.fetchAll();
         this.menu();
      case "4": us.write(this.scanner.nextLine());
         this.menu();
      default:
        break;
    }
  }
}

class Main {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    Cloud testCloud = new Cloud(console);
    User me = new User("me", 100);
    User notMe = new User("Someone Else", 100);
    testCloud.registerUser(me);
    testCloud.registerUser(notMe);
    testCloud.menu();
  }
}
