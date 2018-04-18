(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('ContentComment', ContentComment);

    ContentComment.$inject = ['$resource', 'DateUtils'];

    function ContentComment ($resource, DateUtils) {
        var resourceUrl =  'api/content-comments/:id';

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
