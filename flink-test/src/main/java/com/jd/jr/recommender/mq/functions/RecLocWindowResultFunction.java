package com.jd.jr.recommender.mq.functions;

import com.jd.jr.recommender.mq.bean.RecChartData;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecLocWindowResultFunction implements WindowFunction<Long, RecChartData, Tuple, TimeWindow> {
    /**
     * pv：曝光   pcv：点击
     */
    private String target=null;
    public RecLocWindowResultFunction(String target){
        this.target=target;
    }
    @Override
    public void apply(Tuple tuple, TimeWindow window, Iterable<Long> input, Collector<RecChartData> collector) throws Exception {
        String key = ((Tuple1<String>) tuple).f0;
        String recloc=key;
        Long count = input.iterator().next();
        String dt=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(window.getEnd()));

        RecChartData recChartData = new RecChartData(
                RecChartData.TYPE_REALTIME,
                recloc,
                -1,
                "-1",
                this.target,
                Double.valueOf(String.valueOf(count)),
                dt
        );
        collector.collect(recChartData);
    }
}
