package shop.mtcoding.job.util;

import java.util.List;
import java.util.stream.Collectors;

public class Convert {

    public static List<String> skillMapping(List<Integer> list) throws Exception {

        List<String> result = list.stream().map(number -> {
            switch (number) {
                case 1:
                    return "Java";
                case 2:
                    return "HTML";
                case 3:
                    return "JavaScript";
                case 4:
                    return "VueJS";
                case 5:
                    return "CSS";
                case 6:
                    return "Node.js";
                case 7:
                    return "React";
                case 8:
                    return "ReactJS";
                case 9:
                    return "Typescript";
                case 10:
                    return "Zustand";
                default:
                    return "AWS";
            }
        }).collect(Collectors.toList());

        return result;
    }
}
