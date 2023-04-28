""" Daily problem: Given a dictionary of words and a string made up of those
    words(no spaces), return the original sentence in a list. If there is more than on possible reconsruction,
    return any of them, if there is no possible reconstruction, then return null
    
    For example: given 'the','quick', 'brown', 'fox' and "thequickbrownfox" should return ['the','quick','brown','fox']
    given 'bed', 'bedbath', 'beyond', and "bedbathandbeyond" should return either ['bed','bath','and','beyond'] or ['bedbath','and','beyond']"""


def daily_problem(words, sample):
    """ Simple consumer-parser I make from scratch"""
    current_words = []
    current_word = ""
    for char in sample:
        current_word += char
        if len(current_words) >= 1:
            # If we find an overlap of two words, we pop off the old one and add the new one
            overlap = current_words[-1] + current_word
            if overlap in words:
                current_words.pop()
                current_words.append(overlap)
                current_word = ""
        if current_word in words:
            # If a word is found we append it and clear the working string!
            current_words.append(current_word)
            current_word = ""
    if len(current_words) == 0 or len(current_word) > 0:
        return None # Returns None as expected if all parsings did not match
    return current_words

if __name__ == "__main__":
    print(daily_problem(['the','quick','brown','fox'], "thequickbrownfox"))
    print(daily_problem(['bed','bedbath','and', 'beyond'], "bedbathandbeyond"))