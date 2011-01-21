#include <string>
#include "point.cpp"
#include <list>

class crossword
{
public:
	crossword(std::string word, point start, point end);

	std::list<point> get_points();

	std::string word;
	point start;
	point end;
private:
};
