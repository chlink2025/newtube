package com.newtube.mobile.ui.channel;

import android.app.Activity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.liskovsoft.mediaserviceinterfaces.data.ChannelHeader;
import com.liskovsoft.smartyoutubetv2.tv.R;
import com.newtube.mobile.ui.common.MobileSheets;

import java.util.List;

/**
 * NEWTUBE(channel-about): the channel author's About panel - description, artist biography,
 * country/joined/subscribers/videos/views and external links. Mirrors the official
 * channel_about_metadata_item, opened from the channel header's "...more".
 *
 * <p>The channel page's first /browse already carries the bio and stats; the full panel (links,
 * artist bio) is lazily fetched when this sheet opens and handed back through
 * {@link #update(ChannelHeader, boolean)}.</p>
 */
public final class ChannelAboutSheet {
    public interface Listener {
        void onLinkClicked(String url);
    }

    private static final float HEIGHT_FRACTION = 0.75f;

    private final BottomSheetDialog mDialog;
    private final Listener mListener;
    private final TextView mTitle;
    private final TextView mDescriptionLabel;
    private final TextView mDescription;
    private final TextView mArtistLabel;
    private final TextView mArtist;
    private final TextView mLinksLabel;
    private final LinearLayout mInfoRows;
    private final LinearLayout mLinks;
    private final View mProgress;
    private ChannelHeader mHeader;

    private ChannelAboutSheet(Activity activity, ChannelHeader header, Listener listener, boolean loading) {
        mListener = listener;
        mHeader = header;

        mDialog = new BottomSheetDialog(activity);
        View content = LayoutInflater.from(activity).inflate(R.layout.sheet_mobile_channel_about, null);
        mDialog.setContentView(content);

        mTitle = content.findViewById(R.id.mobile_channel_about_title);
        mDescriptionLabel = content.findViewById(R.id.mobile_channel_about_description_label);
        mDescription = content.findViewById(R.id.mobile_channel_about_description);
        mArtistLabel = content.findViewById(R.id.mobile_channel_about_artist_label);
        mArtist = content.findViewById(R.id.mobile_channel_about_artist);
        mLinksLabel = content.findViewById(R.id.mobile_channel_about_links_label);
        mInfoRows = content.findViewById(R.id.mobile_channel_about_info_rows);
        mLinks = content.findViewById(R.id.mobile_channel_about_links);
        mProgress = content.findViewById(R.id.mobile_channel_about_progress);

        bind(header, loading);
    }

    public static ChannelAboutSheet show(Activity activity, ChannelHeader header, Listener listener, boolean loading) {
        ChannelAboutSheet sheet = new ChannelAboutSheet(activity, header, listener, loading);
        sheet.mDialog.show();
        MobileSheets.expandTo(sheet.mDialog, HEIGHT_FRACTION);

        return sheet;
    }

    public void update(ChannelHeader header, boolean loading) {
        bind(header, loading);
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    private void bind(ChannelHeader header, boolean loading) {
        ChannelHeader previous = mHeader;
        mHeader = header != null ? header : previous;

        String title = firstNonNull(mHeader.getTitle(), previous != null ? previous.getTitle() : null);
        mTitle.setText(title);
        mTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);

        String description = firstNonNull(mHeader.getDescription(), previous != null ? previous.getDescription() : null);
        mDescription.setText(description);
        mDescription.setVisibility(TextUtils.isEmpty(description) ? View.GONE : View.VISIBLE);
        mDescriptionLabel.setVisibility(TextUtils.isEmpty(description) ? View.GONE : View.VISIBLE);

        String artistBio = firstNonNull(mHeader.getArtistBio(), previous != null ? previous.getArtistBio() : null);
        mArtist.setText(artistBio);
        boolean hasArtistBio = !TextUtils.isEmpty(artistBio);
        mArtistLabel.setVisibility(hasArtistBio ? View.VISIBLE : View.GONE);
        mArtist.setVisibility(hasArtistBio ? View.VISIBLE : View.GONE);

        List<ChannelHeader.InfoRow> infoRows = firstNonEmpty(mHeader.getInfoRows(),
                previous != null ? previous.getInfoRows() : null);
        bindInfoRows(infoRows);

        List<ChannelHeader.Link> links = firstNonEmpty(mHeader.getLinks(),
                previous != null ? previous.getLinks() : null);
        bindLinks(links);

        mProgress.setVisibility(loading && links == null ? View.VISIBLE : View.GONE);
    }

    private void bindInfoRows(List<ChannelHeader.InfoRow> rows) {
        mInfoRows.removeAllViews();

        if (rows == null || rows.isEmpty()) {
            mInfoRows.setVisibility(View.GONE);
            return;
        }

        mInfoRows.setVisibility(View.VISIBLE);

        for (ChannelHeader.InfoRow row : rows) {
            if (row == null || TextUtils.isEmpty(row.getLabel())) {
                continue;
            }

            TextView view = new TextView(mInfoRows.getContext());
            view.setText(row.getLabel());
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            view.setTextColor(ContextCompat.getColor(mInfoRows.getContext(), R.color.mobile_color_on_surface_secondary));
            int pad = (int) (4 * mInfoRows.getResources().getDisplayMetrics().density);
            view.setPadding(0, pad, 0, pad);
            mInfoRows.addView(view);
        }
    }

    private void bindLinks(List<ChannelHeader.Link> links) {
        mLinks.removeAllViews();
        mLinksLabel.setVisibility(links != null && !links.isEmpty() ? View.VISIBLE : View.GONE);

        if (links == null) {
            return;
        }

        for (ChannelHeader.Link link : links) {
            if (link == null || TextUtils.isEmpty(link.getUrl())) {
                continue;
            }

            View row = LayoutInflater.from(mLinks.getContext())
                    .inflate(R.layout.item_mobile_channel_about_link, mLinks, false);
            ImageView icon = row.findViewById(R.id.mobile_channel_about_link_icon);
            TextView title = row.findViewById(R.id.mobile_channel_about_link_title);

            title.setText(firstNonNull(link.getTitle(), link.getUrl()));
            row.setOnClickListener(v -> mListener.onLinkClicked(link.getUrl()));

            if (TextUtils.isEmpty(link.getFaviconUrl())) {
                icon.setVisibility(View.GONE);
            } else {
                icon.setVisibility(View.VISIBLE);
                com.bumptech.glide.Glide.with(mLinks.getContext().getApplicationContext())
                        .load(link.getFaviconUrl())
                        .into(icon);
            }

            mLinks.addView(row);
        }
    }

    private static String firstNonNull(String first, String second) {
        return !TextUtils.isEmpty(first) ? first : second;
    }

    private static <T> List<T> firstNonEmpty(List<T> first, List<T> second) {
        return first != null && !first.isEmpty() ? first : second;
    }
}
