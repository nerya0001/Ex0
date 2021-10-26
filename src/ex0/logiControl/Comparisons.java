package ex0.logiControl;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.algo.OurAlgo;

public class Comparisons {
    /**
     * @return the time it takes for the elev to reach the src floor
     */
    public static double timeToSrc(Elevator elev, int src) {

        int distFromSrc = Math.abs(elev.getPos() - src);
        double elevTime = elev.getStopTime(); // stop in source floor

        if (elev.getState() == Elevator.LEVEL)
            elevTime += elev.getStartTime();

        if (elev.getState() == Elevator.DOWN || elev.getState() == Elevator.UP)
            elevTime += elev.getStopTime() + elev.getStartTime();

        return (distFromSrc / elev.getSpeed()) + elevTime; // time * speed = Length of the way
    }

    public static int bestElev(OurAlgo algo, CallForElevator call) {
        int bestElev = -1;
        boolean upCall = call.getSrc() < call.getDest();
        for (int i = 0; i < algo.getBuilding().numberOfElevetors(); i++) {

        }
        return bestElev;
    }

    /**
     * this function is for case 1 when there is available elevator.
     *
     * @return the best distance and time wise available elevator to the source.
     */
    public static int bestAvailElev(CallForElevator call, OurAlgo algo) {
        int bestElevIndex = -1;
        double bestTimeToSrc = Double.MAX_VALUE;
        Elevator curr;

        for (int i = 0; i < algo.getBuilding().numberOfElevetors(); i++) {
            curr = algo.getBuilding().getElevetor(i);
            double currTimeToSrc = timeToSrc(curr, call.getSrc());
            if (currTimeToSrc < bestTimeToSrc && dirAndPass(algo, call, i)) {
                bestElevIndex = i;
                bestTimeToSrc = currTimeToSrc;
            }
        }

        if (bestElevIndex == -1) {
            for (int i = 0; i < algo.getBuilding().numberOfElevetors(); i++) {
                curr = algo.getBuilding().getElevetor(i);
                double currTimeToSrc = timeToSrc(curr, call.getSrc());
                if (currTimeToSrc < bestTimeToSrc) {
                    bestElevIndex = i;
                    bestTimeToSrc = currTimeToSrc;
                }
            }
        }
        return bestElevIndex;
    }

    public static boolean dirAndPass(OurAlgo algo, CallForElevator call, int i) {
        if (algo.up[i] && call.getSrc() < call.getDest() &&
                algo.getBuilding().getElevetor(i).getPos() < call.getSrc())
            return true;
        if (algo.down[i] && call.getSrc() > call.getDest() &&
                algo.getBuilding().getElevetor(i).getPos() > call.getSrc())
            return true;
        if (!algo.down[i] && !algo.up[i]) // available elevator
            return true;
        return false;
    }


    /**
     * Given a callForElevator and a building where all the elevators are occupied
     * the function checks whether the elevator has not yet passed the source floor
     * and whether the elevator is going in the same direction of the call.
     *
     * @return true if the index of the elevator Should take the call.
     */
    public static boolean[] sameDir(Building building, CallForElevator call) {
        boolean[] goodElev = new boolean[building.numberOfElevetors()];
        if (call.getDest() < call.getSrc()) {   // down case
            for (int i = 0; i < building.numberOfElevetors(); i++) {
                if (building.getElevetor(i).getState() == Elevator.DOWN &&
                        building.getElevetor(i).getPos() >= call.getSrc())
                    goodElev[i] = true;
                else goodElev[i] = false;
            }
        } else { ///up case
            for (int i = 0; i < building.numberOfElevetors(); i++) {
                if (building.getElevetor(i).getState() == Elevator.UP &&
                        building.getElevetor(i).getPos() <= call.getSrc())
                    goodElev[i] = true;
                else goodElev[i] = false;
            }
        }
        return goodElev;
    }

    /**
     * @return the best distance and time wise same direction elevator to the source.
     */

    public static int bestSameDirElev(boolean[] goodElev, Building building, int src) {
        int bestElevIndex = -1;
        double bestTimeToSrc = Double.MAX_VALUE;
        Elevator curr;
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            if (goodElev[i]) {
                curr = building.getElevetor(i);
                double currTimeToSrc = timeToSrc(curr, src);
                if (currTimeToSrc < bestTimeToSrc) {
                    bestElevIndex = i;
                    bestTimeToSrc = currTimeToSrc;
                }
            }
        }
        return bestElevIndex;
    }
}
