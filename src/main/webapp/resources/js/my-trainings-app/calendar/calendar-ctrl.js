'use strict';

angular.module('calendar', [])
		.controller('calendarController', ['$scope', '$window', 'calendarService', function ($scope, $window, calendarService) {
			$scope.url = {
				all: 'rest/calendar'
			};
			$scope.loading = true;

			var vm = this,
					now = moment(),
					events = [];
			moment.locale('ru');

			calendarService.get($scope.url.all)
					.then(function (data) {
						events = calendarService.parse(data.data);

						vm.events = events;
						vm.calendarDay = now;
						vm.calendarView = 'month';

						vm.eventClicked = function (event) {
							$window.open(event.url);
						};

						vm.eventEdited = function (event) {

						};

						vm.eventDeleted = function (event) {

						};

						vm.eventTimesChanged = function (event) {

						};

						vm.toggle = function ($event, field, event) {
							$event.preventDefault();
							$event.stopPropagation();
							event[field] = !event[field];
						};
					}, function (data) {
						console.error(data.status + ': ' + data.statusText + ' (' + data.config.url + ')');
					})
					.finally(function () {
						$scope.loading = false;
					});
		}]);