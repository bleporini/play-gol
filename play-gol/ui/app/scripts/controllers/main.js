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

        $scope.autoNext=function(){
                auto($scope.next)
        }

        function auto(getNext){
            if($scope.isRunning){
                clearInterval($scope.running)
                $scope.playIcon='icon-play'
            } else {
                $scope.playIcon='icon-stop'
                $scope.running=setInterval(getNext,$scope.period*1000)
            }
            $scope.isRunning=!$scope.isRunning
        }




        $scope.next=function(){
            $http.post("/computeNext", areaToCells($scope.area)).success(
                function(data, status, headers, config) {
                    placeCells(data)
                }
            )
        }

        function placeCells(cells){
            for(var i=0 ; i< cells.length; i++)
                $scope.area[cells[i].x-1][cells[i].y-1].status=cells[i].status
        }

        $scope.nextByBatch=function(){
            $http.post("/computeNextByBatch", {nbSteps:parseInt($scope.nbSteps), area:areaToCells($scope.area)}).success(
                function(data, status, headers, config) {
                    var areas=data;
                    auto(function(){
                        if(areas.length>0){
                            placeCells(areas[0])
                            areas.shift()
                        }else auto()
                        $scope.$apply()
                    })

                }
            )
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
            },
            frog:function(area){
                area[0][2].status = 'alive'
                area[1][0].status = 'alive'
                area[1][3].status = 'alive'
                area[2][0].status = 'alive'
                area[2][3].status = 'alive'
                area[3][1].status = 'alive'
                return area
            },
            glider2:function(area){
                area[0][1].status = 'alive'
                area[1][2].status = 'alive'
                area[2][0].status = 'alive'
                area[2][1].status = 'alive'
                area[2][2].status = 'alive'
                return area
            },
            "Gosper Glider Gun":function(area){

                $scope.rows=25
                $scope.columns=36

                area[4][0].status = 'alive'
                area[4][1].status = 'alive'
                area[5][0].status = 'alive'
                area[5][1].status = 'alive'
                area[2][12].status = 'alive'
                area[2][13].status = 'alive'
                area[3][11].status = 'alive'
                area[4][10].status = 'alive'
                area[5][10].status = 'alive'
                area[6][10].status = 'alive'
                area[7][11].status = 'alive'
                area[8][12].status = 'alive'
                area[8][13].status = 'alive'

                area[5][14].status = 'alive'
                area[3][15].status = 'alive'
                area[7][15].status = 'alive'
                area[4][16].status = 'alive'
                area[5][16].status = 'alive'
                area[6][16].status = 'alive'
                area[5][17].status = 'alive'

                area[4][20].status = 'alive'
                area[4][21].status = 'alive'
                area[3][20].status = 'alive'
                area[3][21].status = 'alive'
                area[2][20].status = 'alive'
                area[2][21].status = 'alive'
                area[1][22].status = 'alive'
                area[5][22].status = 'alive'
                area[0][24].status = 'alive'
                area[1][24].status = 'alive'
                area[5][24].status = 'alive'
                area[6][24].status = 'alive'

                area[2][34].status = 'alive'
                area[3][34].status = 'alive'
                area[2][35].status = 'alive'
                area[3][35].status = 'alive'

                return area
            }
        }

        $scope.initializers = function(){
            var _inits=[]
            for(var i in initializers)
                _inits.push(i)
            return _inits
        }()

        $scope.initializer=$scope.initializers[4]
        $scope.rows = 10
        $scope.columns = 50
        $scope.period=0.1
        $scope.running={}
        $scope.isRunning=false
        $scope.playIcon='icon-play'
        $scope.nbSteps=1000

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
