/**
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
 * Created on Aug 30, 2011
 * Created by Andreas Prlic
 *
 * @since 3.0.2
 */
package org.biojava.bio.structure.scop.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.biojava.bio.structure.scop.ScopDescription;
import org.biojava.bio.structure.scop.ScopDomain;
import org.biojava.bio.structure.scop.ScopNode;


/** Utility classes for the XML serialization and de-serialization of SCOP.
 * 
 * @author Andreas Prlic
 * @since 3.0.2
 *
 */
public class XMLUtil {

	static JAXBContext jaxbContextScopDescription;
	static {
		try {
			jaxbContextScopDescription= JAXBContext.newInstance(ScopDescription.class);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	static JAXBContext jaxbContextScopDomain;
	static {
		try {
			jaxbContextScopDomain= JAXBContext.newInstance(ScopDomain.class);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	static JAXBContext jaxbContextScopNode;
	static {
		try {
			jaxbContextScopNode= JAXBContext.newInstance(ScopNode.class);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static String getScopDescriptionXML(ScopDescription desc){
		
		return converScopDescription(desc);
		
	}
	
	public static ScopDescription getScopDescriptionFromXML(String xml){

		ScopDescription job = null;

		try {

			Unmarshaller un = jaxbContextScopDescription.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			job = (ScopDescription) un.unmarshal(bais);

		} catch (Exception e){
			e.printStackTrace();
		}

		return job;
	}
	
	private static String converScopDescription(ScopDescription desc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextScopDescription.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( desc, ps);
			

		} catch (Exception e){
			e.printStackTrace();
		}

		return baos.toString();
	}

	public static String getScopDescriptionsXML(List<ScopDescription> descriptions){
		
		ScopDescriptions container = new ScopDescriptions();
		container.setScopDescription(descriptions);
		
		return container.toXML();
		
	}
	
	
	public static String getScopNodeXML(ScopNode scopNode){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextScopNode.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( scopNode, ps);
			

		} catch (Exception e){
			e.printStackTrace();
		}

		return baos.toString();
	}
	
	public static ScopNode getScopNodeFromXML(String xml){
		ScopNode job = null;

		try {

			Unmarshaller un = jaxbContextScopDescription.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			job = (ScopNode) un.unmarshal(bais);

		} catch (Exception e){
			e.printStackTrace();
		}

		return job;
	}

	public static String getScopNodesXML(List<ScopNode> nodes) {
		ScopNodes container = new ScopNodes();
		container.setScopNode(nodes);
		
		return container.toXML();
	}
	
	public static String getScopDomainXML(ScopDomain domain){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextScopDomain.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( domain, ps);
			

		} catch (Exception e){
			e.printStackTrace();
		}

		return baos.toString();
	}
	
	public static ScopDomain getScopDomainFromXML(String xml){
		ScopDomain job = null;

		try {

			Unmarshaller un = jaxbContextScopDomain.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			job = (ScopDomain) un.unmarshal(bais);

		} catch (Exception e){
			e.printStackTrace();
		}

		return job;
	}

	public static String getScopDomainsXML(List<ScopDomain> domains) {
		ScopDomains container = new ScopDomains();
		container.setScopDomain(domains);
		
		return container.toXML();
	}
}
