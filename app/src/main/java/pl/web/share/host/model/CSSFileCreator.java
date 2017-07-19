package pl.web.share.host.model;

/**
 * Class responsible for creating a proper structure of a CSS file.
 * CSS code is being stored in a String type variable
 */
public class CSSFileCreator {

    private String css;
    private String imageDataUri;

    public CSSFileCreator() {
    }

    /**
     * Constructor for setting up external css code.
     *
     * @param css contains CSS code structure
     */
    public CSSFileCreator(String css) {
        this.css = css;
    }

    public String getCss() {
        return css;
    }

    /**
     * Method creates CSS code structure and saves it to String type variable.
     */
    public void createCssFile() {
        css = "<style>\n"
                + "body {\n"
                + "    background-image: url(" + imageDataUri + ");\n"
                + "    background-color: orange;\n"
                + "}\n"
                + "h1 {\n"
                + "    color: blue;\n"
                + "}\n"
                + "p {\n"
                + "    color: red;\n"
                + "}\n"
                + "</style>\n";
    }

    /**
     * Method responsible for setting up imageDataUri to a String type variable.
     * (used as a background image in CSS code)
     *
     * @param imageDataUri contains hardcoded image data URI
     */
    public void setImageDataUri(String imageDataUri) {
        this.imageDataUri = imageDataUri;
    }

}
