(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('content-comment-reply', {
            parent: 'entity',
            url: '/content-comment-reply?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.contentCommentReply.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content-comment-reply/content-comment-replies.html',
                    controller: 'ContentCommentReplyController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contentCommentReply');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('content-comment-reply-detail', {
            parent: 'content-comment-reply',
            url: '/content-comment-reply/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.contentCommentReply.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content-comment-reply/content-comment-reply-detail.html',
                    controller: 'ContentCommentReplyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contentCommentReply');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContentCommentReply', function($stateParams, ContentCommentReply) {
                    return ContentCommentReply.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'content-comment-reply',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('content-comment-reply-detail.edit', {
            parent: 'content-comment-reply-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment-reply/content-comment-reply-dialog.html',
                    controller: 'ContentCommentReplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContentCommentReply', function(ContentCommentReply) {
                            return ContentCommentReply.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content-comment-reply.new', {
            parent: 'content-comment-reply',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment-reply/content-comment-reply-dialog.html',
                    controller: 'ContentCommentReplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contentId: null,
                                contentName: null,
                                userId: null,
                                userName: null,
                                content: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('content-comment-reply', null, { reload: 'content-comment-reply' });
                }, function() {
                    $state.go('content-comment-reply');
                });
            }]
        })
        .state('content-comment-reply.edit', {
            parent: 'content-comment-reply',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment-reply/content-comment-reply-dialog.html',
                    controller: 'ContentCommentReplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContentCommentReply', function(ContentCommentReply) {
                            return ContentCommentReply.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content-comment-reply', null, { reload: 'content-comment-reply' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content-comment-reply.delete', {
            parent: 'content-comment-reply',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment-reply/content-comment-reply-delete-dialog.html',
                    controller: 'ContentCommentReplyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContentCommentReply', function(ContentCommentReply) {
                            return ContentCommentReply.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content-comment-reply', null, { reload: 'content-comment-reply' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
