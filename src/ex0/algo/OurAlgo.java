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
    private boolean[] iterateDown;
    private boolean[] iterateUp;

    public OurAlgo(Building b) {
        this.building = b;
        this.callsQueues = new MyQueue[building.numberOfElevetors()];
        initCalls();
        this.iterateDown = new boolean[building.numberOfElevetors()];
        this.iterateUp = new boolean[building.numberOfElevetors()];
        Arrays.fill(iterateDown, false);
        Arrays.fill(iterateUp, false);
    }

    private void initCalls() {
        for (int i = 0; i < building.numberOfElevetors(); i++)
            this.callsQueues[i] = new MyQueue();
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    public boolean getIterateUp(int elev) {
        return this.iterateUp[elev];
    }

    public boolean getIterateDown(int elev) {
        return this.iterateDown[elev];
    }

    public MyQueue[] getCallsQueues(){
        return callsQueues;
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
            if (!iterateUp[bestElev] && !iterateDown[bestElev])
                building.getElevetor(bestElev).goTo(callsQueues[bestElev].pollUp());
            if (!iterateDown[bestElev])
                iterateUp[bestElev] = true;
        } else {//down case
            callsQueues[bestElev].addToDown(c.getSrc());
            callsQueues[bestElev].addToDown(c.getDest());
            if (!iterateUp[bestElev] && !iterateDown[bestElev])
                building.getElevetor(bestElev).goTo(callsQueues[bestElev].pollDown());
            if (!iterateUp[bestElev])
                iterateDown[bestElev] = true;
        }
        return bestElev;
    }

    @Override
    public void cmdElevator(int elev) {
        Elevator elevator = building.getElevetor(elev);
        // in the middle of getting up and should keep up
        if (callsQueues[elev].sizeUp() != 0 &&
                iterateUp[elev] && elevator.getState() == Elevator.LEVEL)
            elevator.goTo(callsQueues[elev].pollUp());

        // in the end of getting up and maybe should get down
        if (callsQueues[elev].sizeUp() == 0 &&
                iterateUp[elev] && elevator.getState() == Elevator.LEVEL) {
            iterateUp[elev] = false;
            if (callsQueues[elev].sizeDown() != 0) {
                iterateDown[elev] = true;
                elevator.goTo(callsQueues[elev].pollDown());
            }
        }

        //in the middle of getting down and should keep down
        if (callsQueues[elev].sizeDown() != 0 &&
                iterateDown[elev] && elevator.getState() == Elevator.LEVEL)
            elevator.goTo(callsQueues[elev].pollDown());

        // in the end of getting down and maybe should get up
        if (callsQueues[elev].sizeDown() == 0 &&
                iterateDown[elev] && elevator.getState() == Elevator.LEVEL) {
            iterateDown[elev] = false;
            if (callsQueues[elev].sizeUp() != 0) {
                iterateUp[elev] = true;
                elevator.goTo(callsQueues[elev].pollUp());
            }
        }
    }
}
