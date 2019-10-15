package xyz.voltwilz.employee.ClassOnly;

public class Transaction {
    String idStaff, transfer_date, receiver_name, from_budget, notes;
    Integer fee;

    public Transaction(String idStaff, String transfer_date, String receiver_name, String from_budget, String notes, Integer fee) {
        this.idStaff = idStaff;
        this.transfer_date = transfer_date;
        this.receiver_name = receiver_name;
        this.from_budget = from_budget;
        this.notes = notes;
        this.fee = fee;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getFrom_budget() {
        return from_budget;
    }

    public void setFrom_budget(String from_budget) {
        this.from_budget = from_budget;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
