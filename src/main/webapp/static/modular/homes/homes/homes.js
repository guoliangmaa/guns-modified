/**
 * 会议室资源管理管理初始化
 */
var Homes = {
    id: "HomesTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Homes.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '房间号（名称）', field: 'homeno', visible: true, align: 'center', valign: 'middle'},
            {title: '所在地点', field: 'address', visible: true, align: 'center', valign: 'middle'},
            {title: '可容纳人数', field: 'space', visible: true, align: 'center', valign: 'middle'},
            {title: '负责人', field: 'hosterid', visible: true, align: 'center', valign: 'middle'},
            {title: '会议室类型', field: 'hometype', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Homes.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Homes.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会议室资源管理
 */
Homes.openAddHomes = function () {
    var index = layer.open({
        type: 2,
        title: '添加会议室资源管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/homes/homes_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会议室资源管理详情
 */
Homes.openHomesDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会议室资源管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/homes/homes_update/' + Homes.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会议室资源管理
 */
Homes.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/homes/delete", function (data) {
            Feng.success("删除成功!");
            Homes.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("homesId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会议室资源管理列表
 */
Homes.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Homes.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Homes.initColumn();
    var table = new BSTable(Homes.id, "/homes/list", defaultColunms);
    table.setPaginationType("client");
    Homes.table = table.init();
});
