(function () {

    'use strict';

    /* Controllers */

    var controllers = angular.module('nyvrs.controllers', []);

    controllers.controller('SettingsCtrl', function ($scope, Settings) {
        $scope.settings = Settings.get();

        $scope.submit = function() {
            $scope.settings.$save(function() {
                motechAlert('nyvrs.settings.success.saved', 'server.saved');
            }, function() {
                motechAlert('nyvrs.settings.error.saved', 'server.error');
            });
        };

    });

}());
