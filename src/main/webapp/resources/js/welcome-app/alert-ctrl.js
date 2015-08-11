'use strict';

var notificationCtrl = angular.module('notificationCtrl', []);

notificationCtrl.controller('notificationCtrl', ['$scope', '$sce', '$http', '$timeout', function ($scope, $sce, $http, $timeout) {
	var id = 0;
	var types = ['info', 'danger', 'success', 'warning'];
	$scope.notifications = [];

	var getType = function (type) {
		if (type == 1) {
			return types[0];
		}

		if (type == 2) {
			return types[2];
		}

		if (type == 3) {
			return types[1];
		}

		if (type == 4) {
			return types[3];
		}

		if (type == 5) {
			return types[0];
		}

		if (type == 6) {
			return types[2];
		}

		if (type == 7) {
			return types[0];
		}

		if (type == 9) {
			return types[0];
		}

		if (type == 11) {
			return types[3];
		}

		if (type == 12) {
			return types[1];
		}
	};

	var getLink = function (id) {
		return 'training/' + id;
	};

	var getMsg = function (type) {
		if (type == 1) {
			return 'Somebody left a feedback on $$trainingName$$ page. ';
		}

		if (type == 2) {
			return 'The training $$trainingName$$ is approved. ';
		}

		if (type == 3) {
			return 'The training $$trainingName$$ is disapproved. ';
		}

		if (type == 4) {
			return 'The training $$trainingName$$ was edited. ';
		}

		if (type == 5) {
			return 'The training $$trainingName$$ has been created. ';
		}

		if (type == 6) {
			return 'You are in a visitors list on $$trainingName$$. ';
		}

		if (type == 7) {
			return 'The file was uploaded on $$trainingName$$. ';
		}

		if (type == 9) {
			return 'The $$trainingName$$ edition was approved. ';
		}

		if (type == 11) {
			return 'The training $$trainingName$$ is in an hour. ';
		}

		if (type == 12) {
			return 'The training $$trainingName$$ is in a day. ';
		}
	};

	var getCoolDate = function (date) {
		var str = '';
		str += date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear() + ' ';

		if (date.getMinutes() >= 10)
			str += date.getHours() + ':' + date.getMinutes() + ' ';
		else
			str += date.getHours() + ':' + '0' + date.getMinutes() + ' ';
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
		$http.delete('rest/notification/' + $scope.notifications[index].id).then(function () {
			$scope.notifications.splice(index, 1);
		}).catch(function (err) {
			console.log(err.statusText);
		});
	};
}]);