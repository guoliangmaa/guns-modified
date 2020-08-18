/**
 * 初始化预约会议申请审批详情对话框
 */
var MeetingapplyInfoDlg = {
    meetingapplyInfoData : {}
};

/**
 * 清除数据
 */
MeetingapplyInfoDlg.clearData = function() {
    this.meetingapplyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MeetingapplyInfoDlg.set = function(key, val) {
    this.meetingapplyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MeetingapplyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MeetingapplyInfoDlg.close = function() {
    parent.layer.close(window.parent.Meetingapply.layerIndex);
}

/**
 * 收集数据
 */
MeetingapplyInfoDlg.collectData = function() {
    this
    .set('id')
    .set('workerid')
    .set('topic')
    .set('documentlink')
    .set('homeid')
    .set('attendance')
    .set('begintime')
    .set('endtime')
    .set('statusid');
    // .set('applytime');
}

/**
 * 提交添加
 */
MeetingapplyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/meetingapply/add", function(data){
        if (data.code === 200) {
            Feng.success("添加成功!");
            window.parent.Meetingapply.table.refresh();
            MeetingapplyInfoDlg.close();
        }else {
            Feng.error(data.message)
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.meetingapplyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MeetingapplyInfoDlg.editSubmit = function() {

    this.clearData();
    // this.collectData();
    this.set("id").set("statusid");
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/meetingapply/update", function(data){
        if (data.code === 200) {
            Feng.success("修改成功!");
            window.parent.Meetingapply.table.refresh();
            MeetingapplyInfoDlg.close();
        }else {
            Feng.error(data.message)
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.meetingapplyInfoData);
    ajax.start();
}

$(function() {

});
