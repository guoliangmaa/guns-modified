/**
 * 设备类型管理管理初始化
 */
var Equiptypes = {
    id: "EquiptypesTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Equiptypes.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '设备id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '设备类型名称', field: 'typename', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Equiptypes.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Equiptypes.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加设备类型管理
 */
Equiptypes.openAddEquiptypes = function () {
    var index = layer.open({
        type: 2,
        title: '添加设备类型管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/equiptypes/equiptypes_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看设备类型管理详情
 */
Equiptypes.openEquiptypesDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '设备类型管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/equiptypes/equiptypes_update/' + Equiptypes.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除设备类型管理
 */
Equiptypes.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/equiptypes/delete", function (data) {
            Feng.success("删除成功!");
            Equiptypes.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("equiptypesId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询设备类型管理列表
 */
Equiptypes.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Equiptypes.table.refresh({query: queryData});
};

$(function () {
    console.log(2333)
    var defaultColunms = Equiptypes.initColumn();
    var table = new BSTable(Equiptypes.id, "/equiptypes/list", defaultColunms);
    table.setPaginationType("client");
    Equiptypes.table = table.init();


    console.log(table.columns)
});
