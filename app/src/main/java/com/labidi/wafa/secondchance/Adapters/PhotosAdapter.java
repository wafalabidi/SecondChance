package com.labidi.wafa.secondchance.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.Constants;
import com.labidi.wafa.secondchance.Entities.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wafa on 05/12/2017.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> implements GreedoLayoutSizeCalculator.SizeCalculatorDelegate {
    private static final int IMAGE_COUNT = 500; // number of images adapter will show
    private final int[] mImageResIds = Constants.IMAGES;
    private final double[] mImageAspectRatios = new double[Constants.IMAGES.length];

    List<Post>  posts ;
    private Context mContext;

    @Override
    public double aspectRatioForIndex(int index) {
        // Precaution, have better handling for this in greedo-layout
        if (index >= getItemCount()) return 1.0;
        return mImageAspectRatios[getLoopedIndex(index)];
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        public PhotoViewHolder(ImageView imageView) {
            super(imageView);
            mImageView = imageView;
        }
    }

    public PhotosAdapter(Context context,List<Post> items) {
        mContext = context;
        this.posts = items;
        calculateImageAspectRatios();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return new PhotoViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {


        Picasso.with(mContext)
                .load(posts.get(position).getImage())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private void calculateImageAspectRatios() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        for (int i = 0; i < mImageResIds.length; i++) {
                        BitmapFactory.decodeResource(mContext.getResources(), mImageResIds[i], options);
                        mImageAspectRatios[i] = options.outWidth / (double) options.outHeight;
                    }

    }

    // Index gets wrapped around <code>Constants.IMAGES.length</code> so we can loop content.
    private int getLoopedIndex(int index) {
        return index % Constants.IMAGES.length;
    }
}

