package main;


import JSON.Device;
import JSON.Topology;
import org.junit.jupiter.api.Test;
import static main.API.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class APITest {

    @Test
    void readJSONTest() throws Exception {
        Topology top =  readJSON(Path.of(APITest.class.getResource("topology.json").getPath()));
        assertEquals(top.getId(), "top1");
        assertEquals(top.getComponents().size(), 2);


        ArrayList<Device> comps = top.getComponents();
        Device device1 = comps.get(0);
        Device device2 = comps.get(1);

        assertEquals(device1.getId(), "res1");
        assertEquals(device1.getType(), "resistor");
        assertEquals(device1.getPropertiesName(), "resistance");
        assertEquals(device1.getProperties().get("default"), 100);
        assertEquals(device1.getProperties().get("min"), 10);
        assertEquals(device1.getProperties().get("max"), 1000);
        assertEquals(device1.getNetlist().get("t1"), "vdd");
        assertEquals(device1.getNetlist().get("t2"), "n1");


        assertEquals(device2.getId(), "m1");
        assertEquals(device2.getType(), "nmos");
        assertEquals(device2.getPropertiesName(), "m(l)");
        assertEquals(device2.getProperties().get("default"), 1.5);
        assertEquals(device2.getProperties().get("min"), 1);
        assertEquals(device2.getProperties().get("max"), 2);
        assertEquals(device2.getNetlist().get("drain"), "n1");
        assertEquals(device2.getNetlist().get("gate"), "vin");
        assertEquals(device2.getNetlist().get("source"), "vss");
    }

    @Test
    void writeJSONTest() throws Exception {
        readJSON(Path.of(APITest.class.getResource("topology.json").getPath()));
        writeJSON("top1", APITest.class.getResource("topologyout.json").getPath());
        Topology top = readJSON(Path.of(APITest.class.getResource("topologyout.json").getPath()));

        assertEquals(top.getId(), "top1");
        assertEquals(top.getComponents().size(), 2);


        ArrayList<Device> comps = top.getComponents();
        Device device1 = comps.get(0);
        Device device2 = comps.get(1);

        assertEquals(device1.getId(), "res1");
        assertEquals(device1.getType(), "resistor");
        assertEquals(device1.getPropertiesName(), "resistance");
        assertEquals(device1.getProperties().get("default"), 100);
        assertEquals(device1.getProperties().get("min"), 10);
        assertEquals(device1.getProperties().get("max"), 1000);
        assertEquals(device1.getNetlist().get("t1"), "vdd");
        assertEquals(device1.getNetlist().get("t2"), "n1");


        assertEquals(device2.getId(), "m1");
        assertEquals(device2.getType(), "nmos");
        assertEquals(device2.getPropertiesName(), "m(l)");
        assertEquals(device2.getProperties().get("default"), 1.5);
        assertEquals(device2.getProperties().get("min"), 1);
        assertEquals(device2.getProperties().get("max"), 2);
        assertEquals(device2.getNetlist().get("drain"), "n1");
        assertEquals(device2.getNetlist().get("gate"), "vin");
        assertEquals(device2.getNetlist().get("source"), "vss");
    }

    @Test
    void queryTopologiesTest() throws Exception{
        queryTopologies().clear();
        readJSON(Path.of(APITest.class.getResource("topology.json").getPath()));
        readJSON(Path.of(APITest.class.getResource("topology2.json").getPath()));

        assertEquals(2, queryTopologies().size());
    }

    @Test
    void deleteTopologyTest() throws Exception{
        queryTopologies().clear();
        readJSON(Path.of(APITest.class.getResource("topology.json").getPath()));
        readJSON(Path.of(APITest.class.getResource("topology2.json").getPath()));

        assertEquals(2, queryTopologies().size());
        deleteTopology("top2");
        assertEquals(1, queryTopologies().size());
    }

    @Test
    void queryDevicesTest() throws Exception{
        queryTopologies().clear();
        readJSON(Path.of(APITest.class.getResource("topology.json").getPath()));
        List<Device> devices = queryDevices("top1");

        assertEquals(2, devices.size());
    }

    @Test
    void queryDevicesWithNetlistNodeTest() throws Exception{
        queryTopologies().clear();
        readJSON(Path.of(APITest.class.getResource("topology.json").getPath()));

        List<Device> devices = queryDevicesWithNetlistNode("top1", "n1");

        assertEquals(2, devices.size());
    }
}
