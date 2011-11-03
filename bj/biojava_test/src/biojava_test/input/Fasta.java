/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.BioException;
import org.biojava.bio.symbol.*;
import org.biojavax.SimpleNamespace;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;

/**
 *
 * @author porky
 */
public class Fasta {
    
    private RichSequenceIterator iterator;

    public Fasta(String fileName) throws FileNotFoundException, BioException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        Alphabet alpha = AlphabetManager.alphabetForName("DNA");
        SimpleNamespace ns = new SimpleNamespace("biojava");

        iterator = RichSequence.IOTools.readFasta(br,
                alpha.getTokenization("token"), ns);
        
    }
    
    public List<RichSequence> readSequences() {
        List<RichSequence> sequences = new ArrayList<RichSequence>();
        while (iterator.hasNext()) {
            try {
                sequences.add(iterator.nextRichSequence());
//                System.out.println(sequences.get(sequences.size()-1).getName());
//                System.out.println(sequences.get(sequences.size()-1).length());
            } catch (NoSuchElementException ex) {
                Logger.getLogger(Fasta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BioException ex) {
                Logger.getLogger(Fasta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sequences;
    }
    
    
}
