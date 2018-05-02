(function() {
    'use strict';

    angular
        .module('registrationBookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('secondLevelDepartment');
                    $translatePartialLoader.addPart('registrationBook');
                    return $translate.refresh();
                }]
            }
        })


            // .state('home.new', {
        //     parent: 'home',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/second-level-department/second-level-department-dialog.html',
        //             controller: 'SecondLevelDepartmentDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         deptName: null,
        //                         deptEnglishName: null,
        //                         createdBy: null,
        //                         createdDate: null,
        //                         lastModifiedBy: null,
        //                         lastModifiedDate: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('home', null, { reload: 'home' });
        //         }, function() {
        //             $state.go('home');
        //         });
        //     }]
        // })

    }
})();
