package example.com.eventsapp.AppServices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.eventsapp.DataClasses.speakerDetails;
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
    }

    @Override
    public int getItemCount() {
        return speakerlist.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView speakerName;
        TextView speakerBio;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            speakerName = mView.findViewById(R.id.eventSpeakerFrag);
            speakerBio = mView.findViewById(R.id.EventSpeakInfoFrag);
        }
    }
}
