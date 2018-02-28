package org.wah.cloned.im.tencent.security.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IMResponse{

    @SerializedName("ActionStatus")
    private String status;
    @SerializedName("ErrorCode")
    private Integer errorCode;
    @SerializedName("ErrorInfo")
    private String errorInfo;
}
