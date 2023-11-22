package mru.application;

import java.util.Comparator;

public class AvengerMentionComparator implements Comparator<Avenger> {

	@Override
	public int compare(Avenger o1, Avenger o2) {
		return Integer.compare(o1.getMentionOrder(), o2.getMentionOrder());
	}

}
