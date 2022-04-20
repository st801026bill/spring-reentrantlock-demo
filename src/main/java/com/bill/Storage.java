package com.bill;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {

    private static final int MAX_COUNT = 10; //倉庫最大數量
    private List<Production> productions = new ArrayList<>();  //公共資源

    /*
     *  可以利用公平鎖觀察執行的效果會如何
     *  private ReentrantLock reentrantLock = new ReentrantLock(true);
     */
    private ReentrantLock reentrantLock = new ReentrantLock();  //利用鎖控制produce、consume
//    private ReentrantLock reentrantLock = new ReentrantLock(true);  //利用鎖控制produce、consume

    private Condition condition = reentrantLock.newCondition();
    private int index = 0;

    public void produce() {
        try {
            reentrantLock.lock();   //獲取鎖，再訪問公共資源
            if(productions.size() >= MAX_COUNT) {
                System.out.println("prodece await");
                condition.await();
            }
            Thread.sleep(1000);   //耗時1秒
            Production production = new Production(index++);

            System.out.println("producer produce: "+ production.toString());
            productions.add(production);
            //Do Something...

            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void consume() {
        try {
            reentrantLock.lock();   //獲取鎖，再訪問公共資源
            if(productions.size() <= 0) {
                System.out.println("consume await");
                condition.await();  //貨物不足時停止消費
            }
            Thread.sleep(1000);  //耗時1秒
            Production production = productions.remove(0);

            System.out.println("consumer consume: " + production.toString());
            //Do Something...

            condition.signal(); //發個信號通知生產者
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static class Production {
        public int index;

        public Production(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return String.format("Production [index = %d]", index);
        }
    }


}
