import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;

import groovyjarjarasm.asm.ClassReader;
import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.ClassWriter;
import groovyjarjarasm.asm.Opcodes;

/**
 * @author liguandong
 * @data 2022/10/10
 */
public class ByteCodeTest2 {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public int add() {
        int i = 10;
        int j = 1;
        return i + j;
    }
}