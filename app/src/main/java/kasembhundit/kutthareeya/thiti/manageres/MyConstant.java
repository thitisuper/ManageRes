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
    private String urlGetProductWhereID = "http://androidthai.in.th/whi/getProduceWhereID.php";
    private String urlPostOrder = "http://androidthai.in.th/whi/addOrder.php";
    private String urlGetAllProduct = "http://androidthai.in.th/whi/getAllProduct.php";
    private String urlGetOrderWhereIdRef = "http://androidthai.in.th/whi/getOrderWhereIdRef.php";

    //For Spinner
    private String[] buildStrings = new String[]{"Build A", "Build B", "Build C"};
    private String[] roomAStrings = new String[]{"RA1", "2", "3", "4", "5", "6", "7", "8"};
    private String[] roomBStrings = new String[]{"RB1", "2", "3", "4", "5", "6", "7"};
    private String[] roomCStrings = new String[]{"RC1", "2", "3", "4", "5"};
    private String[] topping0 = new String[]{"ไม่มี", "ไข่ดาว", "ไข่เจียว", "เพิ่มเนื้อสัตว์", "เพิ่มผัก"};
    private String[] topping1 = new String[]{"ไม่มี", "เพิ่มผัก", "เพิ่มเนื้อสัตว์"};
    private String[] topping2 = new String[]{"ไม่มี", "เพิ่มผัก", "เพิ่มเนื้อสัตว์", "เพิ่มลูกชิ้น", "เพิ่มเส้น"};
    private String[] itemStrings = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String[] receiveTimeStrings = new String[]{"CurrentTime", "10.00-14.00", "14:00-18.00"};

    public int getTimeAnInt() {
        return timeAnInt;
    }

    //Delay Time
    private int timeAnInt = 1;  //Default ==> 30min

    public String getUrlGetOrderWhereIdRef() {
        return urlGetOrderWhereIdRef;
    }

    public String getUrlGetAllProduct() {
        return urlGetAllProduct;
    }

    public String getUrlPostOrder() {
        return urlPostOrder;
    }

    public String[] getReceiveTimeStrings() {
        return receiveTimeStrings;
    }

    public String[] getItemStrings() {
        return itemStrings;
    }

    public String getUrlGetProductWhereID() {
        return urlGetProductWhereID;
    }

    public String[] getTopping0() {
        return topping0;
    }

    public String[] getTopping1() {
        return topping1;
    }

    public String[] getTopping2() {
        return topping2;
    }

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
