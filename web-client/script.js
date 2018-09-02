var socket;
var isRegistered;
var messageBox;
var socket = new WebSocket("ws://localhost:8080/coursechat");

function Message(content) {
  var COMMAND_TYPE = 'COMMAND'
  var MESSAGE_TYPE = 'COMMON_MESSAGE'
  this.content = content;
  this.type = content.startsWith("/") ? COMMAND_TYPE : MESSAGE_TYPE
  if (this.type === COMMAND_TYPE) {
    this.commandArgs = content.split(" ", 4);
  }
}

function addLine(content) {
  messageBox.value = messageBox.value + "\n" + content;
}

socket.onopen = function() {
  messageBox.value = 'Connected';
};

socket.onclose = function(event) {
  if (event.wasClean) {
    console.log("Connection closed");
  } else {
    console.log("Connection lost");
  }
  console.log("Code: " + event.code + " cause: " + event.reason);
};

socket.onmessage = function(event) {
  var message = JSON.parse(event.data);
  if (message.type == "CONFIRMATION") {
    isRegistered = true;
    addLine("Successfully registered as " + message.username)
  } else {
    addLine(message.username + ': ' + message.content);
  }
};

socket.onerror = function(error) {
  console.log("Error: " + error.message);
};


window.onload = () => {
  isRegistered = false;
  messageBox = document.getElementById("messageBox");
  var chatInput = document.getElementById("input");
  chatInput.addEventListener("keypress", event => {
    if (event.key == "Enter") {
      var content = chatInput.value;
      chatInput.value = "";
      addLine(content);
      if (content.startsWith("/register") === isRegistered) {
        addLine("Wrong action.");
      } else {
        var message = new Message(content);
        socket.send(JSON.stringify(message));
      }
    }
  });
};
