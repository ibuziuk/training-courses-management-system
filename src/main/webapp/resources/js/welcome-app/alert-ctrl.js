'use strict';

var alertCtrl = angular.module('alertCtrl', []);

alertCtrl.controller('alertCtrl', ['$scope', '$sce', '$http', '$timeout', function ($scope, $sce, $http, $timeout) {
	var arr = 0;

	var poll = function () {
		$http.get('rest/notification/' + arr).then(
				function (obj) {
					console.log(obj.data);
					arr += obj.data.length;
					$timeout(poll, 10000);
				}
		).catch(function (err) {
					console.log(err.statusText);
					poll();
				});
	};

	poll();

	$scope.alerts = [
		{
			type: 'info',
			msg: ' Your training $$trainingName$$ is aproved. ',
			time: '2016-07-21 18:20',
			link: '../training.html',
			trainingName: 'Assembler: best-practice',
			id: 1
		},
		{
			type: 'danger',
			msg: ' You have got training $$trainingName$$ in an hour! ',
			time: '2016-07-21 15:10',
			link: '../training.html',
			trainingName: 'C++',
			id: 2
		},
		{
			type: 'success',
			msg: ' You have got training $$trainingName$$ in a day! ',
			time: '2016-07-21 12:07',
			link: '../training.html',
			trainingName: 'Scala and Go',
			id: 3
		},
		{
			type: 'warning',
			msg: ' User $$userName$$ sent you new $$trainingName$$ to approve. ',
			time: '2016-07-21 12:07',
			link: '../training.html',
			trainingName: 'Scala and Go',
			userName: 'Yana Yaroshevich',
			userLink: '../user.html',
			id: 4
		}
	];

	$scope.messages = $scope.alerts.map(function (alert) {
		var s = '<b><time>' + alert.time + '</time></b>' + alert.msg;
		if (alert.type == 'warning') {
			s = s.replace('$$userName$$', '<a href="' + alert.userLink + '"class="alert-link">' + alert.userName + '</a>');
		}
		s = $sce.trustAsHtml(s.replace('$$trainingName$$', '<a href="' + alert.link + '"class="alert-link">' + alert.trainingName + '</a>'));
		return s;
	});

	$scope.getAlerts = function () {
		//TODO
	};

	$scope.closeAlert = function (index) {
		$http.delete('rest/notification/' + alerts[$index].id).then(function () {
			arr -= 1;
		}).catch(function (err) {
			console.log(err.statusText);
		});
		$scope.alerts.splice(index, 1);
	};
}]);