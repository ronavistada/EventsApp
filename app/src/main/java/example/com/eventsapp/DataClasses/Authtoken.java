package example.com.eventsapp.DataClasses;

import com.google.gson.annotations.SerializedName;

public class Authtoken {

    @SerializedName("token")
    private String privatetoken;

    public Authtoken(String token){
        this.privatetoken = token;
    }

    public void setToken(String token){
        this.privatetoken = token;
    }

    public String getToken(){
        return privatetoken;
    }
}
