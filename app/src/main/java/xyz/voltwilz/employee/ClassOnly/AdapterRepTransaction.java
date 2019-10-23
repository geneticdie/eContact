package xyz.voltwilz.employee.ClassOnly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import xyz.voltwilz.employee.R;

public class AdapterRepTransaction extends RecyclerView.Adapter<AdapterRepTransaction.MyViewHolder> {

    private List<Transaction> transactionsList;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public AdapterRepTransaction(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_report_transaction, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction = transactionsList.get(position);
        holder.tranDate.setText(transaction.getTransfer_date());
        holder.tranTypeBudget.setText(transaction.getFrom_budget());
        holder.tranNote.setText(transaction.getNote());
        holder.tranFee.setText(formatRupiah.format(transaction.getFee()));
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tranDate, tranTypeBudget, tranNote, tranFee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tranDate = itemView.findViewById(R.id.listView_transactionDate);
            tranTypeBudget = itemView.findViewById(R.id.listView_transactionTypeBudget);
            tranNote = itemView.findViewById(R.id.listView_transactionNote);
            tranFee = itemView.findViewById(R.id.listView_transactionFee);
        }
    }
}
