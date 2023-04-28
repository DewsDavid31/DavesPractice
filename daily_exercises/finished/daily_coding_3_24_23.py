""" Daily problem: using the function rand5, that returns 1-5 randomly
    with uniform probability, implement rand7 for 1-7 inclusive"""
import random

def daily_problem():
    """ just a simple modulo, that returns if over 7,
      subtracts one to allow 1 to be selected"""
    curr_num = rand5() + rand5()
    if curr_num > 7:
        return curr_num % 7
    return curr_num


def rand5():
    return random.randint(1,5)


if __name__ == "__main__":
    print(daily_problem())