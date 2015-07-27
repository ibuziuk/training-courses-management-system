"use strict";

calendar.factory('calendarService', ['contextRoot', 'moment', function (contextRoot, moment) {
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

	service.parse = function (data) {
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