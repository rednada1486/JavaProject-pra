package db;

import wifi.ApiExplorer;

import wifi.Wifi;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandleSQLite {

    public String getOpenApiWifiInfo(){
        createWifiTable();
        List<Wifi> result;
        result = ApiExplorer.getAllList();
        insertWifiData(result);
        int amount = result.size();

        StringBuffer sb = new StringBuffer();
        sb.append(amount);
        sb.append("개의 WIFI 정보를 정상적으로 저장하였습니다.");
        System.out.println(sb);
        return sb.toString();
    }

    public List<Wifi> getNearByWifiInfo(double x, double y){
    	createWifiTableAgain();
        List<Wifi> result = getWifies(x,y);
        Collections.sort(result);
        int size = result.size();
        if(size>20) {
        	result = new ArrayList<>(result.subList(0, 20));
        }
        
        if(size>=1) {
            createHistoryTable();
            createHistoryDetailTable();
            insertHistoryData(x,y);
            int historyID = getHistoryID(x,y);
            System.out.println(historyID);
            insertHistoryDetail(result,historyID);
        }
        return result;
    }




    public void findUser(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database Successfully");

            String sql = " select * from Person; ";
            preparedStatement = connection.prepareStatement(sql);

            //boolean isCreate = preparedStatement.execute();
            //System.out.println(isCreate);

            rs = preparedStatement.executeQuery();

            while (rs.next()){
                String user = rs.getString("Birthday");
                String password =  rs.getString("Name");
                System.out.println(user+ ", "+password);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


    }

    public void createWifiTable(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");
            statement = connection.createStatement();
            String sql =" CREATE TABLE 'Wifi' ( " +
                    " 'X_SWIFI_MGR_NO' TEXT NOT NULL, " +
                    " 'X_SWIFI_WRDOFC' TEXT, " +
                    " 'X_SWIFI_MAIN_NM' TEXT, " +
                    " 'X_SWIFI_ADRES1' TEXT, " +
                    " 'X_SWIFI_ADRES2' TEXT, " +
                    " 'X_SWIFI_INSTL_FLOOR' TEXT, " +
                    " 'X_SWIFI_INSTL_TY' TEXT, " +
                    " 'X_SWIFI_INSTL_MBY' TEXT, " +
                    " 'X_SWIFI_SVC_SE' TEXT, " +
                    " 'X_SWIFI_CMCWR' TEXT, " +
                    " 'X_SWIFI_CNSTC_YEAR' TEXT, " +
                    " 'X_SWIFI_INOUT_DOOR' TEXT, " +
                    " 'X_SWIFI_REMARS3' TEXT, " +
                    " 'LAT' REAL, " +
                    " 'LNT' REAL, " +
                    " 'WORK_DTTM' TEXT, " +
                    " PRIMARY KEY('X_SWIFI_MGR_NO') " +
                    " ); " ;

            try {
                statement.executeUpdate(sql);

            }catch (SQLException e){
                System.out.println("테이블이 이미 생성되어 있습니다.");
                statement.executeUpdate(" DROP TABLE Wifi; ");
                System.out.println("테이블 다시 생성 중 ");
                statement.executeUpdate(sql);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {

            try {
                if(statement!=null && !statement.isClosed()){
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


    }

    
    
    public void createWifiTableAgain(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");
            statement = connection.createStatement();
            String sql =" CREATE TABLE 'Wifi' ( " +
                    " 'X_SWIFI_MGR_NO' TEXT NOT NULL, " +
                    " 'X_SWIFI_WRDOFC' TEXT, " +
                    " 'X_SWIFI_MAIN_NM' TEXT, " +
                    " 'X_SWIFI_ADRES1' TEXT, " +
                    " 'X_SWIFI_ADRES2' TEXT, " +
                    " 'X_SWIFI_INSTL_FLOOR' TEXT, " +
                    " 'X_SWIFI_INSTL_TY' TEXT, " +
                    " 'X_SWIFI_INSTL_MBY' TEXT, " +
                    " 'X_SWIFI_SVC_SE' TEXT, " +
                    " 'X_SWIFI_CMCWR' TEXT, " +
                    " 'X_SWIFI_CNSTC_YEAR' TEXT, " +
                    " 'X_SWIFI_INOUT_DOOR' TEXT, " +
                    " 'X_SWIFI_REMARS3' TEXT, " +
                    " 'LAT' REAL, " +
                    " 'LNT' REAL, " +
                    " 'WORK_DTTM' TEXT, " +
                    " PRIMARY KEY('X_SWIFI_MGR_NO') " +
                    " ); " ;

            try {
                statement.executeUpdate(sql);
            }catch (SQLException e){
                //System.out.println("테이블이 이미 생성되어 있습니다.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {

            try {
                if(statement!=null && !statement.isClosed()){
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


    }
    
    public void insertWifiData(List<Wifi> Wifies){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            for(int i=0; i<Wifies.size(); i++){
                Wifi curWifi = Wifies.get(i);
                String sql = " insert into Wifi (X_SWIFI_MGR_NO, " +
                        "                  X_SWIFI_WRDOFC, " +
                        "                  X_SWIFI_MAIN_NM, " +
                        "                  X_SWIFI_ADRES1, " +
                        "                  X_SWIFI_ADRES2, " +
                        "                  X_SWIFI_INSTL_FLOOR, " +
                        "                  X_SWIFI_INSTL_TY, " +
                        "                  X_SWIFI_INSTL_MBY, " +
                        "                  X_SWIFI_SVC_SE, " +
                        "                  X_SWIFI_CMCWR, " +
                        "                  X_SWIFI_CNSTC_YEAR, " +
                        "                  X_SWIFI_INOUT_DOOR, " +
                        "                  X_SWIFI_REMARS3, " +
                        "                  LAT, " +
                        "                  LNT, " +
                        "                  WORK_DTTM " +
                        "                  ) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,curWifi.X_SWIFI_MGR_NO);
                preparedStatement.setString(2, curWifi.X_SWIFI_WRDOFC);
                preparedStatement.setString(3, curWifi.X_SWIFI_MAIN_NM);
                preparedStatement.setString(4, curWifi.X_SWIFI_ADRES1);
                preparedStatement.setString(5, curWifi.X_SWIFI_ADRES2);
                preparedStatement.setString(6, curWifi.X_SWIFI_INSTL_FLOOR);
                preparedStatement.setString(7, curWifi.X_SWIFI_INSTL_TY);
                preparedStatement.setString(8, curWifi.X_SWIFI_INSTL_MBY);
                preparedStatement.setString(9, curWifi.X_SWIFI_SVC_SE);
                preparedStatement.setString(10, curWifi.X_SWIFI_CMCWR);
                preparedStatement.setString(11, curWifi.X_SWIFI_CNSTC_YEAR);
                preparedStatement.setString(12, curWifi.X_SWIFI_INOUT_DOOR);
                preparedStatement.setString(13, curWifi.X_SWIFI_REMARS3);
                preparedStatement.setString(14, curWifi.LNT);
                preparedStatement.setString(15, curWifi.LAT);
                preparedStatement.setString(16, curWifi.WORK_DTTM);

                try {
                    int affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows>0){
                        //System.out.println("저장 성공");
                    }else{
                        //System.out.println("저장 실패");
                    }
                }catch (SQLException e){
                    //System.out.println("저장 실패");
                }

                try {
                    if(preparedStatement!=null && !preparedStatement.isClosed()){
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }



    }

    public List<Wifi> getWifies(double x, double y){
        List<Wifi> result = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            String sql = " SELECT * from Wifi; ";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()){
                String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY") ;
                String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
                String LAT = rs.getString("LAT");
                String LNT = rs.getString("LNT");
                String WORK_DTTM = rs.getString("WORK_DTTM") ;

                Wifi curWifi = new Wifi(X_SWIFI_MGR_NO,X_SWIFI_WRDOFC,X_SWIFI_MAIN_NM,X_SWIFI_ADRES1,X_SWIFI_ADRES2,X_SWIFI_INSTL_FLOOR,X_SWIFI_INSTL_TY,X_SWIFI_INSTL_MBY,X_SWIFI_SVC_SE,X_SWIFI_CMCWR,X_SWIFI_CNSTC_YEAR,X_SWIFI_INOUT_DOOR,X_SWIFI_REMARS3,LAT,LNT,WORK_DTTM);
                curWifi.DISTANCE = LocationDistance.distance(x,y,Double.parseDouble(LAT),Double.parseDouble(LNT),"kilometer");

                result.add(curWifi);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        return result;
    }

    public void createHistoryTable(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");
            statement = connection.createStatement();

            String sql = " CREATE TABLE 'History' ( " +
                    " 'ID' INTEGER NOT NULL, " +
                    " 'LAT' REAL, " +
                    " 'LNT' REAL, " +
                    " 'DATE' TEXT, " +
                    " PRIMARY KEY('ID' AUTOINCREMENT) " +
                    ");";

            try {
                statement.executeUpdate(sql);
            }catch (SQLException e){
                //System.out.println("테이블이 이미 생성되어 있습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(statement!=null && !statement.isClosed()){
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void insertHistoryData(double x, double y){

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database Successfully");

            String sql = " INSERT INTO History (LAT, LNT,DATE) " +
                    " VALUES ( ?, ?, strftime('%Y-%m-%dT%H:%M:%S', 'now', 'localtime')); " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, x);
            preparedStatement.setDouble(2, y);


            try {
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows>0){
                    System.out.println("저장 성공");
                }else{
                    System.out.println("저장 실패");
                }
            }catch (SQLException e){
                System.out.println("저장 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }

    public int getHistoryID(double x, double y){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int ID = 0;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");


            String sql = " SELECT max(ID) from History where LAT=? and LNT=?; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(x));
            preparedStatement.setString(2, String.valueOf(y));


            rs = preparedStatement.executeQuery();

            while (rs.next()){
                ID =  rs.getInt("max(ID)");
                System.out.println(ID);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        return ID;
    }
    
    public List<History> getAllHistory(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<History> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            String sql = " SELECT * from History ; ";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()){
                History cur = new History();
                cur.ID = rs.getInt("ID");
                cur.LAT = rs.getString("LAT");
                cur.LNT = rs.getString("LNT");
                cur.DATE = rs.getString("DATE");
                result.add(cur);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        return result;
    }
    
    
    public History getOneHistory(int historyID){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        History result = new History();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            String sql = " SELECT * FROM History WHERE ID=? ; ";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, historyID);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                result.ID = rs.getInt("ID");
                result.LAT = rs.getString("LAT");
                result.LNT = rs.getString("LNT");
                result.DATE = rs.getString("DATE");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        return result;
    }
    
    
    public void deleteHistoryData(int id){

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            String sql = " DELETE FROM History WHERE ID=?; " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, id);


            try {
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows>0){
                    System.out.println("삭제 성공");
                }else{
                    System.out.println("없는 데이터 삭제 실패");
                }
            }catch (SQLException e){
                System.out.println("삭제 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }
    
    
    
    public void createHistoryDetailTable(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");
            statement = connection.createStatement();
            String sql =" CREATE TABLE 'HistoryDetail' ( " +
            		" 'HistoryID' INTEGER NOT NULL, " +
            		" 'DISTANCE' INTEGER, " +
                    " 'X_SWIFI_MGR_NO' TEXT NOT NULL, " +
                    " 'X_SWIFI_WRDOFC' TEXT, " +
                    " 'X_SWIFI_MAIN_NM' TEXT, " +
                    " 'X_SWIFI_ADRES1' TEXT, " +
                    " 'X_SWIFI_ADRES2' TEXT, " +
                    " 'X_SWIFI_INSTL_FLOOR' TEXT, " +
                    " 'X_SWIFI_INSTL_TY' TEXT, " +
                    " 'X_SWIFI_INSTL_MBY' TEXT, " +
                    " 'X_SWIFI_SVC_SE' TEXT, " +
                    " 'X_SWIFI_CMCWR' TEXT, " +
                    " 'X_SWIFI_CNSTC_YEAR' TEXT, " +
                    " 'X_SWIFI_INOUT_DOOR' TEXT, " +
                    " 'X_SWIFI_REMARS3' TEXT, " +
                    " 'LAT' REAL, " +
                    " 'LNT' REAL, " +
                    " 'WORK_DTTM' TEXT " +
                    " ); " ;

            try {
                statement.executeUpdate(sql);

            }catch (SQLException e){
                System.out.println("테이블이 이미 생성되어 있습니다.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {

            try {
                if(statement!=null && !statement.isClosed()){
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


    }

    public void insertHistoryDetail(List<Wifi> Wifies, int historyID){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            for(int i=0; i<Wifies.size(); i++){
                Wifi curWifi = Wifies.get(i);
                System.out.println(curWifi);
                String sql = " insert into HistoryDetail (HistoryID, " +
                		"                  DISTANCE, " +
                		"                  X_SWIFI_MGR_NO, " +
                        "                  X_SWIFI_WRDOFC, " +
                        "                  X_SWIFI_MAIN_NM, " +
                        "                  X_SWIFI_ADRES1, " +
                        "                  X_SWIFI_ADRES2, " +
                        "                  X_SWIFI_INSTL_FLOOR, " +
                        "                  X_SWIFI_INSTL_TY, " +
                        "                  X_SWIFI_INSTL_MBY, " +
                        "                  X_SWIFI_SVC_SE, " +
                        "                  X_SWIFI_CMCWR, " +
                        "                  X_SWIFI_CNSTC_YEAR, " +
                        "                  X_SWIFI_INOUT_DOOR, " +
                        "                  X_SWIFI_REMARS3, " +
                        "                  LAT, " +
                        "                  LNT, " +
                        "                  WORK_DTTM " +
                        "                  ) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, historyID);
                preparedStatement.setDouble(2, curWifi.DISTANCE);
                preparedStatement.setString(3,curWifi.X_SWIFI_MGR_NO);
                preparedStatement.setString(4, curWifi.X_SWIFI_WRDOFC);
                preparedStatement.setString(5, curWifi.X_SWIFI_MAIN_NM);
                preparedStatement.setString(6, curWifi.X_SWIFI_ADRES1);
                preparedStatement.setString(7, curWifi.X_SWIFI_ADRES2);
                preparedStatement.setString(8, curWifi.X_SWIFI_INSTL_FLOOR);
                preparedStatement.setString(9, curWifi.X_SWIFI_INSTL_TY);
                preparedStatement.setString(10, curWifi.X_SWIFI_INSTL_MBY);
                preparedStatement.setString(11, curWifi.X_SWIFI_SVC_SE);
                preparedStatement.setString(12, curWifi.X_SWIFI_CMCWR);
                preparedStatement.setString(13, curWifi.X_SWIFI_CNSTC_YEAR);
                preparedStatement.setString(14, curWifi.X_SWIFI_INOUT_DOOR);
                preparedStatement.setString(15, curWifi.X_SWIFI_REMARS3);
                preparedStatement.setString(16, curWifi.LAT);
                preparedStatement.setString(17, curWifi.LNT);
                preparedStatement.setString(18, curWifi.WORK_DTTM);

                try {
                    int affectedRows = preparedStatement.executeUpdate();
                    if(affectedRows>0){
                        System.out.println("히스토리 디테일 저장 성공");
                    }else{
                        System.out.println("히스토리 디테일 저장 실패");
                    }
                }catch (SQLException e){
                    System.out.println("히스토리 디테일 저장 실패");
                }

                try {
                    if(preparedStatement!=null && !preparedStatement.isClosed()){
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }



    }

    public List<Wifi> getAllHistoryDetail(int historyID){
    	
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Wifi> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            String sql = " SELECT * from HistoryDetail WHERE HistoryID=? ; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, historyID);
            
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY") ;
                String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
                String LAT = rs.getString("LAT");
                String LNT = rs.getString("LNT");
                String WORK_DTTM = rs.getString("WORK_DTTM") ;
                double DISTANCE = rs.getDouble("DISTANCE");
                int HistoryID = rs.getInt("HistoryID");

                Wifi curWifi = new Wifi(X_SWIFI_MGR_NO,X_SWIFI_WRDOFC,X_SWIFI_MAIN_NM,X_SWIFI_ADRES1,X_SWIFI_ADRES2,X_SWIFI_INSTL_FLOOR,X_SWIFI_INSTL_TY,X_SWIFI_INSTL_MBY,X_SWIFI_SVC_SE,X_SWIFI_CMCWR,X_SWIFI_CNSTC_YEAR,X_SWIFI_INOUT_DOOR,X_SWIFI_REMARS3,LAT,LNT,WORK_DTTM);
                curWifi.DISTANCE = DISTANCE;
                curWifi.HistoryID = HistoryID;

                result.add(curWifi);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        return result;
    }
    
    
    public void deleteHistoryDetail(int id){

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            //System.out.println("Opened database Successfully");

            String sql = " DELETE FROM HistoryDetail WHERE HistoryID=?; " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, id);


            try {
                int affectedRows = preparedStatement.executeUpdate();
                if(affectedRows>0){
                    System.out.println("삭제 성공");
                }else{
                    System.out.println("없는 데이터 삭제 실패");
                }
            }catch (SQLException e){
                System.out.println("삭제 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(preparedStatement!=null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }
    
    
   
    
    
    
}
