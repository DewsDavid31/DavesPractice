""" Daily problem: Given an array
    of numbers, sind the maximum sum 
    of any contiguous subarray of the array.
    
    [34,-50,42,14,-5,86] gives 137 since 42,14,-5,86 together
    
    given [-5,-1,-8,-9] gives 0 since we take none
    
    Do this in O(n) time"""


def daily_problem(number_array):
    """ Daily problem, greedy windowed approach"""
    rolling_sum = 0
    for element in number_array:
        if rolling_sum + element > rolling_sum:
            rolling_sum += element
    return rolling_sum

if __name__ == "__main__":
    print(daily_problem([34,-50,42,14,-5,86]))
    print(daily_problem([-5,-1,-8,-9]))