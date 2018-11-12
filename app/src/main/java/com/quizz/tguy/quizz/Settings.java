package com.quizz.tguy.quizz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// Settings : settings menu available in the main menu of the app
public class Settings extends AppCompatActivity {
    // View Model to access the Room
    private RoomViewModel mQuizzViewModel;

    private static class XMLAsync extends AsyncTask<Void, Void, RoomQuizz> {
        BufferedReader bufferedReader = null;
        HttpURLConnection urlConnection = null ;

        private final RoomViewModel viewModel;

        XMLAsync(RoomViewModel vm) {
            viewModel = vm;
        }

        protected RoomQuizz doInBackground(final Void... params) {

            List<RoomQuizz> list_rq = new ArrayList<>();

            try {
                URL url = new URL("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml");

                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(inputStream);
                    doc.getDocumentElement().normalize();

                    Node channelElement = doc.getElementsByTagName("Quizzs").item(0);
                    NodeList quizzList = ((Element) channelElement).getElementsByTagName("Quizz");
                    for (int i = 0; i < quizzList.getLength(); i++) {
                        RoomQuizz rq = new RoomQuizz("XML Quizz");
                        Node quizz = quizzList.item(i);
                        String title = ((Element) quizz).getAttribute("type");
                        rq.setTitle(title);

                        NodeList questionList = ((Element) quizz).getElementsByTagName("Question");
                        for (int j = 0; j < questionList.getLength(); j++) {
                            Node itemQ = questionList.item(j);
                            String question = itemQ.getChildNodes().item(0).getNodeValue().trim();

                            List<String> answers = new ArrayList<>();
                            NodeList answerList = itemQ.getChildNodes().item(1).getChildNodes();
                            for (int k = 0; k < answerList.getLength(); k++) {
                                if (answerList.item(k).getNodeName().equals("Proposition")) {
                                    // Il ne passe jamais la meme si le node name est ok
                                    NodeList answer = answerList.item(k).getChildNodes();
                                    String txt = "";
                                    for (int z = 0; z < answer.getLength(); z++) {
                                        txt += answer.item(z).getNodeValue();
                                    }
                                    answers.add(txt.trim());
                                }
                            }

                            Node rep = itemQ.getLastChild().getPreviousSibling();
                            String repstr = rep.getAttributes().getNamedItem("valeur").getNodeValue();
                            rq.addQuestionWithAnswers(question, answers, Integer.parseInt(repstr));
                        }

                        list_rq.add(rq);
                    }
                } else {
                    Log.i("XMLError", "Response : " + responseCode);
                }

            } catch (Exception e) {
                Log.e("XMLError", "Error parsing XML" + e.toString());
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e("XMLError", "Error closing bufferedReader");
                    }
                }
                if (urlConnection != null) urlConnection.disconnect();

                for (int i = 0; i < list_rq.size(); ++i) {
                    viewModel.insert(list_rq.get(i));
                }
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        // Fill the main RecyclerView that contains the quizz list
        final RecyclerView recyclerView = findViewById(R.id.quizzListSettings);
        final SettingsRecyclerAdapter adapter = new SettingsRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe any modification in the quizz list and adapt the RecyclerView
        mQuizzViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        mQuizzViewModel.getAllQuizz().observe(this, new Observer<List<RoomQuizz>>() {
            @Override
            public void onChanged(@Nullable final List<RoomQuizz> roomAllQuizz) {
                adapter.setQuizzes(roomAllQuizz);
            }
        });

        // Set download button behaviour
        final Button btn_dlquizz = findViewById(R.id.btn_dlQuizz);
        btn_dlquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Download a list of quizz from the url : https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml
                XMLAsync xml = new XMLAsync(mQuizzViewModel);
                xml.execute();
                adapter.notifyDataSetChanged();
            }
        });

        // Set the home button behaviour
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the main menu
                Intent int_home = new Intent(getApplicationContext(), MainMenu.class);
                int_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(int_home);
            }
        });

        final Button btn_addQuizz = findViewById(R.id.btn_addQuizz);
        btn_addQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuizzViewModel.insert(new RoomQuizz());
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                /* Button delay if Primary key duplication
                btn_addQuizz.setEnabled(false);
                btn_addQuizz.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_addQuizz.setEnabled(true);
                    }
                }, 500);*/
            }
        });
    }

    protected void delQuizz(int index) {
        mQuizzViewModel.delete(mQuizzViewModel.getAllQuizz().getValue().get(index));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
