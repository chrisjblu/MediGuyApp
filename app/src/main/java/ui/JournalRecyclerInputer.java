package ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediguyapp.JournalListActivity;
import com.example.mediguyapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Journal;

public class JournalRecyclerInputer extends RecyclerView.Adapter<JournalRecyclerInputer.ViewHoder> {
private Context context;
private List<Journal> journalList;

    public JournalRecyclerInputer(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public JournalRecyclerInputer.ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.journal_row, viewGroup, false);
        return new ViewHoder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerInputer.ViewHoder holder, int position) {

        Journal journal = journalList.get(position);
        String imageUrl;

        holder.title.setText(journal.getTitle());
        holder.entry.setText(journal.getJournalEntry());
        holder.name.setText(journal.getUserName());

        imageUrl = journal.getImageUrl();

        String ago = (String) DateUtils.getRelativeTimeSpanString(journal.getTimedAdded().getSeconds() * 1000);
        holder.date.setText(ago);



        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_one)
                .fit()
                .into(holder.image);








    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        public TextView title, entry, date,name;
        public ImageView image;
        public ImageButton shareBtn;
        String userId;
        String username;

        public ViewHoder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            title = itemView.findViewById(R.id.journal_title_list);
            entry = itemView.findViewById(R.id.journal_entry_list);
            date =  itemView.findViewById(R.id.journal_time);
            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_r_username);

            shareBtn = itemView.findViewById(R.id.journal_r_btn);
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //context.startActivity();
                }
            });

        }
    }
}
