package com.evan.dy.task;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ehook.utils.LogUtil;
import com.evan.dy.api.ChatRoomApi;
import com.evan.dy.api.callback.EICommonCallback;
import com.evan.dy.api.model.DyConfig;
import com.evan.dy.api.model.LiveRoomMessage;
import com.evan.dy.api.model.dy.DyLiveMessage;
import com.evan.dy.utils.ConfigUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class DyTask {
    public static final String TAG = DyTask.class.getSimpleName();
    public static final int MSG_AUTO_CLICK = 100;
    public static final int MSG_AUTO_SEND_BULLETIN = 103;
    public static final int MSG_AUTO_SEND_GIFT = 101;
    public static final int MSG_AUTO_SHOPPING = 102;
    public static final int MSG_SEND_SUBSCRIBE_MSG = 105;
    public static final int MSG_SEND_WELCOME_MSG = 104;
    public static final int TYPE_SUBSCRIBE = 2;
    public static final int TYPE_WELCOME = 1;
    public static final Random random = new Random();
    public static final HashMap<Integer, ArrayList<String>> sendMsgCache = new HashMap<>();
    private static DyTask task = null;
    public static DyTask getInstance() {
        if (task == null) {
            synchronized (DyTask.class) {
                if (task == null) {
                    task = new DyTask();
                }
            }
        }
        return task;
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case MSG_AUTO_CLICK:
                    sendClickMsg();
                    break;
                case MSG_AUTO_SEND_BULLETIN:
                    sendBulletin();
                    break;
                case MSG_AUTO_SEND_GIFT:
                    sendGift();
                    break;
                case MSG_AUTO_SHOPPING:
                    sendShopping();
                    break;
                case MSG_SEND_SUBSCRIBE_MSG:
                    sendSubscribeMsg();
                    break;
                case MSG_SEND_WELCOME_MSG:
                    sendWelcomeMsg();
                    break;
            }
            return false;
        }
    });

    public final synchronized void startTask() {
        ConfigUtil.getInstance().getDyConfigDisk();
        stopTask();
        LogUtil.INSTANCE.e(TAG, "start task...");
        autoSendWelcome();
        autoClick();
        autoSendGift();
        autoShopping();
        autoSendBulletin();
        autoSendSubscribe();
    }


    private void autoClick() {
        DyConfig.DyAutoClick autoClick = ConfigUtil.getInstance().getDyConfig().autoClick;
        if (autoClick != null && autoClick.status == 1) {
            int autoClickInterval = autoClick.threshold;
            if (autoClickInterval > 0) {
                LogUtil.INSTANCE.e(TAG, "start task... autoClick");
                sendEmptyMessageDelayed(MSG_AUTO_CLICK, ((long) autoClickInterval) * ((long) 1000));
            } else {
                LogUtil.INSTANCE.e(TAG, "autoClickThreshold. interval <= 0, not run...");
            }
        }
    }

    private void autoSendWelcome() {
        DyConfig.DyReplyWelcome dyReplyWelcome = ConfigUtil.getInstance().getDyConfig().replyWelcome;
        if (dyReplyWelcome != null && dyReplyWelcome.status == 1) {
            LogUtil.INSTANCE.e(TAG, "start task... autoSendWelcome");
            sendEmptyMessageDelayed(MSG_SEND_WELCOME_MSG, (((long) random.nextInt(5)) + 5) * ((long) 1000));
        }
    }

    private void autoSendSubscribe() {
        DyConfig.DyReplySubscribe dyReplyWelcome = ConfigUtil.getInstance().getDyConfig().replySubscribe;
        if (dyReplyWelcome != null && dyReplyWelcome.status == 1) {
            LogUtil.INSTANCE.e(TAG, "start task... autoSendSubscribe");
            sendEmptyMessageDelayed(MSG_SEND_SUBSCRIBE_MSG, (((long) random.nextInt(5)) + 5) * ((long) 1000));
        }
    }

    private void autoSendBulletin() {
        DyConfig.DySendBulletin dySendBulletin = ConfigUtil.getInstance().getDyConfig().sendBulletin;
        if (dySendBulletin != null && dySendBulletin.status == 1) {
            int autoClickInterval = dySendBulletin.interval;
            if (autoClickInterval > 0) {
                LogUtil.INSTANCE.e(TAG, "start task... autoSendBulletin");
                sendEmptyMessageDelayed(MSG_AUTO_SEND_BULLETIN, ((long) autoClickInterval) * ((long) 1000));
            } else {
                LogUtil.INSTANCE.e(TAG, "autoClickThreshold. interval <= 0, not run...");
            }
        }
    }

    private void autoShopping() {
        DyConfig.DyAutoShopping shopping = ConfigUtil.getInstance().getDyConfig().autoShopping;
        if (shopping != null && shopping.status == 1) {
            int autoClickInterval = shopping.interval;
            if (autoClickInterval > 0) {
                LogUtil.INSTANCE.e(TAG, "start task... autoShopping");
                sendEmptyMessageDelayed(MSG_AUTO_SHOPPING, ((long) autoClickInterval) * ((long) 1000));
            } else {
                LogUtil.INSTANCE.e(TAG, "autoClickThreshold. interval <= 0, not run...");
            }
        }
    }

    private void autoSendGift() {
        DyConfig.DyAutoSendGift sendGift = ConfigUtil.getInstance().getDyConfig().autoSendGift;
        if (sendGift != null && sendGift.status == 1) {
            int autoClickInterval = sendGift.interval;
            if (autoClickInterval > 0) {
                LogUtil.INSTANCE.e(TAG, "start task... autoSendGift");
                sendEmptyMessageDelayed(MSG_AUTO_SEND_GIFT, ((long) autoClickInterval) * ((long) 1000));
            } else {
                LogUtil.INSTANCE.e(TAG, "autoClickThreshold. interval <= 0, not run...");
            }
        }
    }

    protected final void sendBulletin() {
        DyConfig.DySendBulletin sendBulletin = ConfigUtil.getInstance().getDyConfig().sendBulletin;
        if (sendBulletin == null) {
            return;
        }
        if (!TextUtils.isEmpty(sendBulletin.content)) {
            ChatRoomApi.INSTANCE.sendRoomTextMsg(sendBulletin.content, new EICommonCallback() {
                @Override
                public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

                }
            });
            autoSendBulletin();
            LogUtil.INSTANCE.e(TAG, "start task... sendBulletin");
        }
    }

    protected final void sendGift() {
        DyConfig.DyAutoSendGift sendGift = ConfigUtil.getInstance().getDyConfig().autoSendGift;
        if (sendGift == null) {
            return;
        }
        int dayCoinNum = ConfigUtil.getInstance().getComBean().getDayCoinNum();
        LogUtil.INSTANCE.e(TAG, "sendGift 刷币量 ..." + dayCoinNum);
        if (dayCoinNum >= sendGift.threshold) {
            LogUtil.INSTANCE.e(TAG, "sendGift 到达刷币上限 ...");
            return;
        }
        ConfigUtil.getInstance().setDayCoinNum(dayCoinNum + 1);
        if (sendGift.gift_json != null && !sendGift.gift_json.isEmpty()) {
            String gifName = sendGift.gift_json.get(random.nextInt(sendGift.gift_json.size()));
            ChatRoomApi.INSTANCE.sendGift(gifName, 1, new EICommonCallback() {
                @Override
                public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

                }
            });
            LogUtil.INSTANCE.e(TAG, "start task... sendGift");
            autoSendGift();
        }
    }

    protected final void sendSubscribeMsg() {
        DyConfig.DyReplySubscribe replySubscribe = ConfigUtil.getInstance().getDyConfig().replySubscribe;
        if (replySubscribe == null) {
            return;
        }
        if (TextUtils.isEmpty(replySubscribe.content)) {
            LogUtil.INSTANCE.e(TAG, "handleMemberMessage. empty replySubscribe");
            return;
        }
        LogUtil.INSTANCE.e(TAG, "start task... sendSubscribeMsg");
        sendCacheMsg(TYPE_SUBSCRIBE, replySubscribe.content);
        autoSendSubscribe();
    }

    protected final void sendShopping() {
        DyConfig.DyAutoShopping dyAutoShopping = ConfigUtil.getInstance().getDyConfig().autoShopping;
        if (dyAutoShopping == null) {
            return;
        }
        LogUtil.INSTANCE.e(TAG, "start task... sendShopping");
        ChatRoomApi.INSTANCE.sendLiveEvent(new EICommonCallback() {
            @Override
            public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

            }
        });
        autoShopping();
    }

    public final void sendClickMsg() {
        DyConfig.DyAutoClick dyAutoClick = ConfigUtil.getInstance().getDyConfig().autoClick;
        if (dyAutoClick == null) {
            return;
        }
        LogUtil.INSTANCE.e(TAG, "start task... sendClickMsg");
        ChatRoomApi.INSTANCE.digg(new EICommonCallback() {
            @Override
            public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

            }
        });
        autoClick();
    }


    public final void sendWelcomeMsg() {
        DyConfig.DyReplyWelcome welcome = ConfigUtil.getInstance().getDyConfig().replyWelcome;
        if (welcome == null) {
            return;
        }
        if (TextUtils.isEmpty(welcome.content)) {
            LogUtil.INSTANCE.e(TAG, "handleMemberMessage. empty replyWelcomeContent");
            return;
        }
        LogUtil.INSTANCE.e(TAG, "start task... sendWelcomeMsg");
        sendCacheMsg(TYPE_WELCOME, welcome.content);
        autoSendWelcome();
    }

    private final void sendEmptyMessageDelayed(int i, long j) {
        handler.removeMessages(i);
        handler.sendEmptyMessageDelayed(i, j);
    }

    private final void sendCacheMsg(int i, String str) {
        ArrayList arrayList = sendMsgCache.get(Integer.valueOf(i));
        if (arrayList != null) {
            if (!arrayList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList2 = new ArrayList();
                int length = str.length() + 1;
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (str2.length() + 1 + length > 40) {
                        break;
                    }
                    arrayList2.add(str2);
                    length += str2.length() + 1;
                    sb.append("@");
                    sb.append(str2);
                }
                if (!arrayList2.isEmpty()) {
                    arrayList.removeAll(arrayList2);
                    sb.append(" ");
                    sb.append(str);
                    String sb2 = sb.toString();
                    LogUtil.INSTANCE.e(TAG, "start task... sendCacheMsg");
                    ChatRoomApi.INSTANCE.sendRoomTextMsg(sb2, new EICommonCallback() {
                        @Override
                        public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

                        }
                    });
                }
            }
        }
    }

    public final void handleMessage(DyLiveMessage.DyMessage dyMessage) {
        if (dyMessage == null) {
            LogUtil.INSTANCE.e(TAG, "handleMessage. dyMessage == null");
            return;
        }
        if (dyMessage.messageType == LiveRoomMessage.MessageEnum.MEMBER.ordinal()) {
            handleMemberMessage(dyMessage);
        } else if (dyMessage.messageType == LiveRoomMessage.MessageEnum.CHAT.ordinal()) {
            handleTextMessage((DyLiveMessage.TextMessage) dyMessage);
        } else if (dyMessage.messageType == LiveRoomMessage.MessageEnum.GIFT.ordinal()) {
            handleGiftMessage((DyLiveMessage.GiftMessage) dyMessage);
        } else if (dyMessage.messageType == LiveRoomMessage.MessageEnum.SOCIAL.ordinal()) {
            handleSocialMessage((DyLiveMessage.SocialMessage) dyMessage);
        }
    }

    private final void handleSocialMessage(DyLiveMessage.SocialMessage socialMessage) {
        if (socialMessage == null || socialMessage.getLiveUser() == null ||  socialMessage.getLiveUser().getUser() == null ||  TextUtils.isEmpty(socialMessage.getLiveUser().getUser().nickName)) {
            return;
        }
        if (socialMessage.action != 1) {
        } else if (ConfigUtil.getInstance().getDyConfig() == null
            || ConfigUtil.getInstance().getDyConfig().replySubscribe == null
            || TextUtils.isEmpty(ConfigUtil.getInstance().getDyConfig().replySubscribe.content)) {
        } else {
            String nickName = socialMessage.getLiveUser().getUser().nickName;
            ArrayList arrayList = sendMsgCache.get(TYPE_SUBSCRIBE);
            if (arrayList == null) {
                arrayList = new ArrayList();
                sendMsgCache.put(TYPE_SUBSCRIBE, arrayList);
            }
            if (!arrayList.contains(nickName)) {
                arrayList.add(nickName);
            }
        }
    }

    private final void handleGiftMessage(DyLiveMessage.GiftMessage giftMessage) {
        if (giftMessage == null && giftMessage.getLiveUser() == null && giftMessage.getLiveUser().getUser() == null && TextUtils.isEmpty(giftMessage.getLiveUser().getUser().nickName)) {
            return;
        }
        DyConfig.DyReplyGift replyGift = ConfigUtil.getInstance().getDyConfig().replyGift;
        if (replyGift.status == 0) {
            return;
        }
        if (giftMessage != null && giftMessage.getGift() != null) {
            if (!giftMessage.getGift().combo || giftMessage.repeat_end != 0) {
                if (TextUtils.isEmpty(replyGift.content)) {
                    LogUtil.INSTANCE.e(TAG, "handleGiftMessage. empty replyGiftContent");
                    return;
                }
                String giftName = giftMessage.getGiftName();
                if (replyGift != null && replyGift.gift_json != null) {
                    for (String gift : replyGift.gift_json) {
                        if (TextUtils.equals(gift, giftName)) {
                            String nickName = giftMessage.getLiveUser().getUser().nickName;
                            ChatRoomApi.INSTANCE.sendRoomTextMsg("@" + nickName + " " + replyGift.content.replace("[礼物名称]", gift), new EICommonCallback() {
                                @Override
                                public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

                                }
                            });
                            break;
                        }
                    }
                }
            }
        }

    }

    private final void handleTextMessage(DyLiveMessage.TextMessage textMessage) {
        DyConfig.DyReplyKeyword replyKeyword = ConfigUtil.getInstance().getDyConfig().replyKeyword;
        if (replyKeyword.status == 0) {
            return;
        }
        if (replyKeyword == null || textMessage.getLiveUser() == null || textMessage.getLiveUser().getUser() == null || TextUtils.isEmpty(textMessage.getLiveUser().getUser().nickName)) {
            return;
        }
        if (!replyKeyword.data.isEmpty()) {
            String text = textMessage.content;
            for (DyConfig.DyReplyKeyValue keyValue : replyKeyword.data) {
                if (!TextUtils.isEmpty(keyValue.key) &&!TextUtils.isEmpty(text)&& text.contains(keyValue.key) && !TextUtils.isEmpty(keyValue.content)) {
                    String nickName = textMessage.getLiveUser().getUser().nickName;
                    ChatRoomApi.INSTANCE.sendRoomTextMsg("@" + nickName + " " + keyValue.content, new EICommonCallback() {
                        @Override
                        public void onResult(@Nullable Integer code, @Nullable String message, @Nullable Object obj) {

                        }
                    });
                    return;
                }
            }
        }
    }

    private final void handleMemberMessage(DyLiveMessage.DyMessage memberMessage) {
        DyConfig.DyReplyWelcome replyWelcome = ConfigUtil.getInstance().getDyConfig().replyWelcome;
        if (replyWelcome.status == 0) {
            return;
        }
        if (TextUtils.isEmpty(replyWelcome.content)) {
            LogUtil.INSTANCE.e(TAG, "handleMemberMessage. empty replyWelcomeContent");
            return;
        }
        if (memberMessage.getLiveUser() == null || memberMessage.getLiveUser().getUser() == null || TextUtils.isEmpty(memberMessage.getLiveUser().getUser().nickName)) {
            return;
        }
        String nickName = memberMessage.getLiveUser().getUser().nickName;
        ArrayList arrayList = sendMsgCache.get(TYPE_WELCOME);
        if (arrayList == null) {
            arrayList = new ArrayList();
            sendMsgCache.put(TYPE_WELCOME, arrayList);
        }
        if (!arrayList.contains(nickName)) {
            arrayList.add(nickName);
        }
    }

    public void stopTask() {
        LogUtil.INSTANCE.e(TAG, "stop  task...");
        sendMsgCache.clear();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
