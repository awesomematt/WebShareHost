package pl.web.share.host.model;

/**
 * Class responsible for creating a proper structure of a HTML file.
 * HTML code is being stored in a String type variable
 */
public class HTMLFileCreator {

    private String indexHtml, siteTitle, direction, animationMessage, webContent;


    public HTMLFileCreator() {
        initializeDefaultVariables();
    }

    /**
     * Constructor for setting up external html code.
     *
     * @param indexHtml contains HTML code structure
     */
    public HTMLFileCreator(String indexHtml) {
        this.indexHtml = indexHtml;
    }

    /**
     * Method creates HTML code structure and saves it to String type variable.
     *
     * @param userTitle      contains user website title from SettingsActivity
     * @param animMessage    contains user animated text message from SettingsActivity
     * @param websiteContent contains user website body content from WebContentActivity
     * @param animDirection  contains user animated text direction from SettingsActivity
     * @param cssFile        contains CSS code structure
     */
    public void createHtmlFile(String userTitle, String animMessage, String websiteContent, Integer animDirection, String cssFile) {

        checkUserInputAndSetVariables(userTitle, animMessage, websiteContent, animDirection);
        indexHtml = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>" + siteTitle + "</title>\n"
                + cssFile
                + "</head>\n"
                + "<body>\n"
                + " <marquee direction=\"" + direction + "\">\n"
                + animationMessage
                + "</marquee>\n\n"
                + webContent
                + "\n"
                + "</body>\n"
                + "</html>";
    }

    public String getIndexHtml() {
        return indexHtml;
    }

    /**
     * Method initializes default values for all variables in HTMLFileCreator class.
     */
    private void initializeDefaultVariables() {
        siteTitle = "Web Share Host";
        direction = "left";
        animationMessage = "Hello! This is a message of the day on my own website! Hurray!";
        webContent = "This is the body of my website! " +
                "\nSo adorable! " +
                "\nI love this app! ;)";
    }

    /**
     * Method checks if passed variables contains data.
     * If true, values are being set to proper String type variables.
     *
     * @param userTitle      contains user website title from SettingsActivity
     * @param animMessage    contains user animated text message from SettingsActivity
     * @param websiteContent contains user website body content from WebContentActivity
     * @param animDirection  contains user animated text direction from SettingsActivity
     */
    private void checkUserInputAndSetVariables(String userTitle, String animMessage, String websiteContent, Integer animDirection) {
        if (!(userTitle.equals("")))
            siteTitle = userTitle;
        if (!(animMessage.equals("")))
            animationMessage = animMessage;
        if (!(websiteContent.equals("")))
            webContent = websiteContent;
        switch (animDirection) {
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
            default:
                direction = "left";
                break;
        }
    }
}
