package com.example.algovisualize;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class SortingAlgoModel {
    private ArrayList<Integer> Data;
    private ArrayList<Integer> Data_color;
    private int Length;
    private ArrayList<SortingAlgoModelListener> listeners;
    Random random;

    public SortingAlgoModel(int Min, int Max, int Length) {
        Data = new ArrayList<>();
        Data_color = new ArrayList<>();
        this.Length = Length;
        listeners = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < Length; i++) {
            Data.add(random.nextInt(Max-Min+1) + Min);
            Data_color.add(0);
        }
    }

    public ArrayList<Integer> getData() {
        return Data;
    }

    public int getLength() {
        return Length;
    }

    public ArrayList<Integer> getData_color() {
        return Data_color;
    }

    private void BubbleSortColorChange(int pos, int color) {
        if (color < 0) color = 0;
        if (pos > 0) Data_color.set(pos-1, 0);
        Data_color.set(pos, color);
        Data_color.set(pos+1, color);
    }

    private void ChangeColor1Point(int l, int color) {
        if (color < 0) color = 0;
        Data_color.set(l, color);
    }

    private void ChangeColor2Point(int l, int r, int color) {
        if (color < 0) color = 0;
        Data_color.set(l, color);
        Data_color.set(r, color);
    }

    private void ChangeColorAllExcept(int l, int r, int color) {
        if (color < 0) color = 0;
        for (int i = 0; i < Length; i++) {
            if (i == l || i == r) Data_color.set(i, color);
            else Data_color.set(i, 0);
        }
    }

    private void ColorChange_QuickSort(int l, int h, int p, int colorLH, int colorP) {
        if (colorLH < 0) colorLH = 0;
        if (colorP < 0) colorP = 0;
        for (int i = 0; i < Length; i++) {
            if (i == l || i == h) Data_color.set(i, colorLH);
            else if (i == p) Data_color.set(i, colorP);
            else Data_color.set(i, 0);
        }
    }

    private void BubbleSortAlgoRun() throws InterruptedException {
        for (int i = 0; i < Length - 1; i++) {
            for (int pos = 0; pos < Length - 1 - i; pos++) {
                Thread.sleep(5);

                BubbleSortColorChange(pos,1);
                fireUpdate();

                if (Data.get(pos) > Data.get(pos+1)) {
                    int replace = Data.get(pos);
                    Data.set(pos, Data.get(pos+1));
                    Data.set(pos+1, replace);
                }
            }
        }
    }

    public Thread BubbleSortAlgoThread() {
        return new Thread() {
            @Override
            public void run() {
                try {
                    BubbleSortAlgoRun();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void MergeSortAlgoRec(int left, int right) throws InterruptedException {
        if (left < right) {
            int m = left + (right-left)/2;

            MergeSortAlgoRec(left, m);
            MergeSortAlgoRec(m+1, right);

            int l = left, r = m + 1;

            if (Data.get(m) > Data.get(r)) {
                while (l <= m && r <= right) {
                    if (Data.get(l) <= Data.get(r)) {
                        ChangeColorAllExcept(l, r, 2);
                        fireUpdate();
                        Thread.sleep(20);
                        l++;
                    }
                    else {
                        ChangeColorAllExcept(l, r, 2);
                        fireUpdate();
                        Thread.sleep(20);

                        int stored_value = Data.get(r);
                        for (int i = r; i > l; i--) {
                            Data.set(i, Data.get(i-1));
                        }
                        Data.set(l, stored_value);

                        ChangeColor1Point(r, 0);
                        ChangeColor2Point(l, l+1, 2);
                        fireUpdate();
                        Thread.sleep(20);

                        l++;
                        m++;
                        r++;
                    }
                }
                ChangeColorAllExcept(-1,-1,0);
                fireUpdate();
                Thread.sleep(20);
            }
        }
    }

    public Thread MergeSortAlgoThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MergeSortAlgoRec(0, Length - 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void QuickSortAlgoRec(int low, int high) throws InterruptedException {
        if (low < high) {
            int pivot = Data.get(high);
            int i = low - 1;
            for (int j = low; j < high; j++) {
                ColorChange_QuickSort(i, j, high, 2, 1);
                fireUpdate();
                Thread.sleep(20);
                if (Data.get(j) < pivot) {
                    i++;

                    int temp = Data.get(i);
                    Data.set(i, Data.get(j));
                    Data.set(j, temp);
                }
            }
            int temp = Data.get(i+1);

            Data.set(i+1, Data.get(high));
            Data.set(high, temp);
            pivot = i+1;

            ChangeColorAllExcept(i+1, 2, 3);
            fireUpdate();
            Thread.sleep(20);

            QuickSortAlgoRec(low, pivot-1);
            QuickSortAlgoRec(pivot+1, high);
        }
        ChangeColorAllExcept(-1,-1,-1);
        fireUpdate();
    }

    public Thread QuickSortAlgoThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QuickSortAlgoRec(0, Length - 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addListener(SortingAlgoModelListener listener) {
        listeners.add(listener);
    }

    public void fireUpdate() {
        for (SortingAlgoModelListener listener: listeners) {
            listener.updated(this);
        }
    }
}
