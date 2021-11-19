package advisor.controller;


import advisor.Config;
import advisor.Model;
import advisor.View;
import com.google.gson.JsonArray;



import java.io.IOException;

import java.util.ArrayList;

public class FeaturedRequest extends BaseController implements RequestMethod{

    @Override
    public void handleCommand(final String accessToken, final String apiPath, final String addOnArgument) {
        JsonArray playlists = super.request(accessToken, apiPath, Config.FEATURES_URL_PATH)
                .get("playlists").getAsJsonObject()
                .get("items").getAsJsonArray();

        ArrayList<String> names = Model.parseNames(playlists);
        ArrayList<String> links = Model.parseLinks(playlists);

        View.printPlaylistsInfo(names, links);
    }
}
