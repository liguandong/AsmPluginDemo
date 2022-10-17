/**
 * @author liguandong
 * @data 2022/10/10
 */
public class ByteCodeTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public String add(boolean a, String s) {
        if (a) {
            return s;
        } else {
            return "";
        }
    }
}