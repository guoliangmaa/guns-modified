/**
 * 初始化会议室资源管理详情对话框
 */
var HomesInfoDlg = {
    homesInfoData : {}
};

/**
 * 清除数据
 */
HomesInfoDlg.clearData = function() {
    this.homesInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HomesInfoDlg.set = function(key, val) {
    this.homesInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HomesInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
HomesInfoDlg.close = function() {
    parent.layer.close(window.parent.Homes.layerIndex);
}

/**
 * 收集数据
 */
HomesInfoDlg.collectData = function() {
    this
    .set('id')
    .set('homeno')
    .set('address')
    .set('space')
    .set('hosterid')
    .set('hometype');
}

/**
 * 提交添加
 */
HomesInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/homes/add", function(data){
        Feng.success("添加成功!");
        window.parent.Homes.table.refresh();
        HomesInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.homesInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
HomesInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/homes/update", function(data){
        Feng.success("修改成功!");
        window.parent.Homes.table.refresh();
        HomesInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.homesInfoData);
    ajax.start();
}

/**
 * 自己添加的，在初始化的时候，获取所有用户和会议室类型
 * 动态添加到select组件中
 */


$(function() {

});
