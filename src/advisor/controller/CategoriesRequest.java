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

public class CategoriesRequest extends BaseController implements RequestMethod{

    @Override
    public void handleCommand(final String accessToken, final String apiPath, final String addOnArgument) {
        ArrayList<String> names = updateCategories(accessToken, apiPath);
        View.printCategories(names);
    }


    protected ArrayList<String> updateCategories(String accessToken, String apiPath) {
        String uriPath = Config.CATEGORIES_URL_PATH;
        JsonArray categories = super.request(accessToken, apiPath, uriPath)
                .get("categories").getAsJsonObject()
                .get("items").getAsJsonArray();
        Model model = Model.getInstance();
        Map<String, String> categoriesMap = Model.parseCategoryIds(categories);
        model.setCategoryIDs(categoriesMap);
        return Model.parseNames(categories);
    }

}
