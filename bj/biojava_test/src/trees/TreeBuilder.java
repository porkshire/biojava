/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trees;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.seq.io.ParseException;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.phylo.ProgessListenerStub;
import org.biojava3.phylo.TreeConstructionAlgorithm;
import org.biojava3.phylo.TreeConstructor;
import org.biojava3.phylo.TreeType;
import org.biojavax.bio.phylo.DistanceBasedTreeMethod;
import org.biojavax.bio.phylo.io.nexus.CharactersBlock;
import org.biojavax.bio.phylo.io.nexus.TaxaBlock;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 *
 * @author DanielWegner
 */
public class TreeBuilder {
    
    private List<DNASequence> sequences;
    private MultipleSequenceAlignment<DNASequence, NucleotideCompound> multipleSequenceAlignment;
    private TreeConstructor<DNASequence, NucleotideCompound> treeConstructor = null;
    
    public TreeBuilder()
    {
        this.sequences = new LinkedList<DNASequence>();
        multipleSequenceAlignment = null;
    }
    
    public TreeBuilder(List<DNASequence> sequences)
    {
        this.sequences = sequences;
        multipleSequenceAlignment = null;
    }
    
    /**
     * Dodawanie sekwencji proteinowej do listy tworzącej drzewo.
     * Aby uzyskać sekwencję proteinową z DNA należy użyć kolejno
     * metod getRNA() i getDNASequence().
     * @param seq 
     */
    public void addSequence(DNASequence seq)
    {
        this.sequences.add(seq);
    }
    
    /**
     * Usunięcie istniejącej listy.
     */
    public void reset()
    {
        sequences = new LinkedList<DNASequence>();
    }
    
    /**
     * By stworzyć drzewo należy mieć sekwencje protein w formie listy,
     * w której wszystkie sekwencje są tej samej długości.
     * Teoria była na wykładzie, tutaj tylko ważne jest, że ta f-cja 
     * robi odpowiednie przesunięcia i wkłada do odpowiedniej struktury.
     */
    private void alignSequences()
    {
        multipleSequenceAlignment = new MultipleSequenceAlignment<DNASequence, NucleotideCompound>();
        Profile<DNASequence, NucleotideCompound> profile = Alignments.getMultipleSequenceAlignment(sequences); 
        List<AlignedSequence<DNASequence, NucleotideCompound>> l = profile.getAlignedSequences();
        DNASequence p;
        for (int i = 0; i < l.size(); i++)
        {
            Sequence<NucleotideCompound> s = l.get(i);
            p = new DNASequence(s.getSequenceAsString(), s.getCompoundSet());
            p.setAccession(s.getAccession());
            multipleSequenceAlignment.addAlignedSequence(p);
        }
    }
    
    /**
     * Budowanie drzewa metodą Neighbour Joining.
     * http://en.wikipedia.org/wiki/Neighbour_joining
     * @return 
     */
    public String NeighbourJoining()
    {
        alignSequences();
        
        treeConstructor = new TreeConstructor<DNASequence, NucleotideCompound>(multipleSequenceAlignment, TreeType.NJ, TreeConstructionAlgorithm.PID, new ProgessListenerStub());
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
    
   
    
}
