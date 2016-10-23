var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var EventEmitter = require('events');

const PORT = 8080;

var emitter = new EventEmitter();

function pushEvent(computerName, message) {
	emitter.emit('event', computerName, message);
}

function waitForEvent(callback) {
	function handle(computerName, message) {
		callback({computerName: computerName, message: message});
		emitter.removeListener('event', handle);
	}
	emitter.on('event', handle);
}

app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

app.get('/', (req, res) => {
	res.set('Content-Type', 'text/html');
	res.write('<html><body><h1>Hello</h1>');
	res.write('<p>You may want to be here: </p>');
	res.write('<a href="/test">Click</a>');
	res.end('</body></html>');
});

app.post('/send', (req, res) => {
	var computerName = req.body.computerName;
	var message = req.body.message;
	pushEvent(computerName, message);
	res.end('Sup');
});

app.post('/poll', (req, res) => {
	waitForEvent((event) => {
		res.end(JSON.stringify(event));
	});
});

app.get('/test', (req, res) => {
	res.sendFile(__dirname + '/demo.html');
});

app.listen(port, () => {
	console.log('MC-Robots started on port ' + PORT);
})