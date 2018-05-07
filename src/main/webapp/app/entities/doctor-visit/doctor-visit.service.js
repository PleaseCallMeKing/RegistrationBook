(function() {
    'use strict';
    angular
        .module('registrationBookApp')
        .factory('DoctorVisit', DoctorVisit);

    DoctorVisit.$inject = ['$resource', 'DateUtils'];

    function DoctorVisit ($resource, DateUtils) {
        var resourceUrl =  'api/doctor-visits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.visitData = DateUtils.convertDateTimeFromServer(data.visitData);
                        data.visitEndData = DateUtils.convertDateTimeFromServer(data.visitEndData);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }


    angular
        .module('registrationBookApp')
        .factory('DoctorDoctorVisit', DoctorDoctorVisit);
    DoctorDoctorVisit.$inject = ['$resource', 'DateUtils'];

    function DoctorDoctorVisit ($resource, DateUtils) {
        var resourceUrl =  'api/doctor/:doctorId/doctor-visits';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.visitData = DateUtils.convertDateTimeFromServer(data.visitData);
                        data.visitEndData = DateUtils.convertDateTimeFromServer(data.visitEndData);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            }
        });
    }

})();
