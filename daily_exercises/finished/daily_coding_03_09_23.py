""" Daily problem: The edit distance between two strings refers to the minimum number
    of character insertions, deletions and substitutions required to change one string
    into another.
    For example: kitten to sitting is 3: sub k for s, e for i and append g 
    
    Given two strings find the edit distance"""


def daily_problem(string_a, string_b):
    """ just greedily adds one for each unmatching character,
    and adds one for each difference in length"""
    subs = 0
    inserts_deletes = 0
    if len(string_a) != len(string_b):
        inserts_deletes = abs(len(string_a) - len(string_b))
    for char_a, char_b in zip(string_a, string_b):
        if char_a is not char_b:
            subs += 1
    return subs + inserts_deletes

if __name__ == "__main__":
    print(daily_problem("kitten", "sitting"))