package com.jerey.keepgank.douban.itembinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jerey.keepgank.R;
import com.jerey.keepgank.douban.bean.SubjectsBean;
import com.jerey.keepgank.douban.bean.TypeInfoBean;
import com.jerey.keepgank.multitype.ItemViewBinder;
import com.jerey.loglib.LogTools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xiamin
 * @date 8/17/17.
 */
public class TypeInfoBeanBinder extends ItemViewBinder<TypeInfoBean, TypeInfoBeanBinder
        .ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup
            parent) {
        View root = inflater.inflate(R.layout.item_douban_typeinfo, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TypeInfoBean item) {
        if (item == null) return;

        LogTools.i("Title：" + item.getTitle());
        holder.mTextView.setText(item.getTitle());
        holder.mRecyclerView.setLayoutManager(
                new LinearLayoutManager(holder.mRecyclerView.getContext(), LinearLayoutManager
                        .HORIZONTAL, false));
        holder.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        TypeItemAdapter adapter = new TypeItemAdapter(item);
        holder.mRecyclerView.setAdapter(adapter);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        RecyclerView mRecyclerView;
        ImageView mImageViewMore;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_title);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_recyclerView);
            mImageViewMore = (ImageView) itemView.findViewById(R.id.iv_more);
        }
    }

    class TypeItemAdapter extends RecyclerView.Adapter<TypeItemAdapter.ItemViewHolder> {

        TypeInfoBean mTypeInfoBean;

        public TypeItemAdapter(TypeInfoBean typeInfoBean) {
            mTypeInfoBean = typeInfoBean;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_douban_type_item, parent, false);
            return new ItemViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            SubjectsBean subject = mTypeInfoBean.getSubjects().get(position);
            LogTools.d("subject:" + subject.toString());
            LogTools.d("subject:" + subject.getTitle() + subject.getRating());
            if (subject.getTitle() != null) {
                holder.mItemName.setText(subject.getTitle().toString());
            }
            if (subject.getRating() != null) {
                holder.mItemRating.setText("" + subject.getRating().getAverage());
            }
            if (subject.getImages() != null) {
                String url = null;
                if (subject.getImages().getMedium() != null) {
                    url = subject.getImages().getMedium();
                } else if (subject.getImages().getLarge() != null) {
                    url = subject.getImages().getLarge();
                }
                Glide.with(holder.mItemImageView.getContext())
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.bg_grey)
                        .into(holder.mItemImageView);
            }
        }

        @Override
        public int getItemCount() {
            if (mTypeInfoBean != null && mTypeInfoBean.getSubjects() != null) {
                return mTypeInfoBean.getSubjects().size();
            }
            return 0;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.item_imageView)
            ImageView mItemImageView;
            @Bind(R.id.item_name)
            TextView mItemName;
            @Bind(R.id.item_rating)
            TextView mItemRating;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
