""" There's a staircase with N steps, and you can climb 1 or 2 steps at a time. Given N, write a function that returns the number of unique ways you can climb the staircase. The order of the steps matters.

For example, if N is 4, then there are 5 unique ways:

1, 1, 1, 1
2, 1, 1
1, 2, 1
1, 1, 2
2, 2
What if, instead of being able to climb 1 or 2 steps at a time, you could climb any number from a set of positive integers X? For example, if X = {1, 3, 5}, you could climb 1, 3, or 5 steps at a time. Generalize your function to take in X. """
def daily_problem(steps, set_of_steps):
    tree = [[x] for x in set_of_steps]
    final_tree = []
    successful = True
    while(successful):
        for branch_chosen in tree:
            successful, tree = branch(branch_chosen, set_of_steps, tree, steps)
    for branch_chosen in tree:
        if sum(branch_chosen) == steps:
            final_tree.append(branch_chosen)
    print(f"{len(final_tree)} solutions found!")
    for printed_result in final_tree:
        print(printed_result)

def branch(curr_branch, choices, tree, limit):
    success = False
    for choice in choices:
        new_branch = curr_branch[:]
        if sum(new_branch) + choice <= limit:
            new_branch.append(choice)
            success = True
            tree.append(new_branch)
    return (success, tree)

        


if __name__ == "__main__":
    print(daily_problem(4, [1,2]))