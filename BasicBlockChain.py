class Ledger:
	def __init__(self, blocks):
		self.blocks = [new Block("genesis block", 1, 1,0)]

	def add_block(self, block):
		self.blocks.append(block)

	def show(self):
		for item in self.blocks:
			print("block["item.data"]")
	def consensus(self)
		for item in self.blocks:
			self.blocks.consensus();
class Block:
	def __init__(self, data, hash_seed, prev_hash, time):
		self.data = data
		self.hash = hash_seed
		self.prev_hash = prev_hash
		self.time = time

	def compute_hash(self, hash_func):
		for item in self.data:
			self.hash *= hash_func(item)

	def rec_consensus(self):
		print("not supported yet")


class Node:
	def add_data(ledger, data):
		ledger.add_data(data)

	def view_ledger(ledger):
		ledger.show()					
