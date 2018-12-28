package com.barnettwong.quyou.ui.fragment;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.ui.activity.AboutUsActivity;
import com.barnettwong.quyou.ui.activity.MainActivity;
import com.barnettwong.quyou.util.Defaultcontent;
import com.blankj.utilcode.util.ToastUtils;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeController;
import com.kyleduo.switchbutton.SwitchButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * Created by wang on 2018/12/25 14:58
 * 个人中心
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.switch_btn)
    SwitchButton switchBtn;
    //分享
    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        MyOnClickListener onClickListener = new MyOnClickListener();
        llShare.setOnClickListener(onClickListener);
        llAboutUs.setOnClickListener(onClickListener);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChangeModeController.toggleThemeSetting(getActivity());
            }
        });

        initUmengShare();
    }

    private void initUmengShare() {
        mShareListener = new CustomShareListener();
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .addButton("复制文本", "复制文本", "umeng_socialize_copy", "umeng_socialize_copy")
                .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("复制文本")) {
                            Toast.makeText(getActivity(), "复制文本按钮", Toast.LENGTH_LONG).show();
                        } else if (snsPlatform.mShowWord.equals("复制链接")) {
                            Toast.makeText(getActivity(), "复制链接按钮", Toast.LENGTH_LONG).show();

                        } else {
                            UMWeb web = new UMWeb(Defaultcontent.url);
                            web.setTitle("\"友趣\"的文章");
                            web.setDescription("没有谁能击垮你，除非你自甘堕落。不拼一把，你怎么知道自己是人物还是废物！比你差的人没放弃，比你好的人仍在努力，你有什么资格说你无能为力！");
                            web.setThumb(new UMImage(getActivity(), R.mipmap.app_icon));
                            new ShareAction(getActivity()).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    }
                });
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_share:
                    mShareAction.open();
                    break;
                case R.id.ll_about_us:
                    startActivity(AboutUsActivity.class);
                    break;
            }
        }
    }

    private static class CustomShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtils.showShort(platform.getName() + " 收藏成功啦");
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    ToastUtils.showShort(platform.getName() + " 分享成功啦");
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST
                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                    ToastUtils.showShort(platform.getName() + " 分享失败啦");
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(platform.getName() + " 分享取消了");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mShareAction.close();
    }
}
