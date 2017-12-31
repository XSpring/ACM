import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ACM {
    final String[] fileNames = {"Authors", "Conferences", "Affiliations", "Papers", "Subjects", "Terms", "Venues"};
    final String[] entityTypes = {"A", "C", "F", "P", "S", "T", "V"};
    final String[] edgeTypes = {"A_F", "P_A", "P_C", "P_P", "P_S", "P_T", "P_V", "V_C"};
    final String fileExtension = ".txt";

    List<List<Integer>> localIdToGlobalId;

    List<Entity> entities;
    List<Edge> edges;

    int globalID;

    public ACM() {
        localIdToGlobalId = new ArrayList<>();
        for (int i = 0; i < entityTypes.length; i++)
            localIdToGlobalId.add(new ArrayList<>());
        globalID = -1;

        entities = new ArrayList<>();
        edges = new ArrayList<>();
    }

    void loadEntityData(String filePath, int type) {
        System.out.println("Reading data from " + filePath +
                " with entity type " + fileNames[type] + ". " + "Global ID: " + globalID);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            int localID = 0;
            localIdToGlobalId.get(type).clear();

            while ((line=br.readLine())!=null) {
                localID++;
                globalID++;

                Entity newEntity = new Entity(localID, line, type);
                entities.add(newEntity);
                /*
                    Note that here we start from 0, instead of 1.
                    Hence, the localID is always 1-unit forward than the real index
                 */
                localIdToGlobalId.get(type).add(globalID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadEntityData() {
        for (int type = 0; type < entityTypes.length; type++) {
            String filePath = "data/" + fileNames[type] + ".txt";
            loadEntityData(filePath, type);
        }
    }

    int getEntityTypeID(String entityTypeName) {
        int id = 0;
        for (; id < entityTypes.length; id++)
            if (entityTypes[id].startsWith(entityTypeName))
                return id;
        return -1;
    }

    void loadEdgeData(String filePath, int srcTypeId, int dstTypeId, int edgeTypeID) {
        System.out.println("Reading data from " + filePath +
                " from " + entityTypes[srcTypeId] + " to " + entityTypes[dstTypeId]);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line=br.readLine())!=null) {
                String[] entities = line.split("\\s+");
                int entity1ID = Integer.parseInt(entities[0]);
                int entity2ID = Integer.parseInt(entities[1]);

                int global1ID = localIdToGlobalId.get(srcTypeId).get(entity1ID-1);
                int global2ID = localIdToGlobalId.get(dstTypeId).get(entity2ID-1);

                edges.add(new Edge(global1ID, global2ID, edgeTypeID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadEdgeData() {
        for (int type = 0; type < edgeTypes.length; type++) {
            String filePath = "data/" + edgeTypes[type] + ".txt";
            String[] entityTypes = edgeTypes[type].split("_");
            int srcId = getEntityTypeID(entityTypes[0]);
            int dstId = getEntityTypeID(entityTypes[1]);

            if (srcId==-1 || dstId==-1) {
                System.err.println("Cannot extract the corresponding entity type...");
                continue;
            }

            loadEdgeData(filePath, srcId, dstId, type);
        }
    }

    void writeEdgeDataToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Edge e : edges) {
                bw.write(e.srcId + "\t" + e.dstId + "\t" + e.edgeTypeId + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void writeEntityTypeToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int eId = 0; eId < entities.size(); eId++)
                bw.write(eId + "\t" + entities.get(eId).entityTypeID + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void writeEdgeTypeToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int eId = 0; eId < edgeTypes.length; eId++)
                bw.write(edgeTypes[eId] + "\t" + eId + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void writeEntityNameToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int eId = 0; eId < entities.size(); eId++) {
                String entityName = entities.get(eId).entityName.trim().replace(" ", "_");
                bw.write(entityName + "\t" + eId + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Formatting ACM data sets ---");

        ACM obj = new ACM();
        System.out.println("Loading data...");
        obj.loadEntityData();
        obj.loadEdgeData();
        System.out.println("Done. Writing data...");
        obj.writeEdgeDataToFile("ACMAdj.txt");
        obj.writeEntityTypeToFile("ACMEntityType.txt");
        obj.writeEdgeTypeToFile("ACMEdgeType");
        obj.writeEntityNameToFile("ACMEntityName.txt");
    }
}
