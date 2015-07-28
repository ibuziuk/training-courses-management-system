'use strict';

calendar.factory('calendarService', ['contextRoot', '$http', 'moment', function (contextRoot, $http, moment) {
	var service = {},
			event = function (title, type, startsAt, endsAt, deletable) {
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
			},
			today = moment(moment()).unix();

	service.trainerParsing = function (data) {
		var eventsTrainer = [],
				type,
				deletable = false,
				i;

		for (i = 0; i < data.length; i++) {
			if (data[i].approved) {
				if (service.isFuture(today, data[i].date)) {
					type = 'info';
					deletable = true;
					eventsTrainer.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
				} else {
					type = 'important';
					deletable = false;
					eventsTrainer.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
				}
			} else {
				type = 'inverse';
				deletable = true;
				eventsTrainer.push(event(data[i].title, type, data[i].date, service.endsAt(data[i].date, data[i].duration), deletable));
			}
		}
		return eventsTrainer;
	};

	service.visitorParsing = function (data) {
		var type,
				deletable = false,
				eventsVisitor = [],
				i;

		for (i = 0; i < data.length; i++) {
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