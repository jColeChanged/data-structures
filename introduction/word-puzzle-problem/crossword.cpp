#include "crossword.h"

int simplify(int x)
{
	if (x > 0) { return 1;  }
	if (x < 0) { return -1; }
	return 0;
}

crossword::crossword(std::string word, point start, point end)
{
	this->word = word;
	this->start = start;
	this->end = end;
}

std::list<point> crossword::get_points()
{
	int x_direction = simplify(end.x - start.x);
	int y_direction = simplify(end.y - start.y);
	int x = start.x;
	int y = start.y;
	std::list<point> points;
	while (x != end.x || y != end.y)
	{
		points.push_back(point(x, y));
		x += x_direction;
		y += y_direction;
	}
	points.push_back(point(end.x, end.y));
	return points;
}
