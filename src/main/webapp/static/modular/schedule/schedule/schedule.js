/**
 * 会议室日程查询管理初始化
 */
var Schedule = {
    id: "ScheduleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Schedule.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '会议室编号', field: 'homeid', visible: true, align: 'center', valign: 'middle'},
            {title: '占用开始时间', field: 'begintime', visible: true, align: 'center', valign: 'middle'},
            {title: '占用结束时间', field: 'endtime', visible: true, align: 'center', valign: 'middle'},
            {title: '会议状态', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Schedule.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Schedule.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会议室日程查询
 */
Schedule.openAddSchedule = function () {
    var index = layer.open({
        type: 2,
        title: '添加会议室日程查询',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/schedule/schedule_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会议室日程查询详情
 */
Schedule.openScheduleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会议室日程查询详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/schedule/schedule_update/' + Schedule.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会议室日程查询
 */
Schedule.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/schedule/delete", function (data) {
            Feng.success("删除成功!");
            Schedule.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scheduleId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会议室日程查询列表
 */
Schedule.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Schedule.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Schedule.initColumn();
    var table = new BSTable(Schedule.id, "/schedule/list", defaultColunms);
    table.setPaginationType("client");
    Schedule.table = table.init(deal);
});

function deal(data) {
    data.map(item => {
        let status = parseInt(item.status)
        if (status === 0){
            item.status = '<p style="color: #0d8ddb">会议未开始</p>'
        }else if(status === 1){
            item.status = '<p style="color: #00B83F">会议进行中</p>'
        }else {
            item.status = '<p style="color: red">会议已结束</p>'
        }
    });

    return data;
}