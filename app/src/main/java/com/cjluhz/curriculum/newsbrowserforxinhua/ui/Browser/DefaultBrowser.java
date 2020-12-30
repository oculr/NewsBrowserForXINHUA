package com.cjluhz.curriculum.newsbrowserforxinhua.ui.Browser;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cjluhz.curriculum.newsbrowserforxinhua.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultBrowser extends AppCompatActivity {
    private String href;
    private String Title;
    private String date;
    private LinearLayout llayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);
        llayout = this.findViewById(R.id.Browser);

        Bundle getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            Title = getBundle.getString("title");
            href = getBundle.getString("newsLink");
            date = getBundle.getString("date");
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle(Title);
        assert getBundle != null;
        getSupportActionBar().setSubtitle(getBundle.getString("subtitle"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        llayout.addView(getAddView(new PageContent(PageContent.title, getBundle.getString("subtitle"))));

        new loaddoc().execute();
    }


    protected void onSaveInstanceState(@NotNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
    }

    public TextView initTextView(){
        TextView tv = new TextView(this);
        tv.setPadding(20,20,20,20);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(15);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        return tv;
    }

    public ImageView initImageView(){
        ImageView iv = new ImageView(this);
        iv.setPadding(20,20,20,20);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return iv;
    }


    public View getAddView(PageContent content){
        if (content.getItemType() < PageContent.img){
            TextView tv = initTextView();
            switch (content.getItemType()) {
                case PageContent.title:
                    tv.setText(content.getTextOrImgsrc());
                    tv.setTextSize(25);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    break;
                case PageContent.dateandsource:
                    tv.setText(content.getTextOrImgsrc());
                    tv.setTextSize(12);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    break;
                case PageContent.strong:
                    tv.setText(content.getTextOrImgsrc());
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    break;
                case PageContent.navyfont:
                    tv.setText(content.getTextOrImgsrc());
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tv.setTextColor(getResources().getColor(R.color.navy));
                    break;
                case PageContent.p:
                    tv.setText(content.getTextOrImgsrc());
                    tv.setTextSize(15);
                    break;
            }
            return tv;

        }else {
            ImageView iv = initImageView();
            Glide.with(this).load(content.getTextOrImgsrc()).into(iv);
            return iv;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class loaddoc extends AsyncTask<Void, Void, List<PageContent> >{
        @Override
        protected List<PageContent> doInBackground(Void... voids) {
            try {
                XINHUAPageParser parser = new XINHUAPageParser(href);
                parser.access();
                return parser.getContents();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<PageContent> pageContents) {
            super.onPostExecute(pageContents);
            for (PageContent p : pageContents){
                if (p.getItemType() == PageContent.dateandsource){
                    String source = p.getTextOrImgsrc();
                    p.setTextOrImgsrc(date + "  " + source);
                }
                llayout.addView(getAddView(p));
            }
        }
    }
}
