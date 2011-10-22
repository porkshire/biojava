/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.BioException;
import org.biojava.bio.symbol.SymbolList;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.*;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.phylo.ProgessListenerStub;
import org.biojava3.phylo.TreeConstructionAlgorithm;
import org.biojava3.phylo.TreeConstructor;
import org.biojava3.phylo.TreeType;
import org.biojavax.bio.db.ncbi.GenbankRichSequenceDB;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author Dans
 */
public class Biojava_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
        RichSequence rs = null;

        GenbankRichSequenceDB grsdb = new GenbankRichSequenceDB();
        try{
            // Demonstration of use with GenBank accession number
            rs = grsdb.getRichSequence("M98343");
            System.out.println(rs.getName()+" | "+rs.getDescription());
            SymbolList sl = rs.getInternalSymbolList();
            System.out.println(sl.seqString().length());

            DNASequence seq1 = new DNASequence(rs.seqString());

            // Demonstration of use with GenBank GI
            rs = grsdb.getRichSequence("NM_001099497");			
            System.out.println(rs.getName()+" | "+rs.getDescription());
            sl = rs.getInternalSymbolList();
            System.out.println(sl.seqString().length());

            DNASequence seq2 = new DNASequence(rs.seqString());


            ProteinSequence p1 = seq1.getRNASequence().getProteinSequence();
            ProteinSequence p2 = seq2.getRNASequence().getProteinSequence();
            List<ProteinSequence> list = new ArrayList<ProteinSequence>();
            list.add(p1);
            list.add(p2);

            Profile<ProteinSequence, AminoAcidCompound> profile = Alignments.getMultipleSequenceAlignment(list);

            System.out.println("OK");
            MultipleSequenceAlignment<ProteinSequence, AminoAcidCompound> msa = new MultipleSequenceAlignment<ProteinSequence, AminoAcidCompound>();
            msa.addAlignedSequence(new ProteinSequence(profile.getAlignedSequence(1).getSequenceAsString()));
            System.out.println("OK");
            msa.addAlignedSequence(new ProteinSequence(profile.getAlignedSequence(2).getSequenceAsString()));
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
        }
        catch(BioException be){
            be.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
}
