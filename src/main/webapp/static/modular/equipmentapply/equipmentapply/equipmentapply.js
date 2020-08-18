/**
 * 会议设备申请审批管理初始化
 */
var Equipmentapply = {
    id: "EquipmentapplyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Equipmentapply.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            // {title: '会议', field: 'mettingid', visible: true, align: 'center', valign: 'middle'},
            {title: '会议', field: 'meetDetail', visible: true, align: 'center', valign: 'middle'},
            {title: '设备', field: 'equipments', visible: true, align: 'center', valign: 'middle'},
            // {title: '设备', field: 'equipmentid', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Equipmentapply.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Equipmentapply.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会议设备申请审批
 */
Equipmentapply.openAddEquipmentapply = function () {
    var index = layer.open({
        type: 2,
        title: '添加会议设备申请审批',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/equipmentapply/equipmentapply_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会议设备申请审批详情
 */
Equipmentapply.openEquipmentapplyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会议设备申请审批详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/equipmentapply/equipmentapply_update/' + Equipmentapply.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会议设备申请审批
 */
Equipmentapply.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/equipmentapply/delete", function (data) {
            Feng.success("删除成功!");
            Equipmentapply.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("equipmentapplyId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会议设备申请审批列表
 */
Equipmentapply.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Equipmentapply.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Equipmentapply.initColumn();
    var table = new BSTable(Equipmentapply.id, "/equipmentapply/list", defaultColunms);
    table.setPaginationType("client");
    Equipmentapply.table = table.init(deal);
});

function deal(data) {
    data.map(item => {
        let status = item.status
        if (status === 0){
            item.status = '<p style="color: #0d8ddb">审核中</p>'
        }else if(status === 1){
            item.status = '<p style="color: #00B83F">通过审核</p>'
        }else {
            item.status = '<p style="color: red">审核被驳回</p>'
        }
    })

    return data;
}
