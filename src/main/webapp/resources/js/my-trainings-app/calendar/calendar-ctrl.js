"use strict";

var calendar = angular.module('calendar', []);

calendar.controller('calendarController', ['$scope', '$http', '$location', 'calendarService', 'contextRoot', 'moment', function ($scope, $http, $location, calendarService, contextRoot, moment) {
	var vm = this,
			now = moment();

	moment.locale('en');
	vm.calendarDay = now;
	vm.calendarView = 'month';

	$http.get('/rest/calendar').then(function (response) {
		vm.events = calendarService.parse(response.data);
	}, function () {

	});

	vm.eventClicked = function (event) {

	};

	vm.eventEdited = function (event) {

	};

	vm.eventDeleted = function (event) {
		var answer = confirm("Do you really want delete this training?");

		if (answer) {
			var x = vm.events.indexOf(event);
			vm.events.splice(x, 1);
		}
	};

	vm.eventTimesChanged = function (event) {

	};

	vm.toggle = function ($event, field, event) {
		$event.preventDefault();
		$event.stopPropagation();
		event[field] = !event[field];
	};
}]);