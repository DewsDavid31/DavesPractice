""" Daily problem: Implement an auto-complete system, given a query string and a set of strings, return all matching strings.
    Bonus: try processing dictionary into faster datastruct for this!
    Example, de with dog, deer and deal should return [deer, deal]"""



def daily_problem(query, library):
    """ Daily problem"""
    new_list = []
    for word in library:
        if query in word:
            new_list.append(word)
    return new_list

def mapify(library):
    """ makes a dictionary for each portion of the word paired to the full word, for access big O of n"""
    new_map = {}
    for word in library:
        curr_word = ""
        for char in word:
            curr_word += char
            if new_map.get(curr_word) is None:
                new_map[curr_word] = [word]
            else:
                new_map[curr_word].append(word)
    return new_map

def daily_problem_bonus(query, library):
    """ bonus problem using map data structure aka dictionary of partial words mapped to full"""
    new_map = mapify(library)
    return new_map[query]


if __name__ == "__main__":
    print(daily_problem("de", ["dog", "deer","deal"]))
    print(daily_problem_bonus("de", ["dog", "deer","deal"]))