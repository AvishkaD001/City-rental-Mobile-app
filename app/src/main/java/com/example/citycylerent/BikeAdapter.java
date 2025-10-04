package com.example.citycylerent;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.BikeViewHolder> implements Filterable {

    private List<Bike> bikeList;
    private List<Bike> bikeListFull; // Full list for filtering
    private OnReserveClickListener reserveClickListener;

    public BikeAdapter(List<Bike> bikeList, OnReserveClickListener reserveClickListener) {
        this.bikeList = bikeList;
        this.bikeListFull = new ArrayList<>(bikeList); // Copy of the full list
        this.reserveClickListener = reserveClickListener;
    }

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bike, parent, false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
        Bike bike = bikeList.get(position);
        holder.textViewBikeName.setText(bike.name);
        holder.textViewAvailability.setText(bike.isAvailable ? "Available" : "Reserved");

        if (!bike.isAvailable) {
            holder.textViewReservationDetails.setText(
                    "Reserved by: " + bike.reservedBy + "\n" +
                            "Date: " + bike.reservationDate + "\n" +
                            "Time: " + bike.reservationTime + "\n" +
                            "Station: " + bike.stationName + " (" + bike.stationLocation + ")"
            );
        } else {
            holder.textViewReservationDetails.setText(
                    "Station: " + bike.stationName + " (" + bike.stationLocation + ")"
            );
        }

        holder.buttonReserve.setOnClickListener(v -> {
            if (reserveClickListener != null) {
                reserveClickListener.onReserveClick(bike);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    @Override
    public Filter getFilter() {
        return bikeFilter;
    }

    private Filter bikeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Bike> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // If the search query is empty, show the full list
                filteredList.addAll(bikeListFull);
            } else {
                // Filter the list based on the search query
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Bike bike : bikeListFull) {
                    // Search by bike name OR station location
                    if (bike.name.toLowerCase().contains(filterPattern) ||
                            bike.stationLocation.toLowerCase().contains(filterPattern)) {
                        filteredList.add(bike);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bikeList.clear();
            bikeList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class BikeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBikeName, textViewAvailability, textViewReservationDetails;
        Button buttonReserve;

        public BikeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBikeName = itemView.findViewById(R.id.textViewBikeName);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
            textViewReservationDetails = itemView.findViewById(R.id.textViewReservationDetails);
            buttonReserve = itemView.findViewById(R.id.buttonReserve);
        }
    }

    public interface OnReserveClickListener {
        void onReserveClick(Bike bike);
    }
}