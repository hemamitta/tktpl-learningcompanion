package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private List<Book> books;
    private OnItemClickListener listener;

    public BookAdapter(List<Book> books, OnItemClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { ;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.book_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book currentBook = books.get(position);
        holder.textViewTitle.setText(currentBook.getTitle());
        holder.bind(books.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.book_item_title);
        }

        public void bind(final Book book, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
