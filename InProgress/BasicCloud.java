  import java.util.ArrayList;
import java.util.Random;
import java.math.BigInteger;

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
    BigInteger resultNum = numeric.modPow(BigInteger.valueOf(this.e),BigInteger.valueOf(this.n));
    char result = (char) resultNum.intValue();
    return result;
  }

  public char decrypt(char data) {
    int n = this.p * this.q;
    BigInteger numeric = BigInteger.valueOf(data);
    BigInteger resultNum = numeric.modPow(BigInteger.valueOf(this.d),BigInteger.valueOf(n));
    char result = (char) resultNum.intValue();
    return result;
  }
}

class User {
  private BadKey key1;
  public String name;
  
  public User(String name, int cap) {
    this.key1 = new BadKey(cap);
    this.name = name;
  }

  public String encrypt(String data) {
    String result = "";
    System.out.println(this.name + " is Encrypting \"" + data + "\" for uploading...");
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
    System.out.println(this.name + " is Decrypting \"" + data + "\" for reading...");
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
    User me = new User("me", 100);
    User notMe = new User("Someone Else!", 100);
    testCloud.upload(me, "I can see this!");
    testCloud.upload(notMe, "I can\'t see this, this is someone's file on my pc");
    System.out.println(testCloud.read(me, 0));
    System.out.println("Reading file Im not allowed access to...");
    System.out.println(testCloud.read(me, 1));
  }
}
