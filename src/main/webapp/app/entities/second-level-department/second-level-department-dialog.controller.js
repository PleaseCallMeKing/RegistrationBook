(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('SecondLevelDepartmentDialogController', SecondLevelDepartmentDialogController);

    SecondLevelDepartmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SecondLevelDepartment', 'ThirdLevelDepartment'];

    function SecondLevelDepartmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SecondLevelDepartment, ThirdLevelDepartment) {
        var vm = this;

        vm.secondLevelDepartment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.thirdleveldepartments = ThirdLevelDepartment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.secondLevelDepartment.id !== null) {
                SecondLevelDepartment.update(vm.secondLevelDepartment, onSaveSuccess, onSaveError);
            } else {
                SecondLevelDepartment.save(vm.secondLevelDepartment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:secondLevelDepartmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
