package edu.esprit.crudoff.services;

import org.json.JSONObject;

public interface AuthCallback {
    void onLoginSuccess(JSONObject js);
    void onLoginFailure(Exception e);
}
