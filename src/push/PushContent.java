package push;

public class PushContent {
    int id;
    String password;
    String content;
    String time;
    String img;
    int hidename;

    public int getHidename() {
        return hidename;
    }

    public void setHidename(int hidename) {
        this.hidename = hidename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgURL() {
        return img;
    }

    public void setImgURL(String img) {
        this.img = img;
    }

}
