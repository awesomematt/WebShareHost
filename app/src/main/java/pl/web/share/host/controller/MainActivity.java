package pl.web.share.host.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import pl.web.share.host.R;
import pl.web.share.host.model.BackgroundImageDataUri;
import pl.web.share.host.model.CSSFileCreator;
import pl.web.share.host.model.HTMLFileCreator;

/**
 * Main class which hosts server. Corpus of the application.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Declaration of variables visible for every element in the MainActivity class.
     */
    TextView infoIp, infoMsg, clientsInfo;
    Button settingsBtn, exitBtn;
    String msgLog, siteTitle, animText, websiteContent;
    Integer backgroundImage, animDirection;
    ServerSocket httpServerSocket;
    BackgroundImageDataUri imageDataUri;
    String chosenImageDataUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializes all variables for MainActivity class
        initializeVariables();

        //sets IP address with port for TextView variable (UI)
        infoIp.setText(getIpAddress() + ":"
                + HttpServerThread.HttpServerPORT + "\n");

        //starts server thread
        HttpServerThread httpServerThread = new HttpServerThread();
        httpServerThread.start();

        //sets on click listener for settings button (UI) - switches to SettingsActivity
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(settingsActivity, 1);
            }
        });

        //sets on click listener for exit button (UI)
        exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (httpServerSocket != null) {
            try {
                httpServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method obtains IP address via network interface.
     *
     * @return String containing IP address
     */
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Your IP address: "
                                + inetAddress.getHostAddress();
                    }

                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
            ip += "Error: Cannot obtain IP address." + e.toString() + "\n";
        }

        return ip;
    }

    /**
     * Class handles server thread in MainActivity.
     * Initializes ServerSocket and starts HttpResponseThread.
     */
    private class HttpServerThread extends Thread {

        static final int HttpServerPORT = 8080;

        @Override
        public void run() {
            Socket socket;

            try {
                httpServerSocket = new ServerSocket(HttpServerPORT);

                while (true) {
                    socket = httpServerSocket.accept();

                    HttpResponseThread httpResponseThread =
                            new HttpResponseThread(socket);
                    httpResponseThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * Class responsible for http response.
     * Creates and sends complete website data as a response for a user request.
     * HTML and CSS content is being send as a String via PrintWriter - (output stream).
     */
    private class HttpResponseThread extends Thread {

        Socket socket;

        HttpResponseThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            //Declaration of important variables
            BufferedReader is;
            PrintWriter os;
            String request;
            HTMLFileCreator htmlCreator;
            CSSFileCreator cssCreator;
            try {
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                request = is.readLine();
                os = new PrintWriter(socket.getOutputStream(), true);

                //Website setup - creation of html and css
                htmlCreator = new HTMLFileCreator();
                cssCreator = new CSSFileCreator();
                chooseBackgroundImageAndSetPath(backgroundImage);
                cssCreator.setImageDataUri(chosenImageDataUri);
                cssCreator.createCssFile();
                htmlCreator.createHtmlFile(siteTitle, animText, websiteContent, animDirection, cssCreator.getCss());

                //Complete website data is being saved to a response variable
                String response = htmlCreator.getIndexHtml();

                //Output stream - sending content to user
                os.print("HTTP/1.0 200" + "\r\n");
                os.print("Content type: text/html" + "\r\n");
                os.print("Content length: " + response.length() + "\r\n");
                os.print("\r\n");
                os.print(response + "\r\n");
                os.flush();
                socket.close();

                //Contains data which is printed to TextView infoMsg - request info + client IP
                msgLog += "Request of " + request
                        + " from " + socket.getInetAddress().toString() + "\n";
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        infoMsg.setText(msgLog);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for initialization of the variables stored in MainActivity class.
     */
    private void initializeVariables() {
        infoIp = (TextView) findViewById(R.id.ipInfo);
        infoMsg = (TextView) findViewById(R.id.message);
        clientsInfo = (TextView) findViewById(R.id.clientsText);
        settingsBtn = (Button) findViewById(R.id.settingsButton);
        exitBtn = (Button) findViewById(R.id.exitButton);

        msgLog = "";
        siteTitle = "";
        animText = "";
        websiteContent = "";
        backgroundImage = 0;
        animDirection = 0;
        imageDataUri = BackgroundImageDataUri.getInstance();
    }

    /**
     * Method sets the correct image data URI to a chosenImageDataUri String variable.
     * Correct background image selection is being made by a user in SettingsActivity.
     *
     * @param backgroundImage holds Integer type value from 0 to 5, sent back from SettingsActivity.
     */
    private void chooseBackgroundImageAndSetPath(Integer backgroundImage) {
        switch (backgroundImage) {
            case 0:
                chosenImageDataUri = imageDataUri.getB1DataUri();
                break;
            case 1:
                chosenImageDataUri = imageDataUri.getB2DataUri();
                break;
            case 2:
                chosenImageDataUri = imageDataUri.getB3DataUri();
                break;
            case 3:
                chosenImageDataUri = imageDataUri.getB4DataUri();
                break;
            case 4:
                chosenImageDataUri = imageDataUri.getB5DataUri();
                break;
            case 5:
                chosenImageDataUri = imageDataUri.getB6DataUri();
                break;
            default:
                chosenImageDataUri = imageDataUri.getB1DataUri();
                break;
        }

    }

    /**
     * Method collects data which was sent back from SettingsActivity.
     * Data is being retrieved from Intent object.
     *
     * @param requestCode contains request code as an int type value
     * @param resultCode  contains result code as an int type value
     * @param data        contains user input data from SettingsActivity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                //saving user input data from SettingsActivity to variables from MainActivity class
                siteTitle = data.getStringExtra("siteTitle");
                animText = data.getStringExtra("animText");
                websiteContent = data.getStringExtra("websiteContent");
                backgroundImage = data.getIntExtra("backgroundImage", 0);
                animDirection = data.getIntExtra("animDirection", 0);
            }
        }
    }
}