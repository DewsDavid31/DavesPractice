import time
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
		self.blocks = [Block(["a new block"], BadHash(), time.time(), time.time(), True, time.time())]
		
	def add_block(self, block):
		prev_hash = self.blocks[-1].hash
		self.blocks.append(block)
		self.blocks[-1].prev_hash = prev_hash
		self.blocks[-1].compute_hash()

	def show(self):
		valid = True
		print("Ledger:")
		for item in self.blocks:
			print("   block:")
			for datum in item.data:
				print("      Data: " + datum +",")

			print("      Hash: " + str(item.hash))
			print("      Previous Hash:" + str(item.prev_hash))
			print("      Last signed: " + str(item.timestamp) + "secs")
			if item.consensus():
				print("VALID BLOCK")
			else:
				valid = False
				print("INVALID BLOCK")
			print("")
		if valid:
			print("Ledger Consensus: VALID")
		else:
			print("Ledger Consensus: CORRUPTED")
		print("")
		print("")


class Node:
	def add_data(ledger, data):
		ledger.add_data(data)

	def view_ledger(ledger):
		ledger.show()	

if __name__ == "__main__":
	testLedger = Ledger([])
	print("created ledger!")
	testLedger.show()
	testLedger.add_block(Block(["some more data!"], BadHash(), time.time(), time.time(), False, time.time()))
	print("added a valid block...")
	testLedger.show()
	print("illegally modifying a previous block")
	testLedger.blocks[1].data = ["mwahaha I changed this!"]
	testLedger.show()
						
