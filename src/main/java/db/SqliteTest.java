package db;


public class SqliteTest {

    public static void main(String[] args) {
        //List<Wifi> result;
        //result = ApiExplorer.getAllList();
        //System.out.println(result.size());
        HandleSQLite sqLite = new HandleSQLite();
        //sqLite.getOpenApiWifiInfo();
        //List<History> result = sqLite.getAllHistory();
        sqLite.deleteHistoryData(11);
        System.out.println("삭제성공");
    }

}
