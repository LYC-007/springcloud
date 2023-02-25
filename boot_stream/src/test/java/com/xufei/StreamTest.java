package com.xufei;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 在数据量比较大的情况下，CPU负载本身不是很高，不要求顺序执行的时候，可以使用并行流。
 */
public class StreamTest {
    //创建 Stream方式一：通过集合
    @Test
    public void test1() {
        List<Student> list = new ArrayList<>();
        //default Stream<E> stream() : 返回一个顺序流
        Stream<Student> stream = list.stream();
        //default Stream<E> parallelStream() : 返回一个并行流
        Stream<Student> parallelStream = list.parallelStream();

    }

    //创建 Stream方式二：通过数组
    @Test
    public void test2() {
        int[] arr = new int[]{1, 2, 3, 4};
        //调用Arrays类的static <T> Stream<T> stream(T[] array): 返回一个流
        IntStream stream = Arrays.stream(arr);
    }

    //创建 Stream方式三：通过Stream的of()
    @Test
    public void test3() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
    }

    //创建 Stream方式四：创建无限流(了解)
    @Test
    public void test4() {
        //迭代
        //public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
        //遍历前10个偶数
        Stream.iterate(0, t -> {
            return t + 2;
        }).limit(10).forEach(System.out::println);
        //生成
        //public static<T> Stream<T> generate(Supplier<T> s)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }


    /**
     * 中间操作
     */
    @Test
    public void test5() {
        System.out.println("*******************************************中间操作测试*****************************************************");
        Stream<Integer> stream = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        Stream<Integer> newStream = stream
                .filter(s -> s > 5) //6 6 7 9 8 10 12 14 14    过滤流中的某些元素
                .distinct() //6 7 9 8 10 12 14      通过流中元素的 hashCode() 和 equals() 去除重复元素
                .skip(2) //9 8 10 12 14     跳过n元素，配合limit(n)可实现分页
                .limit(4)  //9  8  10 12    获取n个元素
                .map(s -> ++s)//10   9  11 13        接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。map接收的是一个Function表达式，有返回值
                .sorted() // 9  10  11  13  默认排序为升序
                .sorted((o1, o2) -> o2 - o1);   //13   11   10   9
        newStream.forEach(System.out::println);   //13   11   10   9
        /**
         * flatMap:接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
         */
        List<String> arrayList = Arrays.asList("a,b,c", "1,2,3");
        arrayList.stream().flatMap(s -> {
            String[] split = s.split(",");
            return Arrays.stream(split);
        }).forEach(System.out::println); // a b c 1 2 3
        /**
         * 如同于map，能得到流中的每一个元素。peek接收的是Consumer表达式，没有返回值,但map接收的是一个Function表达式;
         */
        Student s1 = new Student("aa", 10);
        List<Student> studentList = Arrays.asList(s1);
        studentList.stream()
                .peek(o -> o.setAge(100))
                .forEach(System.out::println);
    }

    /**
     * 终止操作之收集操作
     */
    public void test7() {
        Student s0 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("cc", 10);
        List<Student> list = Arrays.asList(s0, s2, s3);
        //装成list
        List<Integer> ageList = list.stream().map(Student::getAge).collect(Collectors.toList()); // [10, 20, 10]
        //转成set
        Set<Integer> ageSet = list.stream().map(Student::getAge).collect(Collectors.toSet()); // [20, 10]
        //转成map,注:key不能相同，否则报错
        Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); // {cc=10, bb=20, aa=10}
        //字符串分隔符连接
        String joinName = list.stream().map(Student::getName).collect(Collectors.joining(",", "(", ")")); // (aa,bb,cc)
        //学生总数
        Long coun = list.stream().collect(Collectors.counting()); // 3
        //最大年龄 (最小的minBy同理)
        Integer maxAge = list.stream().map(Student::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
        //所有人的年龄
        Integer sumAge = list.stream().collect(Collectors.summingInt(Student::getAge)); // 40
        //平均年龄
        Double averageAge = list.stream().collect(Collectors.averagingDouble(Student::getAge)); // 13.333333333333334
        //带上以上所有方法
        DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Student::getAge));
        System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());
        //分组
        Map<Integer, List<Student>> ageMap = list.stream().collect(Collectors.groupingBy(Student::getAge));
        //多重分组,先根据名字分再根据年龄分
        Map<String, Map<Integer, List<Student>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy(Student::getAge)));
        //分成两部分，一部分大于10岁，一部分小于等于10岁
        Map<Boolean, List<Student>> partMap = list.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));
    }

    /**
     * 终止操作之规约
     */
    public void test8() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        /**
         * 1.Optional<T> reduce(BinaryOperator<T> accumulator)：第一次执行时，accumulator函数的第一个参数为流中的第一个元素，第二个参数为流中元素的第二个元素;
         * 第二次执行时，第一个参数为第一次函数执行的结果，第二个参数为流中的第三个元素；依次类推。
         */
        Integer v = integerList.stream().reduce((x1, x2) -> x1 + x2).get();
        System.out.println(v);   // 300

        /**
         * 2.T reduce(T identity, BinaryOperator<T> accumulator)：流程跟上面一样，只是第一次执行时，accumulator函数的第一个参数为identity，而第二个参数为流中的第一个元素。
         */
        Integer v1 = integerList.stream().reduce(10, (x1, x2) -> x1 + x2);
        System.out.println(v1);  //310


        /**
         * 3.<U> U reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)：
         *      A.在串行流(stream)中，该方法跟第二个方法一样，即第三个参数combiner不会起作用。
         *      B.在并行流(parallelStream)中,我们知道流被fork join出多个线程进行执行，此时每个线程的执行流程就跟第二个方法reduce(identity,accumulator)一样，
         *      而第三个参数combiner函数，则是将每个线程的执行结果当成一个新的流，然后使用第一个方法reduce(accumulator)流程进行规约。
         */
        Integer v3 = integerList.parallelStream().reduce(
                1,
                (x1, x2) -> {
                    return x1 * x2;  //x1 为默认值(就是第一个参数)， x2 数组的每个元素 ,每个元素都和默认值相乘得到一个新的数组;
                },
                (x1, x2) -> {        //将得到的新数组的各个元素进行累加
                    return x1 + x2;
                });
        System.out.println(v3); //300
    }

    /**
     * 终止操作之匹配，聚合
     */
    public void test9() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        boolean allMatch = list.stream().allMatch(e -> e > 10); //false   接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false
        boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true  接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false
        boolean anyMatch = list.stream().anyMatch(e -> e > 4);  //true    接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false

        Integer findFirst = list.stream().findFirst().get(); //1    返回流中第一个元素
        Integer findAny = list.stream().findAny().get();    //      返回流中的任意元素

        long count = list.stream().count(); //5   返回流中元素的总个数
        Integer max = list.stream().max(Integer::compareTo).get(); //5   指定你的排序规则,返回流中元素最大值
        Integer min = list.stream().min(Integer::compareTo).get(); //1   指定你的排序规则,返回流中元素最小值
    }

    /**
     * Collections工具类总结
     */
    public void test10() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        /*
         * 通过collect() 方法将Stream转换成容器的方法中将Stream转换成List和Set是最常见的操作,在Collectors工具类中已经提供了对应的收集器
         * 将Stream转换成List或者Set
         */
        List<String> list = stream.collect(Collectors.toList());
        Set<String> set = stream.collect(Collectors.toSet());
        /*
         * 有时需要人为指定容器的实际类型,这个需求可以通过Collectors.toCollection(Supplier< C > collectionFactory) 完成
         * 使用toCollection指定规约容器的类型
         */
        ArrayList<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));
        HashSet<String> hashSet = stream.collect(Collectors.toCollection(HashSet::new));

        /*
         * 使用Collectors.joining()拼接字符串
         */
        String join1 = stream.collect(Collectors.joining()); // Iloveyou
        String join2 = stream.collect(Collectors.joining(","));  // I,love,you
        String join3 = stream.collect(Collectors.joining(",", "{", "}")); // {I,love,you}
    }

    /**
     * 收集成 Map
     */
    public void test11() {
        Student s0 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("cc", 10);
        List<Student> list = Arrays.asList(s0, s2, s3);
        //转成map,注:key不能相同，否则报错
        Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); // {cc=10, bb=20, aa=10}

        /**
         * 使用Collectors.partitioningBy() 生成的收集器:适用于将Stream中的元素依据某个二值逻辑(Boolean 满足,不满足)分成互补相交的两部分(将学生的年纪分成大于等于15和小于15岁;)
         */
        Map<Boolean, List<Student>> passingFailing = list.stream().collect(Collectors.partitioningBy(student -> student.getAge() >= 15));


        /**
         * 使用Collectors.groupingBy() 生成的收集器: 对元素做group操作时用到,这里的groupingBy也是按照某个属性对数据进行分组,属性相同的元素会被对应到Map的同一个key上
         */
        Map<Integer, List<Student>> byDept = list.stream().collect(Collectors.groupingBy(Student::getAge));

        /**
         * 增强版的**groupingBy()**允许先对元素分组之后再执行某种运算,比如求和,计数,平均值,类型转换等
         */
        Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        Map<String, Map<Integer, List<Student>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy(Student::getAge)));

    }

}

@Data
@AllArgsConstructor
class Student {
    private String name;
    private int age;
}
