package com.kshv.example.jargogle_app.ui.main;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kshv.example.jargogle_app.AboutActivity;
import com.kshv.example.jargogle_app.JargogleDetailActivity;
import com.kshv.example.jargogle_app.JargogleSeekBarListener;
import com.kshv.example.jargogle_app.R;
import com.kshv.example.jargogle_app.model.Jargogle;
import com.kshv.example.jargogle_app.model.JargogleDataProvider;

import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private JargogleRecyclerViewAdapter adapter;
    private JargogleDataProvider provider;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        provider = JargogleDataProvider.getInstance(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.list);
        adapter = new JargogleRecyclerViewAdapter(provider.getJargogleList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteJargogleCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateJargogleList(JargogleDataProvider
                .getInstance(getContext()).getJargogleList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_add:
                provider.addJargogle(new Jargogle());
                adapter.updateJargogleList(provider.getJargogleList());
                adapter.notifyItemInserted(provider.getJargogleList().size() - 1);
                return true;
            case R.id.option_color:
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(new ContextThemeWrapper(
                                getActivity(), R.style.AppTheme));
                builder.setTitle("set col");
                View view = LayoutInflater.from(getContext()).inflate(R.layout.rgb_selector_view, null);
                SeekBar redBar = view.findViewById(R.id.seekBarRed);
                redBar.setMax(230);
                SeekBar greenBar = view.findViewById(R.id.seekBarGreen);
                greenBar.setMax(230);
                SeekBar blueBar = view.findViewById(R.id.seekBarBlue);
                blueBar.setMax(230);

                int[] seekBarProgresses = JargogleDataProvider.getInstance(getContext()).getSeekBarsPositions();
                redBar.setProgress(seekBarProgresses[0]);
                greenBar.setProgress(seekBarProgresses[1]);
                blueBar.setProgress(seekBarProgresses[2]);

                redBar.setOnSeekBarChangeListener(new JargogleSeekBarListener(getActivity(), redBar, greenBar, blueBar));
                greenBar.setOnSeekBarChangeListener(new JargogleSeekBarListener(getActivity(), redBar, greenBar, blueBar));
                blueBar.setOnSeekBarChangeListener(new JargogleSeekBarListener(getActivity(), redBar, greenBar, blueBar));

                builder.setView(view);
                builder.setNegativeButton("close", (dialog, which) -> {

                });
                builder.setCancelable(true);
                builder.show();
                return true;
            case R.id.option_about:
                startActivity(new Intent(getContext(), AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class JargogleViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView encoded_icon;

        JargogleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            encoded_icon = itemView.findViewById(R.id.list_item_icon);
        }
    }

    private class JargogleRecyclerViewAdapter extends RecyclerView.Adapter<JargogleViewHolder> {
        List<Jargogle> jargogleList;

        JargogleRecyclerViewAdapter(List<Jargogle> jargogleList) {
            this.jargogleList = jargogleList;
        }

        void updateJargogleList(List<Jargogle> newJargogleList) {
            this.jargogleList = newJargogleList;
        }

        @NonNull
        @Override
        public JargogleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JargogleViewHolder(LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull JargogleViewHolder holder, final int position) {
            Jargogle jargogle = jargogleList.get(position);
            holder.title.setText(jargogle.getTitle());
            holder.encoded_icon.setImageResource(jargogle.getEncoded() == 1 ?
                    R.drawable.ic_encode_list_item : R.drawable.ic_decode_list_item);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), JargogleDetailActivity.class);
                intent.putExtra(
                        Jargogle.JARGOGLE_POSITION, position);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return jargogleList.size();
        }
    }

    private class SwipeToDeleteJargogleCallback extends ItemTouchHelper.SimpleCallback {
        private Drawable icon;
        private ColorDrawable background;

        SwipeToDeleteJargogleCallback() {
            super(0, ItemTouchHelper.LEFT);
            icon = ContextCompat.getDrawable(Objects.requireNonNull(
                    getContext()), R.drawable.ic_delete_by_swipe);
            background = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c,
                                @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;

            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + icon.getIntrinsicHeight();

            if (dX < 0) {
                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else {
                background.setBounds(0, 0, 0, 0);
            }

            background.draw(c);
            //icon.draw (c);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Jargogle jargogleToRemove = provider.getJargogleList().get(position);
            if (jargogleToRemove.getEncoded() != 1) {
                provider.deleteJargogleRecord(jargogleToRemove);
            } else {
                Toast toast = Toast.makeText(getContext(),
                        R.string.jargogole_encoded, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
            adapter.updateJargogleList(provider.getJargogleList());
            adapter.notifyItemRemoved(position);
            onResume();
        }
    }
}
