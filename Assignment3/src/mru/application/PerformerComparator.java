package mru.application;

import java.util.Comparator;

public class PerformerComparator implements Comparator <Avenger>{

	@Override
	public int compare(Avenger o1, Avenger o2) {
		if(o1.getPerformerFreq() > o2.getPerformerFreq()) {
			return -1;
		}
		if(o1.getPerformerFreq() < o2.getPerformerFreq()) {
			return 1;
		}
		else {
			if(o1.getHeroName().length() > o2.getHeroName().length()) {
				return 1;
			}
			if(o1.getHeroName().length() < o2.getHeroName().length()) {
				return -1;
			}
			else
				return o1.getHeroAlias().compareTo(o2.getHeroAlias());
		}
	}
}

