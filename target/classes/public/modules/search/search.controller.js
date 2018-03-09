/**
 * Created by Pan on 2016/9/11.
 */
app.controller("SearchController", function (SearchService, $scope) {
    $scope.search = function () {
        var param = $scope.param;
        $scope.search = function () {
            var param = $scope.param;
            SearchService.searchR(param).then(function (data) {
                console.info(data.resultData)
                if (data.resultData != null) {
                    angular.forEach(data.resultData, function (data, index, array) {
                        //data等价于array[index]
                        console.log(data + '=' + array[index]);
                    });

                }
            })
            SearchService.searchP(param).then(function (data) {
                console.info(data.resultData)
                if (data.resultData != null) {
                    angular.forEach(data.resultData, function (data, index, array) {
                        //data等价于array[index]
                        console.log(data + '=' + array[index]);
                    });

                }
            })
            SearchService.searchF(param).then(function (data) {
                console.info(data.resultData)
                if (data.resultData != null) {
                    angular.forEach(data.resultData, function (data, index, array) {
                        //data等价于array[index]
                        console.log(data + '=' + array[index]);
                    });

                }
            })
            SearchService.searchA(param).then(function (data) {
                console.info(data.resultData)
                if (data.resultData != null) {
                    $scope.articles = data.resultData;
                    console.log(data.resultData);
                    angular.forEach(data.resultData, function (data, index, array) {
                        //data等价于array[index]
                        //console.log(data+'='+array[index]);
                    });

                }
            })
            $(".search-div").css("display", "block");
        }
    }
});