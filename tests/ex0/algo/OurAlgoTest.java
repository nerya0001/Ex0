package ex0.algo;

import ex0.Building;
import ex0.simulator.Simulator_A;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OurAlgoTest {
    Building buildingCase0;
    Building buildingCase9;
    OurAlgo algoCase0;
    OurAlgo algoCase9;

    @BeforeEach
    void init(){
        Simulator_A.initData(0,null);
        buildingCase0 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        buildingCase9 = Simulator_A.getBuilding();
        algoCase0 = new OurAlgo(buildingCase0);
        algoCase9 =new OurAlgo(buildingCase9);
    }

    @Test
    void getBuilding() {
        assertEquals(algoCase9.getBuilding(), buildingCase9);
        assertEquals(algoCase0.getBuilding(), buildingCase0);
        assertNotEquals(algoCase0.getBuilding(), buildingCase9);
    }

    @Test
    void algoName() {
        assertEquals(algoCase0.algoName(), "our algo");
    }

    @Test
    void allocateAnElevator() {



    }

    @Test
    void cmdElevator() {


    }
}