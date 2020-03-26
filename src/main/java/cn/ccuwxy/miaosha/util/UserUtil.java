package cn.ccuwxy.miaosha.util;

import cn.ccuwxy.miaosha.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
    public static void main(String[] args) throws Exception {
        creatUser(5000);
    }
    private static void creatUser(int count) throws Exception {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId((long)(15944441110L + i));
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterData(new Date());
            user.setLastLoginDate(new Date());
            user.setSalt("qazwsxedc");
            user.setPassword(MD5Util.inputPassToDbPass("12345678", user.getSalt()));
            user.setLoginCount(0);
//            System.out.println(user.getId());
            users.add(user);
        }
//        System.out.println("creat user");
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/miaosha","root","1528154605");
//        String sql = "insert into user(id,nickname,password,salt,head,register_data,last_login_date,login_count) values(?,?,?,?,?,?,?,?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            pstmt.setLong(1,user.getId());
//            pstmt.setString(2,user.getNickname());
//            pstmt.setString(3,user.getPassword());
//            pstmt.setString(4,user.getSalt());
//            pstmt.setString(5,user.getHead());
//            pstmt.setTimestamp(6,new Timestamp(user.getRegisterData().getTime()));
//            pstmt.setTimestamp(7,new Timestamp(user.getLastLoginDate().getTime()));
//            pstmt.setInt(8,user.getLoginCount());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        conn.close();
//        System.out.println("insert into db");

        String urlString = "http://localhost:8080/login/do_login";
        File file = new File("D:/tokens.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream is = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            is.close();
            bout.close();
            String response = new String(bout.toByteArray());
            JSONObject jo = JSON.parseObject(response);
            String token = jo.getString("data");
//            System.out.println("create token:" + user.getId());

            String row = user.getId() + "," + token;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
//            System.out.println("write to file:" + user.getId());

        }
        raf.close();
        System.out.println("over");
    }
}
