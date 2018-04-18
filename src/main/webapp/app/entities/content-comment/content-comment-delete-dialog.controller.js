(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ContentCommentDeleteController',ContentCommentDeleteController);

    ContentCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContentComment'];

    function ContentCommentDeleteController($uibModalInstance, entity, ContentComment) {
        var vm = this;

        vm.contentComment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContentComment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
