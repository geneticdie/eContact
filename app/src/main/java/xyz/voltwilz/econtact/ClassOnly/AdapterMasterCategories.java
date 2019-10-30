package xyz.voltwilz.econtact.ClassOnly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import xyz.voltwilz.econtact.R;

public class AdapterMasterCategories extends RecyclerView.Adapter<AdapterMasterCategories.MyViewHolder> {

    private List<String> listCategories;

    public AdapterMasterCategories(List<String> listCategories) {
        this.listCategories = listCategories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_master_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String listItem = listCategories.get(position);
        System.out.println(listItem);
        holder.listCategory.setText(listItem);
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView listCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listCategory = itemView.findViewById(R.id.listViewCategory_categories);
        }
    }
}
