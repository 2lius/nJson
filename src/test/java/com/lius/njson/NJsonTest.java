package com.lius.njson;

import com.lius.njson.beans.JsonArray;
import com.lius.njson.beans.JsonObject;
import com.lius.njson.beans.User;
import com.lius.njson.parsers.JSON;
import org.junit.Test;

import java.util.List;

public class NJsonTest {

    @Test
    public void nJsonTests(){
        String jsonDataEntryStr = "{\"name\":\"����1\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]}";
        String jsonDataArrayStr = "[\n" +
                "  {\"name\":\"����1\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����2\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����3\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����4\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����5\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����6\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����7\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����8\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����9\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����10\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"����11\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]}\n" +
                "]";
        long before = System.currentTimeMillis();
        // ��json����ת��ΪJsonObject
        JsonObject jsonObject = JSON.parseObject(jsonDataEntryStr);
        // ��JsonObjectת��Ϊjava����
        User user = jsonObject.toJavaObject(User.class);
        System.out.printf("java����%s\r\n",user.toString());
        // ��JsonObjectת��ΪJson�ַ���
        String jsonDataStr = jsonObject.toString();
        System.out.printf("java�����ַ�����%s\n",jsonDataStr);
        // ��json����ת��ΪJsonArray
        JsonArray jsonArray = JSON.parseArray(jsonDataArrayStr);
        // ��JsonArrayת��Ϊjava�����б�
        List<User> userList = jsonArray.toJavaObject(User.class);
        System.out.printf("java�����б�%s\n",userList);
        // ��JsonObjectת��ΪJson�ַ���
        String jsonArrayDataStr = jsonArray.toString();
        System.out.printf("java�����б��ַ�����%s\n",jsonArrayDataStr);
        System.out.println(userList);
        System.out.println("����json��ʱ��"+(System.currentTimeMillis()-before));
    }
}
