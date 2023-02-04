package com.example.hazard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<News> newList;

    public NewsAdapter(Context mCtx, List<News> productList) {
        this.mCtx = mCtx;
        this.newList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.newlist, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        News news = newList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(news.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(news.getDescription());
        holder.textViewShortDesc.setText(news.getLocation());
        holder.textViewRating.setText(String.valueOf(news.getDate()));
        holder.textViewPrice.setText(String.valueOf(news.getTitle()));



    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}