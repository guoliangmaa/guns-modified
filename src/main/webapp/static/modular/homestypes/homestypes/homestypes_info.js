/**
 * 初始化会议室类型管理详情对话框
 */
var HomestypesInfoDlg = {
    homestypesInfoData : {}
};

/**
 * 清除数据
 */
HomestypesInfoDlg.clearData = function() {
    this.homestypesInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HomestypesInfoDlg.set = function(key, val) {
    this.homestypesInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HomestypesInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
HomestypesInfoDlg.close = function() {
    parent.layer.close(window.parent.Homestypes.layerIndex);
}

/**
 * 收集数据
 */
HomestypesInfoDlg.collectData = function() {
    this
    .set('id')
    .set('typename');
}

/**
 * 提交添加
 */
HomestypesInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/homestypes/add", function(data){
        Feng.success("添加成功!");
        window.parent.Homestypes.table.refresh();
        HomestypesInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.homestypesInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
HomestypesInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/homestypes/update", function(data){
        Feng.success("修改成功!");
        window.parent.Homestypes.table.refresh();
        HomestypesInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.homestypesInfoData);
    ajax.start();
}

$(function() {

});
