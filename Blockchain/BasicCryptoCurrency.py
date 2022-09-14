import time
from datetime import datetime
import random as random
import math as math

class bad_rsa_key:
  
  def gcd(self,a,b):
    if b == 0:
      return a
    else:
      return self.gcd(b, a%b)
  
  def find_p_q(self,e):
     found = False
     while(not found):
       p = self.pick_rand_prime(e + 1,100)
       q = self.pick_rand_prime(e + 1,100)
       if self.gcd(e,(p-1)*(q-1)) == 1:
         print("found: p=" + str(p) + " q=" + str(q) + " e(private key)=" + str(e))
         found = True
     return p,q
    
  def inverse_mod_brute_force(self, a,b):
    index = 1
    while(a*index % b != 1):
      index = index + 1
    return index 
      
  def find_d(self,p,q):
    print("Calculating d")
    d = self.inverse_mod_brute_force(self.e,(p-1)*(q-1))
    print("Found d(public key)=" + str(d))
    return d
    
  def pick_rand_prime(self,start, cap):
    prime = False
    while(not prime):
      candidate = random.randint(start,cap);
      prime = True
      for factors in range(2,candidate):
        if candidate % factors == 0:
          prime = False
    return candidate


  def __init__(self):
      self.e = self.pick_rand_prime(2,100)
      self.p,self.q = self.find_p_q(self.e)
      self.d = self.find_d(self.p, self.q)

  def encrypt_char(self, datum):
    n = self.p * self.q
    return chr(math.ceil(((ord(datum) ** self.e) % n)))

  def decrypt_char(self, datum):
    n = self.p * self.q
    converted_num = math.ceil(((ord(datum) ** self.d) % n))
    converted = chr(converted_num)
    print(str(datum) + " is " + str(ord(datum))+ " Decrypts to: " + str(converted_num) + " or " + converted)
    return chr(math.ceil(((ord(datum) ** self.d) % n)))

class BadHash:
  def __init__(self):
    self.seed = time.time()
    self.hash_func = bad_rsa_key().encrypt_char
    
  def hash(self, data):
    curr_hash = self.seed
    for char in str(data):
      curr_hash %= ord(self.hash_func(char))
    return curr_hash

class Transaction_Block():
  def __init__(self, sender,receiver, amount, hash_func, hash_seed, prev_hash, is_genesis, signer, wallets):
    if is_genesis:
      self.wallets = str(wallets)
      self.hash = hash_seed
      self.is_genesis = True
      self.amt = amount
      self.sndr = sender
      self.rcpt = receiver
      self.prev_hash = hash_seed
      self.signer = signer
      self.valid = True
      self.hash_func = hash_func
    else:
      self.wallets = str(wallets)
      self.hash = prev_hash
      self.is_genesis = False
      self.hash_seed = hash_seed
      self.prev_hash = prev_hash
      self.signer = signer
      self.hash_func = hash_func
      self.rcpt = receiver
      self.sndr = sender
      self.amt = amount
      self.valid = True
      
  def compute_hash(self,signer):
    curr_hash = self.prev_hash
    item = self.sndr + self.rcpt + str(self.amt)
    curr_hash %= self.hash_func.hash(item)
    self.hash = curr_hash
    self.signer = signer
  
  def calculate_hash(self, signer):
    curr_hash = self.prev_hash
    item = self.sndr + self.rcpt + str(self.amt) + str(self.wallets)
    curr_hash %= self.hash_func.hash(item)
    return curr_hash

  def consensus(self, signer):
    if self.is_genesis:
      return True
    self.valid = False
    expected_hash = self.calculate_hash(signer)
    if(expected_hash == self.hash):
      self.valid = True
    return self.valid

class Ledger:
  def __init__(self, blocks, wallets):
    self.wallets = wallets  
    self.blocks = [Transaction_Block("Nobody", "Nobody", 0.0, BadHash(), time.time(), time.time(), True, "nobody", wallets)]

  def consensus(self):
    for item in self.blocks:
      if not item.valid:
        return False
    return True
        
  def rehash(self, signer):
    for bl in self.blocks:
      bl.compute_hash(signer)
		
  def add_block(self, block, signer):
    prev_hash = self.blocks[-1].hash
    self.blocks.append(block)
    self.blocks[-1].prev_hash = prev_hash
    self.blocks[-1].compute_hash(signer)


  def create_block(self,sender, receiver, amount, signer):
	  new_block = Transaction_Block(sender, receiver, amount, BadHash(), time.time(), time.time(), False, signer, self.wallets)
	  self.add_block(new_block, signer)

  def rem_block(self, block_num, signer):
	  if self.blocks[block_num].is_genesis:
		  print("reserved, cannot modify")
	  else:
		  del self.blocks[block_num]
		  self.rehash(signer)
		
  def overwrite_block(self, block_num, new_snd,new_rc,new_amt):
    if self.blocks[block_num].is_genesis:
      print("reserved, cannot modify")
    else:
      self.blocks[block_num].sndr = new_snd
      self.blocks[block_num].rcpt = new_rc
      self.blocks[block_num].amt = new_amt

  def show(self):
    block_index = 0
    valid = True
    for wallet in self.wallets.keys():
      print(wallet + "\'s wallet: " + str(self.wallets[wallet]) + " Garbage Coin(s)")
    print("Ledger:")
    for item in self.blocks:
      print("   block " + str(block_index) + ":")
      print("      Sender: " + item.sndr +",")
      print("      Recipient: " + item.rcpt +",")
      print("      Amount: " + str(item.amt) +",")
      print("      Hash: " + str(item.hash))
      print("      Previous Hash: " + str(item.prev_hash))
      print("      Wallets: " + item.wallets)
      print("      Last signed by: " + item.signer + ", " + str(datetime.fromtimestamp(time.time())))
      if item.consensus(item.signer):
        print("VALID BLOCK")
      else:
        valid = False
        print("INVALID BLOCK: expected " + str(item.hash) +  " hash got " + str(item.calculate_hash(item.signer)) + " hash")
        print("")
      block_index += 1
    if valid:
      print("Ledger Consensus: VALID")
    else:
      print("Ledger Consensus: CORRUPTED")
    print("")
    print("")

class Node:
  def __init__(self, ledger, name):
    self.ledger = ledger
    self.name = name
    self.wallet = ledger.wallets[self.name]
    self.key = bad_rsa_key()
    self.COMP = 1.0

  def menu(self):
    print("Weclome " + self.name + "!")
    self.ledger.show()
    print("1. Add a new Transaction to the chain")
    print("2. Remove a Transaction from the chain")
    print("3. Illegally edit a Block's data")
    print("4. Exit")
    user_input = input("Select an option: ")
    if user_input == "1":
      recip = input("Input recipient name: ")
      amt = input("Input amount: ")
      self.ledger.wallets[self.name] -= float(amt)
      self.ledger.wallets[recip] += float(amt)
      self.ledger.create_block(self.name, recip, amt, self.name)
      if self.ledger.consensus():
        self.compensate()
      self.menu()
    elif user_input == "2":
      self.ledger.show()
      index_rem = input("Input index of block to remove: ")
      self.ledger.rem_block(int(index_rem),self.name)
      self.menu()
    elif user_input == "3":
      self.ledger.show()
      index_edit = input("Input index of block to modify: ")
      new_amt = input("Input amount of garbage coin to change this transaction to: ")
      new_rcp = input("Enter new recipient name: ")
      new_snd = input("Enter new sender name: ")
      self.ledger.overwrite_block(int(index_edit),new_snd, new_rcp, new_amt )
      self.menu()
    elif user_input == "4":
      exit()
    else:		
      print("Invalid selection, retry")
      self.menu()
      
  def compensate(self):
    print("You mined: " + str(self.COMP) + " Garbage Coin doing consensus")
    self.ledger.wallets[self.name] += self.COMP

if __name__ == "__main__":
  testWallets = {"you" : 1.0, "dimwit" : 1.0}
  testLedger = Ledger([], testWallets)
  print("Added another user called dimwit to pay")
  user = Node(testLedger, "you")
  user.menu()
						
