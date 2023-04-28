""" Daily problem: Given a mapping a = 1, b = 2 ... z = 26 and an encoded message, count the number of ways it can be decoded.
    For example 111 will give 3 since it can be aaa or ka or ak, assume messages are decodable, illegal words like 001 are not allowed"""


def daily_problem(word):
    """ Daily problem: mathematically, I discovered if all numbers are valid,
    you will always find Fib(x) combinations of valid mergers of numbers"""
    unmergables = 0
    all_valid_combos = fib(len(word))
    for two_step_index in range(0,len(word) - 1):
        # checks for one invalid merger of chars, removes one combination for each
        if int(word[two_step_index]) * 10 + int(word[two_step_index + 1]) > 26:
            unmergables += 1
    return all_valid_combos - unmergables

def fib(x):
    """ simple fibonnacci sequence, recursive"""
    if x == 0 or x == 1:
        return 1
    else:
        return fib(x - 1) + fib(x - 2)


if __name__ == "__main__":
    print(daily_problem("111"))
    print(daily_problem("1271"))
