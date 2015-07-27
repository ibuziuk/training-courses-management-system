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

	service.parse = function (data, who) {
		var type,
				today = moment(moment()).unix(),
				deletable = false;

		if (who === 'visitor') {
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
		} else {
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