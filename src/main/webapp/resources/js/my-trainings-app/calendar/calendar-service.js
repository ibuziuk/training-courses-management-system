"use strict";

calendar.factory('calendarService', ['contextRoot', '$http', 'moment', function (contextRoot, $http, moment) {
	var service = [],
			event = function (title, type, startsAt, endsAt, deletable, editable, draggable, resizable, incrementsBadgeTotal) {
				return {
					title: title,
					type: type,
					startsAt: startsAt,
					endsAt: endsAt,
					deletable: deletable,
					editable: false,
					draggable: false,
					resizable: false,
					incrementsBadgeTotal: true
				};
			};

	 // TODO promises and $q!!!

	service.trainerGet = function () {
		var trainerEvents = [];
		$http.get('/rest/calendar/trainer')
				.then(function (response) {
					trainerEvents = service.trainerParsing(response.data);
					return trainerEvents;
				}, function () {
					return 'error';
				});
	};

	service.visitorGet = function () {
		var visitorEvents = [];
		$http.get('/rest/calendar/visitor')
				.then(function (response) {
					visitorEvents = service.visitorParsing(response.data)
					return visitorEvents;
				}, function () {

				});
	};

	service.trainerParsing = function (data) {
		var type,
				today = moment(moment()).unix(),
				deletable = false;

		for (var j = 0; j < data.length; j++) {
			if (data[j].approved) {
				if (service.isFuture(today, data[j].date)) {
					type = 'info';
					deletable = true;
					service.push(event(data[j].title, type, data[j].date, service.endsAt(data[j].date, data[j].duration), deletable));
				} else {
					type = 'important';
					deletable = false;
					service.push(event(data[j].title, type, data[j].date, service.endsAt(data[j].date, data[j].duration), deletable));
				}
			} else {
				type = 'inverse';
				deletable = true;
				service.push(event(data[j].title, type, data[j].date, service.endsAt(data[j].date, data[j].duration), deletable));
			}
		}

		return service;
	};

	service.visitorParsing = function (data) {
		var type,
				today = moment(moment()).unix(),
				deletable = false;

		for (var i = 0; i < data.length; i++) {
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'success';
					deletable = true;
					service.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
				} else {
					type = 'warning';
					deletable = false;
					service.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
				}
			}
		}

		return service;
	};

	service.isFuture = function (today, trainingDay) {
		trainingDay /= 1000;
		return today < trainingDay;
	};

	service.endsAt = function (startsAt, duration) {
		return startsAt + duration * 3600000;
	};

	return service;
}]);