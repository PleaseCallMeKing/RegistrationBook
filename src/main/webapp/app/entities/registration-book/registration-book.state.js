(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registration-book', {
            parent: 'entity',
            url: '/registration-book?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.registrationBook.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registration-book/registration-books.html',
                    controller: 'RegistrationBookController',
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
                    $translatePartialLoader.addPart('registrationBook');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('registration-book-detail', {
            parent: 'registration-book',
            url: '/registration-book/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'registrationBookApp.registrationBook.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registration-book/registration-book-detail.html',
                    controller: 'RegistrationBookDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registrationBook');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RegistrationBook', function($stateParams, RegistrationBook) {
                    return RegistrationBook.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'registration-book',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('registration-book-detail.edit', {
            parent: 'registration-book-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-book/registration-book-dialog.html',
                    controller: 'RegistrationBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegistrationBook', function(RegistrationBook) {
                            return RegistrationBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registration-book.new', {
            parent: 'registration-book',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-book/registration-book-dialog.html',
                    controller: 'RegistrationBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                userName: null,
                                mobilePhone: null,
                                idCard: null,
                                deptId: null,
                                deptName: null,
                                doctorId: null,
                                doctorName: null,
                                consultId: null,
                                consultName: null,
                                consultNo: null,
                                visitDateTime: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('registration-book', null, { reload: 'registration-book' });
                }, function() {
                    $state.go('registration-book');
                });
            }]
        })
        .state('registration-book.edit', {
            parent: 'registration-book',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-book/registration-book-dialog.html',
                    controller: 'RegistrationBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegistrationBook', function(RegistrationBook) {
                            return RegistrationBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registration-book', null, { reload: 'registration-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registration-book.delete', {
            parent: 'registration-book',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-book/registration-book-delete-dialog.html',
                    controller: 'RegistrationBookDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RegistrationBook', function(RegistrationBook) {
                            return RegistrationBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registration-book', null, { reload: 'registration-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
