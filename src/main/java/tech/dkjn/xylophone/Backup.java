package tech.dkjn.xylophone;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Backup {
    public static void generate(String className, String methodName) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V24, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, methodName, "(II)D", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.IADD);
        mv.visitInsn(Opcodes.I2D);
        mv.visitLdcInsn(1.0 / 3.0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
        mv.visitInsn(Opcodes.DRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(className + ".class")) {
            fos.write(bytecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run(String className, String methodName, int arg1, int arg2) {
        try {
            File dir = new File("./");
            URL url = dir.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

            Class<?> loadedClass = classLoader.loadClass(className);

            Method method = loadedClass.getMethod(methodName, int.class, int.class);

            Object result = method.invoke(null, arg1, arg2);
            System.out.println(result);

            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void backup(String[] args) {
        String className = "Test";
        String methodName = "math";
        generate(className, methodName);
        run(className, methodName, 4, 4);
    }
}
