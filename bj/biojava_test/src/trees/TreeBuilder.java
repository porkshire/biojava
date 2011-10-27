/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trees;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.phylo.CheckTreeAccuracy;
import org.biojava3.phylo.ProgessListenerStub;
import org.biojava3.phylo.TreeConstructionAlgorithm;
import org.biojava3.phylo.TreeConstructor;
import org.biojava3.phylo.TreeType;
import org.forester.phylogeny.Phylogeny;

/**
 * Klasa do budowania drzew filogenetycznych.
 * 
 * @author DanielWegner
 */
public class TreeBuilder {
    
    private List<ProteinSequence> sequences;
    private MultipleSequenceAlignment<ProteinSequence, AminoAcidCompound> multipleSequenceAlignment;
    private TreeConstructor<ProteinSequence, AminoAcidCompound> treeConstructor = null;
    
    public TreeBuilder()
    {
        this.sequences = new LinkedList<ProteinSequence>();
        multipleSequenceAlignment = null;
    }
    
    public TreeBuilder(List<ProteinSequence> sequences)
    {
        this.sequences = sequences;
        multipleSequenceAlignment = null;
    }
    
    /**
     * Dodawanie sekwencji proteinowej do listy tworzącej drzewo.
     * Aby uzyskać sekwencję proteinową z DNA należy użyć kolejno
     * metod getRNA() i getProteinSequence().
     * @param seq 
     */
    public void addSequence(ProteinSequence seq)
    {
        this.sequences.add(seq);
    }
    
    /**
     * Usunięcie istniejącej listy.
     */
    public void reset()
    {
        sequences = new LinkedList<ProteinSequence>();
    }
    
    /**
     * By stworzyć drzewo należy mieć sekwencje protein w formie listy,
     * w której wszystkie sekwencje są tej samej długości.
     * Teoria była na wykładzie, tutaj tylko ważne jest, że ta f-cja 
     * robi odpowiednie przesunięcia i wkłada do odpowiedniej struktury.
     */
    private void alignSequences()
    {
        multipleSequenceAlignment = new MultipleSequenceAlignment<ProteinSequence, AminoAcidCompound>();
        Profile<ProteinSequence, AminoAcidCompound> profile = Alignments.getMultipleSequenceAlignment(sequences); 
        List<AlignedSequence<ProteinSequence, AminoAcidCompound>> l = profile.getAlignedSequences();
        ProteinSequence p;
        for (int i = 0; i < l.size(); i++)
        {
            Sequence<AminoAcidCompound> s = l.get(i);
            p = new ProteinSequence(s.getSequenceAsString(), s.getCompoundSet());
            p.setAccession(s.getAccession());
            multipleSequenceAlignment.addAlignedSequence(p);
        }
    }
    
    /**
     * Budowanie drzewa metodą Neighbour Joining.
     * http://en.wikipedia.org/wiki/Neighbour_joining
     * @return drzewo w formie Newick (zanawiasowane)
     */
    public String NeighbourJoining()
    {
        alignSequences();
        
        treeConstructor = new TreeConstructor<ProteinSequence, AminoAcidCompound>(multipleSequenceAlignment, TreeType.NJ, TreeConstructionAlgorithm.PID, new ProgessListenerStub());
        String newick = null;
        try 
        {
            treeConstructor.process();
            newick = treeConstructor.getNewickString(true, true);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(TreeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newick;
    }
    
    
    public TreeAccuracyInformation checkAccuracy() throws Exception
    {
        Phylogeny p = treeConstructor.getPhylogeny();
        if (p == null)
            throw new Exception("Run process() method before this one.");
        CheckTreeAccuracy acc = new CheckTreeAccuracy();
        acc.process(p, treeConstructor.getDistanceMatrix());
        TreeAccuracyInformation tai = new TreeAccuracyInformation();
        tai.setAverageMatrixDistance(acc.getAverageMatrixDistance());
        tai.setAverageTreeDistance(acc.getAverageTreeDistance());
        tai.setAverageErrorRate(acc.getAverageTreeErrorDistance());
        return tai;
    }
   
    
}