class Nueron:
	def __init__(self, weight, learning_rate, threshold):
		self.weight = weight
		self.learning_rate = learning_rate
		self.threshold = threshold

	def train(self, input_x, input_y, expected_bool):
		actual = self.classify(input_x, input_y)
		if expected_bool is not actual and actual is False:
			self.weight -= self.learning_rate * self.weight
		else:
			self.weight += self.learning_rate * self.weight


	def classify(self, input_x, input_y):
		dividing_line = self.weight * input_x
		above_class = input_y > dividing_line - self.threshold
		return above_class

	def back_propogate(self, error):
		print("not supported yet, for now just a linear binary perceptron")
		
if __name__ == "__main__":
	test_nueron = Nueron(5.0, 0.3, 0.01)
	print("training values that follow 3.14x or greater, learning rate of 1/3, threshold of 0.1")
	test_data_x = [0,1,2,10]
	test_data_y = [0,3.14,6.28,31.4]
	train_bools = [True, True, True, True]
	while(True):
		for index in range(len(test_data_x)):
			test_nueron.train(test_data_x[index], test_data_y[index], train_bools[index])
		print("current function: " + str(test_nueron.weight) + "x")
		x = float(input("Insert a x value to classify: "))
		y = float(input("Insert a y value to classify: "))
		print("Nueron predicts: " + str(test_nueron.classify(x,y)))
		bool = input("Insert Capitalized boolean that is the real answer: ")
		test_data_x.append(x)
		test_data_y.append(y)
		train_bools.append(bool)
		test_nueron.train(x,y,bool)
	
	
