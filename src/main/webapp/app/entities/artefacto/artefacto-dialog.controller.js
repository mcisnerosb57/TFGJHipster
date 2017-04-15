(function() {
    'use strict';

    angular
        .module('artefactCheckApp')
        .controller('ArtefactoDialogController', ArtefactoDialogController);

    ArtefactoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Artefacto', 'Version'];

    function ArtefactoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Artefacto, Version) {
        var vm = this;

        vm.artefacto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.versions = Version.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.artefacto.id !== null) {
                Artefacto.update(vm.artefacto, onSaveSuccess, onSaveError);
            } else {
                Artefacto.save(vm.artefacto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artefactCheckApp:artefactoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
