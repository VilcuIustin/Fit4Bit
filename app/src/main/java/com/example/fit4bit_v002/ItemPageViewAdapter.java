package com.example.fit4bit_v002;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemPageViewAdapter extends PagerAdapter {
    private ArrayList<ItemPageView> itemPageViewList;
    private Context context;

    public ItemPageViewAdapter(ArrayList<ItemPageView> itemPageViewList, Context context) {
        this.itemPageViewList = itemPageViewList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemPageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.profile_pager_view_item, container, false);

        ImageView image2 = view.findViewById(R.id.image2);
        TextView titluCardProfilePagerView = view.findViewById(R.id.titluCardProfilePagerView);
        TextView descriptionCardProfilePagerView = view.findViewById(R.id.descriptionCardProfilePagerView);

        ItemPageView itemPageView = itemPageViewList.get(position);
        String title = itemPageView.getTitle();
        String description = itemPageView.getDescription();
        int image = itemPageView.getImage();

        titluCardProfilePagerView.setText(title);
        descriptionCardProfilePagerView.setText(description);
        image2.setImageResource(image);

        view.setOnClickListener(v -> Toast.makeText(context, "wow", Toast.LENGTH_SHORT));

        container.addView(view, position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
