package kasembhundit.kutthareeya.thiti.manageres;

/**
 * Created by User on 29/5/2560.
 */

public class MyConstant {

    //For URL
    private String urlGetNews = "http://androidthai.in.th/whi/getNews.php";
    private String urlGetUser = "http://androidthai.in.th/whi/getUser.php";

    //For Spinner
    private String[] buildStrings = new String[]{"Build A", "Build Bฺ", "Build C"};
    private String[] roomAStrings = new String[]{"RA1", "2", "3", "4", "5", "6", "7", "8"};
    private String[] roomBStrings = new String[]{"RB1", "2", "3", "4", "5", "6", "7"};
    private String[] roomCStrings = new String[]{"RC1", "2", "3", "4", "5"};

    public String getUrlGetUser() {
        return urlGetUser;
    }

    public String[] getBuildStrings() {
        return buildStrings;
    }

    public String[] getRoomAStrings() {
        return roomAStrings;
    }

    public String[] getRoomBStrings() {
        return roomBStrings;
    }

    public String[] getRoomCStrings() {
        return roomCStrings;
    }

    public String getUrlGetNews() {
        return urlGetNews;
    }
}   // Main Class
