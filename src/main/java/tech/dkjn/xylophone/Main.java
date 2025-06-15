package tech.dkjn.xylophone;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter class name:");
        String className = scanner.nextLine();
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V24, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);
        System.out.println("Enter method name:");
        String methodName = scanner.nextLine();
        System.out.println("Enter descriptor:");
        String descriptor = scanner.nextLine();
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, methodName, descriptor, null, null);
        System.out.println("Enter operation:");
        String op = scanner.nextLine();
        while(!Objects.equals(op, "exit")) {
            if (Objects.equals(op, "iconstant")) {
                System.out.println("Enter constant:");
                int value = Integer.parseInt(scanner.nextLine());
                mv.visitLdcInsn(value);
            }
            if (Objects.equals(op, "fconstant")) {
                System.out.println("Enter constant:");
                float value = Float.parseFloat(scanner.nextLine());
                mv.visitLdcInsn(value);
            }
            if (Objects.equals(op, "ireturn")) {
                mv.visitInsn(Opcodes.IRETURN);
            }
            if (Objects.equals(op, "freturn")) {
                mv.visitInsn(Opcodes.FRETURN);
            }
            if (Objects.equals(op, "iadd")) {
                mv.visitInsn(Opcodes.IADD);
            }
            if (Objects.equals(op, "fadd")) {
                mv.visitInsn(Opcodes.FADD);
            }
            System.out.println("Enter operation:");
            op = scanner.nextLine();
        }
        mv.visitEnd();
        cw.visitEnd();
        byte[] bytecode = cw.toByteArray();
        try (FileOutputStream fos = new FileOutputStream(className + ".class")) {
            fos.write(bytecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
