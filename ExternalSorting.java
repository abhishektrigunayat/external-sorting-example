package com.test;

import java.util.Arrays;
import java.util.Random;

public class ExternalSorting {
	// Use external sorting, data used is sample
	public static int DISK_SIZE = 900;
	public static int MEM_SIZE = 100;

	public static void main(String[] args) {

		// This array represents the disk which may have 1 million numbers
		float[] disk = new float[DISK_SIZE];

		// This array represents the memory which is limited and can't contain
		// all million numbers at same time
		float[] mem = new float[MEM_SIZE];

		int iter = DISK_SIZE / MEM_SIZE;

		// Populate disk with random numbers
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= DISK_SIZE; ++idx) {
			int randomInt = randomGenerator.nextInt(900);
			// I have converted int to float but using pure float numbers will
			// be similar
			disk[idx - 1] = (float) randomInt;
		}

		// Repeat this 9 times i.e iter
		for (int j = 0; j < iter; j++) {
			// Copy 100 pieces of data from disk to memory
			for (int i = 0; i < MEM_SIZE; i++) {
				mem[i] = disk[i + (j * 100)];
			}

			// Sort it in memory using mergesort or heap sort with time O(nlogn)
			Arrays.sort(mem);

			// Write the sorted data to disk
			for (int i = 0; i < MEM_SIZE; i++) {
				disk[i + (j * 100)] = mem[i];
			}
		}

		// All 100 chunks in disk are sorted

		// Do the K way merge for the sorted data, in this case K will be 9

		// Initialize disk pointers
		int[] in_ptrs = new int[iter];
		for (int i = 0; i < iter; i++) {
			in_ptrs[i] = i * 100;
		}

		// Read the first 10 pieces of data from each 9 sorted chunks in memory
		// and leave last 10 for output buffer
		int count = 0;
		for (int i = 0; i < iter; i++) {
			for (int j = 0; j < 10; j++) {
				mem[count++] = disk[(i * 100) + j];
			}
		}

		// Perform 9 way merge of the data from input buffer and write it to
		// output buffer

		// Initialize pointers in memory
		int[] mem_ptrs = new int[iter];
		for (int i = 0; i < iter; i++) {
			mem_ptrs[i] = i * 10;
		}

		// Do the K way merge
		int temp = count;
		float min = 10000.0f;
		int smallest_index = 0;
		while (temp < MEM_SIZE) {
			for (int i = 0; i < iter; i++) {
				// Get the smallest element from the 9 pointers in memory using
				// any search, liner is used here for simplicity
				if (mem[mem_ptrs[i]] < min) {
					min = mem[mem_ptrs[i]];
					smallest_index = i;
				}
			}
			// Copy it into output buffer and increment the count
			mem[temp++] = min;
			mem_ptrs[smallest_index]++;
			min = 10000.0f;
			// Repeat this until the output buffer is full;
		}

		// Now the output buffer has the first 10 sorted numbers

		// Print the first 5 numbers as needed, which will be minimum

		System.out.println("Lowest numbers after performing external sort : ");
		for (int i = count; i < count + 5; i++) {
			System.out.println(mem[i]);
		}

		// Output from the first 5 smallest numbers from disk, just to check if
		// they are same
		Arrays.sort(disk);

		System.out
				.println("Lowest number from disk after performing a sort on disk array (just for validating) : ");
		for (int i = 0; i < 5; i++) {
			System.out.println(disk[i]);
		}

	}

}
