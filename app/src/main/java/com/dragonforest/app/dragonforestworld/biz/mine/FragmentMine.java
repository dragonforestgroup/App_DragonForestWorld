package com.dragonforest.app.dragonforestworld.biz.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.ContactUsActivity;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.SettingsActivity;
import com.dragonforest.app.module_common.beans.UserInfo;
import com.dragonforest.app.module_common.utils.ImageUtil;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.LoginUtil;
import com.dragonforest.app.module_common.utils.NavigationUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;
import com.wildma.pictureselector.PictureSelector;


/**
 * 我的界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentMine extends Fragment implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_exit_login;
    private ImageView img_head;

    private LinearLayout ll_my_publish;
    private LinearLayout ll_my_offer;
    private LinearLayout ll_my_stars;
    private LinearLayout ll_my_plan;
    private LinearLayout ll_my_msg;
    private LinearLayout ll_my_collect;
    private LinearLayout ll_my_share;
    private LinearLayout ll_contact_us;
    private LinearLayout ll_setting;
    private LinearLayout ll_test;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_fragment_mine, container, false);
        LogUtil.E("FragmentMine", "onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("FragmentMine", "onResume()");
        initData();
    }

    private void initView(View v) {
        tv_name = v.findViewById(R.id.tv_person_name);
        tv_email = v.findViewById(R.id.tv_person_email);
        tv_exit_login = v.findViewById(R.id.tv_exit_login);
        img_head = v.findViewById(R.id.img_head);
        ll_my_publish = v.findViewById(R.id.ll_my_publish);
        ll_my_offer = v.findViewById(R.id.ll_my_offer);
        ll_my_stars = v.findViewById(R.id.ll_my_stars);
        ll_my_plan = v.findViewById(R.id.ll_my_plan);
        ll_my_msg = v.findViewById(R.id.ll_my_msg);
        ll_my_collect = v.findViewById(R.id.ll_my_collect);
        ll_my_share = v.findViewById(R.id.ll_my_share);
        ll_contact_us = v.findViewById(R.id.ll_contact_us);
        ll_setting = v.findViewById(R.id.ll_setting);
        ll_test = v.findViewById(R.id.ll_test);


        tv_exit_login.setOnClickListener(this);
        img_head.setOnClickListener(this);
        ll_my_publish.setOnClickListener(this);
        ll_my_offer.setOnClickListener(this);
        ll_my_stars.setOnClickListener(this);
        ll_my_plan.setOnClickListener(this);
        ll_my_msg.setOnClickListener(this);
        ll_my_collect.setOnClickListener(this);
        ll_my_share.setOnClickListener(this);
        ll_contact_us.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_test.setOnClickListener(this);
    }

    private void initData() {
        UserInfo cacheUserInfo = LoginUtil.getCacheUserInfo(getActivity());
        if (cacheUserInfo != null) {
            tv_name.setText(cacheUserInfo.getUserName() == null ? "" : cacheUserInfo.getUserName());
            tv_email.setText(cacheUserInfo.getUserEmail() == null ? "" : cacheUserInfo.getUserEmail());
            if (cacheUserInfo.getUserHeadImg() != null) {
                Bitmap bitmap = ImageUtil.getInstance().compressLoadFromFile(cacheUserInfo.getUserHeadImg(), 128, 128);
                if (bitmap != null) {
                    img_head.setImageBitmap(bitmap);
                }
            }
        }
    }


    /**
     * 默认操作 提示正在开发中
     */
    private void defaultProcess() {
        ToastUtils.show("该功能正在开发中...", getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_login:
                showExistDialog();
                break;
            case R.id.img_head:
                showChooseHeadDialog();
                break;
            case R.id.ll_my_publish:
                defaultProcess();
                break;
            case R.id.ll_my_offer:
                defaultProcess();
                break;
            case R.id.ll_my_stars:
                defaultProcess();
                break;
            case R.id.ll_my_plan:
                gotoPlan();
                break;
            case R.id.ll_my_msg:
                gotoMessage();
                break;
            case R.id.ll_my_collect:
                defaultProcess();
                break;
            case R.id.ll_my_share:
                shareApp();
                break;
            case R.id.ll_contact_us:
                gotoContactUs();
                break;
            case R.id.ll_setting:
                gotoSetting();
                break;
            case R.id.ll_test:
                gotoTest();
                break;
            default:
                break;
        }
    }

    private void showExistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("退出登录");
        builder.setMessage("确认要退出登录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginUtil.saveUserInfo(getActivity(), null);
                NavigationUtil.navigation(getActivity(), true, "com.dragonforest.app.module_login.LoginActivity");

            }
        });
        builder.show();
    }

    private void showChooseHeadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("更换头像");
        builder.setMessage("确定要更换头像吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 选择图片
                PictureSelector
                        .create(FragmentMine.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 200, 200, 1, 1);
            }
        });
        builder.show();
    }

    private void shareApp() {
        try {
            com.dragonforest.app.module_share.util.ShareUtil.getInstance()
                    .with(getActivity())
                    .shareType(com.dragonforest.app.module_share.util.ShareUtil.SHARE_WEBPAGE)
                    .title("DragonForest")
                    .text("代码世界")
                    .url("https://blog.csdn.net/qq_23992393")
                    .share();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("分享模块缺失！" + e.getMessage(), getActivity());
        }
    }

    private void gotoContactUs() {
        startActivity(new Intent(getActivity(), ContactUsActivity.class));
    }

    private void gotoSetting() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }


    private void gotoPlan() {
        NavigationUtil.navigation(getActivity(), false, "com.dragonforest.app.module_plan.activity.PlanActivityMain");
    }

    private void gotoMessage() {
        NavigationUtil.navigation(getActivity(), false, "com.dragonforest.app.dragonforestworld.biz.mine.activity.message.MessageActivity");
    }

    private void gotoTest() {
        NavigationUtil.navigation(getActivity(), false, "com.dragonforest.app.dragonforestworld.test.TestOAListActivity");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.E("onActivityResult", "选择照片返回。");
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                LogUtil.E("onActivityResult", "开始压缩图片。");
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                Bitmap bitmap = ImageUtil.getInstance().compressLoadFromFile(picturePath, 128, 128);
                if (bitmap != null) {
                    img_head.setImageBitmap(bitmap);
                }
                // 保存到本地信息中
                UserInfo cacheUserInfo = LoginUtil.getCacheUserInfo(getActivity());
                if (cacheUserInfo != null) {
                    cacheUserInfo.setUserHeadImg(picturePath);
                    LoginUtil.saveUserInfo(getActivity(), cacheUserInfo);
                }
                ToastUtils.show("更换头像成功！", getActivity());

                // 上传到服务器中

//                String dstPath = getActivity().getFilesDir() + File.separator + "DragonForestHead" + "128x128_" + ".jpg";
//                boolean isSuccess = ImageUtil.getInstance().compressImageFile(picturePath, dstPath, 128, 128);
//                ToastUtils.show("压缩结果：" + isSuccess + ", 路径：" + dstPath, getActivity());
                /*如果使用 Glide 加载图片，则需要禁止 Glide 从缓存中加载，因为裁剪后保存的图片地址是相同的*/
                /*RequestOptions requestOptions = RequestOptions
                        .circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true);
                Glide.with(this).load(picturePath).libview_oa_money(requestOptions).into(mIvImage);*/
            }
        }
    }
}
