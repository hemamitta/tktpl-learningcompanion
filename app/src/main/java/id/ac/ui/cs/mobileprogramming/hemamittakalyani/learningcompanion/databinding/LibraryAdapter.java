package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Library;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryHolder> {
    private List<Library> libraries = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public LibraryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_item, parent, false);
        return new LibraryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryHolder holder, int position) {
        Library currentLibrary = libraries.get(position);
        holder.textViewTitle.setText(currentLibrary.getLibraryName());
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
        notifyDataSetChanged();
    }

    class LibraryHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;

        public LibraryHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.titleTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(libraries.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Library library);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
