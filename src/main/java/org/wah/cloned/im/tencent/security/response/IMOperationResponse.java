package org.wah.cloned.im.tencent.security.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class IMOperationResponse extends IMResponse{

    @SerializedName("Result")
    private List<Map<String, String>> result;
}
