/**
 * 预约会议申请审批管理初始化
 */
var Meetingapply = {
    id: "MeetingapplyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Meetingapply.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            // {title: '发起人ID', field: 'workerid', visible: true, align: 'center', valign: 'middle'},
            {title: '发起人account', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '会议主题', field: 'topic', visible: true, align: 'center', valign: 'middle'},
            {title: '会议说明文档地址', field: 'documentlink', visible: true, align: 'center', valign: 'middle'},
            {title: '会议室编号', field: 'homeid', visible: true, align: 'center', valign: 'middle'},
            {title: '参加会议人数', field: 'attendance', visible: true, align: 'center', valign: 'middle'},
            {title: '会议开始时间', field: 'begintime', visible: true, align: 'center', valign: 'middle'},
            {title: '会议结束时间', field: 'endtime', visible: true, align: 'center', valign: 'middle'},
            {title: '审核状态', field: 'statusid', visible: true, align: 'center', valign: 'middle'},
            {title: '申请日期', field: 'applytime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Meetingapply.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Meetingapply.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加预约会议申请审批
 */
Meetingapply.openAddMeetingapply = function () {
    var index = layer.open({
        type: 2,
        title: '添加预约会议申请审批',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/meetingapply/meetingapply_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看预约会议申请审批详情
 */
Meetingapply.openMeetingapplyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '预约会议申请审批详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/meetingapply/meetingapply_update/' + Meetingapply.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除预约会议申请审批
 */
Meetingapply.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/meetingapply/delete", function (data) {
            Feng.success("删除成功!");
            Meetingapply.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("meetingapplyId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询预约会议申请审批列表
 */
Meetingapply.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Meetingapply.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Meetingapply.initColumn();
    var table = new BSTable(Meetingapply.id, "/meetingapply/list", defaultColunms);
    table.setPaginationType("client");
    Meetingapply.table = table.init(deal);
    console.log('table     ' + Meetingapply.table)
});

function deal(data) {
    console.log(2333)
    data.map(item => console.log(item))
    return data;
}