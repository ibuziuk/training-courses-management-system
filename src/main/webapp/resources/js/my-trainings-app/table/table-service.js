"use strict";

table.factory('tableService', ['moment', function (moment) {
	var service = [],
			event = function (type, title, date, location, trainerName, places, tags) {
				return {
					type: type,
					title: title,
					date: date,
					location: location,
					trainerName: trainerName,
					places: places,
					tags: tags
				};
			},
			today = moment(moment()).unix(),
			type;

	service.parse = function (data) {
		for (var i = 0; i < data.length; i++) {
			if (service.isFuture(today, data[i].date)) {
				type = 'success';
				service.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
			} else {
				type = 'warning';
				service.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
			}
		}
		return service;
	};

	service.isFuture = function (today, trainingDay) {
		trainingDay /= 1000;
		return today < trainingDay;
	};

	return service;
}]);