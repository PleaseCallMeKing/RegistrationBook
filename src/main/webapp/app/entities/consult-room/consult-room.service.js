(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('ConsultRoom', ConsultRoom);

    ConsultRoom.$inject = ['$resource', 'DateUtils'];

    function ConsultRoom ($resource, DateUtils) {
        var resourceUrl =  'api/consult-rooms/:id';

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
