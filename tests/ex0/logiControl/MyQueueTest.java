package ex0.logiControl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class MyQueueTest {

    MyQueue mq;
    int[] check, check1, check2;
    PriorityQueue<Integer> UPrio;
    PriorityQueue<Integer> DPrio;

    @BeforeEach
    void init() {
        mq = new MyQueue();
        check1 = new int[]{1, 2, 3, 4, 5};
        check2 = new int[]{5, 4, 3, 2, 1};
        check = new int[30];
        for (int i = 0; i < 30; i++)
            check[i] = (int) Math.random()*100;
        UPrio = new PriorityQueue<>();
        DPrio = new PriorityQueue<>(Collections.reverseOrder());
    }


    @Test
    void addToUp() {
        for (int i = 0; i < 30; i++)
            mq.addToUp(check[i]);
        for (int i = 29; i >= 0; i--)
            UPrio.add(check[i]);
        for (int i = 0; i < 30; i++)
            assertEquals(UPrio.poll(), mq.pollUp());
    }

    @Test
    void addToDown() {
        for (int i = 0; i < 30; i++)
            mq.addToDown(check[i]);
        for (int i = 29; i >= 0; i--)
            DPrio.add(check[i]);
        for (int i = 0; i < 30; i++)
            assertEquals(DPrio.poll(), mq.pollDown());
    }

    @Test
    void pollUp() {
        mq.addToUp(5);
        mq.addToUp(2);
        mq.addToUp(4);
        mq.addToUp(1);
        mq.addToUp(3);
        for (int i = 0; i < 5; i++)
            assertEquals(mq.pollUp(), check1[i]);

        mq.addToUp(100);
        assertNotEquals(mq.pollUp(), 50);
    }

    @Test
    void pollDown() {
        mq.addToDown(3);
        mq.addToDown(2);
        mq.addToDown(4);
        mq.addToDown(1);
        mq.addToDown(5);
        for (int i = 0; i < 5; i++)
            assertEquals(mq.pollDown(), check2[i]);

        mq.addToDown(146);
        assertNotEquals(mq.pollDown(), 46);
    }

    @Test
    void sizeUp() {
        assertEquals(mq.sizeUp(), 0);
        for (int i = 0; i < 10; i++)
            mq.addToUp((int) Math.random()*100);

        assertEquals(mq.sizeUp(), 10);
        mq.pollUp();
        assertNotEquals(mq.sizeUp(), 10);

    }

    @Test
    void sizeDown() {
        assertEquals(mq.sizeDown(), 0);
        for (int i = 0; i < 10; i++)
            mq.addToDown((int) Math.random()*50);

        assertEquals(mq.sizeDown(), 10);
        mq.pollDown();
        assertNotEquals(mq.sizeDown(), 10);
    }
}