package com.bill;

public class StorageApplication {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);

        producer.start();
        consumer.start();
    }
}
