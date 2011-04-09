#include "date.h"
namespace Ex1
{
date::date()
{
	date(1, 28, 1900);
}

date::date(int month, int day, int year)
{
	if (valid_date(month, day, year))
	{
		this->month = month;
		this->day = day;
		this->year = year;
	}
	else
	{
		throw;
	}
}

bool valid_date(int month, int day, int year)
{
	return month >= 1 && month <= 12 && day >= 1 && day <= get_days_in_month(month, year);
}

bool is_leap_year(int year)
{
	if (year % 400 == 0)
	{
		return true;
	}
	else if (year % 100 == 0)
	{
		return false;
	}
	else
	{
		return year % 4 == 0;
	}
}

int get_days_in_month(int month, int year)
{
    int days[12] = {31,28,31,30,31,30,31,31,30,31,30,31};
    int days_in_month = days[month - 1];
	if (is_leap_year(year) && month == 2)
	{
		days_in_month++;
	}
	return days_in_month;
}

bool operator==(const date& self, const date& that)
{
	return self.get_day() == that.get_day() &&
		   self.get_month() == that.get_month() &&
		   self.get_year() == that.get_year();
}

bool operator>(const date& self, const date& that)
{
	if (self.get_year() > that.get_year())
	{
		return true;
	}
	else if (self.get_year() == that.get_year())
	{
		if (self.get_month() > that.get_month())
		{
			return true;
		}
		else if (self.get_month() == that.get_month())
		{
			if (self.get_day() > that.get_day())
			{
				return true;
			}
		}
	}
	return false;
}

std::string date::date_to_string()
{
	char dateString[11];
	sprintf(dateString, "%02i/%02i/%04i", month, day, year);
	return std::string(dateString);
}


bool operator!=(const date& self, const date& that) { return !operator==(self, that); }
bool operator<(const date& self, const date& that) { return operator>(that, self); }
bool operator<=(const date& self, const date& that) { return !operator>(self, that); }
bool operator>=(const date& self, const date& that) { return !operator<(self, that); }
}

