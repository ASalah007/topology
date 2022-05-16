package main;

import JSON.Device;
import JSON.Topology;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class API {
    private static ArrayList<Topology> topologies = new ArrayList<>();

    public static Topology readJSON(Path jsonFile) throws IOException{
        String json = Files.readString(jsonFile);
        Topology topology = Topology.parseTopology(json);
        topologies.add(topology);
        return topology;
    }

    public static void writeJSON(String topologyId, String filePath) throws IOException, IllegalArgumentException{
        Topology top = null;
        for(int i=0; i<topologies.size(); i++){
            System.out.println(topologies.get(i).getId());
            if(topologies.get(i).getId().equals(topologyId)){
                top = topologies.get(i);
                break;
            }
        }
        if(top == null){
            throw new IllegalArgumentException("there is no topology with the specified id");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\n  "+lvalue("id"))
            .append(rvalue(top.getId()))
            .append("  "+lvalue("components") + "[\n");

        ArrayList<Device> comps = top.getComponents();
        for(int i=0; i<comps.size(); i++){
            sb.append("    "+comps.get(i).toString().replaceAll("\n", "\n    "));
            if(i != comps.size() -1)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n}\n");

        PrintWriter out = new PrintWriter(new File(filePath));
        out.println(sb.toString());
        out.close();
    }

    public static List<Topology> queryTopologies(){
        return topologies;
    }
    
    public static void deleteTopology(String topologyId){
        for(int i=0; i<topologies.size(); i++){
            if(topologies.get(i).getId().equals(topologyId)){
                topologies.remove(i);
            }
        }

    }

    public static List<Device> queryDevices(String topologyId){
        Topology top = null;
        for(Topology t : topologies){
            if(t.getId().equals(topologyId)){
                top = t;
                break;
            }
        }
        return top.getComponents();
    }

    public static List<Device> queryDevicesWithNetlistNode(String topologyId, String netlistNodeId){
        List<Device> devices = queryDevices(topologyId);
        List<Device> result = new ArrayList<>();
        for(Device d : devices){
            HashMap<String, String> netlist = d.getNetlist();
            Set<String> set = netlist.keySet();

            for(String s : set){
                if(netlist.get(s).equals(netlistNodeId))
                    result.add(d);
            }
        }
        return result;
    }

    public static void main(String[] args){
        try{
            readJSON(Path.of("topology.json"));
            writeJSON("top1", "out.json");
        }
        catch(Exception ex){
        }
    }

    public static String lvalue(String val){
        return "\""+val+"\": ";
    }
    public static String rvalue(String val){
        return "\""+val+"\",\n";
    }
}
