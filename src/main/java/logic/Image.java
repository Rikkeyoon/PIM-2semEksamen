package logic;

/**
 *
 * @author carol
 */
public class Image {

    private String url;
    private boolean primary;
    
    public Image() {
    }

    public Image(String url, boolean primary) {
        this.url = url;
        this.primary = primary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

}
