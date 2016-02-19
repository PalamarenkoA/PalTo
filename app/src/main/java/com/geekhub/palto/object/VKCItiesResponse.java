
package com.geekhub.palto.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VKCItiesResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * 
     * @return
     *     The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

}
