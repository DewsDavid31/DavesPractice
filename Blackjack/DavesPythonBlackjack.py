
import random

#  I added a new comment to test pushing in get
#....
#new comment by vim
class card:
    suites = "♠♥♦♣"
    suite = 0
    value = 0
    owner = "dealer"
    found = ""

    def __init__(self,suite,value):
        self.suite = suite
        self.value = value
        if(suite > 3):
            self.found = self.suites[0]
        self.found = self.suites[suite]

    def print(self):
        return str(self.value) + "" + self.found

    def deal(self,newOwner):
            self.owner = newOwner

class deck:
    size = 52
    cards = [card(1,1),card(1,2)]
    busted = False
    dealt = 0
    def dealersDeal(self):
        dealersValue = 0
        while(dealersValue < 17):
            selection = random.randint(0, len(self.cards) - 1)
            while self.cards[selection].owner != "dealer":
                selection = random.randint(0, len(self.cards) - 1)
            self.cards[selection].owner = "dealt"
            print(self.cards[selection].print())
            dealersValue += self.cards[selection].value
            self.dealt+= 1
            if self.dealt >= 50:
                self.shuffle()
        print("-" + str(dealersValue) + " points from dealer-")
        if dealersValue > self.tally() and dealersValue < 21:
            print("->>>Dealer wins<<<-\n")
        elif dealersValue == self.tally() and dealersValue < 21:
            print("-Dealer tips his hat, you win-\n")
        elif dealersValue > 21:
            print("<<@@-Dealer busts you win-@@>>")
        else:
            print("<<@@-You win with " + str(self.tally()) + " points-@@>>\n")
            self.shuffle()

    def tally(self):
        points = 0
        for index in self.cards:
            if index.owner == "player":
                points += index.value
        return points

    def build(self):
        for index in range(1, 13,1):
            for index2 in range(1,3,1):
                self.cards.append(card(index2,index))

    def draw(self):
        selection = random.randint(0,len(self.cards) - 1)
        while self.cards[selection].owner != "dealer":
            selection =  random.randint(0,len(self.cards) - 1)
        self.cards[selection].owner = "player"
        self.dealt += 1
        if self.dealt >= 50:
            self.shuffle()
        return self.cards[selection]
    def deal(self):
        print(self.draw().print())
        print(self.draw().print())
        print(str(self.tally()) + " points\n")
        self.getState()
    def shuffle(self):
        self.dealt = 0
        print("-dealer shuffles the deck-")
        for x in self.cards:
            x.owner = "dealer"
        self.points = 0
    def hit(self):
        if(not self.busted):
            print("-you hit-")
            print(self.draw().print())
            print(str(self.tally()) + " points")
        self.getState()
    def getState(self):
        total = self.tally()
        if(total > 21):
            print("-bust-\n")
            self.busted = True
        elif(total == 21):
            print("-running hot at 21!-")

    def stay(self):
        print("-You stay with " + str(self.tally()) + " points-\n")
    def menu(self):
        option = input("1)hit \n2)stay\n")
        if option == str(1):
            self.hit()
            if self.busted:
                self.busted = False
                self.shuffle()
                self.deal()
                self.menu()
            else:
                self.menu()

        elif option == str(2):
            self.stay()
            self.dealersDeal()
            self.menu()
        else:
            print("-Not a valid input-\n")
            self.menu()
def main():
    deck1 = deck()
    deck1.build()
    deck1.deal()
    deck1.menu()

if __name__ == "__main__":
    main()


