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
    Building buildingCase1;
    Building buildingCase9;
    OurAlgo algoCase1;
    OurAlgo algoCase9;

    @BeforeEach
    void init(){
        Simulator_A.initData(1,null);
        buildingCase1 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        buildingCase9 = Simulator_A.getBuilding();
        algoCase1 = new OurAlgo(buildingCase1);
        algoCase9 =new OurAlgo(buildingCase9);
    }

    @Test
    void getBuilding() {
        assertEquals(algoCase9.getBuilding(), buildingCase9);
        assertEquals(algoCase1.getBuilding(), buildingCase1);
        assertNotEquals(algoCase1.getBuilding(), buildingCase9);
    }

    @Test
    void algoName() {
        assertEquals(algoCase1.algoName(), "our algo");
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
        int elevIndex = algoCase1.allocateAnElevator(call1);
        //just the dest floor need to be inside
        assertEquals(algoCase1.getCallsQueues()[elevIndex].sizeUp(), 1);
    }

    @Test
    void cmdElevator() {
        CallForElevator call = new Call_A(0, 1, 22);

        int elevIndex = algoCase1.allocateAnElevator(call);//add the call to the elevator
        // queue so that elevator queue Size = 2 (src floor, dest floor)

        algoCase1.cmdElevator(elevIndex);//handle one floor
        //so that elevator queue Size = 1 (only dest floor)

        assertEquals(algoCase1.getCallsQueues()[elevIndex].sizeUp(), 1);
    }
}