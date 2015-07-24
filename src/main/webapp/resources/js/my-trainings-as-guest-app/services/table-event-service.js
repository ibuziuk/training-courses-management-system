angular.module('myTrainingsApp').factory('tableEvent', [function() {
	"use strict";

	var service = [],
			event = function(title, date, location, trainerName, places, tags) {
				return {
					title: title,
					date: date,
					location: location,
					trainerName: trainerName,
					places: places,
					tags: tags
				};
			};

	service.parse = function(data) {
		for (var i = 0; i < data.length; i++) {
			console.log((new Date(data[i].date)).toString());
			service.push(event(data[i].title, (new Date(data[i].date)).toString(), data[i].location, data[i].trainer.name, data[i].maxVisitorsCount, []));
		}
		return service;
	};

	return service;
}]);