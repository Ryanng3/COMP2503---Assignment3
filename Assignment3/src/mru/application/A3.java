package mru.application;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

/**
 * COMP 2503 Fall 2023 Assignment 3 Avenger Statistics
 * 
 * This program reads a input stream and keeps track of the statistics for avengers
 * mentioned by name, alias, or performer's last name.
 * The program uses a BST
 * for storing the data. Multiple BSTs with alternative orderings are
 * constructed to show the required output.
 * 
 * @author Maryam Elahi
 * @date Fall 2023
 */

public class A3 {

	public String[][] avengerRoster = { { "captainamerica", "rogers", "evans" }, { "ironman", "stark", "downey" },
			{ "blackwidow", "romanoff", "johansson" }, { "hulk", "banner", "ruffalo" },
			{ "blackpanther", "tchalla", "boseman" }, { "thor", "odinson", "hemsworth" },
			{ "hawkeye", "barton", "renner" }, { "warmachine", "rhodes", "cheadle" },
			{ "spiderman", "parker", "holland" }, { "wintersoldier", "barnes", "stan" } };

	private int topN = 4;
	private int totalwordcount = 0;
	private Scanner input = new Scanner(System.in);
	private BST<Avenger> alphabticalBST = new BST<Avenger>(Comparator.naturalOrder());
	private BST<Avenger> mentionBST = new BST<Avenger>(new AvengerMentionComparator());
	private BST<Avenger> mostPopularAvengerBST = new BST<Avenger>(new AvengerComparator());
	private BST<Avenger> mostPopularPerformerBST = new BST<Avenger>(new PerformerComparator());
	
	public static void main(String[] args) {
		A3 a3 = new A3();
		a3.run();
	}

	public void run() {
		readInput();
		createdAlternativeOrderBSTs();
		printResults();
	}

	private void createdAlternativeOrderBSTs() {
		/* TODO:
		 *   - delete the following two avengers (if they exist) from the alphabetical tree
		 *   	- barton
		 *   	- banner
		 *   use the tree iterator to do an in-order traversal of the alphabetical tree,
		 *   and add avengers to the other trees with alternative ordering
		 */
		
		deleteA(alphabticalBST);
		
		Iterator<Avenger> i = alphabticalBST.iterator();
		while(i.hasNext()) {
			Avenger a = i.next();
			
			mentionBST.addInOrder(a);
			mostPopularAvengerBST.addInOrder(a);
			mostPopularPerformerBST.addInOrder(a);		
		}	
	}
	
	private void deleteA(BST<Avenger> list) {

		Avenger banner = new Avenger();
		banner.setHeroAlias("hulk");
		banner.setHeroName("banner");
		banner.setPerformer("ruffalo");
		
		Avenger barton = new Avenger();
		barton.setHeroAlias("hawkeye");
		barton.setHeroName("barton");
		barton.setPerformer("renner");
		
		if(findForDeleteA(barton) != null)
			list.delete(barton);
		
		if(findForDeleteA(banner) != null)
			list.delete(banner);
	}

	private Avenger findForDeleteA(Avenger a) {
		Iterator<Avenger> i = alphabticalBST.iterator();
	
		while (i.hasNext()) {
			Avenger foundA = i.next();
			
	
			if (foundA.getHeroName().equals(a.getHeroName())
					|| foundA.getHeroAlias().equals(a.getHeroAlias())
					|| foundA.getPerformer().equals(a.getPerformer())) {
				return foundA;
			}
		}
		return null;
	}

	/**
	 * read the input stream and keep track how many times avengers are mentioned by
	 * alias or last name or performer name.
	 */
	private void readInput() {
		/* Create a mention index counter and initialize it to 1
		 * While the scanner object has not reached end of stream, 
		 * 	- read a word. 
		 * 	- clean up the word 
		 * 	- if the word is not empty, add the word count. 
		 * 	- Check if the word is either an avenger alias or last name, or performer last name then
		 *		- Create a new avenger object with the corresponding alias and last name and performer last name.
		 *		- check if this avenger has already been added to the alphabetically ordered tree
		 *			- if yes, increase the corresponding frequency count for the object already in the tree.
		 *			- if no, add the newly created avenger to the alphabetically ordered BST, 
		 *				- remember to set the frequency and the mention index.
		 * You need to think carefully about how you are keeping track of the mention order by setting the mention order for each new avenger.
		 */
		while(input.hasNext()) {
			String word = input.next();
			word = cleanWord(word);
			
			if(!word.isEmpty()) {
				totalwordcount++;			
				updateAvengerBST(word);
			}
		}
	}
	
	/**
	 * takes a word and cuts off any unnecessary add-ons
	 * @param next
	 * @return ret
	 */
	private String cleanWord(String next) {
		// First, if there is an apostrophe, the substring
		// before the apostrophe is used and the rest is ignored.
		// Words are converted to all lowercase.
		// All other punctuation and numbers are skipped.
		String ret;
		int inx = next.indexOf('\'');
		if (inx != -1)
			ret = next.substring(0, inx).toLowerCase().trim().replaceAll("[^a-z]", "");
		else
			ret = next.toLowerCase().trim().replaceAll("[^a-z]", "");
		return ret;
	}
	
	/**
	 * Reads the given parameter and checks if the word matches with the given array to then either
	 * add the name into the list or add a frequency
	 * @param word
	 */
	private void updateAvengerBST(String word) {
		for(int i = 0; i < avengerRoster.length; i++) {
			if(word.equals(avengerRoster[i][0]) || word.equals(avengerRoster[i][1]) || word.equals(avengerRoster[i][2])) {
				Avenger newA = new Avenger();
				newA.setHeroAlias(avengerRoster[i][0]);
				newA.setHeroName(avengerRoster[i][1]);
				newA.setPerformer(avengerRoster[i][2]);
				
				Avenger a = findA(word);
				
				if(a != null) {
					if(word.equals(avengerRoster[i][0]))
						a.setAliasFreq(a.getAliasFreq() + 1);
					else if(word.equals(avengerRoster[i][1]))
						a.setNameFreq(a.getNameFreq() + 1);
					else if(word.equals(avengerRoster[i][2]))
						a.setPerformerFreq(a.getPerformerFreq() + 1);
				} else {
					a = newA;
					
                    if (word.equals(avengerRoster[i][0])) 
                    	a.setAliasFreq(1);
                    else if (word.equals(avengerRoster[i][1])) 
                    	a.setNameFreq(1);
                    else if (word.equals(avengerRoster[i][2])) 
                    	a.setPerformerFreq(1);
                    
                    a.setMentionOrder(alphabticalBST.size() + 1);
                    alphabticalBST.add(a);
				}
			}
		}
	}

	/**
	 * iterates through the list and matches the word with pre-existing avenger otherwise returns null
	 * @param word
	 * @return foundA
	 */
	private Avenger findA(String word) {
		Iterator<Avenger> i = alphabticalBST.iterator();
		
		while(i.hasNext()) {
			Avenger foundA = i.next();
			
			if(foundA.getHeroName().equalsIgnoreCase(word) || foundA.getHeroAlias().equalsIgnoreCase(word) || foundA.getPerformer().equalsIgnoreCase(word)) {
				return foundA;
			}
		}
		return null;
	}
	/**
	 * print the results
	 */
	private void printResults() {
		// Todo: Print the total number of words (this total should not include words that are all digits or punctuation.)
		System.out.println("Total number of words: " + totalwordcount);
		// TODO: Print the number of mentioned avengers after deleting "barton" and "banner".
		System.out.println("Number of Avengers Mentioned: " + alphabticalBST.size());
		System.out.println();

		System.out.println("All avengers in the order they appeared in the input stream:");
		// TODO: Print the list of avengers in the order they appeared in the input
		// Make sure you follow the formatting example in the sample output
		mentionBST.printInOrder();
		System.out.println();
		
		System.out.println("Top " + topN + " most popular avengers:");
		// TODO: Print the most popular avengers, see the instructions for tie breaking
		// Make sure you follow the formatting example in the sample output
		printTopN(mostPopularAvengerBST);
		System.out.println();

		System.out.println("Top " + topN + " most popular performers:");
		// TODO: Print the most popular performers, see the instructions for tie breaking
		// Make sure you follow the formatting example in the sample output
		printTopN(mostPopularPerformerBST);
		System.out.println();

		System.out.println("All mentioned avengers in alphabetical order:");
		// TODO: Print the list of avengers in alphabetical order
		alphabticalBST.printInOrder();
		System.out.println();

		// TODO: Print the actual height and the optimal height for each of the four trees.
		System.out.println("Height of the mention order tree is : " + mentionBST.height()
			+ " (Optimal height for this tree is : " + mentionBST.optHeight() + ")");
		System.out.println("Height of the alphabetical tree is : " + alphabticalBST.height()
			+ " (Optimal height for this tree is : " + alphabticalBST.optHeight() + ")");
		System.out.println("Height of the most frequent tree is : " + mostPopularAvengerBST.height()
			+ " (Optimal height for this tree is : " + mostPopularAvengerBST.optHeight() + ")");
		System.out.println("Height of the most frequent performer tree is : " + mostPopularPerformerBST.height()
			+ " (Optimal height for this tree is : " + mostPopularPerformerBST.optHeight() + ")");
	}
	
	/**
	 * Takes a list and uses the iterator to print the top until the loop is broken
	 * @param list
	 */
	private void printTopN(BST<Avenger> list) {
		Iterator<Avenger> i = list.iterator();
		int count = 0;
		
		while(i.hasNext() && count < topN) {
			System.out.println(i.next());
			count++;
		}
	}
}
