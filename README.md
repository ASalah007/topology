# Topology

# Documentation
* [API.java](#apijava)
* [JSON.Topology.java](#jsontopologyjava)

## API.java
###  readJSON(Path) : Topology
readJSON() reads json topology in the file with the given path into memory, and returns a reference to it.

if there is no file in the specified path the function will throw an java.nio.file.NoSuchFileException

### writeJSON(topologyId: String, filePath: String)
writeJSON writes the topology with "topologyId" as json format to the file specified by the filePath.

if there is no file at the given path the function will throw java.nio.file.NoSuchFileException

### queryTopologies(): List\<Topology\>
queryTopologie() will return the list of topologies currently loadded in memory

### deleteTopology(topologyId: String)
deleteTopology will delete the topology with topologyId from memory, if there is no topology with the specified topologyId the function will do nothing 

### queryDevices(topologyId : String) : List\<Device\>
queryDevices() will return the list of devices in the topology with the topologyId.
if there is no topology with the topologyId the null will be returned.

### queryDevicesWithNetlistNode(topologyId: String, netlistNodeId: String) : List\<Device\>
queryDevicesWithNetlistNode() will return list of all devices having the netlistNodeId as a value in one of its netlist properties.

if there is no topology with the specifed topologyId null will be returned.

## JSON.Topology.java

### parseTopology(json: String) : Topology
parseTopology will parse the topology from the json string and will return a Topology Object.
The method assumes there is a valid topology json in the given string.
