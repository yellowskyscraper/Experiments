var app = require('express').createServer()
  , io = require('socket.io').listen(app);

var sockets = [];

app.listen(8080);

app.get('/', function (req, res) {
  res.sendfile(__dirname + '/index.html');
});

io.sockets.on('connection', function (socket) {
	sockets.push(socket);
	send_div(socket);
});

function send_div(socket){

	socket.on('hide_me', function(data){
		rec_hide_div(data.div, socket);
	});

	socket.on('show_me', function(data){
		rec_show_div(data.div, socket);
	});
}

function rec_hide_div(div){
	for(var i=0; i < sockets.length; i++){
		
		sockets[i].emit('hide', { div: div });
		
	}
}

function rec_show_div(div){
	for(var i=0; i < sockets.length; i++){
		
		sockets[i].emit('show', { div: div });
		
	}
}