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
       p = self.pick_rand_prime(e,100)
       q = self.pick_rand_prime(e,100)
       if self.gcd(e,(p-1)*(q-1)) == 1:
         print("found: p=" + str(p) + " q=" + str(q) + " e=" + str(e))
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
    print("Found d=" + str(d))
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
    
if __name__ == "__main__":
  test_key = bad_rsa_key()
  test_string = "Hello world!"
  print("Encrypting " + test_string + "...")
  output_str = ""
  for charac in test_string:
    output_str += test_key.encrypt_char(charac) + " "
  print("Encrypted badly: " + output_str)
  output_chars = output_str.split(' ')
  reverted_str= ""
  for chars in output_chars:
    if chars == '':
      continue
    reverted_str += str(test_key.decrypt_char(chars))
  print("Decrypted badly: " + reverted_str)
    
