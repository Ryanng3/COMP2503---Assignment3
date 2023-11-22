package mru.application;

import java.util.Comparator;

/**
 * This class implements comparator interface with custom ordering in case of ties 
 * @author Team 7
 */ 
public class PerformerComparator implements Comparator <Avenger>{
	
	/**
	 * takes in two avenger objects to compare based on their performer frequency
	 * it sums up their frequencies by name, performer, and alias and in case of ties
	 * it orders the objects in ascending alphabetical order by performer's last name
	 * @param avenger1 the first avenger to compare  
	 * @param avenger2 the first avenger to compare 
	 * @return result -1, 0 or 1 based on order
	 */
	@Override
	public int compare(Avenger o1, Avenger o2) {
		
		if(o1.getPerformerFreq() > o2.getPerformerFreq()) {
			return -1;
		}
		if(o1.getPerformerFreq() < o2.getPerformerFreq()) {
			return 1;
		}
		else
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
