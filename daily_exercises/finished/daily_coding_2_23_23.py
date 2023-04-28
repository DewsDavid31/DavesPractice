""" Daily problem: Suppose we represent our file system in the following manner:
dir
    subdir1
    subdir2
        file.ext
    the directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing file.ext
    The string: dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext
    represents:
        dir
            subdir1
                    file1.ext
                    subsubdir1
            subdir2
                    subsubdir2
                        file2.ext
                        
    ...
    We are interested in finding the longest(num of chars)absolute path within our file system.
    For example: in the second example the longest is dir/subdir2/subsubdir2/file2.ext with a length of 32
    
    Given a string fo the previous format, return the length of the longest absolute path to a file in the abstracted
    file system. If there is no file in the system return 0.

    Note: the name of a file must have a period and an extension, the names of directorys never contain periods"""


def daily_problem(dir_string):
    """ Daily problem"""
    absolutes = []
    curr_layer = 0
    next_layer = 0
    curr_absolute = []
    # cutting path string into tabs and items
    for item in dir_string.split(r'\n'):
        print(f"{absolutes},{curr_layer},{next_layer}")
        next_layer = item.count(r'\t')
        # Getting layer in tree using tab count
        if "." in item:
            # if its a file, it uses ., thus we pop from our current path, 
            # preventing going deeper, add to paths list
            curr_absolute.append(item.replace(r'\t',''))
            absolutes.append("/".join(curr_absolute))
            curr_absolute.pop() 
        elif len(curr_absolute) == 0:
            # If we are at the root we push the directory onto our path list
            curr_absolute.append(item.replace(r'\t',''))
            absolutes.append("/".join(curr_absolute))
            curr_layer = next_layer
        elif next_layer > curr_layer:
            # If the next item is less deep, we pop off our current directory
            #  the difference to get to its parent, and append it
            curr_absolute.append(item.replace(r'\t',''))
            absolutes.append("/".join(curr_absolute))
            curr_layer = next_layer
        else:
            for _ in range((curr_layer - next_layer) + 1):
                # we pop backwards atleast once, and the difference to
                # traverse to the next item
                curr_absolute.pop()
            curr_absolute.append(item.replace(r'\t',''))
            absolutes.append("/".join(curr_absolute))
            curr_layer = next_layer
    maximum_len = 0
    for paths in absolutes:
        # finding maximum by comparing lengths
        if len(paths) > maximum_len:
            maximum_len = len(paths)
    return maximum_len


if __name__ == "__main__":
    print(daily_problem(r"dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"))