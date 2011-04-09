// Joshua Cole date/date.cpp date/date.h date/main.cpp

#include <iostream>
#include "date.h"

using namespace std;
using namespace Ex1;

int get_day();
int get_month();
int get_year();
int get_integer();

int main()
{
	int month_one = get_month();
	int day_one = get_day();
	int year_one = get_year();
	date one(month_one, day_one, year_one);

	int month_two = get_month();
	int day_two = get_day();
	int year_two = get_year();
	date two(month_two, day_two, year_two);

	cout << "D1 = " << one.date_to_string() << endl;
	cout << "D2 = " << two.date_to_string() << endl;
	cout << "D1 > D2 is " <<  (one > two) << endl;
	cout << "D1 < D2 is " <<  (one < two) << endl;
	cout << "D1 == D2 is " << (one == two) << endl;
	cout << "D1 >= D2 is " << (one >= two) << endl;
	cout << "D1 <= D2 is " << (one <= two) << endl;
	cout << "D1 != D2 is " << (one != two) << endl;


	return 1;
}

int get_day()
{
	cout << "Enter day: ";
	int a = get_integer();
	return a;
}

int get_month()
{
	cout << "Enter month: ";
	int a = get_integer();
	return a;
}

int get_year()
{
	cout << "Enter year: ";
	int a = get_integer();
	return a;
}

int get_integer()
{
	int input;
	cin >> input;
	return input;
}
