package co.baselib.utils;
import android.util.Base64;

import java.math.BigDecimal;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密和解密，有偏移量
 * @author tyg
 * @date   2018年6月28日下午12:48:01
 */
public class AECUtils {


        // 密匙
        private static final String KEY = "3C4FRyiOMrZ1XJTulZHuTlYNrWsvTcziStv";
        // 偏移量
        private static final String OFFSET = "≥ÄTÔVÚ≤ks7T\\É(’\"";
        // 编码
        private static final String ENCODING = "UTF-8";
        //算法
        private static final String ALGORITHM = "AES";
        // 默认的加密算法
        private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";


        /**
         * 加密
         * @param data
         * @return
         * @throws Exception
         * @return String
         * @author tyg
         * @date   2018年6月28日下午2:50:35
         */
        public static String encrypt(String data) throws Exception {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("ASCII"), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
            String enc = Base64.encodeToString(encrypted,Base64.DEFAULT);
            return enc;
        }


        /**
         * 解密
         * @param data
         * @return
         * @throws Exception
         * @return String
         * @author tyg
         * @date   2018年6月28日下午2:50:43
         */
        public static String decrypt(String data) throws Exception {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("ASCII"), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] buffer =Base64.decode(data.getBytes(), Base64.DEFAULT);
            byte[] encrypted = cipher.doFinal(buffer);
            return new String(encrypted, ENCODING);//此处使用BASE64做转码。
        }

    public static void main(String[] args) {
        try {
            String test = "家源教育";
            // 注意这里，自定义的加密的KEY要和解密的KEY一致，这就是钥匙，如果你上锁了，却忘了钥匙，那么是解密不了的
            AECUtils des = new AECUtils();// 自定义密钥
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des.encrypt(test));
            System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
