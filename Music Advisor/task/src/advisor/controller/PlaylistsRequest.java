package advisor.controller;

import advisor.Config;
import advisor.Model;
import advisor.View;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Map;


public class PlaylistsRequest extends CategoriesRequest {

    @Override
    public void handleCommand(final String accessToken, final String apiPath, final String playlistName) {
        Map<String, String> categoriesMap = Model.getInstance().getCategoryIDs();
        if(categoriesMap.isEmpty()) {
            updateCategories(accessToken, apiPath);
            categoriesMap = Model.getInstance().getCategoryIDs();
        }
        String id = categoriesMap.get(playlistName.toLowerCase());
        if(id == null) {
            View.printUnknownCategory();
            return;
        }

        String newUriPath = Config.CATEGORIES_URL_PATH + "/" + id + Config.ID_CATEGORY_ADD_URL_PATH;
        JsonArray playlists = super.request(accessToken, apiPath, newUriPath)
                .get("playlists").getAsJsonObject()
                .get("items").getAsJsonArray();

        ArrayList<String> names = Model.parseNames(playlists);
        ArrayList<String> links = Model.parseLinks(playlists);

        View.printPlaylistsInfo(names, links);
    }

}