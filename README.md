# JAVA Json工具处理类
<code lang="java">
  String jsonDataEntryStr = "{\"name\":\"张三1\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]}";
        String jsonDataArrayStr = "[\n" +
                "  {\"name\":\"张三1\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三2\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三3\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三4\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三5\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三6\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三7\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三8\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三9\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三10\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]},\n" +
                "  {\"name\":\"张三11\",\"age\":18,\"dataList\":[1,23,4,5,56,7,8,9,0]}\n" +
                "]";
        long before = System.currentTimeMillis();
        // 将json对象转换为JsonObject
        JsonObject jsonObject = JSON.parseObject(jsonDataEntryStr);
        // 将JsonObject转换为java对象
        User user = jsonObject.toJavaObject(User.class);
        System.out.printf("java对象：%s\r\n",user.toString());
        // 将JsonObject转换为Json字符串
        String jsonDataStr = jsonObject.toString();
        System.out.printf("java对象字符串：%s\n",jsonDataStr);
        // 将json数组转换为JsonArray
        JsonArray jsonArray = JSON.parseArray(jsonDataArrayStr);
        // 将JsonArray转换为java对象列表
        List<User> userList = jsonArray.toJavaObject(User.class);
        System.out.printf("java对象列表：%s\n",userList);
        // 将JsonObject转换为Json字符串
        String jsonArrayDataStr = jsonArray.toString();
        System.out.printf("java对象列表字符串：%s\n",jsonArrayDataStr);
        System.out.println(userList);
        System.out.println("解析json耗时："+(System.currentTimeMillis()-before));
</code>
