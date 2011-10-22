/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test;
import biojava_test.input.InputGenerator;
import biojava_test.input.InputType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.*;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.phylo.ProgessListenerStub;
import org.biojava3.phylo.TreeConstructionAlgorithm;
import org.biojava3.phylo.TreeConstructor;
import org.biojava3.phylo.TreeType;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author Dans
 */
public class Biojava_test {

    private static List<RichSequence> sequences;
    private static InputGenerator inputGenerator;
    
    public static void main(String[] args) 
    {
        inputGenerator = new InputGenerator("resources/genbank.txt", InputType.GENBANK);
        //inputGenerator = new InputGenerator("resources/genotype.txt", InputType.FESTA);
        sequences = inputGenerator.readInput();

        DNASequence seq;
        ProteinSequence ps;
        List<ProteinSequence> list = new ArrayList<ProteinSequence>();
        for(RichSequence rs : sequences) {
             seq = new DNASequence(rs.seqString());
             ps = seq.getRNASequence().getProteinSequence();
             list.add(ps);
        }

        Profile<ProteinSequence, AminoAcidCompound> profile = Alignments.getMultipleSequenceAlignment(list);
        System.out.println("OK");
        MultipleSequenceAlignment<ProteinSequence, AminoAcidCompound> msa = new MultipleSequenceAlignment<ProteinSequence, AminoAcidCompound>();
        for(int i = 0; i < sequences.size(); i++) {
            ps = new ProteinSequence(profile.getAlignedSequence(i+1).getSequenceAsString());
            ps.setAccession(new AccessionID(sequences.get(i).getName()));
            msa.addAlignedSequence(ps);
            System.out.println("OK");
        }
        System.out.println("OK" + msa.getSize());

        /*System.out.println("Sekwencje:");
        for (ProteinSequence ps : msa.getAlignedSequences())
            System.out.println(ps.getSequenceAsString());*/

        String treeStr = null;
        TreeConstructor<ProteinSequence, AminoAcidCompound> treeConstructor = new TreeConstructor<ProteinSequence, AminoAcidCompound>(msa, TreeType.NJ, TreeConstructionAlgorithm.PID, new ProgessListenerStub());

        try 
        {
            treeConstructor.process();
            treeStr = treeConstructor.getNewickString(true, true);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Biojava_test.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("OK-END");
        System.out.println(treeStr);
        System.exit(0);
    }
}
