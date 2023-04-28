""" Given an array of integers, return a new array such that each element at index i
 of the new array is the product of all the numbers in the original array except the one at i
 
 for example, if our input was [1, 2, 3, 4, 5] the expected output would be [120, 60, 40, 30, 24] with [3,2,1] it would be
 [2,3,6]
 
 Bonus: what if you can't use division?"""
def daily_problem(list_of_nums):
    """ Takes product sum with for loop approach, then produces new list dividing the product each item on the list"""
    new_list = []
    product = 1
    for item in list_of_nums:
        product *= item
    for divis in list_of_nums:
        new_list.append(round(product/divis))
    return new_list


def daily_problem_bonus(list_of_nums):
    """ Approach using only multiplication by multiplying a base 1 by every other index than its own position"""
    new_list = []
    for l_operand_index in range(len(list_of_nums)):
        new_list.append(1)
        for r_operand_index in range(len(list_of_nums)):
            if l_operand_index != r_operand_index:
                new_list[l_operand_index] *= list_of_nums[r_operand_index]
    return new_list

if __name__ == "__main__":
    print(daily_problem([1,2,3,4,5]))
    print(daily_problem_bonus([1,2,3,4,5]))
    print(daily_problem([1,2,3]))
    print(daily_problem_bonus([1,2,3]))