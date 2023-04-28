""" Daily problem: Write an algorithm to justify text. Given a sequence
    of words and an integer line length k, return a list of strings which
    represents each line fully justified.
    
    More specifically, you should have as many words as possible in each line.
    There should be at least a space between each word. Pad extra spaces when necessary
    so that each line has exactly length k. Spaces should be distributed as equally as possible,
    with the extra spaces, if any distributed starting from the left.
    
    If you can only fit one word on a line. Then you should pad the right hand side with spaces.
    
    For example given ["the","quick","brown","fox","jumps","over","the","lazy","dog"] and k=16
    you should return:
    ["the  quick brown" #one space left,"fox  jumps  over" # 2 spaces each,
    "the    lazy    dog"# 4 spaces each """


def daily_problem(words, k):
    """ I opted to greedily create a new array closest to k in size,
        then pad each word on the right side until we hit length k"""
    result = []
    while len(words) > 0:    
        curr_length = 0
        greedy_selection = []
        for word in words:
            if curr_length + len(word) + len(greedy_selection) <= k: 
                # checking current length is < k when one space is added
                greedy_selection.append(word)
                curr_length += len(word)
        difference = k - curr_length
        padding_index = 0
        for greed in greedy_selection:
            words.remove(greed)
        while difference > 0:
            greedy_selection[padding_index] += " "
            difference -= 1
            padding_index += 1
            padding_index %= len(greedy_selection)
        result.append("".join(greedy_selection))
    return result
        
    

if __name__ == "__main__":
    print(daily_problem(["the","quick","brown","fox","jumps","over","the","lazy","dog"],16))
