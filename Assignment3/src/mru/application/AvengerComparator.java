package mru.application;

import java.util.Comparator;

public class AvengerComparator implements Comparator <Avenger>{

	@Override
	public int compare(Avenger o1, Avenger o2) {
		int total1 = o1.getNameFreq() + o1.getAliasFreq() + o1.getPerformerFreq();
		int total2 = o2.getNameFreq() + o2.getAliasFreq() + o2.getPerformerFreq();
		String p1 = o1.getPerformer();
		String p2 = o2.getPerformer();
		
		if(total1 > total2) {
			return -1;
		}
		else if (total1 < total2) {
			return 1;
		}
		else {
			if(p1 == null && p2 == null) {
				return 0;
			}
			else if (p1 == null) {
				return -1;
			}
			else if (p2 == null) {
				return 1;
			}
			else {
				return p1.compareTo(p2);
			}
		}
	}	
}
