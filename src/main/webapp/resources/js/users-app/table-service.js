'use strict';

angular.module('table')
		.factory('tableService', ['$http', function ($http) {
			var service = {},
					user = function (firstName, lastName, login, email, role, userId) {
						return {
							name: firstName + ' ' + lastName,
							login: login,
							email: email,
							role: role,
							url: 'user/' + userId
						};
					};

			service.parsing = function (data) {
				var users = [],
						size = data.size,
						i;
				for (i = 0; i < data.users.length; i++) {
					users.push(user(data.users[i].firstName,
							data.users[i].lastName,
							data.users[i].login,
							data.users[i].email,
							data.users[i].roleForView,
							data.users[i].userId));
				}

				return {
					size: size,
					list: users
				};
			};

			service.createUrl = function (url, config) {
				var ret = [];
				ret.push('pageNumber=' + config.page);
				ret.push('pageSize=' + config.count);
				for (var param in config.sorting) {
					ret.push('sortType=' + param);
					ret.push('order=' + config.sorting[param]);
				}
				return url + '?' + ret.join("&");
			};

			service.createSearchUrl = function (url, config) {
				var ret = [];
				ret.push('pageNumber=' + config.page);
				ret.push('pageSize=' + config.count);
				ret.push('searchType=' + config.searching.type);
				ret.push('value=' + config.searching.value);
				return url + '?' + ret.join("&");
			};

			service.getAll = function (url, config) {
				return $http.get(service.createUrl(url, config))
			};

			service.getSearch = function (url, config) {
				return $http.get(service.createSearchUrl(url, config))
			};

			return service;
		}]);