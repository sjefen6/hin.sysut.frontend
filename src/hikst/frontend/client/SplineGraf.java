package hikst.frontend.client;

import java.util.Date;

import com.google.gwt.i18n.client.*;  
import com.google.gwt.core.client.EntryPoint;  
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;  
import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*; 

public class SplineGraf {
	Chart chart = new Chart();
	final Series series = chart.createSeries();;
	
	public void onModuleLoad() {  
	        RootPanel.get().add(createChart());  
	    }  
	
	public Chart createChart() {  
		  
       chart.setType(Series.Type.SPLINE)  
            .setMarginRight(10)  
            .setChartTitleText("Strømforbruk")  
            .setBarPlotOptions(new BarPlotOptions()  
                .setDataLabels(new DataLabels()  
                    .setEnabled(true)  
                )  
            )  
            .setLegend(new Legend()  
                .setEnabled(false)  
            )  
            .setCredits(new Credits()  
                .setEnabled(false)  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
                            DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")  
                                .format(new Date(toolTipData.getXAsLong())) + "<br/>" +  
                            NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble());  
                    }  
                })  
            );  
  
        chart.getXAxis()  
            .setType(Axis.Type.DATE_TIME)  
            .setTickPixelInterval(150);  
  
        chart.getYAxis()  
            .setAxisTitleText("Value")  
            .setPlotLines(  
                chart.getYAxis().createPlotLine()  
                    .setValue(0)  
                    .setWidth(1)  
                    .setColor("#808080")  
            );  
   
        chart.addSeries(series  
            .setName("Strømforbruk-data")  
        );  
  
        //Generate an array of random data  
        //long time = new Date().getTime();  
        //for(int i = -19; i <= 0; i++) {  
        //    series.addPoint(time + i * 1000, com.google.gwt.user.client.Random.nextDouble());  
        //}  
  
//        Timer tempTimer = new Timer() {  
//            @Override  
//            public void run() {  
//                series.addPoint(  
//                    new Date().getTime(),  
//                    com.google.gwt.user.client.Random.nextDouble(),  
//                    true, true, true  
//                );  
//            }  
//        };  
//        tempTimer.scheduleRepeating(1000);  
  
        return chart;  
    }  
	
	public int size()
	{
		return series.getPoints().length;
	}
	
	//vi må kanskje telle punktene og sette den andre booleanen til false 
	//sånn at grafen shifter til venstre når det lir fullt! true,true,true liksom 
	//dette bør vi diskutere innad i gruppa. går det ant å scrolle grafen?
	public void addPoint(Double d, long l){
		series.addPoint(  
                l,  
                d,  
                true, false, true);  		
	}
}
