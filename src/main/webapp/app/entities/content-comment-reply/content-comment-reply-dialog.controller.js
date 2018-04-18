(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ContentCommentReplyDialogController', ContentCommentReplyDialogController);

    ContentCommentReplyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContentCommentReply', 'ContentComment'];

    function ContentCommentReplyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContentCommentReply, ContentComment) {
        var vm = this;

        vm.contentCommentReply = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.contentcomments = ContentComment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contentCommentReply.id !== null) {
                ContentCommentReply.update(vm.contentCommentReply, onSaveSuccess, onSaveError);
            } else {
                ContentCommentReply.save(vm.contentCommentReply, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registrationBookApp:contentCommentReplyUpdate', result);
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
