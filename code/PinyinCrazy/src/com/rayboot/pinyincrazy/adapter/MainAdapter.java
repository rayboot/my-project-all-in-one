package com.rayboot.pinyincrazy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.fourmob.tilteffect.TiltEffectAttacher;
import com.rayboot.pinyincrazy.R;
import com.rayboot.pinyincrazy.obj.MainDataObj;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MainAdapter<T> extends MyBaseAdapter<T>
{

    public MainAdapter(Context context, List<T> datas)
    {
        super(context, datas);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub ViewHolder holder;
        ViewHolder holder;
        if (convertView != null)
        {
            holder = (ViewHolder) convertView.getTag();
        }
        else
        {
            convertView =
                    this.mLayoutInflater.inflate(R.layout.item_main, parent,
                            false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        MainDataObj mdo = (MainDataObj) getItem(position);
        holder.mTvName.setText(mdo.name);
        Picasso.with(mContext).load(mdo.imageId).into(holder.mIvLogo);
        Picasso.with(mContext).load(mdo.miniImageId).into(holder.mIvIcon);
        holder.mTvPinyin.setText(mdo.pinyin);
        holder.mTvEnglish.setText(mdo.subName);
        holder.mRlMain.setBackgroundColor(
                mContext.getResources().getColor(mdo.mainBg));
        holder.mLlRight.setBackgroundColor(
                mContext.getResources().getColor(mdo.rightBg));
        holder.mTvName.setTextColor(
                mContext.getResources().getColor(mdo.textColor));
        holder.mTvPinyin.setTextColor(
                mContext.getResources().getColor(mdo.pinyinColor));
        holder.mTvEnglish.setTextColor(
                mContext.getResources().getColor(mdo.subNameColor));
        convertView.setTag(R.string.tag_1, mdo.key);
        return convertView;
    }

    static class ViewHolder
    {
        @InjectView(R.id.ivLogo) ImageView mIvLogo;
        @InjectView(R.id.tvEnglish) TextView mTvEnglish;
        @InjectView(R.id.llRight) LinearLayout mLlRight;
        @InjectView(R.id.ivIcon) ImageView mIvIcon;
        @InjectView(R.id.tvName) TextView mTvName;
        @InjectView(R.id.tvPinyin) TextView mTvPinyin;
        @InjectView(R.id.linearLayout2) LinearLayout mLinearLayout2;
        @InjectView(R.id.rlMain) RelativeLayout mRlMain;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
            TiltEffectAttacher.attach(mRlMain);
        }
    }
}
