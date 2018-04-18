(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('content-comment', {
            parent: 'entity',
            url: '/content-comment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.contentComment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content-comment/content-comments.html',
                    controller: 'ContentCommentController',
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
                    $translatePartialLoader.addPart('contentComment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('content-comment-detail', {
            parent: 'content-comment',
            url: '/content-comment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.contentComment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/content-comment/content-comment-detail.html',
                    controller: 'ContentCommentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contentComment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContentComment', function($stateParams, ContentComment) {
                    return ContentComment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'content-comment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('content-comment-detail.edit', {
            parent: 'content-comment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment/content-comment-dialog.html',
                    controller: 'ContentCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContentComment', function(ContentComment) {
                            return ContentComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content-comment.new', {
            parent: 'content-comment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment/content-comment-dialog.html',
                    controller: 'ContentCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contentType: null,
                                contentId: null,
                                contentName: null,
                                floorNumber: null,
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
                    $state.go('content-comment', null, { reload: 'content-comment' });
                }, function() {
                    $state.go('content-comment');
                });
            }]
        })
        .state('content-comment.edit', {
            parent: 'content-comment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment/content-comment-dialog.html',
                    controller: 'ContentCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContentComment', function(ContentComment) {
                            return ContentComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content-comment', null, { reload: 'content-comment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('content-comment.delete', {
            parent: 'content-comment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/content-comment/content-comment-delete-dialog.html',
                    controller: 'ContentCommentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContentComment', function(ContentComment) {
                            return ContentComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('content-comment', null, { reload: 'content-comment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
