package com.atguigu.edu;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author HaiYu
 */
// @SpringBootTest(classes = EduApplication.class)
// @RunWith(SpringRunner.class)
public class teacherTest {
    // @Autowired
    // TeacherService teacherService;
    //
    // @Test
    // public void test() {
    //     ArrayList<Teacher> list = new ArrayList<>();
    //     for (int i = 0; i < 30; i++) {
    //         list.add(new Teacher(null,"test" + i, "test", "test", 1,"test", 1, false,null,null));
    //     }
    //     teacherService.saveBatch(list);
    // }

    @Test
    public void test() {
        // String s = "{\"msg\":\"成功\",\"code\":\"0\"}";
        // System.out.println(s.substring(1,s.length()-1).split(",")[1].split(":")[1].replace("\"",""));
        // System.out.println(JwtUtils.getJwtToken("1","1"));
        // // System.out.println(s.replace("\"","").replace("}", ""));
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date().getTime());
    }

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        // System.out.println(list.toString().length());
        // System.out.println(list.toString().substring(1,list.toString().length() - 1).replace(" ", ""));
        String videoIds = StringUtils.join(list, ",");
        System.out.println(videoIds);

    }
}
