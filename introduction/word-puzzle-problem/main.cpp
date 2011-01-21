/*
 * main.cpp
 *
 *  Created on: Jan 13, 2011
 *      Author: joshua
 */
#include <iostream>
#include <list>
#include <string>
#include <algorithm>
#include <fstream>
#include <time.h>
#include "stdlib.h"
#include "matrix.cpp"
#include "point.cpp"
#include "crossword.h"

using namespace std;

// solve matrix
list <crossword> solve_puzzle(matrix<string> * crossword, list<string> * words);
list <crossword> check_for_words(matrix<string> * a_matrix, list<string> words, point pos, point dir);

// solve matrix helper functions
bool starts_with(string prefix, string word);
bool ends_with(string postfix, string word);
list<string> get_words_with_prefix(string prefix, list<string> * words);
list<string> get_words_with_postfix(string postfix, list<string> * words);

// create matrix
int get_matrix_size();
matrix<string> generate_matrix(int matrix_size);
string generate_random_letter();

// views
void print_matrix(matrix<string> a_matrix);
void print_answers(list<crossword> answers);
void print_solution(list<crossword> answers, matrix<string> a_matrix);


int main()
{
	int matrix_size = get_matrix_size();

	cout << "Generating matrix.." << endl;
	srand(time(NULL));
	matrix<string> crossword_puzzle = generate_matrix(matrix_size);
	cout << "Matrix generated.." << endl << endl;

	cout << "Printing matrix.." << endl << endl;
	print_matrix(crossword_puzzle);
	cout << endl << "Matrix printed." << endl << endl;

	cout << "Loading word list.." << endl;
	list<string> words;
	string line;
	ifstream word_file;
	word_file.open("/home/joshua/workspace/word-puzzle-problem/words.txt");
	while(word_file.good())
	{
		getline(word_file, line);
		words.push_back(line);
	}
	word_file.close();
	cout << "Word list loaded." << endl << endl;

	cout << "Solving crossword puzzle.." << endl;
	list<crossword> answers = solve_puzzle(&crossword_puzzle, &words);
	cout << "Crossword puzzle solved." << endl << endl;

	cout << "Printing all found words." << endl;
	print_answers(answers);
	cout << "Finished printing all found words." << endl << endl;

	cout << "Printing solution." << endl;
	print_solution(answers, crossword_puzzle);
	cout << "Finished printing solution." << endl << endl;
	return 0;
}

int get_matrix_size()
{
	std::cout << "Enter the size of the matrix:";
	int n;
	std::cin >> n;
	return n;
}

matrix<string> generate_matrix(int matrix_size)
{
	matrix<string> a_matrix(matrix_size, matrix_size);
	for (int i = 0; i < matrix_size; i++)
	{
		for (int j = 0; j < matrix_size; j++)
		{
			a_matrix[i][j] = generate_random_letter();
		}
	}
	return a_matrix;
}

string generate_random_letter()
{
	char letter = (char) (rand() % 26 + (int) 'a');
	char letters[1];
	letters[0] = letter;
	letters[1] = '\0';
	return string(letters);
}

void print_matrix(matrix<string> a_matrix)
{
	for (int i = 0; i < a_matrix.numcols(); i++)
	{
		for (int j = 0; j < a_matrix.numrows(); j++)
		{
			cout << a_matrix[i][j] << " ";
		}
		cout << endl;
	}
}

void print_answers(list<crossword> answers)
{
	for (list<crossword>::iterator i = answers.begin(); i != answers.end(); i++)
	{
		cout << i->word;
		cout << " (" << i->start.x << ", " << i->start.y << ")";
		cout << " (" << i->end.x << ", " << i->end.y << ")";
		cout << endl;
	}
	return;
}

void print_solution(list<crossword> answers, matrix<string> a_matrix)
{
	list<point> points;
	for (list<crossword>::iterator i = answers.begin(); i != answers.end(); i++)
	{
		list<point> temp = i->get_points();
		points.insert(points.end(), temp.begin(), temp.end());
	}
	for (int i = 0; i < a_matrix.numcols(); i++)
	{
		for (int j = 0; j < a_matrix.numrows(); j++)
		{
			point to_find(j, i);
			if (find(points.begin(), points.end(), to_find) != points.end())
			{
				cout << a_matrix[i][j] << " ";
			}
			else
			{
				cout << "  ";
			}
		}
		cout << endl;
	}
}

bool starts_with(string prefix, string word)
{
	return prefix.size() <= word.size() && prefix == word.substr(0, prefix.size());
}

bool ends_with(string postfix, string word)
{
	return (postfix.size() <= word.size() &&
			postfix == word.substr(word.size() - postfix.size()));
}

list<string> get_words_with_prefix(string prefix, list<string> words)
{
	list<string> matches;
	for (list<string>::iterator iter = words.begin(); iter != words.end(); iter++)
	{
		if (starts_with(prefix, *iter))
		{
			matches.push_back(*iter);
		}
	}
	return matches;
}

list<string> get_words_with_postfix(string prefix, list<string> words)
{
	list<string> matches;
	for (list<string>::iterator iter = words.begin(); iter != words.end(); iter++)
	{
		if (ends_with(prefix, *iter))
		{
			matches.push_back(*iter);
		}
	}
	return matches;
}


list<crossword> solve_puzzle(matrix<string> * a_matrix, list<string> * words)
{
	list<crossword> matches;
	list<crossword> temp;
	for (int y = 0; y < a_matrix->numcols(); y++)
	{
		for (int x = 0; x < a_matrix->numrows(); x++)
		{
			temp = check_for_words(a_matrix, *words, point(x, y), point(1, 0));
			matches.insert(matches.end(), temp.begin(), temp.end());

			temp = check_for_words(a_matrix, *words, point(x, y), point(-1, -1));
			matches.insert(matches.end(), temp.begin(), temp.end());

			temp = check_for_words(a_matrix, *words, point(x, y), point(0, -1));
			matches.insert(matches.end(), temp.begin(), temp.end());

			temp = check_for_words(a_matrix, *words, point(x, y), point(1, -1));
			matches.insert(matches.end(), temp.begin(), temp.end());
		}
	}
	return matches;
}


list<crossword> check_for_words(matrix<string> * a_matrix, list<string> words, point pos, point dir)
{
	list<crossword> matches;
	int start_x = pos.x;
	int x = start_x;
	int start_y = pos.y;
	int y = start_y;
	int move_right = dir.x;
	int move_down = dir.y;
	string word_so_far = "";
	while ( !words.empty() &&
			x >= 0 && x < a_matrix->numrows() &&
			y >= 0 && y < a_matrix->numcols())
	{
		// shorten the word list so subsequent searches take less time
		word_so_far += (*a_matrix)[y][x];
		list<string> wpre = get_words_with_prefix(word_so_far, words);
		list<string> wpost = get_words_with_postfix(word_so_far, words);
		wpre.insert(wpre.end(), wpost.begin(), wpost.end());
		words = wpre;
		// add matches that start the word
		if (find(words.begin(), words.end(), word_so_far) != words.end())
		{
			matches.push_back(crossword(word_so_far, point(start_x, start_y), point(x, y)));
		}
		// add matches that end the word
		string reversed_word_so_far = word_so_far;
		reverse(reversed_word_so_far.begin(), reversed_word_so_far.end());
		if (find(words.begin(), words.end(), reversed_word_so_far) != words.end())
		{
			matches.push_back(crossword(reversed_word_so_far, point(start_x, start_y), point(x, y)));
		}
		// adjust current cursor position
		x += move_right;
		y += move_down;
	}
	return matches;
}
