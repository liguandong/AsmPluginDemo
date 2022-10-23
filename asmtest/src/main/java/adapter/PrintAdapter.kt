package adapter

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class PrintAdapter(
    methodVisitor: MethodVisitor, access: Int,
    name: String?,
    signature: String?
) :
    AdviceAdapter(ASM9, methodVisitor, access, name, signature) {

    override fun onMethodEnter() {
        super.onMethodEnter()

        //String压栈
        visitLdcInsn("insert")
        //静态变量压栈
        getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"))
        swap()
        //调用方法
        invokeVirtual(Type.getType("Ljava/io/PrintStream;"),
            Method("println","(Ljava/lang/Object;)V")
        )
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
    }
}