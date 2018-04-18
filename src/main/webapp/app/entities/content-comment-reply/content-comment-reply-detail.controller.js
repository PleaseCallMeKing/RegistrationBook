(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ContentCommentReplyDetailController', ContentCommentReplyDetailController);

    ContentCommentReplyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContentCommentReply', 'ContentComment'];

    function ContentCommentReplyDetailController($scope, $rootScope, $stateParams, previousState, entity, ContentCommentReply, ContentComment) {
        var vm = this;

        vm.contentCommentReply = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:contentCommentReplyUpdate', function(event, result) {
            vm.contentCommentReply = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
