'use strict';

var notificationCtrl = angular.module('notificationCtrl', []);

notificationCtrl.controller('notificationCtrl', ['$scope', '$sce', '$http', '$timeout', function ($scope, $sce, $http, $timeout) {
	var id = 0;
	var types = ['info', 'danger', 'success', 'warning'];
	$scope.notifications = [];

	var getType = function (type) {
		if (type == 5) {
			return types[0];
		}
	};

	var getLink = function (id) {
		return 'training/' + id;
	};

	var getMsg = function (type) {
		if (type == 5) {
			return 'The training $$trainingName$$ has been created. ';
		}
	};

	var getCoolDate = function (date) {
		var str = '';
		str += date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear() + ' ';
		str += date.getHours() + ':' + date.getMinutes() + ' ';
		return str;
	};

	var createNotifications = function (arr) {
		var notifications = [];
		for (var k in arr) {
			var notification = {};
			notification.type = getType(arr[k].type);
			notification.id = arr[k].notificationId;
			notification.trainingName = arr[k].trainingName;
			notification.time = getCoolDate(new Date(arr[k].date));
			notification.msg = getMsg(arr[k].type);
			notification.link = getLink(arr[k].trainingId);
			notifications.push(notification);
		}
		return notifications;
	};

	var poll = function () {
		$http.get('rest/notification/' + id).then(
				function (obj) {
					for (var k in obj.data) {
						if (obj.data[k].notificationId > id) {
							id = obj.data[k].notificationId;
						}
					}
					$scope.notifications = $scope.notifications.concat(createNotifications(obj.data));

					$scope.messages = $scope.notifications.map(function (notification) {
						var s = '<b><time>' + notification.time + '</time></b>' + notification.msg;
						s = $sce.trustAsHtml(s.replace('$$trainingName$$', '<a href="' + notification.link + '"class="notification-link">' + notification.trainingName + '</a>'));
						return s;
					});

					$timeout(poll, 10000);
				}
		).catch(function (err) {
					console.log(err.statusText);
					poll();
				});
	};

	poll();


	$scope.closeNotification = function (index) {
		$http.delete('rest/notification/' + notifications[index].id).then(function () {
			id -= 1;
		}).catch(function (err) {
			console.log(err.statusText);
		});
		$scope.notifications.splice(index, 1);
	};
}]);