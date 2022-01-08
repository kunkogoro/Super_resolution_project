import tinify
tinify.key = "QspRfwXgXbQ7zD9cwVt3bBCfNpFJCCxz"

def compressor(a):
	source = tinify.from_file(a)
	return "OK"


