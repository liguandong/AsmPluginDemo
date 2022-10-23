/**
 * @author liguandong
 * @data 2022/10/10
 */
public class ByteCodeTest2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("Hello World!");
//        long cost = System.currentTimeMillis()-start;
        System.out.println("cost :" + (System.currentTimeMillis()-start) + "ms");
    }
}