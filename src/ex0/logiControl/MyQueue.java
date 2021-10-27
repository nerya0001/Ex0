package ex0.logiControl;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * This class represents the data structure we will use in the algorithm
 */

public class MyQueue {
    private PriorityQueue<Integer> up;
    private PriorityQueue<Integer> down;

    public MyQueue() {
        this.up = new PriorityQueue<Integer>();
        this.down = new PriorityQueue<Integer>(Collections.reverseOrder());
    }

    public void addToUp(int floor){
        up.add(floor);
    }

    public void addToDown(int floor){
        down.add(floor);
    }

    public int pollUp(){
        return up.poll();
    }

    public int pollDown(){
        return down.poll();
    }

    public int sizeUp(){
        return up.size();
    }

    public int sizeDown(){
        return down.size();
    }

}

