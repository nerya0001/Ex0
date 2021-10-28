package ex0.logiControl;


import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.algo.OurAlgo;
import ex0.simulator.Call_A;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonsTest {

    Building buildingCase9;
    OurAlgo algoCase9;
    CallForElevator call;
    Elevator elev3;
    Elevator elev2;


    @BeforeEach
    void init(){
        Simulator_A.initData(9,null);
        buildingCase9 = Simulator_A.getBuilding();
        algoCase9 = new OurAlgo(buildingCase9);
        elev3 = algoCase9.getBuilding().getElevetor(3);
        elev2 = algoCase9.getBuilding().getElevetor(2);
    }


    @Test
    void timeToSrc() {
        // the elevator start at -10 floor
        assertEquals(elev2.getPos(), -10);
        assertTrue(Comparisons.timeToSrc(elev3, 20) < Comparisons.timeToSrc(elev3, 50));
        assertTrue((Comparisons.timeToSrc(elev3, 3) < Comparisons.timeToSrc(elev3, 20)));
       //elev3 is faster then elev2 according to case 9.
        assertTrue((Comparisons.timeToSrc(elev3, 50) < Comparisons.timeToSrc(elev2, 50)));

    }

    @Test
    void dirAndPass() {
        call = new Call_A(0, 5, 50);
        assertTrue(Comparisons.dirAndPass(algoCase9, call, 3));
        call = new Call_A(0, 20, 50);
        elev3.goTo(10);
        assertTrue(Comparisons.dirAndPass(algoCase9, call, 3));
    }

    @Test
    void bestAvailElev() {



    }
}