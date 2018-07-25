package top.vncnliu.server.mash.base;

/**
 * User: liuyq
 * Date: 2018/7/25
 * Description:
 */
public class Constant {

    public enum ErrorCode {
        SUCCESS(0,"成功"),
        MASH_BAK_ERROR(100001,"回滚事件异常");
        private int code;
        private String msg;

        ErrorCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
