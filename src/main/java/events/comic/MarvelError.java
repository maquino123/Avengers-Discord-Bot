package events.comic;

import com.google.gson.annotations.SerializedName;

public class MarvelError {
    @SerializedName("code") private String code;
    @SerializedName("message") private String message;
    @SerializedName("status") private String status = "";

    String getCode(){
        return code;
    }

    String getMessage(){
        return message;
    }

    String getStatus(){
        return status;
    }
}
