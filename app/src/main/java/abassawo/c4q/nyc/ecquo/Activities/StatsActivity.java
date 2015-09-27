package abassawo.c4q.nyc.ecquo.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import abassawo.c4q.nyc.ecquo.Model.User;
import abassawo.c4q.nyc.ecquo.Model.UserDatabaseHelper;
import abassawo.c4q.nyc.ecquo.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 9/12/15.
 */

public class StatsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.likes)
    TextView likes;

    @Bind(R.id.dislikes)
    TextView dislikes;

    @Bind(R.id.chart)
    PieChart pieChart;

    UserDatabaseHelper userDatabaseHelper;
    User user;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_stats);
        ButterKnife.bind(this);
        setupActionBar();

        userDatabaseHelper = UserDatabaseHelper.getInstance(this);
        name = getIntent().getExtras().getString("name");

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(7);

        try {
            user = userDatabaseHelper.readUser(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        likes.setText("" + user.getLikes());
        dislikes.setText("" + user.getDislikes());

        ArrayList<Entry> yValues = new ArrayList<Entry>();
        yValues.add(new Entry(user.getLikes(), user.getLikes()));
        yValues.add(new Entry(user.getDislikes(), user.getDislikes()));

        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("Completed");
        xValues.add("Skipped");

        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setSelectionShift(5);
        pieDataSet.setValueTextSize(15f);

        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.MAGENTA);

        pieChart.setData(pieData);
        pieChart.getLegend().setEnabled(false);
        pieChart.highlightValues(null);
        pieChart.invalidate();

    }

    public void setupActionBar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        Date date = new Date();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.mipmap.ic_ecquo);
    }

}
