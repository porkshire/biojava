/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test.input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.BioException;
import org.biojavax.bio.db.ncbi.GenbankRichSequenceDB;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author porky
 */
public class Genbank {

    private  BufferedReader br;
   
    private GenbankRichSequenceDB grsdb;
    
    public Genbank(String fileName) {
        try {
            FileInputStream fstream = null;
            grsdb = new GenbankRichSequenceDB();
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Genbank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<RichSequence> readSequences() {
        List<RichSequence> sequences = new ArrayList<RichSequence>();
        try {
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                 sequences.add(grsdb.getRichSequence(strLine));
            }           
        } catch (IOException ex) {
            Logger.getLogger(Genbank.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BioException ex) {
            Logger.getLogger(Genbank.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sequences;
    }
}
