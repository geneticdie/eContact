package xyz.voltwilz.econtact.ClassOnly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import xyz.voltwilz.econtact.R;

public class ManageUserAdapter extends RecyclerView.Adapter<ManageUserAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<UserProfile> userProfiles;
    private ArrayList<UserProfile> userProfilesFull;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ManageUserAdapter(Context c, ArrayList<UserProfile> u) {
        context = c;
        userProfiles = u;
        userProfilesFull = new ArrayList<>(userProfiles);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_manage_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String firstName = userProfiles.get(position).getFirstName();
        String lastName = userProfiles.get(position).getLastName();
        if (lastName != null) {
            String fullName = firstName + " " + lastName;
            holder.name.setText(fullName);
        } else {
            holder.name.setText(firstName);
        }
        holder.organization.setText(userProfiles.get(position).getOrganization());
        holder.orgDetail.setText(userProfiles.get(position).getOrgDetail());
        Glide.with(context)
                .load(userProfiles.get(position).getProfPicUrl())
                .placeholder(R.drawable.boy)
                .into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return userProfiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        View root;
        TextView name, organization, orgDetail;
        ImageView profilePicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.manageUser_name);
            organization = itemView.findViewById(R.id.manageUser_organization);
            orgDetail = itemView.findViewById(R.id.manageUser_orgDetail);
            profilePicture = itemView.findViewById(R.id.manageUser_profPic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @Override
    public Filter getFilter() {
        return userProfilesFilter;
    }

    private Filter userProfilesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<UserProfile> filteredUser = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredUser.addAll(userProfilesFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (UserProfile item : userProfilesFull) {
                    if (item.getFirstName().toLowerCase().contains(filterPattern) ||
                        item.getLastName().toLowerCase().contains(filterPattern) ||
                        item.getOrganization().toLowerCase().contains(filterPattern) ||
                        item.getOrgDetail().toLowerCase().contains(filterPattern) ||
                        (item.getFirstName().toLowerCase() + " " + item.getLastName().toLowerCase()).contains(filterPattern)) {
                        filteredUser.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredUser;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userProfiles.clear();
            userProfiles.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
