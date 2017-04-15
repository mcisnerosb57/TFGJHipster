(function() {
    'use strict';

    angular
        .module('artefactCheckApp')
        .controller('ArtefactoDetailController', ArtefactoDetailController);

    ArtefactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Artefacto', 'Version'];

    function ArtefactoDetailController($scope, $rootScope, $stateParams, previousState, entity, Artefacto, Version) {
        var vm = this;

        vm.artefacto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artefactCheckApp:artefactoUpdate', function(event, result) {
            vm.artefacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
