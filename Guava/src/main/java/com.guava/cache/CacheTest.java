package com.guava.cache;

import com.entity.Presider;
import com.google.common.base.Preconditions;
import com.google.common.cache.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.security.Key;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * ClassName: CacheTest
 * Description: 缓存
 * Date: 2016/3/11 15:37
 *
 * @author sm12652
 * @version V1.0
 */
public class CacheTest {
    public static void main(String[] args) throws ExecutionException {
//        test1();
        test2();
//        test3();
//        test4();
    }

    /**
     * 简单使用
     */
    private static void test1() {

        Map<String, String> map = Maps.newHashMap();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        cache.invalidateAll();
        cache.putAll(map);
        System.out.println(cache.getIfPresent("1"));//a
        System.out.println(cache.getIfPresent("4"));//null
        System.out.println(cache.asMap());//{1=a, 3=c, 2=b}
    }

    /**
     * 缓存加载
     * LoadingCache
     * CacheLoader
     * Callable
     * 显式插入
     */
    static void test2() throws ExecutionException {
        /** CacheLoader自动加载： LoadingCache是附带CacheLoader构建而成的缓存实现**/
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                System.out.println("................loadding new cache ........");
                System.out.println("key = " + key);
                String value = "Hello Cache";
                return value;
            }

            //批量的加载
            @Override
            public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
                return super.loadAll(keys);
            }
        });

        // 去获取key1缓存，只会取一遍
        // 这个方法要么返回已经缓存的值，要么使用CacheLoader向缓存原子地加载新值
        System.out.println(loadingCache.get("key1"));//  key = key1  Hello Cache
        System.out.println(loadingCache.get("key1"));//  Hello Cache
        System.out.println(loadingCache.getUnchecked("key2"));//与异常有关


        /**
         * Callable:获取缓存-如果没有-则计算
         * 所有类型的Guava Cache，不管有没有自动加载功能，都支持get(K, Callable<V>)方法。
         * 这个方法返回缓存中相应的值，或者用给定的Callable运算并把结果加入到缓存中。
         **/
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();
        cache.get("key", new Callable<String>() {
            public String call() throws Exception {
                return "Cache from Callable";
            }
        });
        System.out.println(cache.getIfPresent("key"));


        /** 显式插入 **/
        cache = CacheBuilder.newBuilder().build();
        cache.put("key3", "value3");
        System.out.println(cache.getIfPresent("key3"));
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

        /**
         * 2- 定时回收（Timed Eviction）
         * 对定时回收进行测试时，不一定非得花费两秒钟去测试两秒的过期。
         * 你可以使用Ticker接口和CacheBuilder.ticker(Ticker)方法在缓存中自定义一个时间源，而不是非得用系统时钟。
         **/

        // 缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样。
        CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES);

        // 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
        // 如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
        CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES);

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
        // 批量清除keys
        cache.invalidateAll(Lists.newArrayList("a", "b"));
        // 清除所有缓存项
        cache.invalidateAll();

        /** 5- 移除监听器**/
        // 通过CacheBuilder.removalListener(RemovalListener)，你可以声明一个监听器，以便缓存项被移除时做一些额外操作。
        // 缓存项被移除时，RemovalListener会获取移除通知[RemovalNotification]，其中包含移除原因[RemovalCause]、键和值。
        cache = CacheBuilder.newBuilder().removalListener(new RemovalListener<String, String>() {
            public void onRemoval(RemovalNotification<String, String> notification) {
                System.out.println(notification.getKey());
                System.out.println(notification.getValue());
            }
        }).build();

    }

    /**
     * 刷新
     * 正如LoadingCache.refresh(K)所声明，刷新表示为键加载新值，这个过程可以是异步的。
     * 在刷新操作进行时，缓存仍然可以向其他线程返回旧值，而不像回收操作，读缓存的线程必须等待新值加载完成。\
     * 如果刷新过程抛出异常，缓存将保留旧值，而异常会在记录到日志后被丢弃[swallowed]。
     * CacheBuilder.refreshAfterWrite(long, TimeUnit)可以为缓存增加自动定时刷新功能
     */
    static void test4() {

        //有些键不需要刷新，并且我们希望刷新是异步完成的
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(
                    new CacheLoader<String,String>() {
                        public String load(String key) { // no checked exception
                            return "Hello Cache";
                        }

                        public ListenableFuture<String> reload(final String key, String value) {
                            if (Objects.equals(key, null)) {
                                return Futures.immediateFuture("");
                            } else {
                                ExecutorService executor = Executors.newSingleThreadExecutor();
                                // asynchronous!
                                ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                                    public String call() {
                                        return "reloading cache";
                                    }
                                });
                                executor.execute(task);
                                return task;
                            }
                        }
                    });

    }

    /**
     * 其他功能
     */
    static void test5() {
        /** 统计 ：recordStats() **/
        Cache<String, String> cache = CacheBuilder.newBuilder().recordStats().build();//
        CacheStats cacheStats = cache.stats();
        cacheStats.hitRate();//缓存命中率；
        cacheStats.averageLoadPenalty();//加载新值的平均时间，单位为纳秒；
        cacheStats.evictionCount();//缓存项被回收的总数，不包括显式清除。

        /** asMap视图
         asMap视图提供了缓存的ConcurrentMap形式，但asMap视图与缓存的交互需要注意：
         cache.asMap()包含当前所有加载到缓存的项。因此相应地，cache.asMap().keySet()包含当前所有已加载键;
         asMap().get(key)实质上等同于cache.getIfPresent(key)，而且不会引起缓存项的加载。这和Map的语义约定一致。
         所有读写操作都会重置相关缓存项的访问时间，包括Cache.asMap().get(Object)方法和Cache.asMap().put(K, V)方法，
         但不包括Cache.asMap().containsKey(Object)方法，也不包括在Cache.asMap()的集合视图上的操作。
         比如，遍历Cache.asMap().entrySet()不会重置缓存项的读取时间。
         **/
        cache.asMap().values();



    }


}
