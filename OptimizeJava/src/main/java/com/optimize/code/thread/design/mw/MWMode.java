package com.optimize.code.thread.design.mw;

import java.util.Map;
import java.util.Set;

/**
 * ClassName: MWMode
 * Description: 并行设计：Master-Worker
 * Date: 2016/6/12 21:55
 *
 * @author SAM SHO
 * @version V1.0
 */
public class MWMode {

    public static void main(String[] args) {
        // 使用5个Worker
        Master master = new Master(new PlusWorker(), 5);

        // 提交100个任务
        for (int i = 0; i < 100; i++) {
            master.submit(i);
        }
        // 开始计算
        master.execute();

        int result = 0; // 保存最终结果
        Map<String, Object> resultMap = master.getResultMap();
        // 不需要等待所有的Worker 都执行完成，即可开始计算最终结果
        while (resultMap.size() > 0 || !master.isComplete()) {
            Set<String> keys = resultMap.keySet();
            String key = null;
            for (String k : keys) {
                key = k;
                break;
            }

            Integer i = null;
            if (key != null) {
                i = (Integer) resultMap.get(key);
            }

            if (i != null) {
                result += i; // 最终结果
            }

            if (key != null) {
                resultMap.remove(key);
            }
        }

        System.out.println("结果 ： " + result);

    }

    /**
     * 内部类 ，立方和的应用
     */
    static class PlusWorker extends Worker {
        @Override
        protected Object handle(Object input) {//求立方和
            Integer i = (Integer) input;
            return i * i * i;
        }
    }

}
