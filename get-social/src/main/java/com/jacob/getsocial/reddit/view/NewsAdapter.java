package com.jacob.getsocial.reddit.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacob.getsocial.R;
import com.jacob.getsocial.common.NewsStorage;
import com.jacob.getsocial.common.UiUtils;
import com.jacob.getsocial.reddit.data.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.BaseViewHolder> {
    private final NewsStorage mData = new NewsStorage();
    private final OnItemClickListener mListener;

    public NewsAdapter(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        News news = mData.get(position);
        holder.authorTextView.setText(news.getAuthor());
        holder.likesCounter.setText(String.valueOf(news.getLikes()));
        holder.messageCounter.setText(String.valueOf(news.getComments()));
        holder.titleTextView.setText(news.getTitle());

        if (news.isImage()) {
            Picasso.get().load(news.getThumbnail()).into(holder.imageView);
        } else {
            Picasso.get().load(UiUtils.getIconRes()).into(holder.imageView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addNewsToHead(@NonNull List<News> news) {
        mData.addAll(0, news);
        notifyItemRangeChanged(0, news.size());
    }

    public void addNewsToTail(@NonNull List<News> news) {
        int start = mData.size() - 1;
        mData.addAll(news);
        int count = news.size();
        notifyItemRangeChanged(start, count);
    }

    protected class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView likesCounter;
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView messageCounter;
        private ImageView imageView;

        @SuppressLint("SetJavaScriptEnabled")
        private BaseViewHolder(View itemView) {
            super(itemView);
            likesCounter = itemView.findViewById(R.id.news_item_likes_count);
            titleTextView = itemView.findViewById(R.id.news_item_title);
            authorTextView = itemView.findViewById(R.id.news_item_author);
            messageCounter = itemView.findViewById(R.id.news_item_messages_count);
            imageView = itemView.findViewById(R.id.news_item_main_image_view);
            imageView.setOnClickListener(this);
            itemView.findViewById(R.id.news_item_share_image_view).setOnClickListener(this);
            itemView.findViewById(R.id.news_item_messages).setOnClickListener(this);
            itemView.findViewById(R.id.news_item_likes_image).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.news_item_main_image_view) {
                onItemClicked(mData.get(getAdapterPosition()));
            }
        }
    }

    private void onItemClicked(News news) {
        if (mListener != null) {
            mListener.onItemClicked(news);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(News news);
    }
}
