package shop.mtcoding.job.util;

import java.util.HashMap;
import java.util.Map;

public class GetString {
    public static String getString(){
        String skill = "";

        Map<Integer, String> skillMap = new HashMap<>();
        skillMap.put(1, "Java");
        skillMap.put(2, "HTML");
        skillMap.put(3, "JavaScript");
        skillMap.put(4, "VueJS");
        skillMap.put(5, "CSS");
        skillMap.put(6, "Node.js");
        skillMap.put(7, "React");
        skillMap.put(8, "ReactJS");
        skillMap.put(9, "Typescript");
        skillMap.put(10, "Zustand");
        skillMap.put(11, "AWS");

        return skillMap.get(skill);
    }
}
