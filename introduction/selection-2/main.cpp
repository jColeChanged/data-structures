/* Joshua Cole
 * Given a list of integers, find the kth largest.
 */

#include <iostream>
#include <queue>
#include <vector>
#include "stdlib.h"
#include "time.h"

using namespace std;

int main()
{
	int n;
	cout << "Enter n (must be greater than zero and must not overflow an int): ";
	cin >> n;

	cout << "Generating dataset.." << endl;
	vector<int> numbers;
	for (int i=0; i < n; i++)
	{
		numbers.push_back(rand() % 10);
	}
	cout << "Dataset generated." << endl;

	// This bit of code can be used when you want to make sure that your getting the
	// right answer, but I don't want the overhead of IO when entering large n values.
	/*
	cout << "Printing dataset.." << endl;
	for (int i = 0; i < numbers.size() - 1; i++)
	{
		cout << numbers[i] << " ";
	}
	cout << numbers[numbers.size() - 1] << endl;
	*/
	cout << "Calculating k.." << endl;
	int k = n / 2;
	cout << "k is " << k << endl;

	cout << "Searching for the kth largest element in the dataset.." << endl;
	cout << "Starting clock." << endl;
	time_t timer_start = clock();
	priority_queue <int, deque <int>, greater <int> > k_highest;
	while (!numbers.empty())
	{
		if (k_highest.empty() || k_highest.top() < numbers.back())
		{
			k_highest.push(numbers.back());
		}
		if (k_highest.size() > k)
		{
			k_highest.pop();
		}
		numbers.pop_back();
	}
	cout << "The kth largest element is " << k_highest.top() << endl;
	cout << "Stopping timer." << endl;
	time_t timer_stop = clock();
	cout << "Time elapsed: " << (timer_stop - timer_start) / (double) CLOCKS_PER_SEC;
	return 0;
}
