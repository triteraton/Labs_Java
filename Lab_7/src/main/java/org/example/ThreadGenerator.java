package org.example;

import java.util.ArrayList;
import java.util.List;

public class ThreadGenerator {
    int numThreads;
    String action;
    int startRange;
    int endRange;
    private List<Integer> result;

    public ThreadGenerator() {
        this.numThreads = 0;
        this.action = "";
        this.startRange = 0;
        this.endRange = 0;
        this.result = new ArrayList<>();
    }

    public void execute() {
        List<CalculatorThread> threads = new ArrayList<>();
        int step = (endRange - startRange + 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = startRange + i * step;
            int end = (i < numThreads - 1) ? (start + step - 1) : endRange;

            CalculatorThread thread = new CalculatorThread(start, end);
            threads.add(thread);
            thread.start();
        }

        for (CalculatorThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (action.equals("+")) {
            for (CalculatorThread thread : threads) {
                for (int num : thread.getResult()) {
                    result.add(num);
                }
            }
        } else if (action.equals("-")) {
            if (!threads.isEmpty()) {
                result.addAll(threads.get(0).getResult());
                for (int i = 1; i < threads.size(); i++) {
                    for (int num : threads.get(i).getResult()) {
                        result.remove(Integer.valueOf(num));
                    }
                }
            }
        } else if (action.equals("*")) {
            result.add(1);
            for (CalculatorThread thread : threads) {
                for (int num : thread.getResult()) {
                    result.set(0, result.get(0) * num);
                }
            }
        }
    }

    public List<Integer> getResult() {
        return result;
    }
}