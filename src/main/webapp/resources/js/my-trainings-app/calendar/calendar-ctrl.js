'use strict';

angular.module('calendar', []).controller('calendarController', ['$scope', '$http', '$location', '$q', 'calendarService', 'moment', function ($scope, $http, $location, $q, calendarService, moment) {
	var vm = this,
			now = moment(),
			trainerPromise = $http.get('rest/calendar/trainer'),
			visitorPromise = $http.get('rest/calendar/visitor'),
			events = [];
	moment.locale('en');

	$q.all([trainerPromise, visitorPromise]).then(function (results) {
		events = events.concat(calendarService.trainerParsing(results[0].data));
		events = events.concat(calendarService.visitorParsing(results[1].data));

		vm.events = events;
		vm.calendarDay = now;
		vm.calendarView = 'month';

		vm.eventClicked = function (event) {

		};

		vm.eventEdited = function (event) {

		};

		vm.eventDeleted = function (event) {
			var answer = confirm('Do you really want delete this training?');

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
	});
}]);