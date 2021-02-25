package dao;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class BuySongs {



    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000116670110";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQzhSHzY0E5jnkaxV+9n1DBhNtN3TgFk8l1Aups+AIrgoWPNXlVoaj89jactwNsZWayXZdHsdaDJpF9TV00xH0Sb1QPArpUuuqGLUq9nCbUIXu+3bkHy0y8dwvF8E3Ohj97IpmA4rNL8veCZscZObSnw5yEJSLFN4Y/fq119UUQWau6hOU9KowrQGVGkeHypt0hruLUIrAksCDUCowli17IWwFRKN8APvBiWjP/5AtydowU0MV+7up0BIODDO3szupa1xRX342kLg/viQ1eAh0udZ5oXpDCCW7d3o5ohg3nHJ/8PLIXZljDNmlKcItBLWlldYBJEpLHw03IFRpBWNtAgMBAAECggEAX9MQQQIkZO6Ztu+qLPee0wCtUGQXgscgn07gXO/8vKG3aRnfDkIhHVQb2AuUVbeD4geZvWvFznBykwL+2g1nXzqA/hyNt/O4ojpxgTnOMOmriAd+mN2Mwp7M75kPsqVOrpKez31emAQeLBQ9Qh//o7fADnmsYxFvHCjOhRGMHzdaFOSMtkB3Affr4jLF0s5uc17EHQUK8WoY2Oetsg9YnRzF+CdvKYmD3in0q5AzGptJCz+mjMkkXDzbpPn+crpZzzOe4O/jLrpKavr6cfOYZW2LRrgYllYSClqApjPeAD68Fqh36JYhFrQh3sYyJgwQsN/lQxJMQu11v9bmKoNzgQKBgQDjJLSnDD5XG9pt8OtcSaenmmf5jCk49kIb0bUYKFTdn15KkWaExQHHU939N8nqHb+As+XkSGypsr73tSSKRNZ+FogbgkAxxcAEorQHCIeku7q6OE0S3RL16HbMXuk5yj//zi9Yf1Fjk2CUtggvHBmAv2I7hVhlOAgo9ZeQq0NioQKBgQCjM4QU4MyVJK8T9Nj3tkT+H8c4yV21W0nS8sgfi53Vu2ULgBoSvPdwr7kuR114ni2pkQ83JSijLzOTf35vfAfSd2/et+hlSDZ3MkLEUI/4eVKwukVKdEGmX84+y1ebTi8VMO94YpJCcz67n3jWJn4MDU3sYrZG1H9WmRfR278ZTQKBgQCD5cShXBK3Mo1cWhHRvVOFl7vYxPMLCLTKwrOOG51sNCbk1hnZ7yxhOuolKEgvkqapbc2JCiVPRWRvItjdEawb+6l9j90HCfW+u1we2W0q03hSum7HDWj9MsgJVPPJgxA20tvbX9M7J1FmCW4nLQICKSA5v+zcmfTjE/m3IDw5AQKBgGxhD7vVtcbtv57fIR8P2i1w6RpbD3Ial6IRPLlGPKDte6LxhV8gQqqA59dF6VrqoKRVAue6M4x7EiNHra1XjtJEIvSi6SApnpH1vMUmQxXda/6VFOLm5ZoZUZv96cXnW/kUsFMgEsUyR1zOAK8mjNpwE2mB/E55W78kl97zsaqZAoGAZrY2Z2s+lkJ1EHfiPxcF+KLNG4vAwJJ5Pz12A16u6svhV3cp82b/uunheVXlhj/jA3gMON2sheHh2ihgTlQemSuRErLbq8h/4lda+SmgINW74TxqLfLmjiTkfkt2yy6LysoHmEn6R8bMmSM5u2NAKj6ukQs63NmTFSPRXx8ZV5g=";



    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAls9J375dQzn9jD4qSHYsvyHjOwBugzCMa/clJdkI1/2QDDvKxdHgWJDCUWkNDUsq+G0AbSoK5mU8IwudNVRBmHp0ncw19Rhq/spZWuEjmalfeZVN8a4qbpy0YdjRfltI3JzO98tvW5ZvlfI2chHQyOwIVE+4NvaQb51AO01F6dbZ1Gi5Cye3S6Go3nNWRuV7+NameDL922aeqP/nrWmiMCzMExzaNDysxLIT9goqLiYuVZ/fozzz2sfVRXbDX9dT1hCGWlNIhOw2uKuefwxzKNr8tT3IxPCiOzqvdQCqdnPR6uaKOQakIrk0R0gtVIqlGyrA4eW+fJx3AY4ceyI0gQIDAQAB";


    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 http://localhost:8080/notify
    public static String notify_url = "http://www.wuzhaoqi.top:8080/C91-S2-XM/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://www.wuzhaoqi.top:8080/C91-S2-XM/member.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";




    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

