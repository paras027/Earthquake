package com.example.apiproject2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class recadapter extends RecyclerView.Adapter<recadapter.ViewHolder> {
    LayoutInflater inflater;
    List<Earthquake> E1;
    String primaryLocation;
    String locationOffset;
    Context context;
    private Cursor mCursor;
    ProgressBar progressBar;
    private static final String LOCATION_SEPARATOR = " of ";

    public recadapter(Context ctx, List<Earthquake> a)
    {
        this.inflater=LayoutInflater.from(ctx);
        this.E1=a;
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return magnitudeColorResourceId;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sahise,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String formattedMagnitude = formatMagnitude(E1.get(position).getMmagnitude());
        holder.mag.setText(formattedMagnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.mag.getBackground();
        int magnitudeColor = getMagnitudeColor(E1.get(position).getMmagnitude());
        magnitudeCircle.setColor(ContextCompat.getColor(context,magnitudeColor));

        String originalLocation =(E1.get(position).getMlocation());
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = context.getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        holder.lo.setText(locationOffset);
        holder.pl.setText(primaryLocation);

        // Format the date string (i.e. "Mar 3, 1984")
        Date dateObject = new Date((E1.get(position).getmDate()));
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        holder.date.setText(formattedDate);
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        holder.time.setText(formattedTime);

        holder.pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Earthquake e1=E1.get(position);
                        String a=e1.getMlocation();
                        double b=e1.getMmagnitude();
                        String rl=e1.getUrl();
                        Intent intent=new Intent(view.getContext(),feelit.class);
                        intent.putExtra(Intent.EXTRA_TEXT,a);
                        intent.putExtra("add",b);
                        intent.putExtra("aa",position);
                        intent.putExtra("aaa",rl );
                        // Send the intent to launch a new activity
                        context.startActivity(intent);
                    }
                });

    }

    private String formatDate(Date dateObject)   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    @Override
    public int getItemCount() {
        return E1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mag,lo,pl,date,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mag =itemView.findViewById(R.id.mag);
            lo=itemView.findViewById(R.id.location_offset);
            pl=itemView.findViewById(R.id.primary_location);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
        }
    }
}
