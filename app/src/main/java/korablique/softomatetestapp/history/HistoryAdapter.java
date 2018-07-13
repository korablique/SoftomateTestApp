package korablique.softomatetestapp.history;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.R;
import korablique.softomatetestapp.database.HistoryEntity;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryItemViewHolder> {
    private List<HistoryEntity> historyEntityList = new ArrayList<>();

    @NonNull
    @Override
    public HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout item = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item_layout, parent, false);
        return new HistoryItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemViewHolder holder, int position) {
        ViewGroup item = holder.getItem();
        TextView textView = item.findViewById(R.id.text);
        textView.setText(historyEntityList.get(position).getText());
        TextView languageTextView = item.findViewById(R.id.language);
        languageTextView.setText(historyEntityList.get(position).getLanguage());
    }

    @Override
    public int getItemCount() {
        return historyEntityList.size();
    }

    public void addItems(List<HistoryEntity> items) {
        historyEntityList.addAll(items);
        notifyDataSetChanged();
    }
}
