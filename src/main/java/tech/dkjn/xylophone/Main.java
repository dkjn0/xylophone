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
        System.out.println("Enter folder without ending slash:");
        String folder = scanner.nextLine();
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
        while (!Objects.equals(op, "exit")) {
            switch (op) {
                case "iconstant":
                    System.out.println("Enter constant:");
                    int intValue = Integer.parseInt(scanner.nextLine());
                    mv.visitLdcInsn(intValue);
                    break;
                case "fconstant":
                    System.out.println("Enter constant:");
                    float floatValue = Float.parseFloat(scanner.nextLine());
                    mv.visitLdcInsn(floatValue);
                    break;
                case "ireturn":
                    mv.visitInsn(Opcodes.IRETURN);
                    break;
                case "freturn":
                    mv.visitInsn(Opcodes.FRETURN);
                    break;
                case "iadd":
                    mv.visitInsn(Opcodes.IADD);
                    break;
                case "fadd":
                    mv.visitInsn(Opcodes.FADD);
                    break;
                default:
                    System.out.println("Unknown operation: " + op);
            }
            System.out.println("Enter operation:");
            op = scanner.nextLine();
        }
        mv.visitEnd();
        cw.visitEnd();
        byte[] bytecode = cw.toByteArray();
        try (FileOutputStream fos = new FileOutputStream(folder + "/" + className + ".class")) {
            fos.write(bytecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}

