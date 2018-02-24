package org.wah.cloned.bot.enums;

import lombok.Getter;

/**
 * API URL
 */
@Getter
public enum ApiURL{

    IMAGE("%s/webwxgetmsgimg?msgid=%s&skey=%s", ".jpg", "images"),
    HEAD_IMG("%s/webwxgetheadimg?username=%s&skey=%s", ".jpg", "headimg"),
    ICON("%s/webwxgeticon?username=%s&skey=%s", ".jpg", "icons"),
    VOICE("%s/webwxgetvoice?msgid=%s&skey=%s", ".mp3", "voice"),
    VIDEO("%s/webwxgetvideo?msgid=%s&skey=%s", ".mp4", "video");

    private String url;
    private String suffix;
    private String dir;

    private ApiURL(String url){
        this.url = url;
    }

    private ApiURL(String url, String suffix, String dir){
        this.url = url;
        this.suffix = suffix;
        this.dir = dir;
    }
}
