package com.thread.comunication;


/**
 * 
 * TranditionalThreadComunication.java
 *
 * @title 线程同步与互斥: 互斥、同步不是写在线程上，而是写在线程访问的资源上。
 * @description
 * @author SAM-SHO 
 * @Date 2014-8-17
 */
public class TranditionalThreadComunication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TranditionalThreadComunication tThreadComunication = new TranditionalThreadComunication();
		tThreadComunication.init();
	}
	
	/**
	 * 定义初始化方法，
	 * 解决main(静态)方法不能创建内部类实例对象
	 */
	public void init() {
		//保证两个线程访问同一个资源对象
		Bussiness tBussiness = new Bussiness();
		
		ChildThread childThread = new ChildThread(tBussiness);//子线程
		MainThread mainThread = new MainThread(tBussiness);//主线程
		childThread.start();
		mainThread.start();
	}
	
	/*
	 * 子线程
	 */
	class ChildThread extends Thread {
		private Bussiness tBussiness;
		
		public ChildThread(Bussiness tBussiness) {
			this.tBussiness = tBussiness;
		}
		
		@Override
		public void run() {
			for (int i = 0; i <= 20; i++) {				
				tBussiness.eachChild(i);				
			}
		}
	}
	
	
	/*
	 * 主线程
	 */
	class MainThread extends Thread {
		private Bussiness tBussiness;
		
		public MainThread(Bussiness tBussiness) {
			this.tBussiness = tBussiness;
		}
		
		@Override
		public void run() {
			for (int i = 0; i <= 20; i++) {				
				tBussiness.eachMain(i);				
			}
		}
	}
	
	/**
	 * 
	 * Bussiness
	 *
	 * @title 业务实现类
	 * @description 用到的共同数据的若干方法应该设计在一个类身上
	 * 
	 * @author SAM-SHO 
	 * @Date 2014-8-17
	 */
	class Bussiness {
		private boolean bShouldSub = true;
		
		/*
		 * 循环10次的方法
		 * if 可以用 while代替
		 */
		public synchronized void eachChild(int index) {
			while(!bShouldSub) {//一开始是true，那么就是执行循环
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for (int i = 0; i <= 10; i++) {
				System.out.println("子 线程-- "+i+ ", 第"+index+"循环");	
			}
			
			bShouldSub = false;
			this.notify();//唤醒主线程
			
		}
		
		/*
		 * 循环50次的方法
		 * if 可以用 while代替
		 */
		public synchronized void eachMain(int index) {
			while(bShouldSub) {//一开始是true,那么就会wait
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for (int i = 0; i <= 30; i++) {
				System.out.println("主 线程---- "+i + ", 第"+index+"循环");
			}
			
			bShouldSub = true;
			this.notify();//唤醒子线程
		}	
	}
}