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
package org.biojava.bio.structure.scop;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.biojava.bio.structure.align.client.JFatCatClient;
import org.biojava.bio.structure.align.util.HTTPConnectionTools;
import org.biojava.bio.structure.scop.server.ScopDescriptions;
import org.biojava.bio.structure.scop.server.ScopDomains;
import org.biojava.bio.structure.scop.server.ScopNodes;
import org.biojava.bio.structure.scop.server.XMLUtil;

public class RemoteScopInstallation implements ScopDatabase {

	public static final String DEFAULT_SERVER = "http://source.rcsb.org/scopserver/rest/";

	String server = DEFAULT_SERVER;

	public static void main(String[] args){

		ScopDatabase scop = new RemoteScopInstallation();
		ScopFactory.setScopDatabase(scop);

		//System.out.println(scop.getByCategory(ScopCategory.Superfamily));
		
		System.out.println(scop.getDomainsForPDB("4HHB"));
	}


	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public List<ScopDescription> getByCategory(ScopCategory category) {
		List<ScopDescription> results = null;
		try {
			URL u = new URL(server + "getByCategory?category="+category.toString());
			System.out.println(u);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);
			System.out.println(xml);
			ScopDescriptions container = ScopDescriptions.fromXML(xml);
			results = container.getScopDescription();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<ScopDescription> filterByClassificationId(String query) {
		List<ScopDescription> results = null;
		try {
			URL u = new URL(server + "filterByClassificationId?query="+query);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			ScopDescriptions container = ScopDescriptions.fromXML(xml);
			results = container.getScopDescription();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<ScopNode> getTree(ScopDomain domain) {
		List<ScopNode> results = null;
		try {
			URL u = new URL(server + "getTree?scopId="+domain.getScopId());
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			ScopNodes container = ScopNodes.fromXML(xml);
			results = container.getScopNode();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<ScopDomain> filterByDomainName(String query) {
		List<ScopDomain> results = null;
		try {
			URL u = new URL(server + "filterByDomainName?query="+query);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			ScopDomains container = ScopDomains.fromXML(xml);
			results = container.getScopDomain();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<ScopDescription> filterByDescription(String query) {
		List<ScopDescription> results = null;
		try {
			URL u = new URL(server + "filterByDescription?query="+query);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			ScopDescriptions container = ScopDescriptions.fromXML(xml);
			results = container.getScopDescription();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public ScopDescription getScopDescriptionBySunid(int sunid) {
		ScopDescription desc = null;
		try {
			URL u = new URL(server + "getScopDescriptionBySunid?sunid="+sunid);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			desc = XMLUtil.getScopDescriptionFromXML(xml);

		} catch (Exception e){
			e.printStackTrace();
		}
		return desc;
	}

	@Override
	public List<ScopDomain> getDomainsForPDB(String pdbId) {
		List<ScopDomain> results = null;
		try {
			URL u = new URL(server + "getDomainsForPDB?pdbId="+pdbId);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			ScopDomains container = ScopDomains.fromXML(xml);
			results = container.getScopDomain();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public ScopDomain getDomainByScopID(String scopId) {
		ScopDomain desc = null;
		try {
			URL u = new URL(server + "getDomainByScopID?scopId="+scopId);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			desc = XMLUtil.getScopDomainFromXML(xml);

		} catch (Exception e){
			e.printStackTrace();
		}
		return desc;
	}

	@Override
	public ScopNode getScopNode(int sunid) {
		ScopNode desc = null;
		try {
			URL u = new URL(server + "getScopNode?sunid="+sunid);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			desc = XMLUtil.getScopNodeFromXML(xml);

		} catch (Exception e){
			e.printStackTrace();
		}
		return desc;
	}

	@Override
	public String getScopVersion() {
		String version = null;
		try {
			URL u = new URL(server + "getScopVersion");
			InputStream response = HTTPConnectionTools.getInputStream(u);
			version = JFatCatClient.convertStreamToString(response);

		} catch (Exception e){
			e.printStackTrace();
		}
		return version;
	}

	@Override
	public List<ScopDomain> getScopDomainsBySunid(Integer sunid) {
		List<ScopDomain> results = null;
		try {
			URL u = new URL(server + "getScopDomainsBySunid?sunid="+sunid);
			InputStream response = HTTPConnectionTools.getInputStream(u);
			String xml = JFatCatClient.convertStreamToString(response);

			ScopDomains container = ScopDomains.fromXML(xml);
			results = container.getScopDomain();
		} catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

}
