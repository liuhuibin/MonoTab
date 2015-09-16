package com.huibin.monotab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 评论画面
 * Created by huibin on 15/9/16.
 */
public class CommentFragment extends Fragment {

    private TextView mDesc ;
    private String mContent;

    public CommentFragment() {
    }

    public static CommentFragment newInstance(String desc) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("desc", desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = getArguments().getString("desc");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comment,container,false) ;
        mDesc = (TextView) root.findViewById(R.id.tv_desc);
        mDesc.setText(mContent);
        return root;
    }
}
