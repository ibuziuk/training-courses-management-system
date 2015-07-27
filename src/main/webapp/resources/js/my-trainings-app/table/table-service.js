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

	service.parse = function (data, who) {
		if (who === 'visitor') {
			for (var i = 0; i < data.length; i++) {
				if (data[i].approved) {
					if (service.isFuture(today, data[i].date)) {
						type = 'success';
						service.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
					} else {
						type = 'warning';
						service.push(event(type, data[i].title, moment(data[i].date).format('DD MMMM YYYY'), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
					}
				}
			}
		} else {
			for (var j = 0; j < data.length; j++) {
				if (data[j].approved) {
					if (service.isFuture(today, data[j].date)) {
						type = 'info';
						service.push(event(type, data[j].title, moment(data[j].date).format('DD MMMM YYYY'), data[j].location, data[j].trainer.name, data[j].maxVisitorsCount, []));
					} else {
						type = 'danger';
						service.push(event(type, data[j].title, moment(data[j].date).format('DD MMMM YYYY'), data[j].location, data[j].trainer.name, data[j].maxVisitorsCount, []));
					}
				} else {
					type = 'active';
					service.push(event(type, data[j].title, moment(data[j].date).format('DD MMMM YYYY'), data[j].location, data[j].trainer.name, data[j].maxVisitorsCount, []));
				}
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