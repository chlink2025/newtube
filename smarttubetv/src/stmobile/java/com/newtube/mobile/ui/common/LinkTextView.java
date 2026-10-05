package com.newtube.mobile.ui.common;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;

/**
 * NEWTUBE(link-text): a read-only description whose URLs are blue and tappable but which is NOT
 * selectable. Two traps made the old autoLink + textIsSelectable TextView misbehave:
 * <ul>
 *   <li>textIsSelectable focuses the view in touch mode, and the parent scroll's focus-follow
 *       scrolls the whole description into view on the long press that starts a selection -
 *       the page jumps down by a chunk;</li>
 *   <li>autoLink installs LinkMovementMethod, which races the selection Editor for the tap and
 *       sometimes opens the link the person meant to long-press.</li>
 * </ul>
 * Links are rendered as {@link URLSpan}s at {@link #setText} time (no movement method) and taps
 * are routed here, the {@code CommentTextView} pattern; a long press is left to the view's
 * OnLongClickListener (copy).
 */
public class LinkTextView extends AppCompatTextView {
    public interface Listener {
        void onLinkClicked(String url);
    }

    private final GestureDetector mGestures;
    @Nullable
    private Listener mListener;

    public LinkTextView(@NonNull Context context) {
        this(context, null);
    }

    public LinkTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public LinkTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestures = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                URLSpan span = spanAt(e);
                if (span != null && mListener != null) {
                    mListener.onLinkClicked(span.getURL());
                    return true;
                }
                return false;
            }
        });
        // Links are handled here, not by a MovementMethod: autoLink's would also make the text
        // selectable-looking and swallow the long press.
        setLinksClickable(false);
        ViewCompat.enableAccessibleClickableSpanSupport(this);
        setOnTouchListener((v, event) -> {
            mGestures.onTouchEvent(event);
            return false; // never consume: scrolling and the long press keep working
        });
    }

    /** Where a tapped link goes; the owner opens it the app's way (Utils.openLinkExt). */
    public void setListener(@Nullable Listener listener) {
        mListener = listener;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text) || type == BufferType.EDITABLE) {
            super.setText(text, type);
            return;
        }
        SpannableString spannable = new SpannableString(text);
        // Spannable overload: adds the URLSpans without installing a MovementMethod.
        Linkify.addLinks(spannable, Linkify.WEB_URLS);
        super.setText(spannable, BufferType.SPANNABLE);
        setMovementMethod(null);
        setLinksClickable(false);
    }

    @Nullable
    private URLSpan spanAt(MotionEvent e) {
        CharSequence text = getText();
        Layout layout = getLayout();
        if (!(text instanceof Spanned) || layout == null) {
            return null;
        }
        int x = (int) e.getX() - getTotalPaddingLeft() + getScrollX();
        int y = (int) e.getY() - getTotalPaddingTop() + getScrollY();
        if (y < 0 || y > layout.getHeight() || y > getHeight()) {
            return null;
        }
        int line = layout.getLineForVertical(y);
        if (x < layout.getLineLeft(line) || x > layout.getLineRight(line)) {
            return null;
        }
        int offset = layout.getOffsetForHorizontal(line, x);
        URLSpan[] spans = ((Spanned) text).getSpans(offset, offset, URLSpan.class);
        return spans.length > 0 ? spans[0] : null;
    }
}
