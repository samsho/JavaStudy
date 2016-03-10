package com.guava.base;

import com.entity.Presider;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * ClassName: ObjectsTest
 * Description:
 * Date: 2016/3/10 14:49
 *
 * @author sm12652
 * @version V1.0
 */
public class ObjectsTest {
    public static void main(String[] args) {
        test4();
    }

    /**
     * firstNonNull
     */
    static void test1() {
        MoreObjects.firstNonNull(null, 2);
    }

    /**
     * equal:java.util.Objects.equals()代替
     */
    static void test2() {
        Objects.equal("a", "a"); // returns true
        Objects.equal(null, "a"); // returns false
        Objects.equal("a", null); // returns false
        Objects.equal(null, null); // returns true

        java.util.Objects.equals(null, null);//instead

    }

    /**
     * hashCode:使用java.util.Objects.hash代替
     */
    static void test3() {

        Objects.hashCode(2);
        java.util.Objects.hash(2);//instead
        java.util.Objects.hashCode(2);
    }

    /**
     * toStringHelper
     */
    static void test4() {
        // Returns "ClassName{x=1}"
        System.out.println(MoreObjects.toStringHelper(Presider.class).add("name", "sam"));

        // Returns "MyObject{x=1}"
        MoreObjects.toStringHelper("MyObject").add("x", 1).toString();

    }

    static void test5() {
    }
}
