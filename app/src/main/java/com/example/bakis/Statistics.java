package com.example.bakis;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Statistics extends Fragment {

    private CalendarView calendarView;
    private StatisticViewModel statisticViewModel;
    private CourseViewModel courseViewModel;
    private List<Statistic> allStatistics;
    private Context context;

    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        getActivity().setTitle(R.string.statistics);

        calendarView = view.findViewById(R.id.calendarStatistic);
        statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);
        try {
            allStatistics = statisticViewModel.getAllStatistics();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<EventDay> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for(int i = 0; i<allStatistics.size(); i++){
            calendar =  dateToCalendar(allStatistics.get(i).getDateOfExercise());

            events.add(new EventDay(calendar, R.drawable.ic_stats_blue, Color.parseColor("#ff33b5e5")));
        }

        if(events.size()>0)
            calendarView.setEvents(events);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_statistics_on_that_day);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        StatisticAdapter adapter = new StatisticAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        adapter.setCourseViewModel(courseViewModel);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                try {
                    statisticViewModel.getAllStatisticsLiveDataOnThisDay(calendarToDate(clickedDayCalendar)).observe(getViewLifecycleOwner(), new Observer<List<Statistic>>() {
                        @Override
                        public void onChanged(List<Statistic> statistics) {
                            adapter.setStatistics(statistics);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
