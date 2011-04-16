"""Joshua Cole Problem 1-3a-b Maximum Vertexes

Give an algorithm to find the vertex with the maximum x coordiante in O(lg(n)) 
time and an algorithm to find the vertex with the maximum y coordinate in 
O(lg(n)) time.

Earlier we showed that a unimodal search could be implemented in O(lg(n)) 
time. It turns out that a convex polygon's x-coordinates make a unimodal
sequence. Theis means that the unimodal search algorithm can be modified
so that it checks [position][0] at each time it checks [position] to
produce an algorithm which will finish in O(lg(n)) time. I define such 
changes to be busy work, so I will not implement this change. Instead I
will present an algorithm which proves that such a change is all that is
required. This algorithm is O(n) + O(lg(n)). The O(n) only converts from
[p] to [p][0].

Just now we showed that it is possible to find the max_x of a convex
polygon in O(lg(n)) time. One property of a convex polygon is that
everything between the max-x index and the end of the array will be a
unimodal sequence. Another property of that sequence is that the 
y-max of the sequence will be the y-max of the polygon. This means
that it is possible to write an algorithm which finds the y-max in
O(lg(n)) time, since the two unimodal searches for inputs less than n
will always be capped by O(2lg(n)) and constants can be discarded. I
deem the repetition of the unimodal search to be busy work, so below
is code which converts to [position][0] and [position][1]. This is
not O(n) + O(lg(n)), but it still proves that this solution method
works.
"""
#import unimodal

convex_polygon = [(0, 0), (1, -1), (2, -2), (3, -2), (4, -1), (5, 0), (4, 2), (3, 3), (2, 2)]

def convex_polygon_x_max(polygon):
    """Finds the maximum x coordinate of a convex polygon in O(n) + O(lg(n)) time.
    
    Args:
        polygon: a convex polygon made up of a list of (x, y) tuples.
    
    Returns:
      A unimodal sequence is a sequence in which:
        
        a[i] < a[i + 1] for all indexes below and including m
        a[i] > a[i - 1] for all indexes above and including m

      The x coordinates of a vertex are such a sequence of this type. This
      returns m.
      
    Raises:
      TypeError: List was empty.
      TypeError: List was not unimodal.
    """
    x_coordinates = [vertex[0] for vertex in polygon]
    return max_unimodal(x_coordinates)

def convex_polygon_y_max(polygon):
    """Finds the maximum y coordinate of a convex polygon in O(n) + O(lg(n)) time.
    
    Args:
        polygon: a convex polygon made up of a list of (x, y) tuples.
    
    Returns:
      A unimodal sequence is a sequence in which:
        
        a[i] < a[i + 1] for all indexes below and including m
        a[i] > a[i - 1] for all indexes above and including m

      The y coordinates of a vertex are such that the max x vertex through to the
      end of the list is a unimodal sequence whose m is the maximum y vertex of
      the polygon. This returns the index of that y vertex.
      
    Raises:
      TypeError: List was empty.
      TypeError: List was not unimodal.
    """
    x_coordinates = [vertex[0] for vertex in polygon]
    x_vertex = max_unimodal(x_coordinates)
    y_coordinates = [vertex[1] for vertex in polygon][x_vertex:]
    y_vertex = max_unimodal(y_coordinates)
    return y_vertex + x_vertex

