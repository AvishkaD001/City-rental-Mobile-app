package com.example.citycylerent;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RentalHistoryAdapter extends RecyclerView.Adapter<RentalHistoryAdapter.RentalHistoryViewHolder> {

    private List<Bike> rentalHistoryList;

    public RentalHistoryAdapter(List<Bike> rentalHistoryList) {
        this.rentalHistoryList = rentalHistoryList;
    }

    @NonNull
    @Override
    public RentalHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rental_history, parent, false);
        return new RentalHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalHistoryViewHolder holder, int position) {
        Bike reservation = rentalHistoryList.get(position);
        holder.textViewBikeName.setText(reservation.name);
        holder.textViewReservationDate.setText("Date: " + reservation.reservationDate);
        holder.textViewReservationTime.setText("Time: " + reservation.reservationTime);
        holder.textViewStationId.setText("Station ID: " + reservation.stationId);
    }

    @Override
    public int getItemCount() {
        return rentalHistoryList.size();
    }

    public static class RentalHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBikeName, textViewReservationDate, textViewReservationTime, textViewStationId;

        public RentalHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBikeName = itemView.findViewById(R.id.textViewBikeName);
            textViewReservationDate = itemView.findViewById(R.id.textViewReservationDate);
            textViewReservationTime = itemView.findViewById(R.id.textViewReservationTime);
            textViewStationId = itemView.findViewById(R.id.textViewStationname);
        }
    }
}