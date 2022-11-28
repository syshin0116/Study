import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class help {
    public static boolean is1to1(Map<String, String> map){
        if (map == null) return true;

        Set<String> set = new HashSet<>();

        for (String value : map.values()){
//            String val = map.get(value);
            if (set.contains(value)) return false;
            set.add(value);
        }
        return true;
    }
    public static void main(String[] args) {
        Map<String, String> test = new HashMap<String, String>();
        test.put("1234","1234");
        test.put("1111","12345");
        test.put("11", "1234");
        boolean result = is1to1(test);
        System.out.println(result);
    }
}
