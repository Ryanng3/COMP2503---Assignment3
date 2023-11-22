package mru.application;

import java.util.Comparator;

/**
 * This class implements comparator interface with custom ordering in case of ties 
 * @author Team 7
 */
public class AvengerComparator implements Comparator<Avenger>{
	
	/**
	 * Compares two avengers based on the hero's total frequency
	 * and performer names
	 * @param avenger1 the first avenger to compare  
	 * @param avenger2 the first avenger to compare 
	 * @return -1, 0 or 1 based on frequency comparison 
	 */
    @Override
    public int compare(Avenger avenger1, Avenger avenger2) {

        int total1 = avenger1.getNameFreq() + avenger1.getAliasFreq() + avenger1.getPerformerFreq();
        int total2 = avenger2.getNameFreq() + avenger2.getAliasFreq() + avenger2.getPerformerFreq();
        String performer1 = avenger1.getPerformer();
        String performer2 = avenger2.getPerformer();
        
        if (total1 > total2){
            return -1;
        } 
        else if (total1 < total2) {
            return 1;
        }
        else {

            if (performer1 == null && performer2 == null) {
                return 0;
            }
            else if (performer1 == null){
                return -1;
            }
            else if (performer2 == null){
                return 1;
            }
            else {
                return performer1.compareTo(performer2);
            }
        }
    }  
}



