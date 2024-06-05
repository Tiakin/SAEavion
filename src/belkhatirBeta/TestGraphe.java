package belkhatirBeta;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author bendeddouche
 */
public class TestGraphe {
    public static void main(String[] args) {
        /*if(!"UTF-8".equals(System.out.charset().displayName())){
            System.setOut(new PrintStream(
            new FileOutputStream(FileDescriptor.out), true,
            StandardCharsets.UTF_8)); 
        }*/
        ProcessFileCollision pfc = new ProcessFileCollision();
        ProcessFileAeroports pfa = new ProcessFileAeroports();
        pfc.processLineCollision(pfa);
        pfc.getGraphMap().greedyColoring();
        System.out.println(pfc.getGraphMap());
        GraphMap gm = new GraphMap(2);
        gm.addEdge("AF23", "AF230", 1);
        gm.addEdge("AF23", "AF230", 2);
        gm.addEdge("AF23", "AF231", 1);
        gm.addEdge("AF23", "AF238", 1);
        gm.addEdge("AF231", "AF230", 1);
        gm.addEdge("AF231", "AF238", 1);
        gm.addEdge("AF230", "AF99", 1);
        System.out.println(gm.greedyColoring());
    }
}
