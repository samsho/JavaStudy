package com.guava.cache;

import com.entity.Presider;
import com.google.common.cache.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.security.Key;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: CacheTest
 * Description: 缓存
 * Date: 2016/3/11 15:37
 *
 * @author sm12652
 * @version V1.0
 */
public class CacheTest {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {

        Map<String,String> map  = Maps.newHashMap();
        map.put("1","a");
        map.put("2","b");
        map.put("3","c");

        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.invalidateAll();
        cache.putAll(map);
        System.out.println(cache.asMap());//{1=a, 3=c, 2=b}
    }

    /**
     * LoadingCache
     * CacheLoader
     */
    static void test2() {
        /*LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(MY_LISTENER)
                .build(
                        new CacheLoader<Key, Graph>() {
                            public Graph load(Key key) throws AnyException {
                                return createExpensiveGraph(key);
                            }
                        });*/
    }

    /**
     * 缓存回收
     */
    static void test3() {

        /**1- 基于容量的回收（size-based eviction）*/
        CacheBuilder.newBuilder().maximumSize(1000);

        //用CacheBuilder.weigher(Weigher)指定一个权重函数，并且用CacheBuilder.maximumWeight(long)指定最大总重
        CacheBuilder.newBuilder().maximumSize(1000l).weigher(new Weigher<Key, Presider>() {
            public int weigh(Key key, Presider presider) {
                return presider.getId();
            }
        });

        /**2- 定时回收（Timed Eviction）**/

        // 缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样。
        CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES);

        // 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
        // 如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
        CacheBuilder.newBuilder().expireAfterWrite(10,TimeUnit.MINUTES);

        /**3- 基于引用的回收（Reference-based Eviction）**/
        // 使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。
        // 因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键。
        CacheBuilder.newBuilder().weakKeys();

        // 使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收。
        // 因为垃圾回收仅依赖恒等式（==），使用弱引用值的缓存用==而不是equals比较值。
        CacheBuilder.newBuilder().weakValues();

        // 使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。
        // 考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定（见上文，基于容量回收）。
        // 使用软引用值的缓存同样用==而不是equals比较值。
        CacheBuilder.newBuilder().softValues();

        /**4- 显式清除**/
        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.put("a", "1");
        cache.put("b", "2");
        // 个别清除：
        cache.invalidate("a");
        // 批量清除
        cache.invalidateAll(Lists.newArrayList("a","b"));
        // 清除所有缓存项
        cache.invalidateAll();

        /** 5- 移除监听器**/
        // 通过CacheBuilder.removalListener(RemovalListener)，你可以声明一个监听器，以便缓存项被移除时做一些额外操作。
        // 缓存项被移除时，RemovalListener会获取移除通知[RemovalNotification]，其中包含移除原因[RemovalCause]、键和值。
        cache = CacheBuilder.newBuilder().removalListener(new RemovalListener<String, String>(){
            public void onRemoval(RemovalNotification<String, String> notification) {

            }
        }).build();

    }

    /**
     * 刷新
     * 正如LoadingCache.refresh(K)所声明，刷新表示为键加载新值，这个过程可以是异步的。
     */
    static void test4(){

    }



}
