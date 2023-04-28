""" Daily problem: Run-length encoding is a fast and simple way to encode strings.
    Implement run-length encoding and decoding. You can assume the string to only
    be alphabetic. You can assume the string to be decoded is valid.
    
    Example: AAAABBBCCDAA to 4A3B2C1D2A"""


def daily_problem(translating_string):
    """ Checking if in run-length, then converting it"""
    result = ""
    is_runlength = not str.isalpha(translating_string)
    if is_runlength: 
        for char_index in range(0,len(translating_string) - 1,2):
            result += translating_string[char_index + 1] * int(translating_string[char_index])
        return result 
    else: # is in raw form, logs each char in array for count found and char found, joins at the end
        chars = []
        counts = []
        prev = ''
        for char in translating_string:
            if char is not prev:
                chars.append(char)
                counts.append(1)
                prev = char
            else:
                counts[-1] += 1
        return "".join([str(x) + y for x,y in zip(counts,chars)])
if __name__ == "__main__":
    print(daily_problem("AAAABBBCCDAA"))
    print(daily_problem(daily_problem("4A3B2C1D2A")))
    print(daily_problem(daily_problem("AAAABBBCCDAA")))
    print(daily_problem("4A3B2C1D2A"))