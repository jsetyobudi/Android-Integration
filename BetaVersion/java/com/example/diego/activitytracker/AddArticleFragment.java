package com.example.diego.activitytracker;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tgk.integrationwithfragment.R;

public class AddArticleFragment extends Fragment {
    public String headline ;
    public String article;

    EditText headlineText;
    EditText articleText;

    onOkListerner mCallback;

    public interface onOkListerner{
        void okListener();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.add_article_view,container,false);

      Button okButton = (Button) fragmentView.findViewById(R.id.btn_OK);

        headlineText = (EditText)fragmentView.findViewById(R.id.headline);
        articleText = (EditText) fragmentView.findViewById(R.id.article2);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.okListener();
                add();
            }
        });

        return fragmentView;
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (onOkListerner) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOkListener");
        }
    }

    public void add(){
        headline = headlineText.getText().toString();
        article = articleText.getText().toString();

        DbAdapter dbAdapter = new DbAdapter(getActivity());
        dbAdapter.open();
        dbAdapter.createArticle(headline,article);
        dbAdapter.close();
    }

}
