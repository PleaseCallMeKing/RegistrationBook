(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ConsultRoomDetailController', ConsultRoomDetailController);

    ConsultRoomDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ConsultRoom', 'ThirdLevelDepartment'];

    function ConsultRoomDetailController($scope, $rootScope, $stateParams, previousState, entity, ConsultRoom, ThirdLevelDepartment) {
        var vm = this;

        vm.consultRoom = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:consultRoomUpdate', function(event, result) {
            vm.consultRoom = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
