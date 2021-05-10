package com.zaker.clockhall.startgallry;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ArrayRes;

import com.bumptech.glide.Glide;
import com.zaker.clockhall.R;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class ForecastView extends LinearLayout {

    private Paint gradientPaint;
    public static int[] currentGradient;

    private ImageView weatherImage;

    private ArgbEvaluator evaluator;

    public ForecastView(Context context) {
        super(context);
    }

    public ForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        evaluator = new ArgbEvaluator();

        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        inflate(getContext(), R.layout.view_forecast, this);

        weatherImage = findViewById(R.id.weather_image);
    }

    private void initGradient() {
        float centerX = getWidth() * 0.5f;
        Shader gradient = new LinearGradient(
                centerX, 0, centerX, getHeight(),
                currentGradient, null,
                Shader.TileMode.MIRROR);
        gradientPaint.setShader(gradient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (currentGradient != null) {
            initGradient();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), gradientPaint);
        super.onDraw(canvas);
    }

    public void setForecast(Forecast forecast) {
        Weather weather = forecast.getWeather();
        currentGradient = weatherToGradient(weather);
        if (getWidth() != 0 && getHeight() != 0) {
            initGradient();
        }
        Glide.with(getContext()).load(weatherToIcon(weather)).into(weatherImage);
        invalidate();

        weatherImage.animate()
                .scaleX(1f).scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(300)
                .start();
    }

    public void onScroll(float fraction, Forecast oldF, Forecast newF) {
        weatherImage.setScaleX(fraction);
        weatherImage.setScaleY(fraction);
        currentGradient = mix(fraction,
                weatherToGradient(newF.getWeather()),
                weatherToGradient(oldF.getWeather()));
        initGradient();
        invalidate();
    }

    private int[] mix(float fraction, int[] c1, int[] c2) {
        return new int[]{
                (Integer) evaluator.evaluate(fraction, c1[0], c2[0]),
                (Integer) evaluator.evaluate(fraction, c1[1], c2[1]),
                (Integer) evaluator.evaluate(fraction, c1[2], c2[2])
        };
    }

    private int[] weatherToGradient(Weather weather) {
        switch (weather) {
            case EGYPT:
                return colors(R.array.gradient1);
            case SUDAN:
                return colors(R.array.gradient2);
            case DJIBOUTI:
                return colors(R.array.gradient3);
            case COMOROS:
                return colors(R.array.gradient4);
            case SOMALIA:
                return colors(R.array.gradient5);
            case ERITREA:
                return colors(R.array.gradient6);
            case MAURITANIA:
                return colors(R.array.gradient7);
            case ALGERIA:
                return colors(R.array.gradient8);
            case LIBYA:
                return colors(R.array.gradient9);
            case TUNIS:
                return colors(R.array.gradient10);
            case MOROCCO:
                return colors(R.array.gradient11);
            case PALESTINE:
                return colors(R.array.gradient12);
            case LEBANON:
                return colors(R.array.gradient13);
            case JORDAN:
                return colors(R.array.gradient14);
            case IRAQ:
                return colors(R.array.gradient15);
            case SYRIA:
                return colors(R.array.gradient16);
            case BAHRAIN:
                return colors(R.array.gradient17);
            case KUWAIT:
                return colors(R.array.gradient18);
            case OMAN:
                return colors(R.array.gradient19);
            case QATAR:
                return colors(R.array.gradient20);
            case SOUDIARAB:
                return colors(R.array.gradient21);
            case UAE:
                return colors(R.array.gradient22);
            case YEMEN:
                return colors(R.array.gradient23);
           case BURUNDI:
                return colors(R.array.gradient24);
            case ETHIOPIA:
                return colors(R.array.gradient25);
            case KENYA:
                return colors(R.array.gradient26);
            case MADAGASCAR:
                return colors(R.array.gradient27);
            case MALAWI:
                return colors(R.array.gradient28);
            case MAURITIUS:
                return colors(R.array.gradient29);
            case MOZAMBIQUE:
                return colors(R.array.gradient30);
            case RWANDA:
                return colors(R.array.gradient31);
            case SEYCHELLES:
                return colors(R.array.gradient32);
            case TANZANIA:
                return colors(R.array.gradient33);
            case UGANDA:
                return colors(R.array.gradient34);
            case ZAMBIA:
                return colors(R.array.gradient35);
            case ZIMBABWE:
                return colors(R.array.gradient36);
            case ANGOLA:
                return colors(R.array.gradient37);
            case CAMEROON:
                return colors(R.array.gradient38);
            case CENTRAL_AFRICAN_REPUBLIC:
                return colors(R.array.gradient39);
            case CHAD:
                return colors(R.array.gradient40);
            case DEMOCRATIC_CONGO:
                return colors(R.array.gradient41);
            case EQUATORIAL_GUINEA:
                return colors(R.array.gradient42);
            case GABON:
                return colors(R.array.gradient43);
            case CONGO:
                return colors(R.array.gradient44);
            case SãO_TOMé_AND_PRíNCIPE:
                return colors(R.array.gradient45);
            case BOTSWANA:
                return colors(R.array.gradient46);
            case LESOTHO:
                return colors(R.array.gradient47);
            case NAMIBIA:
                return colors(R.array.gradient48);
            case SOUTH_AFRICA:
                return colors(R.array.gradient49);
            case SWAZILAND:
                return colors(R.array.gradient50);
            case BENIN:
                return colors(R.array.gradient51);
            case BURKINA_FASO:
                return colors(R.array.gradient52);
            case CAPE_VERDE:
                return colors(R.array.gradient53);
            case GAMBIA:
                return colors(R.array.gradient54);
            case GHANA:
                return colors(R.array.gradient55);
            case GUINEA:
                return colors(R.array.gradient56);
            case GUINEA_bissau:
                return colors(R.array.gradient57);
            case LIBERIA:
                return colors(R.array.gradient58);
            case MALI:
                return colors(R.array.gradient59);
            case NIGER:
                return colors(R.array.gradient60);
            case NIGERIA:
                return colors(R.array.gradient61);
            case SENEGAL:
                return colors(R.array.gradient62);
            case SIERRA_LEANE:
                return colors(R.array.gradient63);
            case TOGO:
                return colors(R.array.gradient64);
            case KAZAKHSTAN:
                return colors(R.array.gradient65);
            case KYRGYZSTAN:
                return colors(R.array.gradient66);
            case TAJIKISTAN:
                return colors(R.array.gradient67);
            case TURKMENISTAN:
                return colors(R.array.gradient68);
            case UZBEKISTAN:
                return colors(R.array.gradient69);
            case AFGHANISTAN:
                return colors(R.array.gradient70);
            case CHINA:
                return colors(R.array.gradient71);
            case JAPAN:
                return colors(R.array.gradient72);
            case NORTH_KOREA:
                return colors(R.array.gradient73);
            case KOREA:
                return colors(R.array.gradient74);
            case MANGOLIA:
                return colors(R.array.gradient75);
            case BRUNEI:
                return colors(R.array.gradient76);
            case CAMBODIA:
                return colors(R.array.gradient77);
            case EAST_TIMOR:
                return colors(R.array.gradient78);
            case INDONESIA:
                return colors(R.array.gradient79);
            case LAOS:
                return colors(R.array.gradient80);
            case MALAYSIA:
                return colors(R.array.gradient81);
            case PHILIPPINES:
                return colors(R.array.gradient82);
            case SINGAPORE:
                return colors(R.array.gradient83);
            case THAILAND:
                return colors(R.array.gradient84);
            case BANGLDESH:
                return colors(R.array.gradient85);
            case BHUTAN:
                return colors(R.array.gradient86);
            case INDIA:
                return colors(R.array.gradient87);
            case NEPAL:
                return colors(R.array.gradient88);
            case PAKISTAN:
                return colors(R.array.gradient89);
            case SRI_LANKA:
                return colors(R.array.gradient90);
            case ARMRNIA:
                return colors(R.array.gradient91);
            case AZERBAIJAN:
                return colors(R.array.gradient92);
            case GEORGIA:
                return colors(R.array.gradient93);
            case IRAN:
                return colors(R.array.gradient94);
            case TURKEY:
                return colors(R.array.gradient95);
                //
            case ICELAND:
                return colors(R.array.gradient96);
            case NORWAY:
                return colors(R.array.gradient97);
            case SWEDEN:
                return colors(R.array.gradient98);
            case FINLAND:
                return colors(R.array.gradient99);
            case ESTONIA:
                return colors(R.array.gradient100);
            case LATVIA:
                return colors(R.array.gradient101);
            case LITHUANIA:
                return colors(R.array.gradient102);
            case DENMARK:
                return colors(R.array.gradient103);
            case IRELAND:
                return colors(R.array.gradient104);
            case UK:
                return colors(R.array.gradient105);
            case PORTUGAL:
                return colors(R.array.gradient106);
            case SPAIN:
                return colors(R.array.gradient107);
            case MALTA:
                return colors(R.array.gradient108);
            case ITALY:
                return colors(R.array.gradient109);
            case GREECE:
                return colors(R.array.gradient110);
            case ALBANIA:
                return colors(R.array.gradient111);
            case SERBIA:
                return colors(R.array.gradient112);
            case CROATIA:
                return colors(R.array.gradient113);
            case B_H:
                return colors(R.array.gradient114);
            case SLOVENIA:
                return colors(R.array.gradient115);
            case FRANCE:
                return colors(R.array.gradient116);
            case BELGIUM:
                return colors(R.array.gradient117);
            case LUXEMBOURG:
                return colors(R.array.gradient118);
            case NETHERLANDS:
                return colors(R.array.gradient119);
            case GERMANY:
                return colors(R.array.gradient120);
            case AUSTRIA:
                return colors(R.array.gradient121);
            case UKRAINE:
                return colors(R.array.gradient123);
            case CZECH:
                return colors(R.array.gradient124);
            case SLOVAKIA:
                return colors(R.array.gradient125);
            case RUSSIA:
                return colors(R.array.gradient126);
            case MOLDOVA:
                return colors(R.array.gradient127);
            case POLAND:
                return colors(R.array.gradient128);
            case ROMANIA:
                return colors(R.array.gradient129);
            case BULGARIA:
                return colors(R.array.gradient130);
            case HUNGARY:
                return colors(R.array.gradient131);
            case CANADA:
                return colors(R.array.gradient132);
            case MEXICO:
                return colors(R.array.gradient133);
            case USA:
                return colors(R.array.gradient134);
            case A_B:
                return colors(R.array.gradient135);
            case BAHAMAS:
                return colors(R.array.gradient136);
            case BARBADOS:
                return colors(R.array.gradient137);
            case CUBA:
                return colors(R.array.gradient138);
            case DOMINICA:
                return colors(R.array.gradient139);
            case DOMINICAN:
                return colors(R.array.gradient140);
            case GRENADA:
                return colors(R.array.gradient141);
            case HAITI:
                return colors(R.array.gradient142);
            case JAMAICA:
                return colors(R.array.gradient143);
            case S_K_N:
                return colors(R.array.gradient144);
            case S_L:
                return colors(R.array.gradient145);
            case S_V_G:
                return colors(R.array.gradient146);
            case T_T:
                return colors(R.array.gradient147);
            case BELIZE:
                return colors(R.array.gradient148);
            case COSTA_RICA:
                return colors(R.array.gradient149);
            case EL_SALVADOR:
                return colors(R.array.gradient150);
            case GUATEMALA:
                return colors(R.array.gradient151);
            case HONDURAS:
                return colors(R.array.gradient152);
            case NICARAGUA:
                return colors(R.array.gradient153);
            case PANAMA:
                return colors(R.array.gradient154);
            case ARGENTINA:
                return colors(R.array.gradient155);
            case BRAZIL:
                return colors(R.array.gradient156);
            case BOLIVIA:
                return colors(R.array.gradient157);
            case COLOMBIA:
                return colors(R.array.gradient158);
            case CHILE:
                return colors(R.array.gradient159);
            case ECUADOR:
                return colors(R.array.gradient160);
            case URUGUAY:
                return colors(R.array.gradient161);
            case PARAGUAY:
                return colors(R.array.gradient162);
            case SURINAME:
                return colors(R.array.gradient163);
            case VENEZUELA:
                return colors(R.array.gradient164);
            case AUSTRALIA:
                return colors(R.array.gradient165);
            default:
                throw new IllegalArgumentException();
        }
    }

    private int weatherToIcon(Weather weather) {
        switch (weather) {
            case EGYPT:
                return R.drawable.egypt;
            case SUDAN:
                return R.drawable.sudan;
            case DJIBOUTI:
                return R.drawable.djibouti;
            case COMOROS:
                return R.drawable.comoros;
            case SOMALIA:
                return R.drawable.somalia;
            case ERITREA:
                return R.drawable.eritrea;
            case MAURITANIA:
                return R.drawable.mauritania;
            case ALGERIA:
                return R.drawable.algeria;
            case LIBYA:
                return R.drawable.libya;
            case TUNIS:
                return R.drawable.tunis;
            case MOROCCO:
                return R.drawable.morocco;
            case PALESTINE:
                return R.drawable.palestine;
            case LEBANON:
                return R.drawable.lebanon;
            case JORDAN:
                return R.drawable.jordan;
            case IRAQ:
                return R.drawable.iraq;
            case SYRIA:
                return R.drawable.syria;
            case BAHRAIN:
                return R.drawable.bahrain;
            case KUWAIT:
                return R.drawable.kuwait;
            case OMAN:
                return R.drawable.oman;
            case QATAR:
                return R.drawable.qatar;
            case SOUDIARAB:
                return R.drawable.saudi_arabia;
            case UAE:
                return R.drawable.uae;
            case YEMEN:
                return R.drawable.yemen;
            case BURUNDI:
                return R.drawable.burundi;
            case ETHIOPIA:
                return R.drawable.ethiopia;
            case KENYA:
                return R.drawable.kenya;
            case MADAGASCAR:
                return R.drawable.madagascar;
            case MALAWI:
                return R.drawable.malawi;
            case MAURITIUS:
                return R.drawable.mauritius;
            case MOZAMBIQUE:
                return R.drawable.mozambique;
            case RWANDA:
                return R.drawable.rwanda;
            case SEYCHELLES:
                return R.drawable.seychelles;
            case TANZANIA:
                return R.drawable.tanzania;
            case UGANDA:
                return R.drawable.uganda;
            case ZAMBIA:
                return R.drawable.zambia;
            case ZIMBABWE:
                return R.drawable.zimbabwe;
            case ANGOLA:
                return R.drawable.angola;
            case CAMEROON:
                return R.drawable.cameroon;
            case CENTRAL_AFRICAN_REPUBLIC:
                return R.drawable.central_african;
            case CHAD:
                return R.drawable.chad;
            case DEMOCRATIC_CONGO:
                return R.drawable.congo_democratic_republic;
            case EQUATORIAL_GUINEA:
                return R.drawable.equatorial_guinea;
            case GABON:
                return R.drawable.gabon;
            case CONGO:
                return R.drawable.republic_of_the_congo;
            case SãO_TOMé_AND_PRíNCIPE:
                return R.drawable.sao_tome_and_prince;
            case BOTSWANA:
                return R.drawable.botswana;
            case LESOTHO:
                return R.drawable.lesotho;
            case NAMIBIA:
                return R.drawable.namibia;
            case SOUTH_AFRICA:
                return R.drawable.south_africa;
            case SWAZILAND:
                return R.drawable.swaziland;
            case BENIN:
                return R.drawable.benin;
            case BURKINA_FASO:
                return R.drawable.burkina_faso;
            case CAPE_VERDE:
                return R.drawable.cape_verde;
            case GAMBIA:
                return R.drawable.gambia;
            case GHANA:
                return R.drawable.ghana;
            case GUINEA:
                return R.drawable.guinea;
            case GUINEA_bissau:
                return R.drawable.guinea_bissau;
            case LIBERIA:
                return R.drawable.liberia;
            case MALI:
                return R.drawable.mali;
            case NIGER:
                return R.drawable.niger;
            case NIGERIA:
                return R.drawable.nigeria;
            case SENEGAL:
                return R.drawable.senegal;
            case SIERRA_LEANE:
                return R.drawable.sierra_leone;
            case TOGO:
                return R.drawable.togo;
            case KAZAKHSTAN:
                return R.drawable.kazakhstan;
            case KYRGYZSTAN:
                return R.drawable.kyrgyzstan;
            case TAJIKISTAN:
                return R.drawable.tajikistan;
            case TURKMENISTAN:
                return R.drawable.turkmenistan;
            case UZBEKISTAN:
                return R.drawable.uzbekistan;
            case AFGHANISTAN:
                return R.drawable.afghanistan;
            case CHINA:
                return R.drawable.china;
            case JAPAN:
                return R.drawable.japan;
            case NORTH_KOREA:
                return R.drawable.north_korea;
            case KOREA:
                return R.drawable.korea;
            case MANGOLIA:
                return R.drawable.mongolia;
            case BRUNEI:
                return R.drawable.brunei;
            case CAMBODIA:
                return R.drawable.cambodia;
            case EAST_TIMOR:
                return R.drawable.east_timor;
            case INDONESIA:
                return R.drawable.indonesia;
            case LAOS:
                return R.drawable.laos;
            case MALAYSIA:
                return R.drawable.malaysia;
            case PHILIPPINES:
                return R.drawable.philippines;
            case SINGAPORE:
                return R.drawable.singapore;
            case THAILAND:
                return R.drawable.thailand;
            case BANGLDESH:
                return R.drawable.bangladesh;
            case BHUTAN:
                return R.drawable.bhutan;
            case INDIA:
                return R.drawable.india;
            case NEPAL:
                return R.drawable.nepal;
            case PAKISTAN:
                return R.drawable.pakistan;
            case SRI_LANKA:
                return R.drawable.sri_lanka;
            case ARMRNIA:
                return R.drawable.armenia;
            case AZERBAIJAN:
                return R.drawable.azerbaijan;
            case GEORGIA:
                return R.drawable.georgia;
            case IRAN:
                return R.drawable.iran;
            case TURKEY:
                return R.drawable.turkey;
            case ICELAND:
                return R.drawable.iceland;
            case NORWAY:
                return R.drawable.norway;
            case SWEDEN:
                return R.drawable.sweden;
            case FINLAND:
                return R.drawable.finland;
            case ESTONIA:
                return R.drawable.estonia;
            case LATVIA:
                return R.drawable.latvia;
            case LITHUANIA:
                return R.drawable.lithuania;
            case DENMARK:
                return R.drawable.denmark;
            case IRELAND:
                return R.drawable.ireland;
            case UK:
                return R.drawable.united_kingdom;
            case PORTUGAL:
                return R.drawable.portugal;
            case SPAIN:
                return R.drawable.spain;
            case MALTA:
                return R.drawable.malta;
            case ITALY:
                return R.drawable.italy;
            case GREECE:
                return R.drawable.greece;
            case ALBANIA:
                return R.drawable.albania;
            case SERBIA:
                return R.drawable.serbia;
            case CROATIA:
                return R.drawable.croatia;
            case B_H:
                return R.drawable.bosnia_and_herzegovina;
            case SLOVENIA:
                return R.drawable.slovenia;
            case FRANCE:
                return R.drawable.france;
            case BELGIUM:
                return R.drawable.belgium;
            case LUXEMBOURG:
                return R.drawable.luxembourg;
            case NETHERLANDS:
                return R.drawable.netherlands;
            case GERMANY:
                return R.drawable.germany;
            case AUSTRIA:
                return R.drawable.austria;
            case UKRAINE:
                return R.drawable.ukraine;
            case CZECH:
                return R.drawable.czech;
            case SLOVAKIA:
                return R.drawable.slovakia;
            case RUSSIA:
                return R.drawable.russia;
            case MOLDOVA:
                return R.drawable.moldova;
            case POLAND:
                return R.drawable.poland;
            case ROMANIA:
                return R.drawable.romania;
            case BULGARIA:
                return R.drawable.bulgaria;
            case HUNGARY:
                return R.drawable.hungary;
            case CANADA:
                return R.drawable.canada;
            case MEXICO:
                return R.drawable.mexico;
            case USA:
                return R.drawable.usa;
            case A_B:
                return R.drawable.antigua_and_barbuda;
            case BAHAMAS:
                return R.drawable.bahamas;
            case BARBADOS:
                return R.drawable.barbados;
            case CUBA:
                return R.drawable.cuba;
            case DOMINICA:
                return R.drawable.dominica;
            case DOMINICAN:
                return R.drawable.dominican;
            case GRENADA:
                return R.drawable.grenada;
            case HAITI:
                return R.drawable.haiti;
            case JAMAICA:
                return R.drawable.jamaica;
            case S_K_N:
                return R.drawable.saint_kitts_and_nevis;
            case S_L:
                return R.drawable.saint_lucia;
            case S_V_G:
                return R.drawable.saint_vincent_and_the_grenadines;
            case T_T:
                return R.drawable.trinidad_and_tobago;
            case BELIZE:
                return R.drawable.belize;
            case COSTA_RICA:
                return R.drawable.costa_rica;
            case EL_SALVADOR:
                return R.drawable.el_salvador;
            case GUATEMALA:
                return R.drawable.guatemala;
            case HONDURAS:
                return R.drawable.honduras;
            case NICARAGUA:
                return R.drawable.nicaragua;
            case PANAMA:
                return R.drawable.panama;
            case ARGENTINA:
                return R.drawable.argentina;
            case BRAZIL:
                return R.drawable.brazil;
            case BOLIVIA:
                return R.drawable.bolivia;
            case COLOMBIA:
                return R.drawable.colombia;
            case CHILE:
                return R.drawable.chile;
            case ECUADOR:
                return R.drawable.ecuador;
            case URUGUAY:
                return R.drawable.uruguay;
            case PARAGUAY:
                return R.drawable.paraguay;
            case SURINAME:
                return R.drawable.suriname;
            case VENEZUELA:
                return R.drawable.venezuela;
            case AUSTRALIA:
                return R.drawable.australia;
            default:
                throw new IllegalArgumentException();
        }
    }

    private int[] colors(@ArrayRes int res) {
        return getContext().getResources().getIntArray(res);
    }

}
