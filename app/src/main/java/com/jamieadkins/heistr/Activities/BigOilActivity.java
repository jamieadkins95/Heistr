package com.jamieadkins.heistr.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jamieadkins.heistr.R;

/**
 * Created by Jamie on 14/04/2016.
 */
public class BigOilActivity extends AppCompatActivity {

    private enum Element {
        HELIUM,
        DEUTERIUM,
        NITROGEN,
        UNKNOWN
    }

    private enum Nozzle {
        ONE,
        TWO,
        THREE,
        UNKNOWN
    }

    private enum Pressure {
        GREATER_THAN,
        LESS_THAN,
        EQUAL,
        UNKNOWN
    }

    private static class Engine {
        private Element mElement;
        private Nozzle mNozzle;
        private Pressure mPressure;

        private Engine(Element element, Nozzle nozzle,
                       Pressure pressure) {
            mElement = element;
            mPressure = pressure;
            mNozzle = nozzle;
        }

        private boolean meetsRequirments(Element element,
                                     Nozzle nozzle,
                                     Pressure pressure) {
            return meetsElement(element) &&
                    meetsNozzle(nozzle) &&
                    meetsPressure(pressure);
        }

        private boolean meetsPressure(Pressure pressure) {
            return (pressure == mPressure || pressure == Pressure.UNKNOWN)
                    && mPressure != Pressure.EQUAL;
        }

        private boolean meetsNozzle(Nozzle nozzle) {
            return nozzle == mNozzle || nozzle == Nozzle.UNKNOWN;
        }

        private boolean meetsElement(Element element) {
            return element == mElement || element == Element.UNKNOWN;
        }
    }

    private static final Engine[] ENGINES = new Engine[] {
            new Engine(Element.NITROGEN, Nozzle.ONE, Pressure.LESS_THAN),
            new Engine(Element.DEUTERIUM, Nozzle.ONE, Pressure.GREATER_THAN),

            new Engine(Element.HELIUM, Nozzle.TWO, Pressure.EQUAL),
            new Engine(Element.NITROGEN, Nozzle.TWO, Pressure.EQUAL),

            new Engine(Element.DEUTERIUM, Nozzle.TWO, Pressure.LESS_THAN),
            new Engine(Element.HELIUM, Nozzle.TWO, Pressure.GREATER_THAN),

            new Engine(Element.HELIUM, Nozzle.THREE, Pressure.LESS_THAN),
            new Engine(Element.NITROGEN, Nozzle.THREE, Pressure.LESS_THAN),

            new Engine(Element.DEUTERIUM, Nozzle.THREE, Pressure.LESS_THAN),
            new Engine(Element.HELIUM, Nozzle.THREE, Pressure.EQUAL),

            new Engine(Element.NITROGEN, Nozzle.THREE, Pressure.GREATER_THAN),
            new Engine(Element.DEUTERIUM, Nozzle.THREE, Pressure.GREATER_THAN),
    };

    private Element mCurrentElement = Element.UNKNOWN;
    private Pressure mCurrentPressure = Pressure.UNKNOWN;
    private Nozzle mCurrentNuzzle = Nozzle.UNKNOWN;

    private RadioGroup mRadioGroupElement;
    private RadioGroup mRadioGroupPressure;
    private RadioGroup mRadioGroupNozzle;

    private TextView[] mEngineTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_oil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRadioGroupPressure = (RadioGroup) findViewById(R.id.radioGroupPressure);
        mRadioGroupElement = (RadioGroup) findViewById(R.id.radioGroupElement);
        mRadioGroupNozzle = (RadioGroup) findViewById(R.id.radioGroupNozzle);

        mEngineTextViews = new TextView[ENGINES.length];
        mEngineTextViews[0] = (TextView) findViewById(R.id.engine_1);
        mEngineTextViews[1] = (TextView) findViewById(R.id.engine_2);
        mEngineTextViews[2] = (TextView) findViewById(R.id.engine_3);
        mEngineTextViews[3] = (TextView) findViewById(R.id.engine_4);
        mEngineTextViews[4] = (TextView) findViewById(R.id.engine_5);
        mEngineTextViews[5] = (TextView) findViewById(R.id.engine_6);
        mEngineTextViews[6] = (TextView) findViewById(R.id.engine_7);
        mEngineTextViews[7] = (TextView) findViewById(R.id.engine_8);
        mEngineTextViews[8] = (TextView) findViewById(R.id.engine_9);
        mEngineTextViews[9] = (TextView) findViewById(R.id.engine_10);
        mEngineTextViews[10] = (TextView) findViewById(R.id.engine_11);
        mEngineTextViews[11] = (TextView) findViewById(R.id.engine_12);

        setupTextViews();

        mRadioGroupElement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbNitrogen:
                        mCurrentElement = Element.NITROGEN;
                        break;
                    case R.id.rbDeuterium:
                        mCurrentElement = Element.DEUTERIUM;
                        break;
                    case R.id.rbHelium:
                        mCurrentElement = Element.HELIUM;
                        break;
                    case R.id.rbUnknownElement:
                        mCurrentElement = Element.UNKNOWN;
                        break;
                }

                setupTextViews();
            }
        });

        mRadioGroupPressure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbGreaterThanPressure:
                        mCurrentPressure = Pressure.GREATER_THAN;
                        break;
                    case R.id.rbLessThanPressure:
                        mCurrentPressure = Pressure.LESS_THAN;
                        break;
                    case R.id.rbUnknownPressure:
                        mCurrentPressure = Pressure.UNKNOWN;
                        break;
                }

                setupTextViews();
            }
        });

        mRadioGroupNozzle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1Nozzles:
                        mCurrentNuzzle = Nozzle.ONE;
                        break;
                    case R.id.rb2Nozzles:
                        mCurrentNuzzle = Nozzle.TWO;
                        break;
                    case R.id.rb3Nozzles:
                        mCurrentNuzzle = Nozzle.THREE;
                        break;
                    case R.id.rbUnknownNozzles:
                        mCurrentNuzzle = Nozzle.UNKNOWN;
                        break;
                }

                setupTextViews();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_big_oil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                String url = "http://payday.wikia.com/wiki/Big_Oil";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupTextViews() {
        for (int engine = 0; engine < ENGINES.length; engine ++) {
            int colourToSet = R.color.textHint;
            if (ENGINES[engine].meetsRequirments(mCurrentElement, mCurrentNuzzle, mCurrentPressure)) {
                colourToSet = R.color.textPrimary;
            }

            mEngineTextViews[engine].setTextColor(ContextCompat.getColor(this, colourToSet));
        }
    }
}
