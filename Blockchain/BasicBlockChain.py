import time
from datetime import datetime
class Block:
	def __init__(self, data, hash_func, hash_seed, prev_hash, is_genesis, timestamp):
		self.data = data
		if is_genesis:
			self.hash = hash_seed
			self.is_genesis = True
		else:
			self.hash = prev_hash
			self.is_genesis = False
		self.hash_seed = hash_seed
		self.prev_hash = prev_hash
		self.timestamp = timestamp
		self.hash_func = hash_func

	def compute_hash(self):
		curr_hash = self.prev_hash
		for item in self.data:
			curr_hash %= self.hash_func.hash(item)
		self.hash = curr_hash
		self.timestamp = time.time()

	def calculate_hash(self):
		curr_hash = self.prev_hash
		for item in self.data:
			curr_hash %= self.hash_func.hash(item)
		return curr_hash

	def consensus(self):
		if self.is_genesis and self.prev_hash == self.hash:
			return True
		expected_hash = self.calculate_hash()
		return (expected_hash == self.hash)

class BadHash:
	def __init__(self):
		self.seed = time.time()

	def hash(self, data):
		curr_hash = self.seed
		for char in str(data):
			curr_hash %= ord(char)
		return curr_hash

class Ledger:
	def __init__(self, blocks):
		self.blocks = [Block(["Reserved Genesis Block"], BadHash(), time.time(), time.time(), True, time.time())]

	def rehash(self):
		for bl in self.blocks:
			bl.compute_hash()
		
	def add_block(self, block):
		prev_hash = self.blocks[-1].hash
		self.blocks.append(block)
		self.blocks[-1].prev_hash = prev_hash
		self.blocks[-1].compute_hash()

	
	def create_block(self,data_list):
		new_block = Block(data_list, BadHash(), time.time(), time.time(), True, time.time())
		self.add_block(new_block)

	def rem_block(self, block_num):
		if self.blocks[block_num].is_genesis:
			print("reserved, cannot modify")
		else:
			self.blocks.remove(block_num)
			self.rehash()
		
	def overwrite_block(self, block_num, new_data):
		if self.blocks[block_num].is_genesis:
			print("reserved, cannot modify")
		else:
			self.blocks[block_num].data = new_data

	def show(self):
		block_index = 0
		valid = True
		print("Ledger:")
		for item in self.blocks:
			print("   block " + str(block_index) + ":")
			for datum in item.data:
				print("      Data: " + datum +",")

			print("      Hash: " + str(item.hash))
			print("      Previous Hash: " + str(item.prev_hash))
			print("      Last signed: " + str(datetime.fromtimestamp(item.timestamp)))
			if item.consensus():
				print("VALID BLOCK")
			else:
				valid = False
				print("INVALID BLOCK: expected " + str(item.hash) +  " hash got " + str(item.calculate_hash()) + " hash")
			print("")
			block_index += 1
		if valid:
			print("Ledger Consensus: VALID")
		else:
			print("Ledger Consensus: CORRUPTED")
		print("")
		print("")




class Node:
	def __init__(self, Ledger):
		self.ledger = Ledger

	def menu(self):
		print("")
		print("")
		self.ledger.show()
		print("1. Add a new Block to the chain")
		print("2. Remove a Block from the chain")
		print("3. Illegally edit a Block's data")
		print("4. Exit")
		user_input = input("Select an option: ")
		if user_input == "1":
			new_data = input("Input data with commas to split it: ")
			self.ledger.create_block(new_data.split(","))
			self.menu()
		elif user_input == "2":
			self.ledger.show()
			index_rem = input("Input index of block to remove: ")
			self.ledger.rem_block(int(index_rem))
			self.menu()
		elif user_input == "3":
			self.ledger.show()
			index_edit = input("Input index of block to modify: ")
			new_data = input("Input data to overwrite into block as comma separated values: ")
			self.ledger.overwrite_block(int(index_edit), new_data.split(","))
			self.menu()
		elif user_input == "4":
			exit()
		else:		
			print("Invalid selection, retry")
			self.menu()	

if __name__ == "__main__":
	testLedger = Ledger([])
	user = Node(testLedger)
	user.menu()
						
