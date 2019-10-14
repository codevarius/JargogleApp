package com.kshv.example.jargogle_app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kshv.example.jargogle_app.MainJargogleActivity;
import com.kshv.example.jargogle_app.R;
import com.kshv.example.jargogle_app.model.Jargogle;
import com.kshv.example.jargogle_app.model.JargogleCodeManager;
import com.kshv.example.jargogle_app.model.JargogleDataProvider;

import java.util.Objects;

public class JargogleDetailFragment extends Fragment {
    private EditText titleEditText,dataEditText,chainLenField,chainSeedField;
    private Jargogle jargogle;
    private Switch encodeSwitch;
    private String resultMessage;
    private String uuid;

    private JargogleDetailFragment(String uuid) {
        this.uuid = uuid;
    }

    public static JargogleDetailFragment newInstance(String uuid) {
        return new JargogleDetailFragment (uuid);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull (((AppCompatActivity)
                Objects.requireNonNull (getActivity ()))
                .getSupportActionBar ()).setDisplayHomeAsUpEnabled (true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.jargogle_detail_fragment,
                container,false);

        Objects.requireNonNull (((AppCompatActivity) Objects.requireNonNull (getActivity ()))
                .getSupportActionBar ()).setDisplayHomeAsUpEnabled (true);
        jargogle = JargogleDataProvider
                .getInstance (getContext ()).getJargogleByUUID(uuid);

        titleEditText = view.findViewById (R.id.jargogle_title);
        titleEditText.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jargogle.setTitle (s.toString ());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (titleEditText.getText ().length ()
                        >= getResources ().getInteger (R.integer.max_title_length)){
                    Toast.makeText (getContext (),
                            R.string.max_width_title_input_text,Toast.LENGTH_LONG).show ();
                }
            }
        });

        dataEditText = view.findViewById (R.id.jargogle_data);
        dataEditText.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jargogle.setData (s.toString ());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataEditText.getText ().length ()
                        >= getResources ().getInteger (R.integer.max_data_length)){
                    Toast.makeText (getContext (),
                            R.string.max_width_data_input_text,Toast.LENGTH_LONG).show ();
                }
            }
        });
        titleEditText.setText (jargogle.getTitle ());
        dataEditText.setText (jargogle.getData ());

        chainLenField = view.findViewById (R.id.chain_len);
        chainLenField.setText(jargogle.getChain_len());
        chainLenField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jargogle.setChain_len(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        chainSeedField = view.findViewById (R.id.seed);
        chainSeedField.setText(jargogle.getChain_seed());
        chainSeedField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jargogle.setChain_seed(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        encodeSwitch = view.findViewById (R.id.jargogle_encryption_switch);
        encodeSwitch.setChecked (jargogle.getEncoded () == 1);
        if (encodeSwitch.isChecked()){
            dataEditText.setEnabled(false);
        }
        encodeSwitch.setText (encodeSwitch.isChecked ()
                ? getString (R.string.jargogle_encoded)
                : getString (R.string.jargogle_decoded)
        );
        encodeSwitch.setOnCheckedChangeListener (new EncodeDecodeSwitchListener (
                JargogleDetailFragment.this));

        if (jargogle.getEncoded () == Jargogle.ENCODED){
            titleEditText.setEnabled(false);
            dataEditText.setEnabled(false);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu (menu, inflater);
        inflater.inflate(R.menu.jargogle_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()){
            case R.id.delete_jargogle:
                if (jargogle.getEncoded () < 1){
                    JargogleDataProvider.getInstance (
                            getContext ()).deleteJargogleRecord(jargogle);
                    startActivity (new Intent (getContext (), MainJargogleActivity.class));
                }else{
                    Toast toast = Toast.makeText (getContext (),
                            R.string.jargogole_encoded, Toast.LENGTH_SHORT);
                    toast.setGravity (Gravity.TOP, 0, 0);
                    toast.show ();
                }
                return true;

            case R.id.send_jargogle:
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType ("text/plain");
                intent.putExtra (Intent.EXTRA_TEXT,jargogle.getData ());
                startActivity (Intent
                        .createChooser (intent,getString (R.string.send_intent_title)));
                return true;

            default: return super.onOptionsItemSelected (item);
        }
    }

    @Override
    public void onPause() {
        super.onPause ();
        JargogleDataProvider.getInstance (
                getContext ()).updateJargogleRecord(jargogle);
    }

    private class EncodeDecodeSwitchListener implements CompoundButton.OnCheckedChangeListener{

        private final JargogleDetailFragment fragment;

        EncodeDecodeSwitchListener(JargogleDetailFragment jargogleDetailFragment) {
            this.fragment = jargogleDetailFragment;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if (jargogle.getPasswd () == null){
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(new ContextThemeWrapper (
                                    fragment.getActivity (),R.style.AppTheme));
                    builder.setTitle("Set password:");
                    final EditText input = new EditText(fragment.getContext ());
                    input.setInputType(
                            InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    builder.setPositiveButton("OK",
                            (dialog, which) -> jargogle.setPasswd (input.getText().toString()));

                    builder.setNegativeButton("Cancel",
                            (dialog, which) -> {
                                jargogle.setPasswd ("");
                                dialog.cancel();
                    });

                    builder.show();
                }

                titleEditText.setEnabled(false);
                dataEditText.setEnabled(false);
                chainLenField.setEnabled(false);
                chainSeedField.setEnabled(false);

                JargogleCodeManager jargogleCodeManager =
                        new JargogleCodeManager (
                                Long.parseLong(chainLenField.getText ().length () > 0
                                        ? chainLenField.getText ().toString () : "1"),
                                Long.parseLong(chainSeedField.getText ().length () > 0
                                        ? chainSeedField.getText ().toString () : "1"),
                                JargogleCodeManager.ENCODING);

                resultMessage = jargogleCodeManager.getJargogleChain ()
                        .processChain (dataEditText.getText ().toString ().toLowerCase ());
                resultMessage = '\0' + resultMessage;

                dataEditText.setText (resultMessage);
                encodeSwitch.setText (getString (R.string.jargogle_encoded));
                jargogle.setEncoded (Jargogle.ENCODED);

                Toast toast = Toast.makeText (getContext (),
                        R.string.mes_encoded,Toast.LENGTH_SHORT);
                toast.setGravity (Gravity.TOP,0,0);
                toast.show ();

            }else{


                AlertDialog.Builder builder =
                        new AlertDialog.Builder(new ContextThemeWrapper (
                                fragment.getActivity (),R.style.AppTheme));
                builder.setTitle("Enter password:");
                final EditText input = new EditText(getContext ());
                input.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK",
                        (dialog, which) -> {
                            if (input.getText().toString().equals (jargogle.getPasswd ())
                                    || jargogle.getPasswd () == null){
                                titleEditText.setEnabled(true);
                                dataEditText.setEnabled(true);
                                chainLenField.setEnabled(true);
                                chainSeedField.setEnabled(true);

                                JargogleCodeManager jargogleCodeManager =
                                        new JargogleCodeManager (
                                                Long.parseLong (chainLenField.getText ()
                                                        .length () > 0
                                                        ? chainLenField
                                                        .getText ().toString () : "1"),
                                                Long.parseLong (chainSeedField.getText ()
                                                        .length () > 0
                                                        ? chainSeedField
                                                        .getText ().toString () : "1"),
                                                JargogleCodeManager.DECODING);

                                resultMessage = jargogleCodeManager.getJargogleChain ()
                                        .processChain (dataEditText
                                                .getText ().toString ().toLowerCase ());
                                resultMessage.replace ('\0',' ');

                                dataEditText.setText (resultMessage);
                                encodeSwitch.setText (getString (R.string.jargogle_decoded));
                                jargogle.setEncoded (Jargogle.DECODED);

                                Toast toast = Toast.makeText (getContext (),
                                        R.string.mes_decoded, Toast.LENGTH_SHORT);
                                toast.setGravity (Gravity.TOP, 0, 0);
                                toast.show ();
                            }else{
                                encodeSwitch.setOnCheckedChangeListener (null);
                                encodeSwitch.setChecked (true);
                                encodeSwitch.setOnCheckedChangeListener (
                                        new EncodeDecodeSwitchListener (fragment));
                                Toast toast = Toast.makeText (getContext (),
                                        R.string.wrong_passwd, Toast.LENGTH_SHORT);
                                toast.setGravity (Gravity.TOP, 0, 0);
                                toast.show ();
                            }
                        });

                builder.setNegativeButton("Cancel",
                        (dialog, which) -> {
                            encodeSwitch.setOnCheckedChangeListener (null);
                            encodeSwitch.setChecked (true);
                            encodeSwitch.setOnCheckedChangeListener (
                                    new EncodeDecodeSwitchListener (fragment));
                            dialog.cancel();
                        });

                builder.show();
            }
        }
    }
}
