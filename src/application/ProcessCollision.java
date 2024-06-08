package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author bendeddouche
 */
public class ProcessCollision {
    private ArrayList<String> lines = new ArrayList<>();
    private GraphMap<String, Integer> gm;
    private File selectedFile;
   
    public ProcessCollision(File file) {
    	this.selectedFile = file;
        this.gm = new GraphMap<String,Integer>(10);
        readFile();
    }
    
    public void readFile() {
        BufferedReader reader;
        Scanner ent;
        try {
            //instanciation d'un objet FileReader puis du BufferedReader
        	FileReader fr = new FileReader(selectedFile);
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
    
    public void processLineCollision(ChargerAeroport ch) {
        for (int i=0;i<lines.size();i++) {
            String sl = lines.get(i);
            String[] res1 = processLine(sl);
            this.gm.addNode(res1[0]+"("+res1[1]+"-"+res1[2]+")");
            
            LocalTime lt1 = LocalTime.of(Integer.valueOf(res1[3]),
            Integer.valueOf(res1[4]));
            
            for (int j=i+1;j<lines.size();j++) {
                String[] res2 = processLine(lines.get(j));
                LocalTime lt2 = LocalTime.of(Integer.valueOf(res2[3]),
                Integer.valueOf(res2[4]));
                boolean res = ToolBox.processACollision(ch, res1[1],
                res1[2], res2[1], res2[2], lt1, lt2,
                Integer.valueOf(res1[5]),
                Integer.valueOf(res2[5]));
                
                if (res) {
                    this.gm.addEdge(res1[0]+"("+res1[1]+"-"+res1[2]+")", res2[0]+"("+res2[1]+"-"+res2[2]+")", 0);
                }
            }
        }
    }
    public GraphMap<String, Integer> getGraphMap () {
        return gm;
    }
}
