'use strict';

angular.module('book', [])
		.controller('bookController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
			$scope.book = {
				trainingId: {},
				visitors: [],
				lessons: []
			};


			/*
			* lesson in book.lessons
			* lesson.date
			*
			* visitor in book.visitors
			* visitor.login
			* absence in visitor.absences
			* */


			$scope.loading = true;

			var url = $location.absUrl(),
					tmp = url.split('/');

			$scope.trainingId = tmp[tmp.length - 1];

			$http.get('rest/attendance/' + $scope.trainingId)
					.then(function (data) {
						$scope.book.visitors = data.data.users;
						$scope.book.lessons = data.data.lessons;
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);