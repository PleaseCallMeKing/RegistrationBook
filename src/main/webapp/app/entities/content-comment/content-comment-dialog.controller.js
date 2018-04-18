(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ContentCommentDialogController', ContentCommentDialogController);

    ContentCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContentComment', 'ContentCommentReply'];

    function ContentCommentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContentComment, ContentCommentReply) {
        var vm = this;

        vm.contentComment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.contentcommentreplies = ContentCommentReply.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contentComment.id !== null) {
                ContentComment.update(vm.contentComment, onSaveSuccess, onSaveError);
            } else {
                ContentComment.save(vm.contentComment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:contentCommentUpdate', result);
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
