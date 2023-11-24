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
 * @author Maryam Elahi and Team 7
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
	
	/**
	 * starting point to run the program 
	 */
	public static void main(String[] args) {
		A3 a3 = new A3();
		a3.run();
	}

	/**
	 * runs the program by calling a method to read input, 
	 * calling a method of the created alternative order of the binary search trees
	 * and calling a method that prints the output 
	 */
	public void run() {
		readInput();
		createdAlternativeOrderBSTs();
		printResults();
	}

	/**
	 * deletes the two avengers (barton and banner) from the alphabetical tree
	 * uses the tree iterator to do an in-order traversal of the alphabetical tree
	 * and adds avengers to the other trees with alternative ordering
	 */
	private void createdAlternativeOrderBSTs() {
	
		deleteA(alphabticalBST);
		
		Iterator<Avenger> i = alphabticalBST.iterator();
		while(i.hasNext()) {
			Avenger a = i.next();
			
			mentionBST.addInOrder(a);
			mostPopularAvengerBST.addInOrder(a);
			mostPopularPerformerBST.addInOrder(a);		
		}	
	}
	
	/**
	 * deletes two avenger objects from the bst, 
	 * creates two avenger objects, barton and banner, sets their attributes,
	 * then deletes them using the findForDeleteA method 
	 * @param list the bst where avenger objects need to be deleted 
	 */
	private void deleteA(BST<Avenger> list) {

		//creates banner object with attributes 
		Avenger banner = new Avenger();
		banner.setHeroAlias("hulk");
		banner.setHeroName("banner");
		banner.setPerformer("ruffalo");
		
		//creates barton object with attributes 
		Avenger barton = new Avenger();
		barton.setHeroAlias("hawkeye");
		barton.setHeroName("barton");
		barton.setPerformer("renner");
		
		//deletes barton if found
		if(findForDeleteA(barton) != null)
			list.delete(barton);
		
		//deletes banner if found 
		if(findForDeleteA(banner) != null)
			list.delete(banner);
	}

	/**
	 * finds an avenger in the alphabetical BST based on Avenger attributes 
	 * 
	 * @param a the avenger object to be searched in BST
	 * @return the found avenger object or returns null if not found 
	 */
	private Avenger findForDeleteA(Avenger a) {
		Iterator<Avenger> i = alphabticalBST.iterator();
	
		while (i.hasNext()) {
			Avenger foundA = i.next();
			
			// checks object for matching attributes 
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
		//iterating through the list to see if the word matches something in the roster 
		for(int i = 0; i < avengerRoster.length; i++) {
			if(word.equals(avengerRoster[i][0]) || word.equals(avengerRoster[i][1]) || word.equals(avengerRoster[i][2])) {
				Avenger newA = new Avenger();
				newA.setHeroAlias(avengerRoster[i][0]);
				newA.setHeroName(avengerRoster[i][1]);
				newA.setPerformer(avengerRoster[i][2]);
				
				//calling the findA method to see if the roster avenger exists in the BST 
				Avenger a = findA(word);
				
				// if it is not null, it means it exists and it will add to frequency
				if(a != null) {
					if(word.equals(avengerRoster[i][0]))
						a.setAliasFreq(a.getAliasFreq() + 1);
					else if(word.equals(avengerRoster[i][1]))
						a.setNameFreq(a.getNameFreq() + 1);
					else if(word.equals(avengerRoster[i][2]))
						a.setPerformerFreq(a.getPerformerFreq() + 1);
				} 
				// if null it will create a new member on the list 
				else {
					a = newA;
					
                    if (word.equals(avengerRoster[i][0])) 
                    	a.setAliasFreq(1);
                    else if (word.equals(avengerRoster[i][1])) 
                    	a.setNameFreq(1);
                    else if (word.equals(avengerRoster[i][2])) 
                    	a.setPerformerFreq(1);
                    
                    //creates an index to easily search on the list 
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
		//Prints the total number of words (this total does not include words that are all digits or punctuation)
		System.out.println("Total number of words: " + totalwordcount);
		//Prints the number of mentioned avengers after deleting "barton" and "banner".
		System.out.println("Number of Avengers Mentioned: " + alphabticalBST.size());
		System.out.println();

		System.out.println("All avengers in the order they appeared in the input stream:");
		//Prints the list of avengers in the order they appeared in the input
		mentionBST.printInOrder();
		System.out.println();
		
		System.out.println("Top " + topN + " most popular avengers:");
		//Prints the most popular avengers 
		printTopN(mostPopularAvengerBST);
		System.out.println();

		System.out.println("Top " + topN + " most popular performers:");
		//Prints the most popular performers
		printTopN(mostPopularPerformerBST);
		System.out.println();

		System.out.println("All mentioned avengers in alphabetical order:");
		//Prints the list of avengers in alphabetical order
		alphabticalBST.printInOrder();
		System.out.println();

		//Prints the actual height and the optimal height for each of the four trees.
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
