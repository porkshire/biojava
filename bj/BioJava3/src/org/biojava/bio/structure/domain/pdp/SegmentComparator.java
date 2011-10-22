package org.biojava.bio.structure.domain.pdp;

import java.util.Comparator;

public class SegmentComparator implements Comparator<Segment> {

	public int compare(Segment v1, Segment v2) {
		
		if (v1.getFrom() < v2.getFrom())
			return -1;
		else if (v1.getFrom() > v2.getFrom())
			return 1;
		else
			return 0;
	}	
}


