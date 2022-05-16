package JSON;

import java.util.ArrayList;
import java.util.Scanner;

public class Topology {
    String id;
    ArrayList<Device> components;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public ArrayList<Device> getComponents(){
        return components;
    }

    public static Topology parseTopology(String json){
        json = json
            .replaceAll("[\"\s\r\n]", "")
            .replaceAll("[:,]", " ")
            .replaceAll("\\{", "{ ")
            .replaceAll("\\}", " }")
            .replaceAll("\\[", "[ ")
            .replaceAll("\\]", " ]");
        Scanner in = new Scanner(json);
        Topology topology = new Topology();

        in.next(); // {
        in.next(); // id
        topology.id = in.next();
        in.next(); // components
        topology.components = Topology.parseComponents(in);

        return topology;
    }

    private static ArrayList<Device> parseComponents(Scanner in){
        ArrayList<Device> devices = new ArrayList<>();
        while(in.hasNext()){
            if(in.next().charAt(0) ==  '{')
               devices.add(Device.parseDevice(in));
            else
                continue;
        }
        return devices;
    }

    @Override
    public String toString() {
        return id;
    }

}

