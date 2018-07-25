package com.zcl.future.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaocl1 on 2018/7/12.
 */
public class StudentExecutor {
    private Executor executor;
    private List<Student> students = new ArrayList<>();

    public StudentExecutor(Executor executor, List<Student> students) {
        this.executor = executor;
        this.students = students;
    }

    public boolean executor(){
        List<Integer> result = executor.executor(students, new ExecutorCallback() {
            @Override
            public int execute(Person person) {
                return ((Student)person).game();
            }
        });
        for (Integer i : result){
            System.out.println("student game:"+i);
        }
        if (result != null && result.size() > 0){
            return true;
        }
        return false;
    }
}
