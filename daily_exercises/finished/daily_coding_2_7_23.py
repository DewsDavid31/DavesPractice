"""
Given a list of numbers of and a number k, return whether any two numbers from the set add up to k
example: [10,15,3,7] and 17 return true since 10 + 7 = 17
Can you do this in one pass?
"""
def daily_problem(list_of_nums, total):
    """ n^2 brute force approach"""
    past_nums = []
    for num in list_of_nums:
        if num > total:
            continue
        for num_sum in past_nums:
            if num + num_sum == total:
                return True
        past_nums.append(num)
    return False


def daily_problem_bonus(list_of_nums, total):
    """ Technically a single-pass using known pairs detected in original to sum to k, but still checks target array, being n^2"""
    targets = []
    for num in list_of_nums:
        if num in targets:
            return True
        candidate = total - num
        if candidate not in targets:
            targets.append(candidate)
    return False
        

def daily_problem_bonus_2(list_of_nums, total):
    """ Merge-sort approach, that is technically an unknown number of passes since array is split and merged concurrently"""
    midpoint = round(len(list_of_nums)/2)
    if len(list_of_nums) <= 2:
        return sum(list_of_nums) == total
    else:
        return  __splitter__(list_of_nums[:midpoint], total), __splitter__(list_of_nums[midpoint:], total)

def __splitter__(list_of_nums, total):
    midpoint = round(len(list_of_nums)/2)
    if len(list_of_nums) <= 2:
        return list_of_nums[0],False,total, list_of_nums[1], False,total
    else:
        return __splitter__(list_of_nums[:midpoint], total), __splitter__(list_of_nums[midpoint:], total)

def __merger__(list_l,list_l_total, list_l_bool, list_r, list_r_total,list_r_bool, total):
    for item in list_l:
        if total - item in list_r:
            return list_l.extend(list_r),total, True
    return list_l.extend(list_r),total,False or list_l_bool or list_r_bool
   
if __name__ == "__main__":
    print(daily_problem_bonus_2([10,15,3, 7], 17))