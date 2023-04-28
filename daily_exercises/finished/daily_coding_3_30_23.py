""" Daily problem: Given a function that generates
    perfectly random numbers between 1 and k inclusive
    where k is the input. Write a function that shuffles
    a deck of cards represented by an array using only
    swaps"""
import random

def daily_problem(deck, rand_func):
    """ Daily problem: has a list of booleans for each unswapped item,
        chooses swap elements randomly """
    modified = [False for x in deck]
    while False in modified:
        candidate = rand_func(len(deck))
        second_candidate = rand_func(len(deck))
        if not modified[candidate - 1] and second_candidate != candidate:
            swap(deck, candidate - 1, second_candidate - 1)
            modified[candidate - 1] = True
            modified[second_candidate - 1] = True
    return deck

def swap(list,a,b):
    """ Simple swap"""
    temp = list[a]
    list[a] = list[b]
    list[b] = temp

def test_func(k):
    """ simple randint, not perfect, but works"""
    return random.randint(1,k)

if __name__ == "__main__":
    test_deck = [x for _ in list(range(1,14*4))]
    print(daily_problem(test_deck, test_func))