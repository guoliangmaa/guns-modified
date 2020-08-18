/**
 * 初始化会议设备申请审批详情对话框
 */
var EquipmentapplyInfoDlg = {
    equipmentapplyInfoData : {}
};

/**
 * 清除数据
 */
EquipmentapplyInfoDlg.clearData = function() {
    this.equipmentapplyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EquipmentapplyInfoDlg.set = function(key, val) {
    this.equipmentapplyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EquipmentapplyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EquipmentapplyInfoDlg.close = function() {
    parent.layer.close(window.parent.Equipmentapply.layerIndex);
}

/**
 * 收集数据
 */
EquipmentapplyInfoDlg.collectData = function() {
    this
    .set('id')
    .set('mettingid')
    .set('equipmentid')
        .set('status');
}

/**
 * 提交添加
 */
EquipmentapplyInfoDlg.addSubmit = function() {

    this.clearData();
    // this.collectData();

    //获取到会议id
    this.set("mettingid");
    let arr = []
    $("input[name='ids']:checked").each(function (i) {
        arr.push($(this).val())
    })
    this.set("equipmentid",JSON.stringify(arr))
    console.log(arr)
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/equipmentapply/add", function(data){
        Feng.success("添加成功!");
        window.parent.Equipmentapply.table.refresh();
        EquipmentapplyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.equipmentapplyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EquipmentapplyInfoDlg.editSubmit = function() {

    this.clearData();
    // this.collectData();
    this.set("id")
    this.set("status")
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/equipmentapply/update", function(data){
        Feng.success("修改成功!");
        window.parent.Equipmentapply.table.refresh();
        EquipmentapplyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.equipmentapplyInfoData);
    ajax.start();
}

$(function() {

});
