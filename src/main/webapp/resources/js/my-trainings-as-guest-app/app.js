'use strict';

angular
		.module('myTrainingsApp', ['ngTable', 'tableController', 'calendarController', 'ui.bootstrap', 'mwl.calendar'])
		.constant('contextRoot', document.documentElement.getAttribute('data-context-root'));