package kasembhundit.kutthareeya.thiti.manageres;

/**
 * Created by User on 29/5/2560.
 */

public class MyConstant {

    //For URL
    private String urlPostUser = "http://androidthai.in.th/whi/addUser.php";
    private String urlGetNews = "http://androidthai.in.th/whi/getNews.php";
    private String urlGetUser = "http://androidthai.in.th/whi/getUser.php";
    private String urlGetFoodFavorite = "http://androidthai.in.th/whi/getFoodWhereFavorite.php";
    private String urlGetFoodGroup = "http://androidthai.in.th/whi/getFoodWhereGroup.php";

    //For Spinner
    private String[] buildStrings = new String[]{"Build A", "Build B", "Build C"};
    private String[] roomAStrings = new String[]{"RA1", "2", "3", "4", "5", "6", "7", "8"};
    private String[] roomBStrings = new String[]{"RB1", "2", "3", "4", "5", "6", "7"};
    private String[] roomCStrings = new String[]{"RC1", "2", "3", "4", "5"};

    //for Database
    private String[] columnUserStrings = new String[]{
            "id",
            "Name",
            "Surname",
            "Build",
            "Room",
            "User",
            "Password",
            "Status"};

    public String getUrlGetFoodFavorite() {
        return urlGetFoodFavorite;
    }

    public String getUrlGetFoodGroup() {
        return urlGetFoodGroup;
    }

    public String[] getColumnUserStrings() {
        return columnUserStrings;
    }

    public String getUrlPostUser() {
        return urlPostUser;
    }

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
