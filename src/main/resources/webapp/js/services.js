(function () {
    'use strict';

    /* Services */

    var services = angular.module('nyvrs.services', ['ngResource']);

    services.factory('Settings', function($resource) {
        return $resource('../nyvrs/api/settings');
    });

}());
