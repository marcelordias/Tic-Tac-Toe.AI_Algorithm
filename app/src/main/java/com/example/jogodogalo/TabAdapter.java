package com.example.jogodogalo;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class TabAdapter extends BaseAdapter {
    // Declare context variable
    private final Context context;
    /*
     * Array with all possible Id's to give
     * This usage is to give an Id to the imageView (Code line-> 71)
     */
    public final int[] positions = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    // An list to save available positions
    public static ArrayList<Boolean> itemClickable = new ArrayList<Boolean>();

    //Constructor
    public TabAdapter(Context context) {
        this.context = context;
        // Set all board positions disabled
        this.setAllItemDisabled();

    }

    // Method to disable all positions
    public void setAllItemDisabled() {
        for (int j = 0; j < positions.length; j++) {
            itemClickable.set(j, false);
        }
    }

    // Method to enable/disable x position
    public void setItemClickable(int position, Boolean typeValue) {
        itemClickable.set(position, typeValue);
    }

    /*
     * Method to verify if is all disabled:
     * return false if is not
     * return true is it is
     */
    public Boolean isAllDisabled() {
        for (int i = 0; i < positions.length; i++) {
            if (itemClickable.get(i)) {
                return false;
            }
        }
        return true;
    }

    // Get all positions count
    @Override
    public int getCount() {
        return this.positions.length;
    }

    // Get x item
    @Override
    public Object getItem(int position) {
        return this.positions[position];
    }

    // This method will interpretate the x position to give the enable attribute
    @Override
    public boolean isEnabled(int position) {
        return itemClickable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Create an image view to generate the board
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setId(this.positions[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
        imageView.setBackgroundColor(Color.GRAY);
        imageView.setContentDescription("-1");
        return imageView;
    }
}
