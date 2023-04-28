""" Daily problem: The area of a circle is defined as pi r^2. Estimate PI to 3 decimal places using Monte Carlo Method.
    Hint: the basic equation of a circle is x^2 + y^2 = r^2"""
import random
import math

def daily_problem():
    """ Daily problem"""
    # 2 times the upper hemisphere, done on unit circle:
    # y = 2 * (1 - x^2)^1/2
    # so iterating over slivers of function and adding until the end of unit circle
    y = 0
    x = -1
    area = 0
    while x < 1:
        partial = 1 - (x ** 2)
        if partial >= 0: # bounding function over positive hemisphere
            y = math.sqrt(partial)
        else:
            y = 0
        #print(y)
        x += 0.001 # increment
        area += y * x
    return 2 * area


if __name__ == "__main__":
    print(daily_problem())