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

    /**
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return primary
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     *
     * @param primary
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

}
