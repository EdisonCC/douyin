package com.evan.dy.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DyConfig {
    public AssistantBindDy assistantBindDy;
    public MasterSwitchStatus masterSwitchStatus;
    public DyReplyWelcome replyWelcome;//欢迎语
    public DySendBulletin sendBulletin;//定时公告
    public DyAutoClick autoClick;//直播点赞
    public DyAutoSendGift autoSendGift;//自动刷礼物
    public DyAutoShopping autoShopping;//自动刷单
    public DyReplyGift replyGift;//礼物回复
    public DyReplyKeyword replyKeyword;//关键词回复
    public DyReplySubscribe replySubscribe;//关注回复
    public DySendFan sendFan;//粉丝群发消息
//    public DyAutoSubscribe autoSubscribe;//


    public static class DyReplyWelcome extends DyBaseConfig {
        public String content;
    }

    public static class AssistantBindDy extends DyBaseConfig {
        public String msg;
    }

    public static class DyBaseConfig implements Serializable{
        public String name;
        public int id;
        public int status;
    }

    public static class GiftList extends ArrayList<String> {
    }

    public static class MasterSwitchStatus extends DyBaseConfig {
        public int assistant_id;
        public int type;
    }


    public static class DyAutoClick extends DyBaseConfig {
        public int threshold;
    }

    public static class DyAutoSendGift extends DyBaseConfig {
        public List<String> gift_json;
        public int interval;
        public int threshold;
    }

    public static class DyAutoShopping extends DyBaseConfig {
        public int interval;
    }

//    public static class DyAutoSubscribe extends DyBaseConfig {
//        public int interval;
//        public int threshold;
//    }

    public static class DyReplyGift extends DyBaseConfig {
        public String content;
        public List<String> gift_json;
    }

    public static class DyReplyKeyword extends DyBaseConfig {
        public List<DyReplyKeyValue> data;
    }

    public static class DyReplyKeyValue implements Serializable {
        public String key;
        public String content;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DyReplyKeyValue keyValue = (DyReplyKeyValue) o;
            return Objects.equals(key, keyValue.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    public static class DyReplySubscribe extends DyBaseConfig {
        public String content;
    }


    public static class DySendFan extends DyBaseConfig {
        public String content;
        public int interval;
        public int max_send_num;
        public int sex_type;
        public int subscribe_type;
    }

    public static class DySendBulletin extends DyBaseConfig {
        public String content;
        public int interval;
    }
}
