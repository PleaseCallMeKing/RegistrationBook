(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('ContentCommentReply', ContentCommentReply);

    ContentCommentReply.$inject = ['$resource', 'DateUtils'];

    function ContentCommentReply ($resource, DateUtils) {
        var resourceUrl =  'api/content-comment-replies/:id';

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
