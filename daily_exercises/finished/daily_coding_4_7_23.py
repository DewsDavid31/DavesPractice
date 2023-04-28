""" Daily problem: Implement a file sequencing alg
    for two computers over a low bandwith network. What
    if we know the files in the two computers are mostly
    the same?"""
import random


class Computer:
    def __init__(self, name, files):
        self.files = files
        self.ack = 0
        self.name = name

    def receive(self, msg):
        if msg != "ack" and msg not in self.files:
            self.files.append(msg)
            self.files.sort()
            print(self.name  + ": ", end='')
            print(self.files)

    def send(self, msg, recipient, drop_rate):
        print(self.name  + ": sending " + msg)
        self.ack += 1
        while recipient.ack < self.ack:
            drop_range = [x >= drop_rate for x  in range(100)]
            drop = random.choice(drop_range)
            if not drop:
                recipient.receive(msg)
                recipient.send("ack", self, drop_rate)
            else:
                print(self.name + " dropped packet " + str(self.ack))



def daily_problem():
    """ Daily problem simply using old TCP handshake and sorting to transfer files
        over a noisy connection"""
    computer_a = Computer("computer a", ["file_01", "file_02"])
    computer_b = Computer("computer b", ["file_01", "file_03"])
    computer_a.send("file_02", computer_b, 50)
    computer_b.send("file_03", computer_a, 50)
    return computer_a.files == computer_b.files




if __name__ == "__main__":
    print("files are in order: " + str(daily_problem()))