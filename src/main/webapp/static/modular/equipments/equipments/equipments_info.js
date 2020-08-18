/**
 * 初始化会议室设备管理详情对话框
 */
var EquipmentsInfoDlg = {
    equipmentsInfoData : {}
};

/**
 * 清除数据
 */
EquipmentsInfoDlg.clearData = function() {
    this.equipmentsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EquipmentsInfoDlg.set = function(key, val) {
    this.equipmentsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EquipmentsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EquipmentsInfoDlg.close = function() {
    parent.layer.close(window.parent.Equipments.layerIndex);
}

/**
 * 收集数据
 */
EquipmentsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('equipmentid')
    .set('equipmentname')
    .set('belongto')
    .set('storetime')
    .set('equiptype');
}

/**
 * 提交添加
 */
EquipmentsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/equipments/add", function(data){
        Feng.success("添加成功!");
        window.parent.Equipments.table.refresh();
        EquipmentsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.equipmentsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EquipmentsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/equipments/update", function(data){
        Feng.success("修改成功!");
        window.parent.Equipments.table.refresh();
        EquipmentsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.equipmentsInfoData);
    ajax.start();
}

$(function() {

});
