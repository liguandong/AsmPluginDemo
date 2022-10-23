package adapter

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class TimeCostAdapter(
    methodVisitor: MethodVisitor, access: Int,
    name: String?,
    signature: String?
) :
    AdviceAdapter(ASM9, methodVisitor, access, name, signature) {
    var slot = 0
    override fun onMethodEnter() {
        super.onMethodEnter()
        //调用静态方法，并返回一个值压栈
        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis","()J"))
        //创建一个本地变量，返回一个索引
        slot = newLocal(Type.LONG_TYPE)
        //栈顶元素复制到一个局部变量
        storeLocal(slot)
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        //创建对象
        newInstance(Type.getType("Ljava/lang/StringBuilder;"))
        //栈顶复制指令，因为需要对象的初始化方法，需要消耗一个栈顶元素
        dup()
        // 调用初始化方法，消耗栈顶出栈
        invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"),Method("<init>","()V"))
        //字符参数入站
        visitLdcInsn("cost:")
        //调用方法增加字符，消耗栈顶元素，返回值入栈
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
        //获取时间方法，返回值入栈
        invokeStatic(Type.getType("Ljava/lang/System"),Method("currentTimeMillis","()J"))
        //取在本地变量入栈
        loadLocal(slot)
        //调用Long减法指令，消耗栈2个元素出栈，返回值入栈
        visitInsn(LSUB);
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("append","(J)Ljava/lang/StringBuilder;"))
        visitLdcInsn("ms")
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("toString","()Ljava/lang/String;"))
        getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"))
        //栈顶和栈顶-1 交换位置， 把上面两个指令交换下就不用
        swap()
        invokeVirtual(Type.getType("Ljava/io/PrintStream;"),Method("println","(Ljava/lang/Object;)V"))

    }
}