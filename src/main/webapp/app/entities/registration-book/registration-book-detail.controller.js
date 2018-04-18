(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('RegistrationBookDetailController', RegistrationBookDetailController);

    RegistrationBookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RegistrationBook'];

    function RegistrationBookDetailController($scope, $rootScope, $stateParams, previousState, entity, RegistrationBook) {
        var vm = this;

        vm.registrationBook = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:registrationBookUpdate', function(event, result) {
            vm.registrationBook = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
