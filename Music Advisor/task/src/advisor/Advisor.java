package advisor;

import advisor.controller.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Advisor {
    private Auth auth;
    private RequestMethod requestMethod;
    private final String apiServer;

    Advisor (String authServer, String apiServer) {
        auth = new Auth(authServer);
        this.apiServer = apiServer;
    }

    public void run(Scanner input) {
        View.printInfo();
        String userInput = input.nextLine();
        while (userInput != null) {
            if (userInput.equals("exit")) {
                System.out.println("---GOODBYE!---");
                return;
            } else if (userInput.equals("auth") || auth.isAuthorized()) {
                commandRequest(userInput);
            } else {
                System.out.println("Please, provide access for application.");
            }
            userInput = input.nextLine();
        }
    }

    private void commandRequest(String userInput) {
        if(userInput.contains("playlists")) {
            requestMethod = new PlaylistsRequest();
            requestMethod.handleCommand(auth.getAccessToken(), apiServer, userInput.replaceAll("playlists","").toLowerCase().trim());
            return;
        }
        switch (userInput) {
            case "auth":
                if(auth.isAuthorized()) {
                    View.printAuthAlready();
                    break;
                }
                auth.authorize();
                break;
            case "new":
                requestMethod = new NewRequest();
                requestMethod.handleCommand(auth.getAccessToken(), apiServer, "");
                break;
            case "featured":
                requestMethod = new FeaturedRequest();
                requestMethod.handleCommand(auth.getAccessToken(), apiServer, "");
                break;
            case "categories":
                requestMethod = new CategoriesRequest();
                requestMethod.handleCommand(auth.getAccessToken(), apiServer, "");
                break;
            case "names":
                requestMethod = new ProfileRequest();
                ArrayList<String> accessTokens = auth.getAllAccessTokens();
                for (int i = 0; i < accessTokens.size(); i++) {
                    requestMethod.handleCommand(accessTokens.get(i), apiServer, Config.PROFILE_INFO_URL_PATH);
                }

                break;
            case "prev":
                View.printPrevPage();
                break;
            case "next":
                View.printNextPage();
                break;
            case "help":
                View.printInfo();
                return;
            default:
                View.printUnknownCommand();
                break;
        }

    }
}
