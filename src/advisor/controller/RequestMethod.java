package advisor.controller;

import java.io.IOException;

public interface RequestMethod {
    void handleCommand(final String accessToken,
                       final String apiPath,
                       final String addOnArgument); //third argument for playlists
}
