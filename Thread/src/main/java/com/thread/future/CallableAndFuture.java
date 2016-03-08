package com.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * CallableAndFuture.java
 *
 * @title Callable和Future Callable返回结果, Future可以拿到结果
 * @description
 * @author SAM-SHO 
 * @Date 2014-8-23
 */
public class CallableAndFuture {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//1-起一个简单的线程池
		ExecutorService threadPool = Executors.newSingleThreadExecutor();		

		//2-调用
		
		//2-1 execute执行，没有返回值
//		threadPool.execute(command);
		
		//2-2 submit有返回结果 Callable返回结果, Future可以拿到结果
		// Future取得的结果类型和Callable返回的类型必须保持一致
		Future<String> future = threadPool.submit(new Callable<String>(){

			public String call() throws Exception {
				Thread.sleep(2000);
				return "hello";
			}
			
		});
		
		System.out.println("等待结果");
		try {
			System.out.println("拿到结果: "+future.get());//拿到结果，拿不到的时候会一直等...
//			System.out.println("拿到结果: "+future.get(timeout, unit)get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		threadPool.shutdown();
		
		
		//3-固定的线程池,10个线程
		// CompletionService 用于提交一组 Callable 任务，
		// 其take方法返回已完成的一个 Callable任务对应的Future对象。
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Integer> eCService = new ExecutorCompletionService<Integer>(threadPool2);
		for (int i = 1; i < 10; i++) {
			final int seq = i;
			
			eCService.submit(new Callable<Integer>() {//一组Callable
				public Integer call() throws Exception {
					Thread.sleep(5000);
					return seq;//返回的值，为int
				}
			});
		}
		
		// 谁先完成，就先取谁
		for (int i = 0; i < 10; i++) {
			System.out.println("等待...");
			
			try {
				System.out.println(eCService.take().get());//take方法返回对应的Future对象
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		threadPool2.shutdown();		
	}
}

