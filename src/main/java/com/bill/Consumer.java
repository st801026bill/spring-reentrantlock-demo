package com.bill;

public class Consumer extends Thread {
    private Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while(true) {
            storage.consume();
        }
    }
}
