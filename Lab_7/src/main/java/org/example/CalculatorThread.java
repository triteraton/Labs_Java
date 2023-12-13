package org.example;

import java.util.ArrayList;
import java.util.List;

public class CalculatorThread extends Thread {
    private int start;
    private int end;
    private List<Integer> result;

    public CalculatorThread(int start, int end) {
        this.start = start;
        this.end = end;
        this.result = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int num = start; num <= end; num++) {
            if (num * 4 % 10 == 0) {
                result.add(num);
            }
        }
    }

    public List<Integer> getResult() {
        return result;
    }
}