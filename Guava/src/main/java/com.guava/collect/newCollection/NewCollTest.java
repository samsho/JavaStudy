package com.guava.collect.newCollection;

import com.entity.Presider;
import com.google.common.collect.*;

import java.util.*;

/**
 * ClassName: NewCollTest
 * Description: 提供的新的集合
 * Date: 2016/3/10 16:15
 * http://ifeve.com/google-guava-newcollectiontypes/
 * @author sm12652
 * @version V1.0
 */
public class NewCollTest {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    /**
     * Multiset:带统计的集合
     * Multiset不是Map,是一种Collection类型
     */
    static void test1(){

        List<String> words = Lists.newArrayList("a","b","d","g","b","d","d","g","b");
        //统计一个词在文档中出现了多少次，传统的做法是这样的：
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String word : words) {
            Integer count = counts.get(word);
            if (count == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, count + 1);
            }
        }

        System.out.println(counts);//{g=2, d=3, b=3, a=1}

        /*----------带统计的集合--------------------------*/
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(words);//添加集合
        System.out.println(multiset);//[g x 2, d x 3, b x 3, a]

        /** 当把Multiset看作Map<E, Integer>时，它也提供了符合性能期望的查询操作： **/
        // 所有Multiset实现的内存消耗随着不重复元素的个数线性增长。
        // count(Object)返回给定元素的计数。HashMultiset.count的复杂度为O(1)，TreeMultiset.count的复杂度为O(log n)。
        System.out.println(multiset.count("a"));//1

        // entrySet()返回Set<Multiset.Entry<E>>，和Map的entrySet类似。
        System.out.println(multiset.entrySet());//[g x 2, d x 3, b x 3, a]
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            entry.getCount();
            entry.getElement();
        }

        // elementSet()返回所有不重复元素的Set<E>，和Map的keySet()类似。
        System.out.println(multiset.elementSet());//[g, d, b, a]
        for (String s : multiset.elementSet()) {
            System.out.print(s + "==");//g==d==b==a==4
        }


        /** 当把Multiset看成普通的Collection时，它表现得就像无序的ArrayList：**/

        // add(E)添加单个给定元素
        // iterator()返回一个迭代器，包含Multiset的所有元素（包括重复的元素）
        // size()返回所有元素的总个数（包括重复的元素）
        Multiset<String> hashMultiset = HashMultiset.create();
        hashMultiset.add("a");
        hashMultiset.add("b");
        hashMultiset.add("c");
        hashMultiset.add("c");
        System.out.println(hashMultiset.size());// 4
        System.out.println(hashMultiset.count("c"));// 2
        for (String s : hashMultiset) {
            System.out.print(s + "-");// b-c-c-a-
        }





    }

    /**
     * Multimap:是把键映射到任意多个值的一般方式。
     * Map<K, List<V>>或Map<K, Set<V>>,
     * ListMultimap或SetMultimap
     */
    static void test2(){

        /** 修改Multimap **/
        // Multimap.get(key)以集合形式返回键所对应的值视图，即使没有任何对应的值，也会返回空集合。
        // ListMultimap.get(key)返回List，SetMultimap.get(key)返回Set。

        /** ListMultimap **/
        ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        listMultimap.put("a","1");
        listMultimap.put("a","2");
        listMultimap.put("1", "3");
        listMultimap.put("A", "4");

        System.out.println(listMultimap.asMap());//{1=[3], A=[4], a=[1, 2]}
        System.out.println(listMultimap.get("a"));//[1, 2]
        List<String> lis = listMultimap.get("a");//返回List
        listMultimap.removeAll("1");//删除
        System.out.println(listMultimap.asMap());//{A=[4], a=[1, 2]}， Map视图
        System.out.println(listMultimap.containsKey("A"));//true
        System.out.println(listMultimap.containsValue("2"));//true
        System.out.println(listMultimap.containsEntry("a", "3"));//false
        System.out.println(listMultimap.keys());//[A, a x 2]
        System.out.println(listMultimap.values());//[4, 1, 2]

        /**   SetMultimap  */
        SetMultimap<String, String> setMultimap = HashMultimap.create();


        /** Multimap的视图 **/

        // asMap为Multimap<K, V>提供Map<K,Collection<V>>形式的视图。返回的Map支持remove操作，并且会反映到底层的Multimap，但它不支持put或putAll操作。
        // 更重要的是，如果你想为Multimap中没有的键返回null，而不是一个新的、可写的空集合，你就可以使用asMap().get(key)。
        // （你可以并且应当把asMap.get(key)返回的结果转化为适当的集合类型——如SetMultimap.asMap.get(key)的结果转为Set，
        // ListMultimap.asMap.get(key)的结果转为List——Java类型系统不允许ListMultimap直接为asMap.get(key)返回List
        // 译者注：也可以用Multimaps中的asMap静态方法帮你完成类型转换）
        Map<String, Collection<String>> map =  listMultimap.asMap();
        Collection<String> collection = new ArrayList<String>();
//        map.put("aa", collection);//java.lang.UnsupportedOperationException,不支持put或putAll操作

//        entries用Collection<Map.Entry<K, V>>返回Multimap中所有”键-单个值映射”——包括重复键。（对SetMultimap，返回的是Set）
        for (Map.Entry<String, String> entry : listMultimap.entries()) {
            System.out.print(entry.getKey() + ":" + entry.getValue() + "  ");//A++4  a++1  a++2
        }

//        keySet用Set表示Multimap中所有不同的键。
//        keys用Multiset表示Multimap中的所有键，每个键重复出现的次数等于它映射的值的个数。可以从这个Multiset中移除元素，但不能做添加操作；移除操作会反映到底层的Multimap。
//        values()用一个”扁平”的Collection<V>包含Multimap中的所有值。这有一点类似于Iterables.concat(multimap.asMap().values())，但它直接返回了单个Collection，而不像multimap.asMap().values()那样是按键区分开的Collection。
        for (String str : listMultimap.keySet()) {
            
        }


    }

    /**
     * BiMap<K, V>是特殊的Map：
     * 可以用 inverse()反转BiMap<K, V>的键值映射
     * 保证值是唯一的，因此 values()返回Set而不是普通的Collection
     */
    static void test3(){
        // 传统上，实现键值对的双向映射需要维护两个单独的map，并保持它们间的同步
        // 如果"Bob"和42已经在map中了，会发生什么?
        // 如果我们忘了同步两个map，会有诡异的bug发生...
        Map<String, Integer> nameToId = Maps.newHashMap();
        Map<Integer, String> idToName = Maps.newHashMap();
        nameToId.put("Bob", 42);
        idToName.put(42, "Bob");


        /*                */
        BiMap<String, Integer> biMap  = HashBiMap.create();
        biMap.put("Bob",42);
        System.out.println(biMap);//{Bob=42}
        BiMap<Integer, String> biMap2 = biMap.inverse();//翻转
        System.out.println(biMap2);//{42=Bob}
        biMap.put("Bob", 45);//替换值
        System.out.println(biMap);//{Bob=45}

        //biMap.put("Bob2", 45);//值是唯一的，IllegalArgumentException: value already present: 42
        biMap.forcePut("Bob2", 45);//强制替换
        System.out.println(biMap);//{Bob2=45}

        biMap.put("Bob", 42);//替换值
        System.out.println(biMap.values());//[42, 45]
    }

    /**
     * Table
     * 多个键做索引的时候，你可能会用类似Map<FirstName, Map<LastName, Person>>的实现，
     * 这种方式很丑陋，使用上也不友好。Guava为此提供了新集合类型Table，它有两个支持所有类型的键：”行”和”列”
     */
    static void test4(){
        Table<String, String, Integer> baseTable = HashBasedTable.create();
        baseTable.put("v1", "v2", 4);
        baseTable.put("v1", "v3", 20);
        baseTable.put("v2", "v3", 5);

        System.out.println(baseTable.row("v1"));//{v3=20, v2=4}
        System.out.println(baseTable.column("v3"));//{v1=20, v2=5}


        // 2-视图
        // rowMap()：用Map<R, Map<C, V>>表现Table<R, C, V>。同样的， rowKeySet()返回”行”的集合Set<R>。
        System.out.println(baseTable.rowMap());//{v1={v3=20, v2=4}, v2={v3=5}}
        System.out.println(baseTable.rowKeySet());//[v1, v2]

        // row(r) ：用Map<C, V>返回给定”行”的所有列，对这个map进行的写操作也将写入Table中。
        System.out.println(baseTable.row("v1"));//{v3=20, v2=4}
        baseTable.row("v1").put("v4", 30);
        System.out.println(baseTable.row("v1"));//{v4=30, v3=20, v2=4}

        // 类似的列访问方法：columnMap()、columnKeySet()、column(c)。（基于列的访问会比基于的行访问稍微低效点）


        // cellSet()：用元素类型为Table.Cell<R, C, V>的Set表现Table<R, C, V>。Cell类似于Map.Entry，但它是用行和列两个键区分的。
        System.out.println(baseTable.cellSet());//[(v1,v4)=30, (v1,v3)=20, (v1,v2)=4, (v2,v3)=5]
        for (Table.Cell<String, String, Integer> cell : baseTable.cellSet()) {
            System.out.println(
                    cell.getRowKey() + "-" +
                            cell.getColumnKey() + "-" +
                            cell.getValue()
            );
        }

        /*v1-v4-30
          v1-v3-20
          v1-v2-4
          v2-v3-5*/


        // 3-实现
        // HashBasedTable：本质上用HashMap<R, HashMap<C, V>>实现；
        // TreeBasedTable：本质上用TreeMap<R, TreeMap<C,V>>实现；
        // ImmutableTable：本质上用ImmutableMap<R, ImmutableMap<C, V>>实现；注：ImmutableTable对稀疏或密集的数据集都有优化。
        // ArrayTable：要求在构造时就指定行和列的大小，本质上由一个二维数组实现，以提升访问速度和密集Table的内存利用率。ArrayTable与其他Table的工作原理有点不同，请参见Javadoc了解详情。
    }

    /**\
     * ClassToInstanceMap
     * ClassToInstanceMap是一种特殊的Map：它的键是类型，而值是符合键所指类型的对象。
     *
     * RangeSet:
     * RangeMap
     *
     *
     */
    static void test5() {

        /** ClassToInstanceMap*/

        ClassToInstanceMap<Object> classToInstanceMap = MutableClassToInstanceMap.create();
        classToInstanceMap.putInstance(Presider.class, new Presider());

        /** RangeSet */
        System.out.println(Range.closed(1, 10));//[1‥10]

        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}
        System.out.println(rangeSet);

        /** RangeMap */
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}

        System.out.println(rangeMap);
    }


}
