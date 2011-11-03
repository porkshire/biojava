/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biojava_test.input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.seq.io.ParseException;
import org.biojavax.bio.phylo.io.nexus.NexusBlock;
import org.biojavax.bio.phylo.io.nexus.NexusFile;
import org.biojavax.bio.phylo.io.nexus.NexusFileBuilder;
import org.biojavax.bio.phylo.io.nexus.NexusFileFormat;
import org.biojavax.bio.phylo.io.nexus.TreesBlock;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author porky
 */
public class Nexus {
    
    private NexusFile nexus;

    public Nexus(String fileName) {
        try {
            NexusFileBuilder builder = new NexusFileBuilder();
            NexusFileFormat.parseFile(builder, new File(fileName));
            nexus = builder.getNexusFile();
        } catch (IOException ex) {
            Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<RichSequence> readSequences() {
        List<RichSequence> sequences = new ArrayList<RichSequence>();
        TreesBlock node = getTreeNode();
        
        return sequences;
    }
    
    TreesBlock getTreeNode() {
        Iterator it = nexus.blockIterator();
        NexusBlock block;
        while(it.hasNext()) {
            block = (NexusBlock)it.next();
            if (block.getBlockName().equals("TREES")) {
                    return (TreesBlock)block;
            }
        }
        return null;
    }
}
