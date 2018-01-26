package org.wah.cloned.commons.security.converter;

import com.google.gson.Gson;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.wah.doraemon.utils.GsonUtils;

public class WsonHttpMessageConverter extends GsonHttpMessageConverter{

    private static Gson gson;

    public WsonHttpMessageConverter(){
        super.setGson(this.getGson());
    }

    public Gson getGson(){
        if(gson == null){
            gson = GsonUtils.getGson(false, false, true, null);
        }

        return gson;
    }
}
