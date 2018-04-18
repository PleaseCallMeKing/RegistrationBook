(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('ThirdLevelDepartment', ThirdLevelDepartment);

    ThirdLevelDepartment.$inject = ['$resource', 'DateUtils'];

    function ThirdLevelDepartment ($resource, DateUtils) {
        var resourceUrl =  'api/third-level-departments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
