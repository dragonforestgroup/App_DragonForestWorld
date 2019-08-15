package com.dragonforest.app.module_share.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.dragonforest.app.module_share.uitool.ShareBoard;
import com.dragonforest.app.module_share.uitool.ShareBoardlistener;
import com.dragonforest.app.module_share.uitool.SnsPlatform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.facebook.Facebook;
import cn.jiguang.share.facebook.messenger.FbMessenger;
import cn.jiguang.share.jchatpro.JChatPro;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.twitter.Twitter;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;

/**
 * @author 韩龙林
 * @date 2019/8/15 18:03
 */
public class ShareUtil {

    public final static int SHARE_WEBPAGE = Platform.SHARE_WEBPAGE;

    public final static String SHARE_PLATFORM_QQ = QQ.Name;

    private static ShareUtil instance;
    private Context context;
    private ShareBoard mShareBoard;

    private String sharePlatform = SHARE_PLATFORM_QQ;
    private int shareType = Platform.SHARE_WEBPAGE;
    private String title;
    private String text;
    private String url;


    public ShareUtil() {
    }

    public static ShareUtil getInstance() {
        return new ShareUtil();
    }

    public ShareUtil with(Context context) {
        this.context = context;
        return this;
    }

    public ShareUtil shareType(int type) {
        this.shareType = type;
        return this;
    }

    public ShareUtil title(String title) {
        this.title = title;
        return this;
    }

    public ShareUtil url(String url) {
        this.url = url;
        return this;
    }

    public ShareUtil text(String text) {
        this.text = text;
        return this;
    }

    public void share() {
        showBroadView();
    }

    public void shareTo(String platform) {
        //这里以分享链接为例
        ShareParams shareParams = new ShareParams();
        shareParams.setTitle(title);
        shareParams.setText(text);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(url);
        shareParams.setImagePath("");
        JShareInterface.share(platform, shareParams, mShareListener);
    }

    private void showBroadView() {
        if (mShareBoard == null) {
            mShareBoard = new ShareBoard((Activity) context);
            List<String> platforms = JShareInterface.getPlatformList();
            if (platforms != null) {
                Iterator var2 = platforms.iterator();
                while (var2.hasNext()) {
                    String temp = (String) var2.next();
                    SnsPlatform snsPlatform = createSnsPlatform(temp);
                    mShareBoard.addPlatform(snsPlatform);
                }
            }
            mShareBoard.setShareboardclickCallback(mShareBoardlistener);
        }
        mShareBoard.show();
    }

    private ShareBoardlistener mShareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, String platform) {
            switch (shareType) {
                case Platform.SHARE_WEBPAGE:
                    //这里以分享链接为例
                    ShareParams shareParams = new ShareParams();
                    shareParams.setTitle(title);
                    shareParams.setText(text);
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setUrl(url);
                    shareParams.setImagePath("");
                    JShareInterface.share(platform, shareParams, mShareListener);
                    break;
                default:
                    break;
            }


        }
    };


    private static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (platformName.equals(Wechat.Name)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (platformName.equals(WechatMoments.Name)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (platformName.equals(WechatFavorite.Name)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";

        } else if (platformName.equals(SinaWeibo.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_key";
        } else if (platformName.equals(SinaWeiboMessage.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_msg_key";
        } else if (platformName.equals(QQ.Name)) {
            mIcon = "jiguang_socialize_qq";
            mGrayIcon = "jiguang_socialize_qq";
            mShowWord = "jiguang_socialize_text_qq_key";

        } else if (platformName.equals(QZone.Name)) {
            mIcon = "jiguang_socialize_qzone";
            mGrayIcon = "jiguang_socialize_qzone";
            mShowWord = "jiguang_socialize_text_qq_zone_key";
        } else if (platformName.equals(Facebook.Name)) {
            mIcon = "jiguang_socialize_facebook";
            mGrayIcon = "jiguang_socialize_facebook";
            mShowWord = "jiguang_socialize_text_facebook_key";
        } else if (platformName.equals(FbMessenger.Name)) {
            mIcon = "jiguang_socialize_messenger";
            mGrayIcon = "jiguang_socialize_messenger";
            mShowWord = "jiguang_socialize_text_messenger_key";
        } else if (Twitter.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_twitter";
            mGrayIcon = "jiguang_socialize_twitter";
            mShowWord = "jiguang_socialize_text_twitter_key";
        } else if (platformName.equals(JChatPro.Name)) {
            mShowWord = "jiguang_socialize_text_jchatpro_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }

    private PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享成功";
                handler.sendMessage(message);
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享失败:" + error.getMessage() + "---" + errorCode;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onCancel(Platform platform, int action) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享取消";
                handler.sendMessage(message);
            }
        }
    };

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
