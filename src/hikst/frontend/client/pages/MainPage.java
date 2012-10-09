package hikst.frontend.client.pages;

import java.util.Date;

import hikst.frontend.client.SplineGraph;
import hikst.frontend.shared.Description;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;


import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;
 

public class MainPage extends Composite implements HasText {

	ObjectMenu panel;
	MyDockLayoutPanel oldPanel;
	SimResoults simPanel;
	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);
	@UiField Button buttonLogout;
	@UiField Button emailAdmin;
	@UiField Button adminAccount;
	@UiField Button oldFront;
	@UiField FlowPanel centerPanel;
	@UiField Button tilbake;
	@UiField Button simResoult;

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}

	public MainPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}
	
	@UiHandler("tilbake")
	void onBackClick(ClickEvent event){
		
		centerPanel.clear();
		centerPanel.add(SplineGraph.createChart());
		
	}

	@UiHandler("newSim")
	void onNewSimClick(ClickEvent event) {
		RootLayoutPanel.get().add(new ObjectMenu());
		panel = new ObjectMenu();
		RootLayoutPanel.get().add(panel);
	}
	@UiHandler("oldFront")
	void onOldFrontClick(ClickEvent event) {
		RootLayoutPanel.get().add(new MyDockLayoutPanel());
		oldPanel = new MyDockLayoutPanel();
		RootLayoutPanel.get().add(oldPanel);	
	}

	public Chart createChart() {  
		  
        final Chart chart = new Chart()  
            .setType(Series.Type.SPLINE)  
            .setMarginRight(10)  
            .setChartTitleText("Live random data")  
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
  
        final Series series = chart.createSeries();  
        chart.addSeries(series  
            .setName("Random data")  
        );  
  
        // Generate an array of random data  
        long time = new Date().getTime();  
        for(int i = -19; i <= 0; i++) {  
            series.addPoint(time + i * 1000, com.google.gwt.user.client.Random.nextDouble());  
        }  
  
        Timer tempTimer = new Timer() {  
            @Override  
            public void run() {  
                series.addPoint(  
                    new Date().getTime(),  
                    com.google.gwt.user.client.Random.nextDouble(),  
                    true, true, true  
                );  
            }  
        };  
        tempTimer.scheduleRepeating(1000);  
  
        return chart;  
    }  
	
	@UiHandler("simResoult")
	void onSimResoultClick(ClickEvent event){
		simPanel = new SimResoults();
		RootLayoutPanel.get().add(simPanel);
	}
}
