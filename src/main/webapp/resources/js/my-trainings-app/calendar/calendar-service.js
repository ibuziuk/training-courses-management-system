"use strict";

calendar.factory('calendarService', ['contextRoot', '$http', 'moment', function (contextRoot, $http, moment) {
	var service = {},
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

	service.trainerGet = function (callback) {
		$http.get('/rest/calendar/trainer')
				.then(function(response){callback(response);}, function () {
					console.log('error');
				});
	};

	service.visitorGet = function (callback) {
		$http.get('/rest/calendar/visitor')
				.then(function (response) {callback(response);} , function () {
					console.log('error');
				});
	};

	service.trainerParsing = function (data) {
		var eventsTrainer = [];
		var type,
				today = moment(moment()).unix(),
				deletable = false;

		for (var j = 0; j < data.length; j++) {
			if (data[j].approved) {
				if (service.isFuture(today, data[j].date)) {
					type = 'info';
					deletable = true;
					eventsTrainer.push(event(data[j].title, type, data[j].date, service.endsAt(data[j].date, data[j].duration), deletable));
				} else {
					type = 'important';
					deletable = false;
					eventsTrainer.push(event(data[j].title, type, data[j].date, service.endsAt(data[j].date, data[j].duration), deletable));
				}
			} else {
				type = 'inverse';
				deletable = true;
				eventsTrainer.push(event(data[j].title, type, data[j].date, service.endsAt(data[j].date, data[j].duration), deletable));
			}
		}
		return eventsTrainer;
	};

	service.visitorParsing = function (data) {
		var type,
				today = moment(moment()).unix(),
				deletable = false,

		eventsVisitor = [];

		for (var i = 0; i < data.length; i++) {
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'success';
					deletable = true;
					eventsVisitor.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
				} else {
					type = 'warning';
					deletable = false;
					eventsVisitor.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
				}
			}
		}
		return eventsVisitor;
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