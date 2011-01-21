#ifndef POINT_CPP
#define POINT_CPP
/*
 * point.cpp
 *
 *  Created on: Jan 13, 2011
 *      Author: joshua
 */
class point
{
public:
	point(){};
	point(int a, int b) { x = a; y = b; }
	bool operator==(point that)
	{
		return this->x == that.x && this->y == that.y;
	}
	int x;
	int y;
};
#endif
