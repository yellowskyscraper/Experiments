from roundwared import server

def test (actual, expected):
	if actual != expected:
		print "FAILED"

test(server.stream_url_to_name("http://scapesaudio.dyndns.org:8000/stream127960004754.mp3"),
	"stream127960004754")
test(server.stream_url_to_name("http://scapesaudio.dyndns.org:8000/lobby.mp3"),
	"lobby")

