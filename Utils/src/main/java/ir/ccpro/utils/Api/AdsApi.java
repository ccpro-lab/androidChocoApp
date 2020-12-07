package ir.ccpro.utils.Api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.ccpro.utils.Models.AdsDataMoldel;
import ir.ccpro.utils.Models.NormalDataModel;
import ir.ccpro.utils.Models.VolleyCallback;

public class AdsApi extends Api {
    public AdsDataMoldel Get(final Context context, final VolleyCallback volleyCallback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                apiAddress + "api/ads",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyCallback.onSuccess(response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return null;
    }

    public void SaveVisit(final Context context, final VolleyCallback volleyCallback, long visitId, boolean isDesktop, boolean isMobile, String url) {
        Map<String, Object> params = new HashMap();
        params.put("id", visitId);
        params.put("url", url);
        params.put("isDesktop", isDesktop);
        params.put("isMobile", isMobile);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiAddress + "api/ads",
                parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyCallback.onSuccess(response.toString());

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

}
