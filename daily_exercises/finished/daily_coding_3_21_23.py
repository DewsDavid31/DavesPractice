""" Daily problem: Given a list of integers S
    and a target number k. Return a subset of S
    that sums to k, if you cannot return null.
    Assume positive integers and integers can be
    duplicate.
    Example: [12,1,61,5,9,2] k=24 should return
    [12,9,2,1]"""


def daily_problem(s,k):
    """ Daily problem: exhaustive search appending each
        possible combination by iterating over a for loop of size n*n,
        ignoring combinations of too many duplicates and same sets."""
    combos = []
    for element in s:
        combos.append([element])
    for _ in range(len(s)):
        temp_combos = combos[:]
        for combo in temp_combos:
            for second in s:
                temp_combo = combo[:]
                if temp_combo.count(second) < s.count(second):
                    temp_combo.append(second)
                    temp_combo.sort()
                    if sum(temp_combo) == k:
                        return temp_combo
                    if temp_combo not in combos:
                        combos.append(temp_combo)
    return None

if __name__ == "__main__":
    print(daily_problem([12,1,61,5,9,2], 24))