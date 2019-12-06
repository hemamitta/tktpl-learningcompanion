package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Quote;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> {
    private Context context;
    private Quote quote;

    public QuoteAdapter(Context context, Quote quote) {
        this.context = context;
        this.quote = quote;
    }

    @NonNull
    @Override
    public QuoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.ViewHolder viewHolder, int i) {
        viewHolder.quoteContent.setText(quote.getQuote());
        viewHolder.quoteAuthor.setText(quote.getAuthor());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quoteContent, quoteAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteContent = itemView.findViewById(R.id.quote_item_quote);
            quoteAuthor = itemView.findViewById(R.id.quote_item_author);
        }
    }
}
