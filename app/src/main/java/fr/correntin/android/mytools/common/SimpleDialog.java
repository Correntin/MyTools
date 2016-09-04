package fr.correntin.android.mytools.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import fr.correntin.android.mytools.R;

/**
 * Created by Corentin on 01/05/16.
 */
public class SimpleDialog extends Activity
{
    public static final String TEXT_EXTRA = "TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.simple_dialog);
        ((TextView) findViewById(R.id.simple_dialog_textview)).setText(this.getIntent().getStringExtra(TEXT_EXTRA));

    }
}
