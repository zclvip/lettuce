package com.zcl.future.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaocl1 on 2018/7/12.
 */
public class App {



    public static void main(String[] args) {
//        studentExecutor();
//        teacherExecutor();
        teacherExecutor2();
    }

    public static void studentExecutor(){
        Executor executor = new Executor();
        List<Student> students = new ArrayList<>();
        Student student = new Student("zhang san");
        Student student1 = new Student("li si");
        Student student2 = new Student("wang wu");
        students.add(student);
        students.add(student1);
        students.add(student2);
        StudentExecutor studentExecutor = new StudentExecutor(executor,students);
        boolean result = studentExecutor.executor();
        System.out.println("result=" + result);
    }

    public static void teacherExecutor(){
        Teacher teacher = new Teacher("zhang teacher");
        Teacher teacher1 = new Teacher("wang teacher");
        Teacher teacher2 = new Teacher("li teacher");
        Teacher teacher3 = new Teacher("zhao teacher");
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);

        TeacherExecutor teacherExecutor = new TeacherExecutor(teachers);
        boolean result = teacherExecutor.executor();
        System.out.println("result=" + result);
    }

    public static void teacherExecutor2(){
        Teacher teacher = new Teacher("zhang teacher");
        Teacher teacher1 = new Teacher("wang teacher");
        Teacher teacher2 = new Teacher("li teacher");
        Teacher teacher3 = new Teacher("zhao teacher");
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);

        TeacherExecutor teacherExecutor = new TeacherExecutor(teachers);
        boolean result = teacherExecutor.executor2();
        System.out.println("result=" + result);
    }
}
