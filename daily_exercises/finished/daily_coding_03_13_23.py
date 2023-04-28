""" Daily problem: Given a string, find the palindrome that can be made
    by inserting the fewest number of charsters as possible in the word.
    If there are two possible words of shortest length, 
    return the alphabetically first one
    
    Example: given race, you should return ecarace since we add a minimum of 3 letters
    Example: google should return elgoogle"""


def daily_problem(word):
    """ Daily problem: spaghetti, but will return if a plaindrome by reversal
        or if not, finds common palindrome in the middle,
        removes it and adds remainder in direction that is
        higher in alphabet. very inefficient, but works fine"""
    if __is_palindrome__(word):
        return word
    partial = ""
    inner_palin = ""
    for char in word:
        partial += char
        if __is_palindrome__(partial):
            inner_palin = partial
    if ord(word[0]) < ord(word[-1]):
        curr_candidate = word
        if len(inner_palin) > 1:
             first_instance = curr_candidate.index(inner_palin)
             curr_candidate = curr_candidate[:first_instance] + curr_candidate[first_instance + len(inner_palin) - 1:]
        for rev_index in range(len(word) -1,-1,-1):
              curr_candidate += word[rev_index]
    else:
        curr_candidate = ""
        for rev_index in range(len(word) -1,0,-1):
                curr_candidate += word[rev_index]
        if len(inner_palin) > 1:
             first_instance = word.index(inner_palin)
             word = word[:first_instance] + word[first_instance + len(inner_palin) - 1:]
        curr_candidate += word
        return curr_candidate

def __is_palindrome__(word):
    reverse = ""
    for reverse_index in range(len(word) -1,-1,-1):
        reverse += word[reverse_index]
    if word == reverse:
        return True
    return False


if __name__ == "__main__":
    print(daily_problem("race"))
    print(daily_problem("google"))