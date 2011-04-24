# Joshua Cole Copyright 2011
# Release under the MIT License

# Getting the hang of writing matrix multiplication in Python in preperation for
# writing equivalent code in Clojure.

# Matrices are represented in math such that A_yx should be equivalent to
# A[y-1][x-1] in Python.

def multiply_matrix(A, B):
    """Multiples two matrices, A and B, and returns the result.

    Args:
      A: an nxm matrix
      B: another nxm matrix

    Returns:
      The result of multiplying A by B using a naive matrix multiplication
      algorithm.

    """
    n = len(A) # The number of rows.
    m = len(A[0]) # The number of columns.
    C = [[0 for col in range(n)] for col in range(m)]
    for i in range(n):
        for j in range(n):
            for k in range(m):
                C[i][k] += A[i][j] * B[j][k]
    return C

matrix = [[1, 2],
          [1, 2]]
print multiply_matrix(matrix, matrix)
