package com.zcl.lambda;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by zhaocl1 on 2018/5/18.
 */
public class Two_Stream {
    /**
     * 使用lambdas 和 streams
     *Stream是对集合的包装，通常和lambda一起用，可支持操作有：map, filter, limit, sorted, count, min, max, sum, collect
     */
    public static void test4(){
        List<Person> javaProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
                add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
                add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
                add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
                add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
                add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
                add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
                add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
                add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
                add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
            }
        };

        List<Person> phpProgrammers = new ArrayList<Person>() {
            {
                add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
                add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
                add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
                add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
                add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
                add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
                add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
                add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
                add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
                add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
            }
        };

        javaProgrammers.forEach((p)-> System.out.printf("%s %s ;",p.getFirstName(),p.getLastName()));
        phpProgrammers.forEach((p) -> System.out.printf("%s %s;", p.getFirstName(), p.getLastName()));

        Consumer<Person> giveRaise = e -> e.setSalary(e.getSalary()/100*5+e.getSalary());
        javaProgrammers.forEach(giveRaise);
        phpProgrammers.forEach(e -> e.setSalary(e.getSalary() / 100 * 10 + e.getSalary()));

        javaProgrammers.forEach((p)-> System.out.println(p.getSalary()));
        phpProgrammers.forEach((p) -> System.out.println(p.getSalary()));

        System.out.println("月薪超过1400");
        phpProgrammers.stream().filter((p)->(p.getSalary()>1400)).forEach((p) -> System.out.println(p));


        Predicate<Person> ageFilter = (p) -> (p.getAge() > 25);
        Predicate<Person> salaryFilter = (p) -> (p.getSalary()>1400);
        Predicate<Person> genderFilter = (p) ->("female".equals(p.getGender()));
        System.out.println("1400 25 female");
        phpProgrammers.stream().filter(ageFilter).filter(salaryFilter).filter(genderFilter).forEach((p) -> System.out.println(p));
        javaProgrammers.stream().filter(ageFilter).filter(salaryFilter).filter(genderFilter).forEach((p) -> System.out.println(p));

        System.out.println("limit 3");
        javaProgrammers.stream().limit(3).forEach((p) -> System.out.println(p));

        System.out.println(" salary sort");
        List<Person> sortedJava = javaProgrammers.stream().sorted((p1, p2) -> (p1.getSalary() - p2.getSalary()))
                .collect(Collectors.toList());

        sortedJava.forEach((p) -> System.out.println(p));

        System.out.println(" min ");
        Person person = javaProgrammers.stream().min((p1, p2) -> (p1.getSalary() - p2.getSalary())).get();
        System.out.println(person);

        System.out.println("max");
        System.out.println(javaProgrammers.stream().max((p1,p2)->(p1.getSalary() - p2.getSalary())).get());

        System.out.println("to String");
        String phpDeve = phpProgrammers.stream().map(Person::getFirstName).collect(Collectors.joining(";"));
        System.out.println("String :"+phpDeve);

        // map函数可以说是函数式编程里最重要的一个方法了。map的作用是将一个对象变换为另外一个
        System.out.println("to Set");
        Set<String> phpDeveSet = phpProgrammers.stream().map(Person::getFirstName).collect(Collectors.toSet());
        System.out.println("phpDeveSet :"+phpDeveSet);

        System.out.println("to TreeSet");
        TreeSet<String> phpDeveTreeSet = phpProgrammers.stream().map(Person::getFirstName).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("phpDeveTreeSet :"+phpDeveTreeSet);

        System.out.println(" the total money");
        int totalSalary = javaProgrammers.parallelStream().mapToInt(p -> p.getSalary()).sum();
        System.out.println("totalSalary ="+totalSalary);

        System.out.println("summaryStatistics");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x)->x).summaryStatistics();
        System.out.println(" getMax "+stats.getMax());
        System.out.println(" getAverage "+stats.getAverage());
        System.out.println(" getCount "+stats.getCount());
        System.out.println(" getMin "+stats.getMin());
        System.out.println(" getSum "+stats.getSum());

        // map函数可以说是函数式编程里最重要的一个方法了。map的作用是将一个对象变换为另外一个
        //reduce实现的则是将所有值合并为一个
        System.out.println(" map reduce");
        numbers.stream().map(x -> x + x * 0.5).forEach(x -> System.out.println(x));
        int all = numbers.stream().reduce((sum,x)->(sum+x)).get();
        System.out.println("all="+all);
    }


    public static void main(String[] args) {
        test4();
    }
}
