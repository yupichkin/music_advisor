package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private static Model instance = new Model();
    private Map<String, String> categoryIDs = new HashMap<>();

    private Model() { }

    public static Model getInstance() {
        return instance;
    }

    public Map<String, String> getCategoryIDs() {
        return categoryIDs;
    }

    public void setCategoryIDs(Map<String, String> categoryIDs) {
        this.categoryIDs = categoryIDs;
    }

    public static Map<String, String> parseCategoryIds(JsonArray categories) {
        Map<String, String> result = new HashMap<String, String>(categories.size());
        String name;
        String id;
        for (int i = 0; i < categories.size(); i++) {
            JsonObject category = categories.get(i).getAsJsonObject();
            id = getId(category);
            name = getName(category).toLowerCase();         //for ignore comparing conflicts
            result.put(name, id);
        }
        return result;
    }

    public static ArrayList<String> parseNames(JsonArray array) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject playlist = array.get(i).getAsJsonObject();
            result.add(getName(playlist));
        }
        return result;
    }

    public static ArrayList<String> parseArtistsNames(JsonArray array) {
        ArrayList<String> result = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            JsonObject song = array.get(i).getAsJsonObject();
            JsonArray artistsArray = song.get("artists").getAsJsonArray();
            String[] names = new String[artistsArray.size()];
            for (int j = 0; j < artistsArray.size(); j++) {
                names[j] = getName(artistsArray.get(j).getAsJsonObject());
            }
            result.add(Arrays.toString(names));
        }
        return result;
    }

    public static ArrayList<String> parseLinks(JsonArray array) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject playlist = array.get(i).getAsJsonObject();
            result.add(getSpotifyLink(playlist));
        }
        return result;
    }

    private static String getId(JsonObject jsonObject) {
        return jsonObject.get("id")
                .toString()
                .replace("\"", "");
    }
    private static String getName(JsonObject jsonObject) {
        return jsonObject.get("name")
                .toString()
                .replace("\"", "");
    }
    private static String getSpotifyLink(JsonObject jsonObject) {
        return jsonObject.get("external_urls")
                .getAsJsonObject()
                .get("spotify")
                .toString()
                .replace("\"", "");
    }
}
