""" Daily problem: Given a list of integers, write a function that returns the largest sum of non-adjacent numbers.
 These numbers can be 0 or negative.
 For example, [2,4,6,2,5] should return 13 since we pick 2,6,5
 [5,1,1,5] should return 10 since we pick 5,5"""


def daily_problem(list_o_nums):
    """ Daily problem: going by odd/even and summing as long as above 0, then checking ends"""
    even_sum = 0
    odd_sum = 0
    for evens in range(0,len(list_o_nums), 2):
        if list_o_nums[evens] > 0:
            even_sum += list_o_nums[evens]
    for odds in range(1, len(list_o_nums), 2):
        if list_o_nums[odds] > 0:
            odd_sum += list_o_nums[odds]
    if len(list_o_nums) > 2:
        tip_sum = list_o_nums[0] + list_o_nums[-1]
    return max([even_sum, odd_sum, tip_sum])


if __name__ == "__main__":
    print(daily_problem([2,4,6,2,5]))
    print(daily_problem([5,1,1,5]))
