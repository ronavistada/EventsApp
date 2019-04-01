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

import java.util.List;

import example.com.eventsapp.DataClasses.speakerDetails;
import example.com.eventsapp.Events;
import example.com.eventsapp.R;

public class SpeakerListAdapter extends RecyclerView.Adapter<SpeakerListAdapter.CustomViewHolder> {

    private List<speakerDetails> speakerlist;
    private Context context;

    public SpeakerListAdapter(Context context, List<speakerDetails> speakers){
        this.context = context;
        this.speakerlist = speakers;
    }

    @NonNull
    @Override
    public SpeakerListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.speaker_row_layout,viewGroup,false);
        return new SpeakerListAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakerListAdapter.CustomViewHolder customViewHolder, int i) {
        customViewHolder.speakerName.setText(speakerlist.get(i).getSpeakerfirstname()+" "+speakerlist.get(i).getSpeakerlastname());
        customViewHolder.speakerBio.setText(speakerlist.get(i).getSpeakerbio());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(speakerlist.get(i).getSpeakerimageurl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(customViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return speakerlist.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView speakerName;
        TextView speakerBio;
        ImageView imageView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            speakerName = mView.findViewById(R.id.eventSpeakerFrag);
            speakerBio = mView.findViewById(R.id.EventSpeakInfoFrag);
            imageView = mView.findViewById(R.id.speakerImageFrag);
        }
    }

    public List<speakerDetails> getSpeakerlist() {
        return speakerlist;
    }

    public void setSpeakerlist(List<speakerDetails> speakerlist) {
        this.speakerlist = speakerlist;
    }
}
