package advisor;

public class Config {
    public static final String DEFAULT_AUTH_SERVER = "https://accounts.spotify.com";
    public static final String DEFAULT_API_SERVER = "https://api.spotify.com";
    public static final int DEFAULT_PAGE_SIZE = 5;

    public static final String FEATURES_URL_PATH = "/v1/browse/featured-playlists";
    public static final String NEW_URL_PATH = "/v1/browse/new-releases";
    public static final String CATEGORIES_URL_PATH = "/v1/browse/categories";
    public static final String ID_CATEGORY_ADD_URL_PATH = "/playlists";
    public static final String PROFILE_INFO_URL_PATH = "/v1/me";

    public static final String CLIENT_ID = "044c81d5455041e587d9f99b292aa8fc";
    public static final String CLIENT_SECRET = "42d13cf0937e4ba8be6c846c7cedb4ff";
    public static final String REDIRECT_URI = "http://localhost:8080";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String RESPONSE_TYPE = "code";

}
