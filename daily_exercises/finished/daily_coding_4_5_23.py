""" Daily problem: Given a string s and an integer k,
    break up the string into multiple lines such that
    each line is of length k or less. You must not break
    words accross lines. Each line must have the maximum
    number of words possible. If no solution return null.
    words have no spaces in input, and output has one space
    between each word.
    
    example: :the quick brown fox jumps over the lazy dog with k=10 gives:
    the quick, brown fox, jumps over, the lazy dog"""


def daily_problem(s,k):
    """ Daily problem: greedy approach of running words together until running over k,
        then appending to another list"""
    words = s.split(' ')
    words.reverse()
    results = []
    temp_str = ""
    while len(words) > 0:
        next_word = words.pop()
        if len(next_word) + len(temp_str) > k:
            results.append(temp_str)
            temp_str = ""
        if len(next_word) > k:
            return None
        else:
            temp_str += next_word + " "
    return results

if __name__ == "__main__":
    print(daily_problem("the quick brown fox jumps over the lazy dog", 10))