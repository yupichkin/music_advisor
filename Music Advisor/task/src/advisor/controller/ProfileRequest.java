package advisor.controller;

import advisor.Config;
import advisor.Model;
import advisor.View;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProfileRequest extends BaseController implements RequestMethod {

    @Override
    public void handleCommand(final String accessToken, final String apiPath, final String addOnArgument) {
        JsonObject profileData = request(accessToken, apiPath, addOnArgument);
        String name = profileData.get("display_name").getAsString();
        System.out.println(name);
        //ArrayList<String> names = updateCategories(accessToken, apiPath);
        //View.printCategories(names);
    }

}
