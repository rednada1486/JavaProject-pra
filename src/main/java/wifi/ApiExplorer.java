package wifi;/* Java 1.8 샘플 코드 */

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ApiExplorer {
    public static List<Wifi> getAllList() {
        List<Wifi> wifiList = new ArrayList<>();
        int i=0;
        while (true){
            List<Wifi> curList = new ArrayList<>();

            try {
                curList = getWifiList(1000*(i)+1,1000*(i+1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(curList.size()==0){
                break;
            }else{
                wifiList.addAll(curList);
            }

            i++;
        }
        return wifiList;
    }


    public static List<Wifi> getWifiList(int from, int to) throws IOException {
        List<Wifi> result = new ArrayList<>();
        String start = Integer.toString(from);
        String end = Integer.toString(to);

        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("506868724272656436324b4b514a6b","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(start,"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(end,"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
        urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }


        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        String jsonData = sb.toString();
        //System.out.println(jsonData);
        JsonElement jsonElement = JsonParser.parseString(jsonData);
        JsonElement parseResult = jsonElement.getAsJsonObject().get("TbPublicWifiInfo");
        if(parseResult == null){
            return new ArrayList<>();
        }else{
            JsonArray jsonArray = (JsonArray) jsonElement.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject().get("row");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type listType = new TypeToken<ArrayList<Wifi>>(){}.getType();
            result = gson.fromJson(jsonArray.toString(), listType);
        }
        rd.close();
        conn.disconnect();
        return result;
    }




}