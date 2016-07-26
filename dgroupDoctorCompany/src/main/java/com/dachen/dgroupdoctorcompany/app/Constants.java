package com.dachen.dgroupdoctorcompany.app;

public class Constants {

    /**
     * 药企用户代表
     **/
    public static final String USER_TYPE = "10";
    public static final int USER_TYPEC = 10;
    /**
     * 网络连接超时
     **/
    public static final int HTTP_REQUEST_CONNECT_TIMEOUT = 198;
    public static String USER_UPDATE = "user/update";

    /**
     * 网络连接异常
     **/
    public static final int HTTP_REQUEST_NET_WORK_EXCEPTION = 199;
    public static String VERIFY_TELEPHONE = "sms/verify/telephone";
    /**
     * 请求成功状态码
     **/
    public static final int HTTP_REQUEST_SUCCESS_CODE = 200;
    public static String VERIFY_CODE = "sms/randcode/verifyCode";
    public static String VISITRECORD = "visit/getVisitList";
    public static String VISITINFO = "visit/getVisitInfoByUserId";
    /**
     * 自动登录
     */
    public static String USER_LORGIN_AUTO = "user/login/auto";

    public static final int HTTP_REQUEST_NOT_LOGIN = 302;
    public static String USER_REGISTER = "user/register";
    /**
     * 错误返回码
     **/
    public static final int HTTP_REQUEST_ERROR_CODE = 500;
    /**
     * 我 登出
     */

    public static String LOGOUT = "user/logout";
    // 登录接口
    public static final String LOGIN = "user/login";
    public static final String LOGINVERIFY = "login";
    public static final String XIAOMI = "user/registerDeviceToken";
    public static final String XIAOMIREMOVE = "user/removeDeviceToken";

    public static final String COMPANYCONTACTLIST = "org/drugCompany/dept/getEnterpriseListByTs";
    public static final String COMPANYGETALLCONTACT = "org/drugCompany/dept/getAllEnterUsersByDptId";
    // 注册借口
    public static final String REGISTER = "sms/verify/telephone";
    /**
     * 请求验证码
     */
    public static String SEND_AUTH_CODE = "sms/randcode/getSMSCode";

    public static String GOOD_SELECT = "c_GoodsIndication.select?";

    // 上传个人头像user/update
    public static String UploadAvatarServlet = "upload/UploadAvatarServlet";


    public static String CODE_ID = "c_Recipe.getDrug_from_mc?";
    public static String ALL_ILL_INFO = "c_SFFL.select_explorer";
    /**
     * 扫二维码后得到的药单
     */
    public static String SALAESCLERK = "c_Recipe.qr_code?";
    /**
     * 药品销售记录
     */
    public static String SALAE_SRECORDS = "c_DrugSalesLog.query?";
    /**
     * 修改密码
     */
    public static String MODIFY_PWD = "user/updatePassword";

    public static String RECIPEGETRECIPE_ITEMS_GIVE = "c_user_JF.get_goods_toDH?";
    public static String RECIPEGETRECIPE_ITEMS = "c_Recipe.get_recipeitems?";
    //得到用户信息
    public static String PATIENT_INFO = "c_Recipe.qr_code?";
    //提交药单
    public static String SAVE_TO_SERVER = "c_Recipe.drug_buy_bypatient?";
    //提交赠药订单
    public static String SAVE_TO_SERVER_GIVE = "c_user_JF.dh_goods_store?";
    /**
     * 请求重置密码
     */
    public static String PRE_RESET_PASSWD = "health/user/preResetPassword";
    /**
     * 重置密码
     */
    public static String RESET_PASSWD = "user/resetPassword";
    /**
     * 用户 - 验证重设密码的验证码
     */
    public static String VERIFYRESETPASSWORD = "health/user/verifyResetPassword";

    public static String MEDIE_MANAGEMENT = "org/assignGoodsGroup/getMySellerDrug";

    public static String MEDIE_DOCUMENT = "org/goods/file/queryFile";

    public static String PUB_ISCUSTOMER = "pub/isCustomer";

    public static String SAVE_FILE = "vpanfile/saveFile";
    public static String IS_IN_MY_FILE_LIST = "vpanfile/isInMyFileList";
    public static String ARCHIVE_LIST = "vpanfile/queryFile";
    public static String GET_INFO = "org/drugCompany/dept/getEnterUsersByUserId";
    public static String GET_VISIT_URL = "org/h5VistUrl/getUrlForH5";
    //直播会议 - 创建会议
    public static String MEETING_CREATE = "meeting/create";
    //直播会议 - 修改会议
    public static String MEETING_UPDATE = "meeting/update";
    //直播会议 - 会议列表
    public static String MEETING_LIST = "meeting/list";
    //直播会议 - 取消或完成会议
    public static String MEETING_STOP = "meeting/stop";
    //文件管理 - 医药代表获取所有的文件
    public static String GET_UPLOADED_FILE_LIST = "org/goods/file/getUploadedFileList";
    //地图选取接口
    public static String GET_VISIT_BASIC_DATA = "visit/getBasicData";
    //新建或修改上班打卡
    public static String CREATE_OR_UPDATA_SIGIN_IN = "signed/createOrUpdateSigned";
    //新建或修改拜访客户
    public static String CREATE_OR_UPDATA_VISIT = "visit/createOrUpdateVisit";
    //上班打卡详情
    public static String SIGIN_IN_DETAIL = "signed/getSignedDetail";
    //拜访客户详情
    public static String VISIT_DETAIL = "visit/getVisitDetail";
    //获取签到历史记录
    public static String GET_VISIT_LIST = "visit/getList";
    //修改用户名称
    public static String UPDATE_USER_NAME = "health/user/updateUserName";
    //修改用户职位
    public static String UPDATE_JOB_TITLE = "org/drugCompany/dept/updateUserTitle";
    //删除拜访
    public static String DELETE_VISIT = "visit/deleteVisit";
    //获取用户组织结构
    public static String GET_ENTER_ORG = "org/drugCompany/dept/getEnterOrg";
    //修改用户组织部门
    public static String UPDATE_ORG = "org/drugCompany/dept/updateUserOrg";
    //根据部门ID获取部门结构
    public static String DEPSTRUCT = "org/drugCompany/dept/getDepartmentsByParentIdAndEId";
    public static String MANINDEP = "org/drugCompanyEmployee/getDepartmentByUserId";
    //org/signed/drugCompanyEmployee/getDepartmentSinged
    public static String SIGNEDRECORD = "signed/getDepartmentSigned2";
    public static String GETSINGINFOBYUSERID = "signed/getSignedInfoByUserId";
    public static String GETVISITPEOPLE = "org/drugCompany/dept/getAllEnterUsersByDptId";
    public static String VISITDETAIL = "visit/getDepartmentVisitDetail";
    //得到某人的医生好友列表
    public static String FRIENDLIST = "org/saleFriend/search";
    //得到登录用户信息
    public static String GETUSERINFO = "org/drugCompanyEmployee/getLoginInfo";
    //得到还没有添加的品种列表
    public static String GETUNSIGNGOODSLIST = "org/assignGoodsGroup/getUnAssignGoodsGroup";
    //添加品种
    public static String ADDGOODS = "org/assignGoodsGroup/addRelationGoodsgroup";
    //得到某品种下没有管理的医院
    public static String GETNOTSIGNHOSPITAL = "org/saleFriend/getAssignHospitalList";
    //删除医院
    public static String DELETEHOSTIPAL = "org/saleFriend/deleteHospital";
    //得到分管品种及医院
    public static String GETMANAGERHOSPITAL = "org/assignGoodsGroup/getGoodsGroupHospitalList";
    //得到新的好友列表
    public static String GETFRIEND = "org/doctorFriend/getFriendReqList";
    //搜索医院列表
    public static String SEARCHHOSPITALS = "org/saleFriend/searchHospitalList";
    public static String ADDHOSPITAL = "org/saleFriend/addHospital";
    public static String GETHOSPITAL = "org/saleFriend/getList";
    //获取签到标签
    public static String GET_SIGNED_LABLE = "signed/getSignedLable";
    //附近符合条件协同拜访组列表查询
    public static String GET_VISIT_GROUP = "visit/findSynergGroupList";
    //创建协同拜访组
    public static String ADD_VISIT_GROUP = "visit/createSynergyGroup";
    //协同拜访组成员加入
    public static String JOIN_VISIT_GROUP = "visit/addSynergGroup";
    //取消协同拜访组(创建者)
    public static String DELETE_VISIT_GROUP = "visit/deleteSynergGroup";
    //取消协同拜访组(成员)
    public static String CANCEL_VISIT_GROUP = "visit/cancelSynergGroup";
    //创建者确定协同组
    public static String CONFIRM_VISIT_GROUP = "visit/confirmSynergGroup";
    //品种组 - 根据关键字搜索品种组（分页）
    public static String GET_GOODSGROUP_LIST = "org/goods/group/getGoodsGroupList";

    public static String CREATE_AND_JOIN_VISIT_GROUP = "visit/startSynergGroup";

    //获取版本号
    public static String GET_VERSION = "appService/getVersion";
}
