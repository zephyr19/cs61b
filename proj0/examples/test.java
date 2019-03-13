import java.util.Scanner;
public class test {
  public static void main(String[] args) {
    //Scanner S = new Scanner(System.in);
    //String tel = S.next();
    String tel = "13317082202";
    int[] list = new int [10];
    int x;
    for (int i = 0; i < 10; i++) {
      list[i] = 0;
    }
    for (int i = 0; i < 11; i++) {
      x = Character.getNumericValue(tel.charAt(i));
      list[x] += 1;
    }
    int len = 0;
    for (int i : list) {
      if (i != 0) {
        len += 1;
      }
    }
    int[] newlist = new int [len];
    int point = 9;
    for (int i = 0; i < len; i++) {
      for (int j = point; j >= 0; j--) {
        if (list[j] != 0) {
          newlist[i] = j;
          point = j-1;
          break;
        }
      }
    }
    int[] output = new int[11];
    for (int i = 0; i < 11; i++) {
      x = Character.getNumericValue(tel.charAt(i));
      for (int j = 0; j < len; j++) {
        if (newlist[j] == x) {
          output[i] = j;
          break;
        }
      }
    }
    String str1 = "{";
    String str2 = "{";
    for (int i = 0; i < len-1; i++) {
      str1 = str1 + Integer.toString(newlist[i]) + ",";
    }
    str1 = str1 + Integer.toString(newlist[len-1]) + "};";
    for (int i = 0; i < 10; i++) {
      str2 = str2 + Integer.toString(output[i]) + ",";
    }
    str2 = str2 + Integer.toString(output[10]) + "};";
    System.out.printf("int[] arr = new int[]%s\n", str1);
    System.out.printf("int[] index = new int[]%s", str2);
  }
}