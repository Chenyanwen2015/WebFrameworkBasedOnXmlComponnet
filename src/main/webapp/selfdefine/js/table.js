var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tradeList').bootstrapTable({
            url: url,     //请求后台的URL（*）
            dataType: "json",
            method: 'post',           //请求方式（*）
            striped: true,           //是否显示行间隔色
            cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,          //是否显示分页（*）
            sortable: true,           //是否启用排序
            sortOrder: "asc",          //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
            strictSearch: true,
            clickToSelect: true,        //是否启用点击选中行
            uniqueId: "id",           //每一行的唯一标识，一般为主键列
            cardView: false,          //是否显示详细视图
            detailView: false,          //是否显示父子表
            search:true,
            columns:columns,
            onClickRow:function(row, $element){
                console.log(row);
                console.log($element);
                alert(row);
                alert($element);
            }
        });
    };

    oTableInit.queryParams = function (params) {
        var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,  //页面大小
            offset: params.offset //页码
            /*
            sdate: $("#stratTime").val(),
            edate: $("#endTime").val(),
            sellerid: $("#sellerid").val(),
            orderid: $("#orderid").val(),
            CardNumber: $("#CardNumber").val(),
            maxrows: params.limit,
            pageindex: params.pageNumber,
            portid: $("#portid").val(),
            CardNumber: $("#CardNumber").val(),
            tradetype: $('input:radio[name="tradetype"]:checked').val(),
            success: $('input:radio[name="success"]:checked').val(),*/
        };
        return temp;
    };
    return oTableInit;
};

$(document).ready(function(){
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    /* var oButtonInit = new ButtonInit();
     oButtonInit.Init(); */
});

