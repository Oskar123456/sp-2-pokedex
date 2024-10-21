package dk.obhnothing.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javafaker.Faker;

import lombok.ToString;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class JsonUtils
{

    private static int MAXLEN = 256;

    public static JsonObject toClass(String json, String objectName)
    {
        JsonObject jo = new JsonObject(objectName);
        jo.type = TYPE.OBJECT;
        //fillOutJsonObject(json, 0, jo);
        return jo;
    }

    public static void fillOutJsonObjectStr(char[] jsonStr, JsonObjectStr JO)
    {
        //while (Character.isWhitespace(jsonStr[idx])) ++idx;
        //if (jsonStr.charAt(idx) != '{')
        //    throw new RuntimeException(
        //            String.format("invalid json (at idx %d):%n%s", idx, showWhere(jsonStr, idx)));

        boolean escaped = false;
        int idx = 0, mode = 0, left = 0; // 0 == k, 1 == v, 2 == skip {}, 3 == skip [], 4 == skip ""
        char char_k[] = new char[MAXLEN]; int char_k_idx = 0;
        char char_v[] = new char[MAXLEN]; int char_v_idx = 0;
        JsonObjectStr child = null;

        while (idx < jsonStr.length) {
            char c = jsonStr[idx++];
            if (mode == 0) {
                if (c == '\\' && !escaped) {
                    escaped = true;
                    continue;
                }
                else
                    escaped = false;
                if (c == ':' && !escaped) {
                    child = new JsonObjectStr(new String(Arrays.copyOfRange(char_k, 0, char_k_idx)), null);
                    mode = 1;
                    continue;
                }
                char_k[char_k_idx++] = c;
            }
            else if (mode == 1) {
                if (c == '{') { mode = 2; left = 1; child.isObj = true; }
                if (c == '[') { mode = 3; left = 1; child.isArr = true; }
                if (c == '"' && !escaped)
                    escaped = true;
                else
                    escaped = false;
                if (c == ',' && !escaped) {
                    child.V = new String(Arrays.copyOfRange(char_v, 0, char_v_idx));
                    JO.fields.add(child);
                    char_k_idx = 0; char_v_idx = 0;
                    mode = 0; continue;
                }
                char_v[char_v_idx++] = c;
            }
            else if (mode == 2) {
                char_v[char_v_idx++] = c;
                if (c == '{')
                    left++;
                if (c == '}')
                    left--;
                if (left < 1)
                    mode = 1;
            }
            else if (mode == 3) {
                char_v[char_v_idx++] = c;
                if (c == '[')
                    left++;
                if (c == ']')
                    left--;
                if (left < 1)
                    mode = 1;
            }
        }

        if (char_v_idx > 0) {
            JO.fields.add(new JsonObjectStr(
                        new String(Arrays.copyOfRange(char_k, 0, char_k_idx)),
                        new String(Arrays.copyOfRange(char_v, 0, char_v_idx))));
        }

        for (JsonObjectStr jo : JO.fields)
            if (jo.isObj || jo.isArr)
                fillOutJsonObjectStr(jo.V.toCharArray(), jo);

    }

    private static String showWhere(String jsonStr, int idx)
    {
        return jsonStr.substring(0, idx) + "-->" + jsonStr.substring(idx, idx + 1) + "<--" + jsonStr.substring(idx);
    }

    public enum TYPE { NULL, NUMBER, STRING, BOOL, OBJECT }

    @ToString
    public static class JsonObject
    {
        public String id;
        public TYPE type;
        public boolean isArray;
        public List<JsonObject> children;
        public JsonObject(String id) { this.id = id; this.children = new ArrayList<>(); }
    }

    public static class JsonObjectStr
    {
        public boolean isObj;
        public boolean isArr;
        public String K;
        public String V; // name & value
        public List<JsonObjectStr> fields;
        public JsonObjectStr(String k, String v) {
            try {
                K = k.replace("\n", "").replace("\r", "");
                //System.err.println(K);
                K = K.substring(k.indexOf("\"") + 1);
                //System.err.println(K);
                K = K.substring(0, K.lastIndexOf("\"")).trim();
                //System.err.println(K);
                V = (v != null) ? v.replace("\n", "").replace("\r", "").trim() : null;
                //System.err.println(V);
                fields = new ArrayList<>();
            }
            catch (Exception e) {
                System.err.println(k + " , " +  v);
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        public String toString()
        {
            String r = String.format("json[id:%s, ", K);
            if (isObj)
                for (JsonObjectStr j : fields)
                    r += String.format("%n\t%s", j.toString());
            else
                r += String.format("%s", V);
            return r + "]";
        }
    }

}




































