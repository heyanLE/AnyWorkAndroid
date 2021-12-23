package com.qgstudio.anyworkc.core;

public class Apis {
    /*static {
        System.loadLibrary("jni_apis");
    }*/

    /**
     * 登录
     * @return String
     */
    public static String loginApi() {
        return "user/login";
    }

    /**
     * 注册
     * @return String
     */
    public static String registerApi() {
        return "user/register";
    }

    /**
     * 退出
     * @return String
     */
    public static String logoutApi() {
        return "user/exit";
    }

    /**
     * 修改密码
     * @return String
     */
    public static String changePasswordApi() {
        return "user/password/change";
    }

    /**
     * 更改用户信息
     * @return String
     */
    public static String changeUserInfoApi() {
        return "user/update";
    }

    /**
     * 获取排行榜
     * @return String
     */
    public static String getRankingApi() {
        return "leaderboard/paper/show";
    }

    public static String getTotalRankingApi() {
        return "leaderboard/show";
    }

    /**
     * 标记公告为已读
     * @return String
     */
    public static String markWatchedApi() {
        return "message/change";
    }

    /**
     * 获取公告
     * @return String
     */
    public static String getNoticeApi() {
        return "message/show";
    }

    /**
     * 脱离组织
     * @return String
     */
    public static String leaveOrganizationApi() {
        return "organization/leave";
    }

    /**
     * 加入组织
     * @return String
     */
    public static String joinOrganizationApi() {
        return "organization/join";
    }

    /**
     * 获取我的组织列表
     * @return String
     */
    public static String getJoinOrganizationApi() {
        return "organization/me";
    }

    /**
     * 添加反馈
     * @return String
     */
    public static String uploadFeedbackApi() {
        return "suggest/add";
    }

    /**
     * 添加收藏
     * @return String
     */
    public static String collectApi() {
        return "quest/collect";
    }

    /**
     * 取消收藏
     * @return String
     */
    public static String unCollectApi() {
        return "quest/collect/delete";
    }

    /**
     * 获取收藏的题目
     * @return String
     */
    public static String getAllCollectionsApi() {
        return "quest/collect/list";
    }

    /**
     * 提交试卷
     * @return String
     */
    public static String submitTestpaperApi() {
        return "test/submit";
    }

    /**
     * 搜索组织
     * @return String
     */
    public static String getAllOrganizationApi() {
        return "organization/search";
    }

    /**
     * 获取详细的试题（完成一部分和还未做的）
     * @return String
     */
    public static String getTestpaperApi(){
        return "test/none/detail";
    }

    /**
     * 获取组织下的章节列表
     * @return String
     */
    public static String getChapterApi() {
        return "test/chapter";
    }

    /**
     * 获取世界概要列表
     * @return String
     */
    public static String getCatalogApi() {
        return "test/list";
    }

    /**
     * 上传头像
     * @return String
     */
    public static String changePicApi() {
        return "user/upload";
    }

   // public static native String initLib();



    public static String changeInfoApi() {
        return "test/detail";
    }








    /*public static native String submitTestpaperApi();*/

    /*public static native String unCollectApi();*/

    /*public static native String collectApi();*/

    /*public static native String getJoinOrganizationApi();*/

    /*public static native String leaveOrganizationApi();*/

    //public static native String loginApi();

    /*public static native String logoutApi();*/

    /*public static native String changePasswordApi();*/

    /*public static native String changeUserInfoApi();*/

    /*public static native String getRankingApi();*/

    /*public static native String getTotalRankingApi();*/

    /*public static native String markWatchedApi();*/

    /*public static native String getNoticeApi();*/

    /*public static native String joinOrganizationApi();*/

    /*public static native String getAllCollectionsApi();*/
}
