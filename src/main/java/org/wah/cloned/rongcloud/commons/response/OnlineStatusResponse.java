package org.wah.cloned.rongcloud.commons.response;

import org.wah.cloned.rongcloud.commons.security.consts.OnlineStatus;

public class OnlineStatusResponse extends CodeResponse{

    private OnlineStatus status;

    public OnlineStatusResponse(){

    }

    public OnlineStatus getStatus(){
        return status;
    }

    public void setStatus(OnlineStatus status){
        this.status = status;
    }
}
