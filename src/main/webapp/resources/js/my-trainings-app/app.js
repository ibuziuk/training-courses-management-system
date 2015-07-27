'use strict';

angular
		.module('myTrainingsApp', ['ngTable', 'calendar', 'table', 'ui.bootstrap', 'mwl.calendar'])
		.constant('contextRoot', document.documentElement.getAttribute('data-context-root'));