def insertion_sort(col):
    i = 1
    while i < len(col):
        to_place = col.pop(i)
        j = 0
        while j < i and to_place > col[j]:
            j += 1
        col.insert(j, to_place)
        i += 1
    return col


def selection_sort(col):
        for i in range(0, len(col)):
            low_key = i
            low = col[low_key]
            for j in range(low_key, len(col)):
                if low > col[j]:
                    low_key = j
                    low = col[low_key]
            col[i], col[low_key] = col[low_key], col[i]
        return col


def binary_search(col, n):
    start = 0
    end = len(col)
    
    while start < end:
        mid = (end - start / 2) - 1
        mid_item = col[mid]
        if mid_item == n:
            return mid
        elif mid_item < n:
            end = mid
        elif mid_item > n:
            start = mid
    return None
        
def find_x(col, x):
    j = None
    for i in range(len(col)):
        j = binary_search(col, x - col[i])
        if j != None:
            break
    return [i, j]
