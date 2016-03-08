package com.thread.synchronize;

/**
 * 
 * TraditionalThreadSynchronized.java
 * 
 * @title 传统线程互斥
 * @description
 * @author SAM-SHO
 * @Date 2014-8-17
 */
public class TraditionalThreadSynchronized {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 静态方法不能创建内部类实例对象
		// 原因： 内部类可以访问外部类的成员变量；成员变量是在对象创建后才分配空间的；静态方法中可以不创建对象即访问对象的方法。
		// 如
		// Outputer是内部类，可以访问外部类的成员变量，即一定有了外部类的实例对象，但是在main方法中根本不需要创建外部类对象，就矛盾了。
		// FirstThread c = new FirstThread();

		TraditionalThreadSynchronized tThreadSynchronized = new TraditionalThreadSynchronized();
		tThreadSynchronized.init();

	}

	/**
	 * 定义初始化方法， 解决main(静态)方法不能创建内部类实例对象
	 */
	public void init() {
		// 线程一
		FirstThread firstThread = new FirstThread();
		firstThread.start();

		// 线程二
		SecondThread secondThread = new SecondThread();
		secondThread.start();

	}

	/*
	 * 线程一
	 */
	class FirstThread extends Thread {
		Outputer out = new Outputer();//new 以后就是两个对象，使用this是锁不住的

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				out.output2("ShaoXiaoBao");
			}
		}
	}

	/*
	 * 线程二
	 */
	class SecondThread extends Thread {
		Outputer out = new Outputer();

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				out.output2("ZhaoXiaoNiu");
			}
		}
	}

	/**
	 * 
	 * Outputer.java
	 * 
	 * @title 内部类实现输出，业务类
	 * @description output方法要实现原子性
	 * 
	 * @author SAM-SHO
	 * @Date 2014-8-17
	 */
	class Outputer {

		// 使用synchronized 代码块
		// synchronized(对象) 代码块需要锁定同一个对象，
		// 这边如果synchronized (name)就不行，一定要 Outputer对象
		// output() 和  output1() 方法也是互斥的
		public void output(String name) {
			int len = name.length();
			synchronized (this) {//this就是Outputer这个对象
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		/**
		 * synchronized 方法
		 * 其实锁定的也是Outputer 对象
		 * @param name
		 */
		public synchronized void output1(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}

		/**
		 * 如果有静态方法 a()，output2()与之需要互斥，
		 * 则可以锁定该类字节码
		 * @param name
		 */
		public void output2(String name) {
			int len = name.length();
			synchronized (Outputer.class) {// 解决与静态方法的互斥，使用字节码对象
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		


	}
}
