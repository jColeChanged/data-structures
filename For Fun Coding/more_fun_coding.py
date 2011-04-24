import operator

def power(a, b):
    if b == 0:
        return 1
    elif b == 1:
        return a
    elif b == 2:
        return operator.mul(a, a)
    elif b % 2 == 0:
        return power(power(a, b / 2), 2) 
    else:
        return operator.mul(power(a, b - 1), a)

def higher_order_power(f, a, b):
    if b == 0:
        return 1
    elif b == 1:
        return a
    elif b == 2:
        return f(a, a)
    elif b % 2 == 0:
        return power(power(a, b / 2), 2) 
    else:
        return f(power(a, b - 1), a)

def create_power_function(f):
    def power(a, b):
        if b == 0:
            return 1
        elif b == 1:
            return a
        elif b == 2:
            return f(a, a)
        elif b % 2 == 0:
            return power(power(a, b / 2), 2) 
        else:
            return f(power(a, b - 1), a)
    return power


def add(a):
    def adder(b):
        return a + b
    return adder
