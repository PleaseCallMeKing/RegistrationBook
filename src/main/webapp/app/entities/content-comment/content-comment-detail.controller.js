(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .controller('ContentCommentDetailController', ContentCommentDetailController);

    ContentCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContentComment', 'ContentCommentReply'];

    function ContentCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, ContentComment, ContentCommentReply) {
        var vm = this;

        vm.contentComment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('registrationBookApp:contentCommentUpdate', function(event, result) {
            vm.contentComment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
