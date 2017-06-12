package com.example.viktor.sensesmart;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    Button button;
    String sendMessage;
    EditText message;
    LinearLayout list;
    ScrollView scroll;
    Toolbar toolbar;
    View rootView;

    public static final String URL = "http://130.240.135.15:8080/hey/hey";

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        findViewsById();

        button.setOnClickListener(this);
        ImageView imgview = new ImageView(getContext());
        imgview.setImageResource(R.drawable.icon_back);
        imgview.setLayoutParams(new Toolbar.LayoutParams(70,70,Gravity.START));
        toolbar.addView(imgview);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), BaseActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                rootView.setVisibility(View.INVISIBLE);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        //Thread background = new Thread(new LocationAPI(getApplicationContext(), handler));
        // background.start();

    }

    private void findViewsById()
    {

        button = (Button) rootView.findViewById(R.id.button);
        scroll = (ScrollView) rootView.findViewById(R.id.scroll);
        list = (LinearLayout) rootView.findViewById(R.id.messagesContainer);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        message = (EditText) rootView.findViewById(R.id.message);

    }

    public void onClick(View view)
    {

        if (!message.getText().toString().equals(""))
        {
            GetXMLTask task = new GetXMLTask();
            task.execute(new String[]{URL});
        }

        message.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s) {}

            //CHECKS IF THE TEXT HAS CHANGED. IF THE TEXTFIELD IS EMPTY, THE COLOR OF THE
            //BUTTON WILL CHANGE.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(message.getText().toString().equals(""))
                {

                    button.setBackgroundColor
                            (ContextCompat.getColor
                                    (getContext(),R.color.colorPrimaryLight));
                }
                else {

                    button.setBackgroundColor
                            (ContextCompat.getColor
                                    (getContext(),R.color.colorPrimary));
                }
            }
        });
    }
    //A NEW CLASS, GETXMLTASK. THIS IS USED WHEN WE CHAT WITH A BOT.
    private class GetXMLTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            String output = null;
            for (String url : urls)
            {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url)
        {

            StringBuffer output = new StringBuffer("");

            try
            {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream, "UTF-8"));

                String s;

                while ((s = buffer.readLine()) != null)
                    output.append(s);

            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
            return output.toString();
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString) throws IOException
        {

            InputStream stream = null;

            sendMessage = message.getText().toString();

            java.net.URL url = new URL(urlString+"?message="+ URLEncoder.encode(sendMessage, "UTF-8"));
            URLConnection connection = url.openConnection();

            try
            {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    stream = httpConnection.getInputStream();
                }

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return stream;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String output)
        {
            try
            {

                //CREATING THE LAYOUT FOR THE USERMESSAGES AND SERVERMESSAGES
                LinearLayout.LayoutParams serverParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams userParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                //SETTING THE GRAVITY PARAMETER FOR EACH
                serverParams.gravity = Gravity.START;

                userParams.gravity = Gravity.END;
                userParams.setMargins(0, 20, 0, 0);
                serverParams.setMargins(0, 20, 0, 0);

                //SETS THE LINEARLAYOUT PARAMETER TO OUR TEXTVIEW
                TextView serverMess = new TextView(getContext());
                TextView userMess = new TextView(getContext());

                //SOME OTHER STYLING
                serverMess.setTextSize(16);
                userMess.setTextSize(16);
                serverMess.setTextColor(Color.parseColor("#FFFFFF"));
                userMess.setTextColor(Color.parseColor("#FFFFFF"));
                serverMess.setLayoutParams(serverParams);
                userMess.setLayoutParams(userParams);
                userMess.setBackgroundResource(R.drawable.background_chatbubble);
                serverMess.setBackgroundResource(R.drawable.background_chatbubble);

                //SETS THE TEXT
                serverMess.setText(URLDecoder.decode(output, "UTF-8"));
                userMess.setText(sendMessage);

                //ADDS THE TEXTVIEWS TO OUR VIEW
                list.addView(userMess);
                list.addView(serverMess);
                message.getText().clear();

                scroll.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        scroll.setSmoothScrollingEnabled(true);
                        scroll.setOverScrollMode(1);
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });

            }catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
    }
}
