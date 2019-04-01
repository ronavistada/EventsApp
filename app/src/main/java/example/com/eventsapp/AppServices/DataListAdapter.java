package example.com.eventsapp.AppServices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import example.com.eventsapp.DataClasses.EventData;
import example.com.eventsapp.R;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.CustomViewHolder> {

    private List<EventData> eventlist;
    private Context context;

    public DataListAdapter(Context context, List<EventData> events){
        this.context = context;
        this.eventlist = events;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_data_layout,viewGroup,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.eventName.setText(eventlist.get(i).getItemtitle());
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy h:mm");
        String date = DATE_FORMAT.format(eventlist.get(i).getStartdatetime());
        if (eventlist.get(i).getStartdatetime().getHours() < 12)
            date = date + "AM - ";
        else
            date = date + "PM - ";

        DATE_FORMAT = new SimpleDateFormat("h:mm");
        date = date + DATE_FORMAT.format(eventlist.get(i).getEnddatetime());
        if (eventlist.get(i).getEnddatetime().getHours() < 12)
            date = date + "AM";
        else
            date = date + "PM";
        customViewHolder.eventDate.setText(date);

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(eventlist.get(i).getUrlimage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .into(customViewHolder.coverImage);
    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView eventName;
        TextView eventDate;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            eventName = mView.findViewById(R.id.Eventname);
            coverImage = mView.findViewById(R.id.eventImage);
            eventDate = mView.findViewById(R.id.datetime);
        }
    }

    public List<EventData> getEventlist() {
        return eventlist;
    }

}
