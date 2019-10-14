package com.kshv.example.jargogle_app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kshv.example.jargogle_app.AboutActivity;
import com.kshv.example.jargogle_app.JargogleDetailActivity;
import com.kshv.example.jargogle_app.R;
import com.kshv.example.jargogle_app.model.Jargogle;
import com.kshv.example.jargogle_app.model.JargogleDataProvider;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private JargogleDataProvider provider;

    public static MainFragment newInstance() {
        return new MainFragment ();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.main_fragment, container, false);
        provider = JargogleDataProvider.getInstance (getContext ());
        recyclerView = view.findViewById (R.id.list);
        recyclerView.addItemDecoration (
                new DividerItemDecoration (Objects.requireNonNull (
                        getContext ()),DividerItemDecoration.VERTICAL)
        );
        adapter = new MyAdapter (provider.getJargogleList ());
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
        recyclerView.setAdapter (adapter);
        setHasOptionsMenu (true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume ();
        adapter.updateJargogleList (JargogleDataProvider
                .getInstance (getContext ()).getJargogleList ());
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu (menu, inflater);
        inflater.inflate (R.menu.top_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()){
            case R.id.option_add:
                provider.addJargogle (new Jargogle ());
                adapter.updateJargogleList (provider.getJargogleList ());
                adapter.notifyDataSetChanged ();
                return true;
            case R.id.option_about:
                startActivity (new Intent (getContext (), AboutActivity.class));
                return true;
            default: return super.onOptionsItemSelected (item);
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView encoded_icon;

        MyHolder(@NonNull View itemView) {
            super (itemView);
            title = itemView.findViewById (R.id.title);
            encoded_icon = itemView.findViewById (R.id.list_item_icon);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder>{
        List<Jargogle> jargogleList;

        MyAdapter(List<Jargogle> jargogleList) {
            this.jargogleList = jargogleList;
        }

        void updateJargogleList(List<Jargogle> newJargogleList){
            this.jargogleList = newJargogleList;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from (getContext ())
                    .inflate (R.layout.list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
            Jargogle jargogle = jargogleList.get (position);
            holder.title.setText (jargogle.getTitle ());
            holder.encoded_icon.setImageResource (jargogle.getEncoded () == 1 ?
                    R.drawable.ic_encode_list_item : R.drawable.ic_decode_list_item);
            holder.itemView.setOnClickListener (v -> {
                Intent intent = new Intent (getContext (), JargogleDetailActivity.class);
                intent.putExtra (
                        Jargogle.JARGOGLE_POSITION,position);
                startActivity (intent);
            });
        }

        @Override
        public int getItemCount() {
            return jargogleList.size ();
        }
    }
}
