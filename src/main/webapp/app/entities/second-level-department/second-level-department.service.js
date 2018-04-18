(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('SecondLevelDepartment', SecondLevelDepartment);

    SecondLevelDepartment.$inject = ['$resource', 'DateUtils'];

    function SecondLevelDepartment ($resource, DateUtils) {
        var resourceUrl =  'api/second-level-departments/:id';

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
