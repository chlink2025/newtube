package com.newtube.mobile.ui.channel;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.liskovsoft.mediaserviceinterfaces.data.ChannelHeader;
import com.liskovsoft.smartyoutubetv2.tv.R;
import com.newtube.mobile.ui.common.Motion;

/**
 * NEWTUBE(channel-about): the channel page's header row - avatar, name, handle/subscribers, the
 * bio and the "...more" affordance that opens the full About sheet.
 *
 * <p>One full-span item concatenated in front of the channel grid. Holds ZERO items until a
 * header arrives, so a channel without About data renders exactly as before.</p>
 */
final class ChannelHeaderAdapter extends RecyclerView.Adapter<ChannelHeaderAdapter.HeaderHolder> {
    interface Listener {
        void onAboutClicked();
    }

    private final Listener mListener;
    private ChannelHeader mHeader;

    ChannelHeaderAdapter(Listener listener) {
        mListener = listener;
    }

    void setHeader(ChannelHeader header) {
        boolean had = mHeader != null;
        mHeader = header;

        if (had && header == null) {
            notifyItemRemoved(0);
        } else if (!had && header != null) {
            notifyItemInserted(0);
        } else if (header != null) {
            notifyItemChanged(0);
        }
    }

    boolean hasHeader() {
        return mHeader != null;
    }

    ChannelHeader getHeader() {
        return mHeader;
    }

    @Override
    public int getItemCount() {
        return mHeader != null ? 1 : 0;
    }

    @NonNull
    @Override
    public HeaderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mobile_channel_header, parent, false);

        return new HeaderHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderHolder holder, int position) {
        holder.bind(mHeader);
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatar;
        private final TextView mTitle;
        private final TextView mMeta;
        private final TextView mDescription;
        private final TextView mMore;

        HeaderHolder(@NonNull View itemView, Listener listener) {
            super(itemView);

            mAvatar = itemView.findViewById(R.id.mobile_channel_header_avatar);
            mTitle = itemView.findViewById(R.id.mobile_channel_header_title);
            mMeta = itemView.findViewById(R.id.mobile_channel_header_meta);
            mDescription = itemView.findViewById(R.id.mobile_channel_header_description);
            mMore = itemView.findViewById(R.id.mobile_channel_header_more);

            View.OnClickListener openAbout = v -> listener.onAboutClicked();
            mDescription.setOnClickListener(openAbout);
            mMore.setOnClickListener(openAbout);
        }

        void bind(ChannelHeader header) {
            if (header == null) {
                return;
            }

            mTitle.setText(header.getTitle());

            String meta = joinMeta(header.getHandle(), header.getSubscriberCount(),
                    header.getVideoCount());
            mMeta.setText(meta);
            mMeta.setVisibility(TextUtils.isEmpty(meta) ? View.GONE : View.VISIBLE);

            String description = header.getDescription();
            mDescription.setText(description);
            mDescription.setVisibility(TextUtils.isEmpty(description) ? View.GONE : View.VISIBLE);
            mMore.setVisibility(TextUtils.isEmpty(description) ? View.GONE : View.VISIBLE);

            if (TextUtils.isEmpty(header.getAvatarUrl())) {
                mAvatar.setImageResource(R.drawable.ic_watch_channel_placeholder);
            } else {
                Glide.with(itemView.getContext().getApplicationContext())
                        .load(header.getAvatarUrl())
                        .circleCrop()
                        .placeholder(R.drawable.ic_watch_channel_placeholder)
                        .error(R.drawable.ic_watch_channel_placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade((int) Motion.FADE_IN_MS))
                        .into(mAvatar);
            }
        }

        private static String joinMeta(String handle, String subscribers, String videos) {
            StringBuilder builder = new StringBuilder();

            append(builder, handle);
            append(builder, subscribers);
            append(builder, videos);

            return builder.toString();
        }

        private static void append(StringBuilder builder, String value) {
            if (TextUtils.isEmpty(value)) {
                return;
            }

            if (builder.length() > 0) {
                builder.append(" • ");
            }

            builder.append(value);
        }
    }
}
