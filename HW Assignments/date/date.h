#ifndef DATE_H
#define DATE_H
#include <string>
#include <cstdio>
namespace Ex1
{
bool valid_date(int month, int day, int year);
bool is_leap_year(int year);
int get_days_in_month(int month, int year);

class date
{
public:
	date();
	date(int month, int day, int year);

	int get_day()   const {return day;}
	int get_month() const {return month;}
	int get_year()  const {return year;}
	std::string date_to_string();

private:
	int day;
	int month;
	int year;
};
bool operator==(const date& self, const date& that);
bool operator>(const date& self, const date& that);
bool operator!=(const date& self, const date& that);
bool operator<(const date& self, const date& that);
bool operator<=(const date& self, const date& that);
bool operator>=(const date& self, const date& that);
}
#endif
