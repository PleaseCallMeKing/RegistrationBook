(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('RegistrationBook', RegistrationBook);

    RegistrationBook.$inject = ['$resource', 'DateUtils'];

    function RegistrationBook ($resource, DateUtils) {
        var resourceUrl =  'api/registration-books/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.visitDateTime = DateUtils.convertDateTimeFromServer(data.visitDateTime);
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
