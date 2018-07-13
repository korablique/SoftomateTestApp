package korablique.softomatetestapp.history;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class HistoryItemViewHolder extends RecyclerView.ViewHolder {
    private ViewGroup item;

    public HistoryItemViewHolder(ViewGroup itemView) {
        super(itemView);

        item = itemView;
    }

    public ViewGroup getItem() {
        return item;
    }
}
