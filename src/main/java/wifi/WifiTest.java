package wifi;


import java.util.List;

public class WifiTest {
    public static void main(String[] args) {
        List<Wifi> result;
        result = ApiExplorer.getAllList();
        System.out.println(result);
    }
}
