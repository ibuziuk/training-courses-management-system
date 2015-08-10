'use strict';

angular.module('userApp',
		['tabs.controllers',
			'ui.bootstrap',
			'collapseCtrl',
			'languageApp',
			'statistics'
		]);
angular.module('tabs.controllers', ['tabs.service']);
angular.module('tabs.service', []);
angular.module('statistics', []);