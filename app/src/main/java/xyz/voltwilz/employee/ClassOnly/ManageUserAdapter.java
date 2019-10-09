package xyz.voltwilz.employee.ClassOnly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import xyz.voltwilz.employee.FragmentManageUser;
import xyz.voltwilz.employee.Profile;
import xyz.voltwilz.employee.R;

public class ManageUserAdapter extends RecyclerView.Adapter<ManageUserAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserProfile> userProfiles;
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
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_manage_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(userProfiles.get(position).getName());
        holder.organization.setText(userProfiles.get(position).getOrganization());
        holder.orgDetail.setText(userProfiles.get(position).getOrgDetail());
        Glide.with(context)
                .load(userProfiles.get(position).getProfPicUrl())
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
}
