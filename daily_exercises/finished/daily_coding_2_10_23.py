import sys
""" Given an array of integers, find the missing positive integer in linear time and constant space. 
In other words find the lowest positive integer that does not exist in the array. The array can contain duplicates and negative numbers as well.
For example, the input [3,4,-1,1] should return 2, [1,2,0] should give 3, you can only modify in-place"""


def daily_problem(list_of_nums):
    """ Daily problem, using sum of an incrementing formulat of first + last * n /2"""
    total = 0
    minimum = sys.maxsize
    maximum = -sys.maxsize
    num_items = 0
    for item in list_of_nums:
        if item < 0:
            continue
        num_items += 1
        total += item
        if item < minimum:
            minimum = item
        if maximum < item:
            maximum = item 
    return(round((((num_items + 1) * (minimum + maximum))/(2)))- total)

if __name__ == "__main__":
    print(daily_problem([3,4,-1,1]))
    print(daily_problem([1,2,4]))
    
