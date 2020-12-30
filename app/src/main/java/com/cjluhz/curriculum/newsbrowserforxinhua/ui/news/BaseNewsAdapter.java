package com.cjluhz.curriculum.newsbrowserforxinhua.ui.news;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cjluhz.curriculum.newsbrowserforxinhua.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BaseNewsAdapter extends BaseMultiItemQuickAdapter<XINHUAPoliticsItem, BaseViewHolder> {

    public BaseNewsAdapter(List<XINHUAPoliticsItem> data) {
        super(data);
        // 绑定 layout 对应的 type
        addItemType(XINHUAPoliticsItem.IMG, R.layout.xinhuaitem);
        addItemType(XINHUAPoliticsItem.NOIMG, R.layout.xinhuaitemnoimg);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, XINHUAPoliticsItem item) {
        switch (baseViewHolder.getItemViewType()) {
            case XINHUAPoliticsItem.IMG:
                baseViewHolder.setText(R.id.NewsTitle, item.getTitle());
                baseViewHolder.setText(R.id.NewsDate, item.getDate());
                ImageView iv = baseViewHolder.getView(R.id.NewsImg);
                Glide.with(getContext()).load(item.getImgsrc()).into(iv);
                break;
            case XINHUAPoliticsItem.NOIMG:
                baseViewHolder.setText(R.id.NewsTitle, item.getTitle());
                baseViewHolder.setText(R.id.NewsDate, item.getDate());
                break;
            default:
                break;
        }
    }
}
