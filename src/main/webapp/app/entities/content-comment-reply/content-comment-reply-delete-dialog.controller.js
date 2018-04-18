(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ContentCommentReplyDeleteController',ContentCommentReplyDeleteController);

    ContentCommentReplyDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContentCommentReply'];

    function ContentCommentReplyDeleteController($uibModalInstance, entity, ContentCommentReply) {
        var vm = this;

        vm.contentCommentReply = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContentCommentReply.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
