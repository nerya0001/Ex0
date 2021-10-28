package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.simulator.Call_A;
import ex0.simulator.Simulator_A;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class OurAlgoTest {
    Building buildingCase5;
    Building buildingCase9;
    OurAlgo algoCase5;
    OurAlgo algoCase9;

    @BeforeEach
    void init(){
        Simulator_A.initData(5,null);
        buildingCase5 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        buildingCase9 = Simulator_A.getBuilding();
        algoCase5 = new OurAlgo(buildingCase5);
        algoCase9 =new OurAlgo(buildingCase9);
    }

    @Test
    void getBuilding() {
        assertEquals(algoCase9.getBuilding(), buildingCase9);
        assertEquals(algoCase5.getBuilding(), buildingCase5);
        assertNotEquals(algoCase5.getBuilding(), buildingCase9);
    }

    @Test
    void algoName() {
        assertEquals(algoCase5.algoName(), "our algo");
    }

    @Test
    void allocateAnElevator() {
        Elevator elev3 = algoCase9.getBuilding().getElevetor(3);
        CallForElevator call1 = new Call_A(0, 1, 22);
        CallForElevator call2 = new Call_A(0, 22, 43);
        //elevator 3 is the fastest elevator in case 9
        assertEquals(algoCase9.allocateAnElevator(call1), 3);
        //elevator 1 is the second fastest elevator in case 9
        // and its at the same floor as the source floor of call2
        assertEquals(algoCase9.allocateAnElevator(call2), 1);

        //chacking if the allocate take care of the source floor
        int elevIndex = algoCase5.allocateAnElevator(call1);
        //just the dest floor need to be inside
        assertEquals(algoCase5.getCallsQueues()[elevIndex].sizeUp(), 1);
    }

    @Test
    void cmdElevator() {
        CallForElevator call1 = new Call_A(0, 1, 22);
        CallForElevator call2 = new Call_A(0, 22, 43);
        CallForElevator call3 = new Call_A(0, 30, 41);
        CallForElevator call4 = new Call_A(0, 10, -5);
        int elevIndex = algoCase5.allocateAnElevator(call1);
        assertEquals(algoCase5.getCallsQueues()[elevIndex].sizeUp(), 1);
        algoCase5.cmdElevator(elevIndex);
        assertEquals(algoCase5.getCallsQueues()[elevIndex].sizeUp(), 0);
    }
}