'use strict';

angular.module('uiApp')
    .controller('MainCtrl', function ($scope,$http) {

        $scope.init=function(){
            $scope.area=buildArea()
        }

        function areaToCells(area){
            var cells = []
            for(var x=0; x < area.length; x++)
                for (var y=0; y < area[x].length; y++)
                    cells.push({"x":x+1,"y":y+1,"status":area[x][y].status})
            return cells
        }

        $scope.next=function(){
            $http.post("/computeNext", areaToCells($scope.area)).success(
                function(data, status, headers, config) {
                    for(var i=0 ; i< data.length; i++)
                        $scope.area[data[i].x-1][data[i].y-1].status=data[i].status
                }
            )
        }

        $scope.autoNext=function(){
            if($scope.isRunning)
                clearInterval($scope.running)
            else{
                $scope.running=setInterval($scope.next,$scope.period*1000)
            }
            $scope.isRunning=!$scope.isRunning

        }



        var initializers = {
            blank:function(area){
                return area
            },
            glider: function (area) {
                area[area.length - 1][1].status = 'alive'
                area[area.length - 2][2].status = 'alive'
                area[area.length - 3][0].status = 'alive'
                area[area.length - 3][1].status = 'alive'
                area[area.length - 3][2].status = 'alive'
                return area
            }
        }

        $scope.initializers = function(){
            var _inits=[]
            for(var i in initializers)
                _inits.push(i)
            return _inits
        }()

        $scope.initializer=$scope.initializers[1]
        $scope.rows = 10
        $scope.columns = 10
        $scope.period=0.5
        $scope.running={}
        $scope.isRunning=false

        $scope.area = buildArea()

        function buildArea() {
            var _area = []

            for (var i = 0; i < $scope.rows; i++) {
                _area[i] = []
                for (var j = 0; j < $scope.columns; j++)
                    _area[i][j] = {status: ""}
            }

            return initializers[$scope.initializer].apply(this, [_area])
        }


    });
