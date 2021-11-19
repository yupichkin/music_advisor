package advisor;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        String authServerUrl = Config.DEFAULT_AUTH_SERVER;
        String apiServerUrl = Config.DEFAULT_API_SERVER;
        int pageSize = Config.DEFAULT_PAGE_SIZE;

        if(args.length != 0) {
            for (int i = 0; i < args.length - 1; i++) {
                if (args[i].equals("-access")) {
                    authServerUrl = args[i + 1];
                }
                if (args[i].equals("-resource")) {
                    apiServerUrl = args[i + 1];
                }
                if (args[i].equals("-page")) {
                    pageSize = Integer.parseInt(args[i + 1]);
                }
            }
        }

        View.setPageSize(pageSize);
        Advisor advisor = new Advisor(authServerUrl, apiServerUrl);
        advisor.run(new Scanner(System.in));
    }


}
