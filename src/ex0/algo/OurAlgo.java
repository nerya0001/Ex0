package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.logiControl.Comparisons;
import ex0.logiControl.MyQueue;

import java.util.Arrays;

public class OurAlgo implements ElevatorAlgo {

    private Building building;
    private MyQueue[] callsQueues;
    //Arrays that say whether the elevator going through the up queue, the down queue or none of them.
    private boolean[] down;
    private boolean[] up;

    public OurAlgo(Building b) {
        this.building = b;
        this.callsQueues = new MyQueue[building.numberOfElevetors()];
        initCalls();
        this.down = new boolean[building.numberOfElevetors()];
        this.up = new boolean[building.numberOfElevetors()];
        Arrays.fill(down, false);
        Arrays.fill(up, false);
    }

    private void initCalls() {
        for (int i = 0; i < building.numberOfElevetors(); i++)
            this.callsQueues[i] = new MyQueue();
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    public boolean getUp(int elev) {
        return this.up[elev];
    }

    public boolean getDown(int elev) {
        return this.down[elev];
    }

    @Override
    public String algoName() {
        return "our algo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int bestElev = Comparisons.bestAvailElev(c, this);
        if (c.getSrc() < c.getDest()) {//up case
            callsQueues[bestElev].addToUp(c.getSrc());
            callsQueues[bestElev].addToUp(c.getDest());
            if (!up[bestElev] && !down[bestElev])
                building.getElevetor(bestElev).goTo(callsQueues[bestElev].pollUp());
            if (!down[bestElev])
                up[bestElev] = true;
        } else {//down case
            callsQueues[bestElev].addToDown(c.getSrc());
            callsQueues[bestElev].addToDown(c.getDest());
            if (!up[bestElev] && !down[bestElev])
                building.getElevetor(bestElev).goTo(callsQueues[bestElev].pollDown());
            if (!up[bestElev])
                down[bestElev] = true;
        }
        return bestElev;
    }

    @Override
    public void cmdElevator(int elev) {
        Elevator elevator = building.getElevetor(elev);
        // in the middle of getting up and should keep up
        if (callsQueues[elev].sizeUp() != 0 &&
                up[elev] && elevator.getState() == Elevator.LEVEL)
            elevator.goTo(callsQueues[elev].pollUp());

        // in the end of getting up and maybe should get down
        if (callsQueues[elev].sizeUp() == 0 &&
                up[elev] && elevator.getState() == Elevator.LEVEL) {
            up[elev] = false;
            if (callsQueues[elev].sizeDown() != 0) {
                down[elev] = true;
                elevator.goTo(callsQueues[elev].pollDown());
            }
        }

        //in the middle of getting down and should keep down
        if (callsQueues[elev].sizeDown() != 0 &&
                down[elev] && elevator.getState() == Elevator.LEVEL)
            elevator.goTo(callsQueues[elev].pollDown());

        // in the end of getting down and maybe should get up
        if (callsQueues[elev].sizeDown() == 0 &&
                down[elev] && elevator.getState() == Elevator.LEVEL) {
            down[elev] = false;
            if (callsQueues[elev].sizeUp() != 0) {
                up[elev] = true;
                elevator.goTo(callsQueues[elev].pollUp());
            }
        }
    }
}
