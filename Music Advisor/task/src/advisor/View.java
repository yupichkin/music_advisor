package advisor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class View {
    private static int pagesCount = 0;
    private static int currentPosition = 0;
    private static int pageSize = 5;


    private static ArrayList<String> output = null;


    public static void setPageSize(int pageSize) {
        View.pageSize = pageSize;
    }


    public static void printPage() {
        for (int i = 0; i < pageSize && currentPosition * pageSize + i < output.size(); i++) {
            System.out.println(output.get(currentPosition * pageSize + i));
        }
        System.out.println("---PAGE " + (currentPosition + 1) + " OF " + pagesCount + "---");
    }

    public static void printNextPage() {
        if(currentPosition + 1 == pagesCount) {
            System.out.println("No more pages.");
            return;
        }
        currentPosition++;
        printPage();
    }

    public static void printPrevPage() {
        if(currentPosition == 0) {
            System.out.println("No more pages.");
            return;
        }
        currentPosition--;
        printPage();
    }

    public static void printCategories(ArrayList<String> output) {
        View.output = output;
        currentPosition = 0;
        pagesCount = (int)Math.ceil((double)output.size() / pageSize);
        printPage();
    }

    public static void printPlaylistsInfo(ArrayList<String> names, ArrayList<String> links) {
        output = new ArrayList<String>(names.size());
        currentPosition = 0;
        for (int i = 0; i < names.size(); i++) {
            output.add(names.get(i) + "\n" + links.get(i) + "\n");
        }
        pagesCount = (int)Math.ceil((double)output.size() / pageSize);
        printPage();
    }

    public static void printSongInfo(ArrayList<String> songNames, ArrayList<String> songArtists, ArrayList<String> links) {
        output = new ArrayList<String>(songNames.size());
        currentPosition = 0;
        for (int i = 0; i < songNames.size(); i++) {
            output.add(songNames.get(i) + "\n" + songArtists.get(i) + "\n" + links.get(i) + "\n");
        }
        pagesCount = (int)Math.ceil((double)output.size() / pageSize);
        printPage();
    }
    public static void printUnknownCommand() {
        System.out.println("Unknown command.");
    }

    public static void printUnknownCategory() {
        System.out.println("Unknown category name.");
    }

    public static void printInfo() {
        System.out.println("Supported commands: " +
                "auth - prints link for authentication\n" +
                "featured — list of Spotify featured playlists with their links fetched from API.\n" +
                "new — list of new albums with artists and links on Spotify.\n" +
                "categories — list of all available categories on Spotify (just their names).\n" +
                "playlists C_NAME, where C_NAME — name of category. List contains playlists of this category and their links on Spotify.\n" +
                "next - prints next page.\n" +
                "prev - prints prev page.\n" +
                "help - prints prints commands info"
        );
    }
    public static void printAuthAlready() {
        System.out.println("You are already logged in!");
    }
}
