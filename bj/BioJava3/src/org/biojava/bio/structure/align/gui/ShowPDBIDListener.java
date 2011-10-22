/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Jul 16, 2010
 * Author: ap3 
 *
 */

package org.biojava.bio.structure.align.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.align.gui.jmol.StructureAlignmentJmol;
import org.biojava.bio.structure.align.util.AtomCache;
import org.biojava.bio.structure.align.util.UserConfiguration;
import org.biojava.bio.structure.align.webstart.WebStartMain;


public class ShowPDBIDListener
implements ActionListener {
   public void actionPerformed(ActionEvent e) {
       String cmd = e.getActionCommand();                  
       if ( cmd.equals("Show By ID")){
          
          String pdbId = JOptionPane.showInputDialog(null,
                "Which ID to display?",
                "Enter PDB ID, PDB.chainId, or SCOP domain ID",
                JOptionPane.QUESTION_MESSAGE);
          
          if ( pdbId != null) {
             try {
                pdbId = pdbId.trim();
                UserConfiguration config = WebStartMain.getWebStartConfig();
                AtomCache cache = new AtomCache(config.getPdbFilePath(),config.isSplit());
                Structure s = cache.getStructure(pdbId); 
                StructureAlignmentJmol jmol = new StructureAlignmentJmol(null,null,null);
                jmol.setStructure(s);
                                    
                jmol.evalString("set antialiasDisplay on; select all;spacefill off; wireframe off; backbone off; cartoon;color cartoon chain; select ligand;wireframe 0.16;spacefill 0.5; select all; color cartoon structure;");
                jmol.evalString("save STATE state_1");
            } catch (Exception ex){
                ex.printStackTrace();
            }
          }        
       }               
   }       
}
