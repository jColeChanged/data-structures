def remove_duplicates(a_list):
    return list(set(a_list))

def remove_last_duplicates(a_list):
    # I would have to check the implementation of set to be sure of this, but..
    return list(set(reversed(a_list)))

def binary_search(sorted_list, n):
    start = 0
    end = len(sorted_list)
    while start < end:
        mid = end - (end - start) / 2
        if n == sorted_list[mid]:
            return mid
        elif n < sorted_list[mid]:
            end = mid
        elif n > sorted_list[mid]:
            start = mid
    return -1
