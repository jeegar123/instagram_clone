package com.app.instagramclone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class InstagramFragment extends Fragment {

    int layoutId;

    public InstagramFragment(int layoutId) {
        this.layoutId = layoutId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId, container, false);
    }
}
