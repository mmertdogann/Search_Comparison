/*
Name: Mert Doðan
ID: 041701041
Date: 26.12.2018
Explanation: This class is the main class whose project name is Search Analysis.
This program has three two which are main, shuffleArray and a default classes which are
ArrayList,HashMap,Linked List and I use BST class which given by the instructor 
The program compares the searching time of a shuffled random M size integer array.
The array is cloning three times at each for loop which is the first, and 
create different data structures and fill into with the integer array 
In the program print out each searching times of the data structures.
 */
package mert_dogan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

public class mert_dogan {
	public static void main(String[] args) throws IOException, WriteException {

		int i, j, k, l, t; // For indexing
		int M = 1000; // Creating first Size
		double start = 0.0; // starting time
		double finish = 0.0; // ending time
		double elapsedTimeMilliSeconds = 0.0; // the elapsed time
		// calculated time will store in each arrays
		double[] elapsedTimearrayList = new double[100];
		double[] elapsedTimeLinkedList = new double[100];
		double[] elapsedTimeHashMap = new double[100];
		double[] elapsedTimeBST = new double[100];
		int size = elapsedTimearrayList.length;
		for(i = 0;i<size;i++) {
			elapsedTimearrayList[i]=0.0;
			elapsedTimeLinkedList[i]=0.0;
			elapsedTimeHashMap[i]=0.0;
			elapsedTimeBST[i]=0.0;
		}

		for (i = 0; i < size; i++, M += 100) { // loop for M tries (don't forget increase M = M + 1000)

			Integer[] arrayaList = new Integer[M]; // Creating Random Shuffling integer array for arrayList
			Integer[] arraylList = new Integer[M]; // Creating Random Shuffling integer array for LinkedList
			Integer[] arrayHash = new Integer[M]; // Creating Random Shuffling integer array for HashMap
			Integer[] arrayBST = new Integer[M]; // Creating Random Shuffling integer array for BST
			for (k = 0; k < M; k++) {
				arrayaList[k] = k+1; // first assign increasing integers -up to M size- into array
			}
			shuffleArray(arrayaList); // calling shuffle method
			System.arraycopy(arrayaList, 0, arraylList, 0, M); // Copy the array for LinkedList
			System.arraycopy(arrayaList, 0, arrayHash, 0, M); // Copy the array for HashMap
			System.arraycopy(arrayaList, 0, arrayBST, 0, M); // Copy the array for BST

			ArrayList<Integer> aList = new ArrayList<>(); // creating ArrayList
			LinkedList<Integer> lList = new LinkedList<>(); // creating LinkedList
			HashMap<Integer, Integer> myMap = new HashMap<>(M); // creating HashMap
			BST<Integer> bTree = new BST<>(); // creating BST

			// inserting array of random integers
			for (l = 0; l < M; l++) {

				aList.add(arrayaList[l]);
				lList.add(arraylList[l]);
				myMap.put(arrayHash[l], arrayHash[l]);
				bTree.insert(arrayBST[l]);

			}

			for (j = 0; j < M; j++) {
				// Time calculate for ArrayList search
				start = System.nanoTime();
				aList.contains(j+1);
				finish = System.nanoTime();
				elapsedTimeMilliSeconds = (finish - start) / 1000f;
				elapsedTimearrayList[i] += elapsedTimeMilliSeconds;

				// Time calculate for LinkedList search
				start = System.nanoTime();
				lList.contains(j+1);
				finish = System.nanoTime();
				elapsedTimeMilliSeconds = (finish - start) / 1000f;
				elapsedTimeLinkedList[i] += elapsedTimeMilliSeconds;

				// Time calculate for HashMap search
				start = System.nanoTime();
				myMap.containsKey(j+1);
				finish = System.nanoTime();
				elapsedTimeMilliSeconds = (finish - start) / 10000f;
				elapsedTimeHashMap[i] += elapsedTimeMilliSeconds;

				// Time calculate for BST search
				start = System.nanoTime();
				bTree.search(j+1);
				finish = System.nanoTime();
				elapsedTimeMilliSeconds = (finish - start) / 10000f;
				elapsedTimeBST[i] += elapsedTimeMilliSeconds;

			}

		}
		int trying = 1000; // try counts for M
		// printing whole tries
		System.out.println("Sample Program Output");
		System.out.println(
				"Sequence Size; Array List Search Time; Hash Map Search Time; Linked List Search Time; BST Search Time");
		for (t = 0; t < elapsedTimearrayList.length; t++) {

			System.out.printf("%d;%.2f;%.2f;%.2f;%.2f\n", trying * (t + 1), elapsedTimearrayList[t],
					elapsedTimeHashMap[t], elapsedTimeLinkedList[t], elapsedTimeBST[t]);

		}

		// Write out excel sheet
		try {
			String fileName = "C:\\Users\\Mert\\Desktop\\file.xls"; // Name the excel file
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName)); // Create file
			WritableSheet sheet = workbook.createSheet("Sheet1", 0); // Create sheet

			// Filling the labels
			Label label = new Label(0, 0, "Search Size");
			Label label1 = new Label(1, 0, "Search Time (msec)");
			Label label2 = new Label(2, 0, "Linked List");
			Label label3 = new Label(3, 0, "Array List");
			Label label4 = new Label(4, 0, "Binary Search Tree");
			Label label5 = new Label(5, 0, "Hash Map");

			// Adding the created labels to sheet
			sheet.addCell(label);
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);

			// Adding the calculated searching times into each labels
			for (i = 0; i < size; i++) {
				// Creating new numbers which will add in labels
				Number number = new Number(2, i + 1, elapsedTimeLinkedList[i]);
				Number number1 = new Number(3, i + 1, elapsedTimearrayList[i]);
				Number number2 = new Number(4, i + 1, elapsedTimeBST[i]);
				Number number3 = new Number(5, i + 1, elapsedTimeHashMap[i]);
				// Adding the numbers into Cells
				sheet.addCell(number);
				sheet.addCell(number1);
				sheet.addCell(number2);
				sheet.addCell(number3);
			}

			// Closing the workbook writing process
			workbook.write();
			workbook.close();
		} catch (WriteException e) {

		}
	}

	// shuffling integers with using random index access and swap them
	private static void shuffleArray(Integer[] array) {
		int index, temp,i,size=array.length;
		Random random = new Random();
		for (i = size - 1; i > 0; i--) {
			index = random.nextInt(i + 1); // random index
			// and swapping
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
	}

}
