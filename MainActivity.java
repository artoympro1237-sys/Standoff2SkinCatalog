package com.example.standoff2skincatalog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int BG = Color.rgb(16,17,20);
    private final int PANEL = Color.rgb(29,31,37);
    private final int RED = Color.rgb(240,68,56);
    private LinearLayout content;
    private String category = "Оружие";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        build();
    }

    private void build() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        root.setPadding(dp(18), dp(18), dp(18), dp(10));

        TextView brand = text("SKIN CATALOG", 13, RED, true);
        root.addView(brand);
        TextView heading = text("Коллекция скинов", 27, Color.WHITE, true);
        LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-1, -2);
        hp.topMargin = dp(5);
        root.addView(heading, hp);
        TextView sub = text("Визуальный каталог • без Root", 13, 0xFF9EA3AD, false);
        root.addView(sub);

        Button login = button("Войти через Google");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, dp(48));
        lp.topMargin = dp(16);
        root.addView(login, lp);
        login.setOnClickListener(v -> new AlertDialog.Builder(this)
            .setTitle("Google-вход")
            .setMessage("Для включения настоящего Google-входа добавьте Firebase-проект и файл google-services.json. Пока каталог доступен без авторизации. Приложение никогда не запрашивает пароль Google.")
            .setPositiveButton("Понятно", null).show());

        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);
        String[] cats = {"Оружие", "Ножи", "Перчатки"};
        for (String c : cats) {
            Button b = button(c);
            LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(0, dp(42), 1);
            bp.setMargins(dp(2), dp(14), dp(2), dp(10));
            tabs.addView(b, bp);
            b.setOnClickListener(v -> { category = c; renderItems(); });
        }
        root.addView(tabs);

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView foot = text("Не изменяет файлы игры и не влияет на игровой аккаунт.", 11, 0xFF777D88, false);
        foot.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(-1, dp(34));
        root.addView(foot, fp);
        setContentView(root);
        renderItems();
    }

    private void renderItems() {
        content.removeAllViews();
        String[][] items;
        if (category.equals("Ножи")) {
            items = new String[][]{{"Karambit","Legendary • пример"},{"Butterfly","Epic • пример"},{"Kunai","Rare • пример"}};
        } else if (category.equals("Перчатки")) {
            items = new String[][]{{"Sport Gloves","Legendary • пример"},{"Hand Wraps","Epic • пример"},{"Driver Gloves","Rare • пример"}};
        } else {
            items = new String[][]{{"M4A1 — Neon","Legendary • пример"},{"AKR — Carbon","Epic • пример"},{"AWM — Frost","Rare • пример"},{"USP — Digital","Epic • пример"}};
        }
        for (String[] item : items) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(dp(15), dp(13), dp(15), dp(13));
            card.setBackground(round(PANEL, 14));
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
            cp.bottomMargin = dp(10);
            content.addView(card, cp);

            TextView art = text("✦", 34, RED, true);
            art.setGravity(Gravity.CENTER);
            art.setBackground(round(0xFF242730, 12));
            card.addView(art, new LinearLayout.LayoutParams(-1, dp(76)));
            TextView name = text(item[0], 18, Color.WHITE, true);
            LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1, -2);
            np.topMargin = dp(10);
            card.addView(name, np);
            card.addView(text(item[1], 12, 0xFF9EA3AD, false));
            Button preview = button("Посмотреть");
            LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1, dp(40));
            pp.topMargin = dp(10);
            card.addView(preview, pp);
            preview.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle(item[0])
                .setMessage("Демонстрационная карточка скина. Это приложение показывает каталог и не применяет предмет в Standoff 2.")
                .setPositiveButton("Закрыть", null).show());
        }
    }

    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }
    private Button button(String s) {
        Button b = new Button(this);
        b.setText(s); b.setTextColor(Color.WHITE); b.setTextSize(12);
        b.setAllCaps(false);
        b.setBackground(round(RED, 10));
        return b;
    }
    private GradientDrawable round(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(dp(radius)); return d;
    }
    private int dp(int x) { return (int)(x * getResources().getDisplayMetrics().density + 0.5f); }
}
