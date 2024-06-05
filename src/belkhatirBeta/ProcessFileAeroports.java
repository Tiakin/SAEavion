package belkhatirBeta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author bendeddouche
 */
public class ProcessFileAeroports {
    private Map<String,String[]> m;
    
    public ProcessFileAeroports() {
        m = new HashMap<>();
        readFile();
    }
    
    public void readFile() {
        //String st = null;
        BufferedReader reader ;
        //= new BufferedReader(new InputStreamReader(System.in));
        Scanner ent ;
        //= new Scanner(System.in);
        FileReader fr;
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
            reader = new BufferedReader(fr) ;
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
                //((line=reader.readLine())!=null) {
                processLine(ent.nextLine());
            }
                reader.close(); fr.close(); ent.close();
            } catch(IOException ex) { System.err.println(ex); }
    }
    private void processLine(String s) {
        String[] res = s.split(";");
        m.put(res[0], res);
    }
    
    public Map getMapAero() {
        return m;
    }
    
}
