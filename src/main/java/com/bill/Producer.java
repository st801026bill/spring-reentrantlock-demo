package com.bill;

public class Producer extends Thread {
    private Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            storage.produce();  //δΈεηη’
        }
    }
}
