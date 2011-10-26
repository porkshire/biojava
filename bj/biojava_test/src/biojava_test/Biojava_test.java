/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test;
import biojava_test.input.InputGenerator;
import biojava_test.input.InputType;
import java.util.ArrayList;
import java.util.List;
import org.biojava3.core.sequence.*;
import org.biojavax.bio.seq.RichSequence;
import trees.TreeBuilder;

/**
 *
 * @author Dans
 */
public class Biojava_test {

    private static List<RichSequence> sequences;
    private static InputGenerator inputGenerator;
    
    public static void main(String[] args) 
    {
        inputGenerator = new InputGenerator("C:\\Users\\Dans\\Desktop\\biojava\\bj\\biojava_test\\resources\\genbank.txt", InputType.GENBANK);
        //inputGenerator = new InputGenerator("resources/genotype.txt", InputType.FESTA);
        sequences = inputGenerator.readInput();

        DNASequence seq;
        //ProteinSequence ps;
        List<DNASequence> list = new ArrayList<DNASequence>();
        for(RichSequence rs : sequences) {
             seq = new DNASequence(rs.seqString());
             seq.setAccession(new AccessionID(rs.getAccession()));
             //ps = seq.getRNASequence().getProteinSequence();
             //ps.setAccession(new AccessionID(rs.getAccession()));
             list.add(seq);
        }

        /*System.out.println("Sekwencje:");
        for (ProteinSequence ps : msa.getAlignedSequences())
            System.out.println(ps.getSequenceAsString());*/
        
        TreeBuilder t = new TreeBuilder(list);
        String s = t.NeighbourJoining();
        System.out.println(s);
        System.out.println("OK-END");
        System.exit(0);
    }
}
