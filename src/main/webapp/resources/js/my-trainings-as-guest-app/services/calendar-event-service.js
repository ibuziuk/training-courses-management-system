angular.module('myTrainingsApp').factory('calendarEvent', ['contextRoot', function(contextRoot) {
	"use strict";

	var service = [];

	var event = function(title, type, startsAt, endsAt, editable, deletable, draggable, resizable, incrementsBadgeTotal) {
		return {
			title: title,
			type: type,
			startsAt: startsAt,
			endsAt: endsAt,
			editable: false,
			deletable: false,
			draggable: false,
			resizable: false,
			incrementsBadgeTotal: true
		};
	};

	service.parse = function(data) {
		var type,
				today = new Date();

		for (var i = 0; i < data.length; i++) {
			if (data[i].approved) {
				if (service.isFuture(today.getTime(), data[i].date)) {
					type = 'success';
				} else {
					type = 'warning';
				}
				service.push(event(data[i].title, type, new Date(data[i].date), (new Date(data[i].date)).getTime() + data[i].duration * 3600000));
			}
		}

		return service;
	};

	service.isFuture = function(today, trainingDay) {
		return today < trainingDay;
	};

	return service;
}]);