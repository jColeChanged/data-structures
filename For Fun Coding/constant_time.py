def multiply(a, b):
    """Multiplies in constant time two 2x2 matrices.
    
    Args:
      a: A 2x2 matrix.
      b: Another 2x2 matrix.

    Returns:
      The result of AB. Please note that AB != BA when dealing with matrices.
    """
    return [[a[0][0] * b[0][0] + a[0][1] * b[1][0]]
            [a[1][0]   ]]
