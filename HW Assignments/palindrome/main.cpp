// Note to readers: We were required to do it this way.

#include <iostream>
#include <string>
#include <stack>
#include <list>
using namespace std;

bool is_palindrome(std::string word);

int main()
{
	std::string word;

	std::cout << "Enter a string: ";
	getline(std::cin, word);
	std::cout << is_palindrome(word);
	return 0;
}

bool is_palindrome(std::string word)
{
	std::stack<char> reversed_word;
	std::list<char> unreversed_word;

	for (std::string::iterator i = word.begin(); i != word.end(); i++)
	{
		reversed_word.push((char) *i);
		unreversed_word.push_back((char) *i);
	}

	for (list<char>::iterator i = unreversed_word.begin(); i != unreversed_word.end(); i++)
	{
		if (*i != reversed_word.top())
		{
			return false;
		}
		reversed_word.pop();
	}
	return true;
}
