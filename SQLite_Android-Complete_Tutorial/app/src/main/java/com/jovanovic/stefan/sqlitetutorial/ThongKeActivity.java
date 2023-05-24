//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.Description;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
//import com.jovanovic.stefan.sqlitetutorial.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ThongKeActivity extends AppCompatActivity {
//
//    private Spinner spnKhuVuc;
//    private BarChart barChart;
//
//    private DBHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_thong_ke);
//
//        dbHelper = new DBHelper(this);
//
//        spnKhuVuc = findViewById(R.id.spnKhuVuc);
//        barChart = findViewById(R.id.barChart);
//
//        List<String> khuVucList = dbHelper.getAllKhuVuc();
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, khuVucList);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnKhuVuc.setAdapter(spinnerAdapter);
//
//        spnKhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                String selectedKhuVuc = adapterView.getItemAtPosition(position).toString();
//                generateBarChart(selectedKhuVuc);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//    }
//
//    private void generateBarChart(String khuVuc) {
//        List<Integer> countList = dbHelper.getCountBienBanByKhuVuc(khuVuc);
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        for (int i = 0; i < countList.size(); i++) {
//            entries.add(new BarEntry(i, countList.get(i)));
//        }
//
//        BarDataSet dataSet = new BarDataSet(entries, "Số biên bản");
//        dataSet.setColor(Color.BLUE);
//
//        ArrayList<String> labels = dbHelper.getQuanListByKhuVuc(khuVuc);
//
//        BarData barData = new BarData(dataSet);
//        barData.setBarWidth(0.5f);
//        barChart.setData(barData);
//        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//        barChart.getXAxis().setGranularity(1f);
//        barChart.getXAxis().setGranularityEnabled(true);
//        barChart.getAxisLeft().setAxisMinimum(0f);
//        barChart.getAxisRight().setAxisMinimum(0f);
//        barChart.getDescription().setEnabled(false);
//        barChart.setFitBars(true);
//        barChart.invalidate();
//    }
//}
