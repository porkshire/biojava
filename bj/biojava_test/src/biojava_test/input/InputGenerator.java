/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test.input;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.BioException;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author porky
 */
public class InputGenerator {

    private InputType type;
    private String fileName;
    
    public InputGenerator(String fileName, InputType type) {
        this.type = type;
        this.fileName = fileName;
    }
    
    public List<RichSequence> readInput() {
        List<RichSequence> sequences = new ArrayList<RichSequence>();
        switch(type) {
            case FESTA:
                try {
                    Fasta fasta = new Fasta(fileName);
                    sequences = fasta.readSequences();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InputGenerator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BioException ex) {
                    Logger.getLogger(InputGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case GENBANK:
                Genbank genbank = new Genbank(fileName);
                sequences = genbank.readSequences();
                break;
        }
        return sequences;
    }
}
