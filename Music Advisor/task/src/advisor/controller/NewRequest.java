package advisor.controller;

import advisor.Config;
import advisor.Model;
import advisor.View;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;


public class NewRequest extends BaseController implements RequestMethod{

    @Override
    public void handleCommand(final String accessToken, final String apiPath, final String addOnArgument) {
        JsonArray newReleases = super.request(accessToken, apiPath, Config.NEW_URL_PATH)
                .get("albums").getAsJsonObject()
                .get("items").getAsJsonArray();

        ArrayList<String> songNames   = Model.parseNames(newReleases);
        ArrayList<String> songArtists = Model.parseArtistsNames(newReleases);
        ArrayList<String> links       = Model.parseLinks(newReleases);

        View.printSongInfo(songNames, songArtists, links);
    }
}
