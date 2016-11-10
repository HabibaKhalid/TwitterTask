package com.eventtus.task.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.eventtus.task.R;
import com.eventtus.task.helpers.Utilities;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.twitter.sdk.android.core.models.User;

import java.util.List;


public class RvUsersAdapter extends RecyclerView.Adapter<RvUsersAdapter.UsersViewHolder> {

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        CircularImageView imgItem;
        TextView username;
        TextView handle;
        TextView bio;


        UsersViewHolder(View itemView) {
            super(itemView);

            imgItem = (CircularImageView) itemView.findViewById(R.id.imgItem);
             username= (TextView) itemView.findViewById(R.id.tvUserName);
             handle= (TextView) itemView.findViewById(R.id.tvHandle);
             bio = (TextView) itemView.findViewById(R.id.tvBio);


        }

        public void bind(final User item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    List<User> usersList;
    Context context;
    private final OnItemClickListener listener;

    public RvUsersAdapter(List<User> _usersList, Context _context, OnItemClickListener listener) {
        this.usersList = _usersList;
        this.context = _context;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.usersadapteritem, viewGroup, false);
        UsersViewHolder view = new UsersViewHolder(v);
        return view;
    }

    @Override
    public void onBindViewHolder(final UsersViewHolder notificationViewHolder, int i) {
        User currentItem = usersList.get(i);
        if (currentItem != null) {
            notificationViewHolder.username.setText(currentItem.name);
            notificationViewHolder.handle.setText("@"+currentItem.screenName);

            notificationViewHolder.username.setText(currentItem.name);

            if (currentItem.description != null && !currentItem.description.equals("")  )

            {
                notificationViewHolder.bio.setText(currentItem.description);
            }

            Glide.with(context)
                    .load(currentItem.profileImageUrl)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (context != null) {
                                resource = Utilities.GetRoundedCornerBitmap(context, resource, 10, false, false, true, true);
                                notificationViewHolder.imgItem.setImageBitmap(resource);
                                resource = null;
                            }
                        }
                    });

            notificationViewHolder.bind(currentItem, listener);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnItemClickListener {
        void onItemClick(User item);
    }


}