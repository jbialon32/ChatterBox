package net.androidbootcamp.chatterbox.urlStuff;

public class BuildApiLink {

    private static String urlBase = "http://192.168.1.90/";
    //private String urlBase = "http://192.168.0.37/";
    //private String urlBase = "http://teamblues.x10host.com/MessageRefresh.php";

    /**********************************************************
     Builds full URL using the predefined base in this class
     @param path EX: "api/getMessages.php"
     @return "preredefined.url/path"
     @author James Bialon
     @since 1.1
     *********************************************************/

    public static String getApiLink(String path) {
        return urlBase + path;
    }

}
