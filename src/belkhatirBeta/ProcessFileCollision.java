package belkhatirBeta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author bendeddouche
 */
public class ProcessFileCollision {
    private ArrayList<String> lines = new ArrayList<>();
    private GraphMap gm;
   
    public ProcessFileCollision() {
        this.gm = new GraphMap<String,Integer>(10);
        readFile();
    }
    
    public void readFile() {
        //String st = null;
        BufferedReader reader;
        FileReader fr;
        Scanner ent;
        try {
            //récupération du nom du fichier
            /*do {
            System.out.print ( " Entrez votre fichier : " );
            //fileName = reader.readLine();
            st = ent.nextLine();
            }
            while ( st == null || st.length() == 0 );*/
            //instanciation d'un objet FileReader puis du BufferedReader
            fr = new FileReader ( "C:\\Users\\..." ) ;
            reader = new BufferedReader (fr) ;
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
                //((line=reader.readLine())!=null) {
                lines.add(ent.nextLine());
            }
            reader.close(); fr.close(); ent.close();
        } catch(IOException ex) { System.err.println(ex); }
    }
    private String[] processLine (String sl) {
        String[] result = sl.split(";");
        return result;
    }
    
    public void processLineCollision(ProcessFileAeroports pfa) {
        for (int i=0;i<lines.size();i++) {
            String sl = lines.get(i);
            String[] res1 = processLine(sl);
            this.gm.addNode(res1[0]);
            
            LocalTime lt1 = LocalTime.of(Integer.valueOf(res1[3]),
            Integer.valueOf(res1[4]));
            
            for (int j=0;j<lines.size();j++) {
                if (j!=i) {
                String[] res2 = processLine(lines.get(j));
                LocalTime lt2 = LocalTime.of(Integer.valueOf(res2[3]),
                Integer.valueOf(res2[4]));
                boolean res = ToolBox.processACollision(pfa, res1[1],
                res1[2], res2[1], res2[2], lt1, lt2,
                Integer.valueOf(res1[4]),
                Integer.valueOf(res2[4]));
                
                if (res) {
                    this.gm.addEdge(res1[0], res2[0], 0);
                }
                }
            }
        }
    }
    public GraphMap getGraphMap () {
        return gm;
    }
}
