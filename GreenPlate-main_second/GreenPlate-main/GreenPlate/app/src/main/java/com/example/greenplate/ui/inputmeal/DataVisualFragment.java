package com.example.greenplate.ui.inputmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.core.cartesian.series.RangeColumn;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.JumpLine;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.greenplate.R;
import com.example.greenplate.database.User;
import com.example.greenplate.databinding.FragmentDataVisualBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DataVisualFragment extends Fragment {

    private FragmentDataVisualBinding binding;

    private View root;

    private static int graphNumber = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DataVisualViewModel homeViewModel =
                new ViewModelProvider(this).get(DataVisualViewModel.class);

        binding = FragmentDataVisualBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //final TextView textView = binding.textData;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        if (graphNumber == 1) {
            makeHorizontalChart();
        } else {
            makeDemoChart();
        }

        return root;
    }

    public static void setGraph(int graphType) {
        graphNumber = graphType;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void makeDemoChart() {
        AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.cartesian();

        cartesian.title("Calorie Offset Compared to Calorie Goal");

        List<DataEntry> data = new ArrayList<>();
        //input data

        User user = User.getInstance();
        double calorieGoal = user.getCalorieGoal();
        ArrayList<Integer> monthlyCalories = user.getMonthlyCalories();

        Calendar calendar = Calendar.getInstance();

        for (int i = 29; i >= 0; i--) {
            calendar.add(Calendar.DATE, -1);
            CustomDataEntry2 entry = new CustomDataEntry2(calendar.getTime()
                    .toString().substring(0, calendar.getTime().toString().length() - 18),
                    0, 0, 0, monthlyCalories.get(i) - calorieGoal);
            data.add(entry);
        }

        /*data.add(new CustomDataEntry2("Jan", 0, 0, 0, 8.9));
        data.add(new CustomDataEntry2("Feb", 0, 0, 0, -8.2));
        data.add(new CustomDataEntry2("Mar", 0, 0, 0, -8.1));
        data.add(new CustomDataEntry2("Apr", 0, 0, 0, 9.8));
        data.add(new CustomDataEntry2("May", 0, 0, 0, 10.7));
        data.add(new CustomDataEntry2("June", 0, 0, 0, 14.5));
        data.add(new CustomDataEntry2("July", 0, 0, 0, 16.7));
        data.add(new CustomDataEntry2("Aug", 0, 0, 0, 16.3));
        data.add(new CustomDataEntry2("Sep", 0, 0, 0, 15.3));
        data.add(new CustomDataEntry2("Oct", 0, 0, 0, 14.4));
        data.add(new CustomDataEntry2("Nov", 0, 0, 0, 10.7));
        data.add(new CustomDataEntry2("Dec", 0, 0, 0, 11.1));*/

        Set set = Set.instantiate();
        set.data(data);
        Mapping londonData = set.mapAs("{ x: 'x', high: 'londonHigh', low: 'londonLow' }");
        Mapping edinburgData = set.mapAs("{ x: 'x', high: 'edinburgHigh', low: 'edinburgLow' }");

        RangeColumn columnLondon = cartesian.rangeColumn(londonData);
        columnLondon.name("Calories");

        //RangeColumn columnEdinburg = cartesian.rangeColumn(edinburgData);
        //columnEdinburg.name("Edinburgh");

        cartesian.xAxis(true);
        cartesian.yAxis(true);

        cartesian.yScale()
                .minimum(-2000d)
                .maximum(2000d);

        cartesian.legend(true);

        cartesian.yGrid(true)
                .yMinorGrid(true);

        cartesian.tooltip().titleFormat("{%SeriesName} ({%x})");

        anyChartView.setChart(cartesian);
    }

    private void makeHorizontalChart() {
        AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));

        Cartesian vertical = AnyChart.vertical();

        vertical.animation(true)
                .title("Daily Caloric Intake in the Past 30 Days.");

        List<DataEntry> data = new ArrayList<>();
        // In real program, we can just loop through days

        Calendar calendar = Calendar.getInstance();
        //System.out.println("Current Date = " + calendar.getTime());
        //calendar.add(Calendar.DATE, -2);
        //System.out.println("Updated Date = " + calendar.getTime());


        /*for (int i = 0; i < 30; i++) {
            calendar.add(Calendar.DATE, -1);
            CustomDataEntry entry = new CustomDataEntry(calendar.getTime()
                    .toString().substring(0, calendar.getTime().toString().length() - 18),
                    (int) (Math.random() * 3000), User.getInstance().getCalorieGoal());
            data.add(entry);
        }*/
        for (int i = 29; i >= 0; i--) {
            calendar.add(Calendar.DATE, -1);
            CustomDataEntry entry = new CustomDataEntry(calendar.getTime()
                    .toString().substring(0, calendar.getTime().toString().length() - 18),
                    User.getInstance().getMonthlyCalories().get(i),
                    User.getInstance().getCalorieGoal());
            data.add(entry);
        }


        Set set = Set.instantiate();
        set.data(data);
        Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping jumpLineData = set.mapAs("{ x: 'x', value: 'jumpLine' }");

        Bar bar = vertical.bar(barData);
        //bar.labels().format("${%Value} mln");
        bar.labels().format("{%Value} calories");

        JumpLine jumpLine = vertical.jumpLine(jumpLineData);
        jumpLine.stroke("1 #60727B");
        jumpLine.labels().enabled(false);

        vertical.yScale().minimum(0d);

        vertical.labels(true);

        vertical.tooltip()
                .displayMode(TooltipDisplayMode.UNION)
                .positionMode(TooltipPositionMode.POINT)
                .unionFormat(
                        "function() {\n"
                                + "      return 'Calorie Goal: ' + this.points[1].value + \n"
                                + "        '\\n' + 'Calories Reached: ' + this.points[0].value ;\n"
                                + "    }");

        vertical.interactivity().hoverMode(HoverMode.BY_X);

        vertical.xAxis(true);
        vertical.yAxis(true);
        //vertical.yAxis(0).labels().format("${%Value} mln");
        vertical.yAxis(0).labels().format("{%Value}");

        anyChartView.setChart(vertical);
    }

    private class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number jumpLine) {
            super(x, value);
            setValue("jumpLine", jumpLine);
        }
    }

    private class CustomDataEntry2 extends DataEntry {
        public CustomDataEntry2(String x, Number edinburgHigh,
                                Number edinburgLow, Number londonHigh, Number londonLow) {
            setValue("x", x);
            setValue("edinburgHigh", edinburgHigh);
            setValue("edinburgLow", edinburgLow);
            setValue("londonHigh", londonHigh);
            setValue("londonLow", londonLow);
        }
    }
}