package ext.st.pmgt.indicator.dingTalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.netfactory.util.misc.Strings;
import com.taobao.api.FileItem;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

public class DingTalkUtils {
////    需要重汽开通权限得到相应的APPKEY，APPSECRET，AGENTID
//    private static final String APPKEY = "dingrnmcpd2mgtbubxgn";//(这里是应用的AppKey)
//    private static final String APPSECRET = "cnCuXRVuqYIrpDTQGXdb4bZOFxLckCuvWUhfJmUSJGmQ1jWHpR8_c_ALLTkAxYgF";//(这里是应用的APPSECRET )
//    private static final Long AGENTID = 1044570466L;//(这是申请的应用id）
//重汽
    private static final String APPKEY = "dinge9tvgvc28q6xg0dh";//(这里是应用的AppKey)
    private static final String APPSECRET = "rA8LqBd7aLDIZsj75vEk8ltUNn5cKsuKzoj7ux00GEEAouvcenkVCmXmpkmWe2qF";//(这里是应用的APPSECRET )
    private static final Long AGENTID = 1069269974L;//(这是申请的应用id）

    /**
     * 获取部门下的所有用户列
     *
     * @param departmentId
     * @return
     */
    public static List getDepartmentUser(Long departmentId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(departmentId);
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");

        OapiUserSimplelistResponse response = client.execute(request, getAccessToken());
        List<OapiUserSimplelistResponse.Userlist> userlist = response.getUserlist();
        return userlist;
    }

    /**
     * 通过电话号码得到用户id
     * 管理员必须授权该接口
     */
    public static String getUseridBymobile(String mobileNumber) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
        OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
        request.setMobile(mobileNumber);

        OapiUserGetByMobileResponse response = client.execute(request, getAccessToken());
        String userid = response.getUserid();
        return userid;
    }

    /**
     * 获取子部门id列表
     */
    public static List getDeptList() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId("1");
        request.setHttpMethod("GET");

        OapiDepartmentListIdsResponse response = client.execute(request, getAccessToken());
        List<Long> subDeptIdList = response.getSubDeptIdList();
        return subDeptIdList;
    }

    //    得到token
    private static String getAccessToken() throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(APPKEY);
        request.setAppsecret(APPSECRET);
        request.setHttpMethod("GET");

        OapiGettokenResponse response = client.execute(request);
        String accessToken = response.getAccessToken();
        return accessToken;
    }


    /**
     * 上传文件、图片等media
     * @param filePath   文件路径
     * @param type       文件类型（图片（image）、语音（voice）、普通文件(file)）
     * @return mediaId     返回id
     * @throws Exception
     */
    public static String uploadMedia(String filePath,String type) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType(type);
        req.setMedia(new FileItem(filePath));//"/tmp/file.txt"

        OapiMediaUploadResponse rsp = client.execute(req, getAccessToken());
        String mediaId = rsp.getMediaId();
        return mediaId;
    }

    /**
     * 发送text文本信息
     * 返回
     * {
     *     "errcode":0,
     *     "errmsg":"ok",
     *     "task_id":123
     * }
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendTextMessage(String UseridList,  boolean ToAllUser, String content) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(content);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     * 发送图片信息
     *
     * @param UseridList 发送的用户id列表    多个用户id用“,”隔开
     * @param ToAllUser  是否发送所有人
     * @param mediaId    图片mediaId
     * @return
     * @throws Exception
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendImageMessage(String UseridList,  boolean ToAllUser, String mediaId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("image");
        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
        msg.getImage().setMediaId(mediaId);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     * 发送文件信息
     *
     * @param UseridList 发送的用户id列表
     * @param ToAllUser  是否发送所有人
     * @param mediaId    文件mediaId
     * @return
     * @throws Exception
     */

    public static OapiMessageCorpconversationAsyncsendV2Response sendFileMessage(String UseridList,  boolean ToAllUser, String mediaId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("file");
        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
        msg.getFile().setMediaId(mediaId);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     * 发送link消息
     *
     * @param UseridList 发送的用户id列表
     * @param ToAllUser  是否发送所有人
     * @param title      标题
     * @param text       内容
     * @param messageUrl 信息url
     * @param picMediaId 图片MediaId
     * @return
     * @throws Exception
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendLinkMessage(String UseridList, boolean ToAllUser, String title, String text, String messageUrl, String picMediaId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(title);
        msg.getLink().setText(text);
        msg.getLink().setMessageUrl(messageUrl);
        msg.getLink().setPicUrl(picMediaId);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     * 发送OA信息
     *
     * @param UseridList 发送的用户id列表
     * @param ToAllUser  是否发送所有人
     * @param headText   头部标题
     * @param bodyTitle  正文标题
     * @param form       表单form
     * @param context    正文内容
     * @param rich       富文本rich
     * @param mediaId    图片mediaId
     * @param aothor     自定义作者名
     * @return
     * @throws Exception
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendOAMessage(String UseridList, boolean ToAllUser, String headText, String bodyTitle, List<OapiMessageCorpconversationAsyncsendV2Request.Form> form, String context,
                                                                               OapiMessageCorpconversationAsyncsendV2Request.Rich rich, String mediaId, String aothor) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText(headText);
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent(context);
        msg.getOa().getBody().setTitle(bodyTitle);
        msg.getOa().getBody().setForm(form);
        msg.getOa().getBody().setRich(rich);
        msg.getOa().getBody().setImage(mediaId);
        msg.getOa().getBody().setAuthor(aothor);
        msg.setMsgtype("oa");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     * @param UseridList 发送的用户id列表
     * @param ToAllUser  是否发送所有人
     * @param title      标题
     * @param text       内容 markdown格式的消息，建议500字符以内
     * @return
     * @throws Exception
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendMarkdownMessage(String UseridList,  boolean ToAllUser, String title, String text) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("markdown");
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setText(text);
        msg.getMarkdown().setTitle(title);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     * 发送整体卡片消息
     * @param UseridList  发送的用户id列表
     * @param ToAllUser   是否发送所有人
     * @param title       标题
     * @param markdown    markdown格式的消息，建议500字符以内
     * @param singleTitle   跳转的标题
     * @param singleUrl     跳转的路径  跳转的标题和路径必须同时设置
     * @return
     */
    public static OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String UseridList,boolean ToAllUser,String title,String markdown,String singleTitle,String singleUrl) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
    	msg.getActionCard().setTitle(title);
    	msg.getActionCard().setMarkdown(markdown);
    	msg.getActionCard().setSingleTitle(singleTitle);
    	msg.getActionCard().setSingleUrl(singleUrl);
    	msg.setMsgtype("action_card");
    	request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }
    /**
     * 发送独立跳转卡片消息
     * @param UseridList  发送的用户id列表
     * @param ToAllUser   是否发送所有人
     * @param title       标题
     * @param markdown    markdown格式的消息，建议500字符以内
     * @param btn_orientation   0  按钮竖直排列   1 横向排列必须与btn_json_list同时设置。
     * @param stbtn_json_list   按钮列表
     * @return
     */

    public static OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String UseridList,  boolean ToAllUser,String title,String markdown,
                                                                                       String btn_orientation,List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> stbtn_json_list) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //接收者的用户userid列表
        request.setUseridList(UseridList);
        //申请的应用AgentId
        request.setAgentId(AGENTID);
        //true为全体员工
        request.setToAllUser(ToAllUser);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(title);
        msg.getActionCard().setMarkdown(markdown);
        msg.getActionCard().setBtnOrientation(btn_orientation);
        msg.getActionCard().setBtnJsonList(stbtn_json_list);
        msg.setMsgtype("action_card");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        return response;
    }

    /**
     *   发送消息的结果
     * @param taskId    发送消息的任务id
     * @return
     *
    {
    "send_result":{
    "invalid_user_id_list":"zhangsan,lisi",        无效的用户id
    "forbidden_user_id_list":"zhangsan,lisi",     因发送消息超过上限而被流控过滤后实际未发送的userid。未被限流的接收者仍会被收到消息
    "failed_user_id_list":"zhangsan,lisi",        发送失败的用户id
    "read_user_id_list":"zhangsan,lisi",         已读消息的用户id
    "unread_user_id_list":"zhangsan,lisi",       未读消息的用户id
    "invalid_dept_id_list":"1,2,3"               无效的部门id
    },
    "errcode":0,                               返回码
    "errmsg":"ok"                              对返回码的文本描述内容
    }
     * @throws Exception
     */
    public  static OapiMessageCorpconversationGetsendresultResponse sendMessageResult(Long taskId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendresult");
        OapiMessageCorpconversationGetsendresultRequest request  = new OapiMessageCorpconversationGetsendresultRequest();
        request.setAgentId(AGENTID);
        request.setTaskId(taskId);
        OapiMessageCorpconversationGetsendresultResponse response = client.execute(request, getAccessToken());
         return response;
    }

    public static String getUseridList(Set<PIUser> userSet) throws Exception {
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(userSet)) {
            for (PIUser user : userSet) {
                if (Strings.isNotBlank(user.getTelephone())) {
                    sb.append("," + getUseridBymobile(user.getTelephone()));
                }
            }
            String useridList = sb.toString().substring(1);
            return useridList;
        }
        return null;
    }
}
