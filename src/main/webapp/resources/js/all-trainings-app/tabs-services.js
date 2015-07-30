'use strict';

angular.module('tabsServices', [])
		.factory('tableService', [function () {
			var service = {},
					event = function (title, date, time, location, trainerName, placesOccupied, placesAll, tags) {
						return {
							title: title,
							date: date,
							time: time,
							location: location,
							trainerName: trainerName,
							places: placesOccupied + '/' + placesAll,
							tags: tags
						};
					},
					tag = function (title, color) {
						return {
							title: '#' + title,
							color: color
						};
					};

			service.parsing = function (data) {
				var events = [],
						tags = [],
						i,
						j;
				for (i = 0; i < data.length; i++) {
					for (j = 0; j < data[i].tags.length; j++) {
						tags.push(tag(data[i].tags[j].name, data[i].tags[j].color));
					}
					events.push(event(data[i].title, data[i].dateOnString, data[i].time, data[i].location, data[i].trainer.firstName + ' ' + data[i].trainer.lastName, data[i].visitors.length, data[i].maxVisitorsCount, tags));
					tags = [];
				}

				return events;
			};

			return service;
		}]);