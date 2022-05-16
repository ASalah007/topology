package JSON;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static main.API.*;

public class Device {
    private String type;
    private String id;
    private String propertiesName;
    private HashMap<String, Double> properties;
    private HashMap<String, String> netlist;

    public String getId(){ return id;}
    public String getPropertiesName(){return propertiesName;}
    public String getType() {return type;}
    public HashMap<String, Double> getProperties(){return properties;}



    public HashMap<String, String> getNetlist(){
        return netlist;
    }


    static Device parseDevice(Scanner in){
        Device device = new Device();
        in.next(); // type

        device.type = in.next();
        in.next(); //id

        device.id = in.next();
        device.propertiesName = in.next();
        device.properties = Device.parseProperites(in);
        in.next(); //netlist

        device.netlist = Device.parseNetlist(in);
        return device;
    }

    private static HashMap<String, Double> parseProperites(Scanner in){
        in.next(); // {
        HashMap<String, Double> hm = new LinkedHashMap<>();
        String s = in.next();
        while(s.charAt(0) != '}'){
            hm.put(s, Double.parseDouble(in.next()));
            s = in.next();
        }
        return hm;
    }

    private static HashMap<String, String> parseNetlist(Scanner in){
        in.next(); // {
        HashMap<String, String> hm = new LinkedHashMap<>();
        String s = in.next();
        while(s.charAt(0) != '}'){
            hm.put(s, in.next());
            s = in.next();
        }
        return hm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{\n  "+lvalue("type"))
            .append(rvalue(type))
            .append("  " + lvalue("id"))
            .append(rvalue(id))
            .append("  " + lvalue(propertiesName) + "{\n");
        
        // properties
        String[] keys = properties.keySet().toArray(String[]::new);
        for(int i=0; i<keys.length; i++){
            sb.append("    "+lvalue(keys[i]))
                .append(doubleFormat(properties.get(keys[i])));
            if(i!=keys.length-1)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("  },\n");

        // netlist
        sb.append("  "+lvalue("netlist")+"{\n");
        keys = netlist.keySet().toArray(String[]::new);
        for(int i=0; i<keys.length; i++){
            sb.append("    "+lvalue(keys[i]))
                .append("\""+netlist.get(keys[i]) +"\"");
            if(i != keys.length-1)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("  }\n}");

        return sb.toString();
    }
    private String doubleFormat(double d){
        if(d == (long) d)
            return String.valueOf((long)d);
        else
            return String.valueOf(d);
    }
}
