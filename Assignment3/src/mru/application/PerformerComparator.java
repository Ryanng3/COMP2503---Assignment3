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
    public int compare(Avenger avenger1, Avenger avenger2) {
        int result = avenger2.getPerformerFreq() - avenger1.getPerformerFreq();
        int lengthCompare = 0;
 
        if (result == 0)
            lengthCompare = avenger1.getHeroName().length() - avenger2.getHeroName().length();

            if (lengthCompare == 0) {
                result = avenger1.getHeroAlias().compareTo(avenger2.getHeroAlias());
            }
            else {
                result = lengthCompare;
            }

        return result;
    }
    
  

}
