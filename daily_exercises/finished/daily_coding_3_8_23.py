""" Daily problem: You are given an array of non-negative integers that
    represents a 2d elevation map where each element is the unit-width
    wall and the integer is the height. Suppose it will rain and all
    the spots between two walls will fill up.
    
    Compute how many units of water remain trapped on the map in O(n) time
    and O(1) space.
    
    For example: given the input [2,1,2], we can hold 1 unit of water in the middle
    Given [3,0,1,3,0,5], we can hold 3 units in the first index and 2 in the second,
    3 in the fourth, trapping 8. we cannot hold any in index 5 due to run off"""


def daily_problem(elevation_array):
    """time: O(2n) => O(n), space: O(1) version
        Finds two highest points,
        adds to water difference, two loops 3 variables"""
    water = 0
    largest = 0
    second_largest = 0
    for elevation in elevation_array:
        if elevation > largest:
            largest = elevation
        elif elevation > second_largest:
            second_largest = elevation
    for waterline in elevation_array:
        if waterline < second_largest:
            water += second_largest - waterline
    return water

    

if __name__ == "__main__":
    print(daily_problem([2,1,2]))
    print(daily_problem([3,0,1,3,0,5]))