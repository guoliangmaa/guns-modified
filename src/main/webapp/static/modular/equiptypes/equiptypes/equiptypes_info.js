/**
 * 初始化设备类型管理详情对话框
 */
var EquiptypesInfoDlg = {
    equiptypesInfoData : {}
};

/**
 * 清除数据
 */
EquiptypesInfoDlg.clearData = function() {
    this.equiptypesInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EquiptypesInfoDlg.set = function(key, val) {
    this.equiptypesInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EquiptypesInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EquiptypesInfoDlg.close = function() {
    parent.layer.close(window.parent.Equiptypes.layerIndex);
}

/**
 * 收集数据
 */
EquiptypesInfoDlg.collectData = function() {
    this
    .set('id')
    .set('typename');
}

/**
 * 提交添加
 */
EquiptypesInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/equiptypes/add", function(data){
        Feng.success("添加成功!");
        window.parent.Equiptypes.table.refresh();
        EquiptypesInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.equiptypesInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EquiptypesInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/equiptypes/update", function(data){
        Feng.success("修改成功!");
        window.parent.Equiptypes.table.refresh();
        EquiptypesInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.equiptypesInfoData);
    ajax.start();
}

$(function() {

});
