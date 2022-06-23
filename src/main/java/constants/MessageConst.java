package constants;

public enum MessageConst {

    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました"),
    I_LOGOUT("ログアウトしました"),

    I_RESISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    E_NONAME(""),
    E_NOPASSWORD(""),
    E_NOEMP_CODE(""),
    E_EMP_CODE_EXIST(""),
    E_NOTITLE(""),
    E_NOCONTEMT("");

    private final String text;

    private MessageConst(final String text) {
        this.text = text;
    }

    public String getMessage() {
        return this.text;
    }

}
