package com.liskovsoft.smartyoutubetv2.common.app.views;

import com.liskovsoft.mediaserviceinterfaces.data.ChannelHeader;
import com.liskovsoft.smartyoutubetv2.common.app.models.data.VideoGroup;
import com.liskovsoft.smartyoutubetv2.common.utils.LoadFailure;

public interface ChannelView {
    void update(VideoGroup videoGroup);
    void setPosition(int index);
    void showProgressBar(boolean show);
    void clear();

    /**
     * NEWTUBE(channel-about): the author's About block that arrives with the channel page's first
     * batch, before its sections.
     */
    default void showChannelHeader(ChannelHeader header) {
    }

    /**
     * NEWTUBE(channel-about): the full About panel (description, stats, links, artist bio),
     * lazily loaded when the user opens it.
     */
    default void showChannelAbout(ChannelHeader header) {
    }

    /**
     * NEWTUBE(page-load-errors): the channel's first load put nothing on screen. {@code state} is a
     * {@link LoadFailure} constant; the retry is
     * {@link com.liskovsoft.smartyoutubetv2.common.app.presenters.ChannelPresenter#reload}. If the
     * view still holds rows (a pull-to-refresh that failed), it keeps them. Dismissed by
     * {@link #clear()} and by rows arriving.
     */
    default void showLoadFailure(int state) {
    }

    /**
     * NEWTUBE(page-load-errors): the NEXT page of a section failed. The rows on screen stay; the
     * retry is another {@code onScrollEnd} for the same section, which re-asks for the same page.
     */
    default void showLoadMoreFailure() {
    }
}
