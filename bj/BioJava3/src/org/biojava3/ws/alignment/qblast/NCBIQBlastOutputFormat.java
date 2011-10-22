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
 */

package org.biojava3.ws.alignment.qblast;
/**
 * The RemoteQBlastOutputFormat enum acts like static fields for specifiying various
 * values for certain output options.
 * 
 * <p>
 * Many thanks to Matthew Busse for helping in debugging after the migration from BJ1.7 to BJ3.0.
 * </p>
 * 
 * @author Sylvain Foisy, Diploide BioIT
 * @since Biojava 3 
 */
public enum NCBIQBlastOutputFormat {
	TEXT,
	XML,
	HTML,
	PAIRWISE,
	QUERY_ANCHORED,
	QUERY_ANCHORED_NO_IDENTITIES,
	FLAT_QUERY_ANCHORED,
	FLAT_QUERY_ANCHORED_NO_IDENTITIES,
	TABULAR;
}
