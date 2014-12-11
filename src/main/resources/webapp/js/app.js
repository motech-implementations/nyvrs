(function () {
    'use strict';

    /* App Module */

    angular.module('nyvrs', ['motech-dashboard', 'nyvrs.controllers', 'nyvrs.services', 'ngCookies', 'ui.bootstrap']).config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/nyvrs/settings', {templateUrl: '../nyvrs/resources/partials/settings.html', controller: 'SettingsCtrl'});
        }]);
}());
