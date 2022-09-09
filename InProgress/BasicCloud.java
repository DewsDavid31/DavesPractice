class Cloud {
  String[][] hardDrives;
  int[][] jumpTable;
  int[][] deviceTable;
  int[][] Authorizations;
  String[][] uploads;
  final int BLOCKSIZE = 40;

  public void Cloud(){
    this.jumpTable = new int[2][4];
    this.uploads = new String[2][400];
    this.deviceTable = new int[2][4];
    this.hardDrives = new String[2][400];
  }

  public void loadBalanceRoundRobin() {
    int userIndex = 0;
    int deviceIndex = 0;
    for (int uploadIndex = 0; uploadIndex < this.uploads[0].length; uploadIndex++) {
      this.hardDrives[deviceIndex][uploadIndex] = this.uploads[userIndex][uploadIndex];
      userIndex++;
      deviceIndex++;
      userIndex = userIndex % 2;
      deviceIndex = deviceIndex % 2;
    }
  }

  public void upload(int user, String data) {
    int blockIndex = 0;
    for (int charIndex = 0; charIndex < data.length(); charIndex += BLOCKSIZE) {
      this.uploads[user][blockIndex] = data.substring(blockIndex * BLOCKSIZE, charIndex);
    }
  }

  public String read(int device, int location) {
    String result = hardDrives[device][location];
    if (this.jumpTable[device][location] != -1) {
      return result + this.read(deviceTable[device][location], jumpTable[device][location]);
    } else {
      return result;
    }
  }

}

class Main {
  public static void main(String[] args) {
    Cloud testCloud = new Cloud();
    testCloud.upload(0, "I can see this!");
    testCloud.upload(1, "I am not allowed to see this");
    testCloud.upload(0, "this file is too large to be only on my pc !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(testCloud.read(0,1));
    System.out.println(testCloud.read(0,2));
    System.out.println(testCloud.read(0,3));
    System.out.println(testCloud.read(0,4));
  }
}
