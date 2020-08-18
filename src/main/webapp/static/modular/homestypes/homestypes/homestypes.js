/**
 * 会议室类型管理管理初始化
 */
var Homestypes = {
    id: "HomestypesTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Homestypes.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '会议室类型id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '会议室类型名称', field: 'typename', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Homestypes.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Homestypes.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会议室类型管理
 */
Homestypes.openAddHomestypes = function () {
    var index = layer.open({
        type: 2,
        title: '添加会议室类型管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/homestypes/homestypes_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会议室类型管理详情
 */
Homestypes.openHomestypesDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会议室类型管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/homestypes/homestypes_update/' + Homestypes.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会议室类型管理
 */
Homestypes.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/homestypes/delete", function (data) {
            Feng.success("删除成功!");
            Homestypes.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("homestypesId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会议室类型管理列表
 */
Homestypes.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Homestypes.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Homestypes.initColumn();
    var table = new BSTable(Homestypes.id, "/homestypes/list", defaultColunms);
    table.setPaginationType("client");
    Homestypes.table = table.init();
});
