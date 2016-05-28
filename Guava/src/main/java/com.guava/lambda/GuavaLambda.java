package com.guava.lambda;

import com.entity.Person;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.and;


/**
 * ClassName: GuavaLambda
 * Description:
 * Date: 2016/5/28 9:17
 *
 * @author SAM SHO
 * @version V1.0
 */
public class GuavaLambda {

    public static void main(String[] args) {
        GuavaLambda lambda = new GuavaLambda();
//        lambda.test();
//        lambda.test1();
//        lambda.test2();
        lambda.test3();

    }

    public void test() {
        List<Person> people = new ArrayList<Person>();
        people.add(new Person("bowen",27));
        people.add(new Person("bob", 20));
        people.add(new Person("Katy", 18));
        people.add(new Person("Logon", 24));

        System.out.println(people);

        /** 选取年龄 > 20 **/
        List<Person> oldPeople = new ArrayList<Person>();
        for (Person person : people) {
            if (person.getAge() >= 20) {
                oldPeople.add(person);
            }
        }

        System.out.println(oldPeople);
    }

    /**
     * newArrayList()方法
     */
    public void test1() {
        List<Person> people = Lists.newArrayList(new Person("bowen", 27),
                new Person("bob", 20),
                new Person("Katy", 18),
                new Person("Logon", 24));

        System.out.println(people);

        /**
         * 这就是典型的filter模式。filter即从一个集合中根据一个条件筛选元素。
         * 其中person.getAge() >=20就是这个条件。Guava为这种模式提供了一个filter的方法。所以我们可以这样写。
         *
         * Predicate 里面只有一个apply方法，接收一个泛型的实参，返回一个boolean值。
         * 由于java世界中函数并不是一等公民，所以我们无法直接传递一个条件函数，只能通过Predicate这个类包装一下。
         *
         * Iterables.filter 与 Collections2.filter 一样可以用
         *
         **/
        List<Person> oldPeople = Lists.newArrayList(Iterables.filter(people, new Predicate<Person>() {
            public boolean apply(Person person) {
                return person.getAge() >= 20;
            }
        }));

        System.out.println(oldPeople);
    }

    /**
     * And  Predicate
     */
    public void test2() {
        List<Person> people = Lists.newArrayList(new Person("bowen", 27),
                new Person("bob", 20),
                new Person("Katy", 18),
                new Person("Logon", 24));
        System.out.println(people);

        List<Person> filteredPeople = Lists.newArrayList(Collections2.filter(people, Predicates.and(ageBiggerThan(20), nameContains("b"))));

        System.out.println(filteredPeople);
    }

    private Predicate<Person> ageBiggerThan(final int age) {
        return new Predicate<Person>() {
            public boolean apply(Person person) {
                return person.getAge() >= age;
            }
        };
    }

    private Predicate<Person> nameContains(final String str) {
        return new Predicate<Person>() {
            public boolean apply(Person person) {
                return person.getName().contains(str);
            }
        };
    }

    /**
     * Map(transform):map pattern
     * 就是将数组中的所有元素映射为另一种元素的列表
     **/
    public void test3() {

        List<Person> people = Lists.newArrayList(new Person("bowen", 27),
                new Person("bob", 20),
                new Person("Katy", 18),
                new Person("Logon", 24));

        /*求People列表中的所有人名。程序员十有八九都会这样写。*/
        List<String> names = new ArrayList<String>();
        for (Person person : people) {
            names.add(person.getName());
        }
        System.out.println(names);

        /**
         * Function是另外一种用于封装函数的接口对象。
         * 它与Predicate非常相似，但不同的是它接收两个泛型，
         * apply方法接收一种泛型实参，返回值是另一种泛型值。正是这个apply方法定义了数组间元素一对一的map规则。
         **/
        List<String> names2 = Lists.newArrayList(Collections2.transform(people, new Function<Person, String>() {
            public String apply(Person person) {
                return person.getName();
            }
        }));
        System.out.println(names2);

    }


}
