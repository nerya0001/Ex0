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

    /**
     * Given a callForElevator and a building (in OurAlgo shape)
     * the function checks whether the elevator has not yet passed the source floor
     * and whether the elevator is going in the same direction of the call.
     * @return true if the index of the elevator Should take the call.
     * @return the best distance and time wise available elevator to the source.
     */
    public static int bestAvailElev(CallForElevator call, OurAlgo algo) {
        int bestElevIndex = -1;
        double bestTimeToSrc = Double.MAX_VALUE;
        Elevator curr;
        double currTimeToSrc;
//        for (int i = 0; i < algo.getBuilding().numberOfElevetors(); i++) {
//            curr = algo.getBuilding().getElevetor(i);
//            currTimeToSrc = timeToSrc(curr, call.getSrc());
//            if (currTimeToSrc < bestTimeToSrc && dirAndPass(algo, call, i)) {
//                if ()
//                bestElevIndex = i;
//                bestTimeToSrc = currTimeToSrc;
//            }
//        }

//        if (bestElevIndex == -1) {
            for (int i = 0; i < algo.getBuilding().numberOfElevetors(); i++) {
                curr = algo.getBuilding().getElevetor(i);
                currTimeToSrc = timeToSrc(curr, call.getSrc());
                if (currTimeToSrc < bestTimeToSrc && dirAndPass(algo, call, i)) {
                    bestElevIndex = i;
                    bestTimeToSrc = currTimeToSrc;
                }
            }

            if (bestElevIndex == -1) {
                for (int i = 0; i < algo.getBuilding().numberOfElevetors(); i++) {
                    curr = algo.getBuilding().getElevetor(i);
                    currTimeToSrc = timeToSrc(curr, call.getSrc());
                    if (currTimeToSrc < bestTimeToSrc) {
                        bestElevIndex = i;
                        bestTimeToSrc = currTimeToSrc;
                    }
                }
            }
//        }
        return bestElevIndex;
    }

    /**
     * Given a callForElevator, access to OurAlgo and elevator's index i
     * the function checks whether the elevator (i) has not yet passed the source floor,
     * whether the elevator is going in the same direction of the call
     * or the elevator not doing anything
     *
     * @return true if the index of the elevator Should take the call.
     */
    public static boolean dirAndPass(OurAlgo algo, CallForElevator call, int i) {

        if (algo.up[i] && call.getSrc() < call.getDest() &&
                algo.getBuilding().getElevetor(i).getPos() < call.getSrc())
            return true;

        if (algo.down[i] && call.getSrc() > call.getDest() &&
                algo.getBuilding().getElevetor(i).getPos() > call.getSrc())
            return true;
        // available elevator
        if (!algo.down[i] && !algo.up[i])
            return true;
        return false;
    }


    /**
     * Given a callForElevator and a building (in OurAlgo shape)
     * the function checks whether the elevator has not yet passed the source floor
     * and whether the elevator is going in the same direction of the call.
     * @return true if the index of the elevator Should take the call.
     */

    /**
     * @return the best distance and time wise same direction elevator to the source.
     */


}
