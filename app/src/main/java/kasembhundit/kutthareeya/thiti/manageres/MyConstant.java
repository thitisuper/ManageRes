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
    private String urlGetOrderWhereIdRefAndStatus = "http://androidthai.in.th/whi/getOrderWhereIdRefAndStatus.php";
    private String urlGetUserWhereID = "http://androidthai.in.th/whi/getUserWhereID.php";
    private String urlPostStatusOrderMer = "http://androidthai.in.th/whi/postStatusOrder.php";
    private String urlPostNew = "http://androidthai.in.th/whi/addNew.php";
    private String urlGetOrderWhereUser = "http://androidthai.in.th/whi/getOrderWhereIdUser.php";
    private String urlPostProduct = "http://androidthai.in.th/whi/addProduct.php";
    private String urlUpdateProduct = "http://androidthai.in.th/whi/updateProduct.php";
    private String urlUpdateProductPromotion = "http://androidthai.in.th/whi/updateProductPromotion.php";
    private String urlUpdateNews = "http://androidthai.in.th/whi/updateNews.php";

    //For Spinner
    private String[] buildStrings = new String[]{"Build A", "Build B", "Build C"};
    private String[] roomAStrings = new String[]{"RA-001", "RA-002", "RA-003", "RA-004", "RA-005", "RA-006", "RA-007", "RA-008"};
    private String[] roomBStrings = new String[]{"RB-001", "RB-002", "RB-003", "RB-004", "RB-005", "RB-006", "RB-007"};
    private String[] roomCStrings = new String[]{"RC-001", "RC-002", "RC-003", "RC-004", "RC-005"};
    private String[] topping0 = new String[]{"ไม่มี", "ไข่ดาว", "ไข่เจียว", "เพิ่มเนื้อสัตว์", "เพิ่มผัก"};
    private String[] topping1 = new String[]{"ไม่มี", "เพิ่มผัก", "เพิ่มเนื้อสัตว์"};
    private String[] topping2 = new String[]{"ไม่มี", "เพิ่มผัก", "เพิ่มเนื้อสัตว์", "เพิ่มลูกชิ้น", "เพิ่มเส้น"};
    private String[] itemStrings = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String[] category = new String[]{"อาหารจานเดียว", "ประเภทกับข้าว", "ประเภทเส้น"};

    public int getTimeAnInt() {
        return timeAnInt;
    }

    //Delay Time
    private int timeAnInt = 1;  //Default ==> 30min

    public String getUrlUpdateNews() {
        return urlUpdateNews;
    }

    public String getUrlUpdateProductPromotion() {
        return urlUpdateProductPromotion;
    }

    public String getUrlUpdateProduct() {
        return urlUpdateProduct;
    }

    public String getUrlPostProduct() {
        return urlPostProduct;
    }

    public String getUrlGetOrderWhereUser() {
        return urlGetOrderWhereUser;
    }

    public String getUrlPostNew() {
        return urlPostNew;
    }

    public String getUrlPostStatusOrderMer() {
        return urlPostStatusOrderMer;
    }

    public String getUrlGetUserWhereID() {
        return urlGetUserWhereID;
    }

    public String getUrlGetOrderWhereIdRefAndStatus() {
        return urlGetOrderWhereIdRefAndStatus;
    }

    public String getUrlGetOrderWhereIdRef() {
        return urlGetOrderWhereIdRef;
    }

    public String getUrlGetAllProduct() {
        return urlGetAllProduct;
    }

    public String getUrlPostOrder() {
        return urlPostOrder;
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

    public String[] getCategory() {
        return category;
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
