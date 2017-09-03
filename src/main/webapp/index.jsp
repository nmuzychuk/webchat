<html ng-app="webchat">
<head>
    <title>Web Chat</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.5/angular.min.js"></script>

    <script>
        angular.module('webchat', [])
            .constant('config', {
                chatEndpoint: 'ws://localhost:8080/webchat/chat/'
            })
            .controller('AppController', ['config', '$scope', function (config, $scope) {
                var chat = this;

                chat.roomOptions = [
                    {name: 'general', value: 'general'},
                    {name: 'java', value: 'java'},
                    {name: 'html', value: 'html'}
                ];

                chat.messages = [];

                function onMessageReceived(e) {
                    $scope.$apply(function () {
                        chat.messages.push(JSON.parse(e.data));
                    });
                }

                chat.toggleView = true;

                function toggleView() {
                    chat.toggleView = chat.toggleView === true ? false : true;
                }

                chat.join = function () {
                    chat.user = chat.joinForm.user;
                    chat.webSocket = new WebSocket(config.chatEndpoint + chat.user.room);
                    chat.webSocket.onmessage = onMessageReceived;
                    toggleView();

                };

                chat.send = function () {
                    chat.messageForm.message.sender = chat.user.username;
                    chat.webSocket.send(JSON.stringify(chat.messageForm.message));
                    chat.messageForm = {}
                };

                chat.leave = function () {
                    chat.webSocket.close();
                    chat.user = {};
                    chat.messages = [];
                    toggleView();
                };

            }]);
    </script>
</head>
<body>
<div ng-controller="AppController as chat" class="container">
    <div class="page-header text-center">
        <h1>Web Chat</h1>
    </div>

    <div ng-show="chat.toggleView" class="row">
        <div class="col-md-offset-4 col-md-4">
            <form name="chat.joinForm" ng-submit="chat.join()">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input id="username" name="username" class="form-control"
                           ng-model="chat.joinForm.user.username" required/>
                </div>

                <div class="form-group">
                    <label for="room">Room</label>
                    <select id="room" name="room" class="form-control"
                            ng-model="chat.joinForm.user.room"
                            ng-options="option.value as option.name for option in chat.roomOptions"
                            ng-init="chat.joinForm.user.room = chat.roomOptions[0].value">
                    </select>
                </div>

                <button class="btn btn-primary">Join</button>
            </form>
        </div>
    </div>

    <div ng-hide="chat.toggleView" class="row">
        <div class="col-md-offset-3 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h2># {{ chat.user.room }}</h2>
                </div>

                <div class="panel-body">
                    <div class="alert alert-info" role="alert"
                         ng-repeat="message in chat.messages">
                        {{ message.sender }}: <b>"{{ message.body }}"</b>
                        <small>{{ message.time}}</small>
                    </div>
                </div>

                <div class="panel-footer">
                    <form name="chat.messageForm" ng-submit="chat.send()">

                        <div class="form-group">
                            <label for="body" class="sr-only">Message</label>
                            <input id="body" name="body" class="form-control"
                                   ng-model="chat.messageForm.message.body"/>
                        </div>
                    </form>
                    <button class="btn btn-sm btn-primary"
                            ng-click="chat.send()">Send
                    </button>
                    <button class="btn btn-sm btn-warning pull-right"
                            ng-click="chat.leave()">Leave
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
