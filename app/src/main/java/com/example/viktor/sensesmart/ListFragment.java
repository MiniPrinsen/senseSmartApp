package com.example.viktor.sensesmart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is the one that shows the different touchpoints as a grid.
 * The class uses its children GridListAdapter to fill the grid with different items.
 */
public class ListFragment extends Fragment  {
    private static final int DEFAULT_SPAN_COUNT = 2;
    private RecyclerView mRecyclerView;
    private GridListAdapter mAdapter;
    private List<Item> mItemList = new ArrayList<>();
    private int mHeaderCounter = 0;
    private int mGridCounter;
    View rootView;

    public ListFragment() {
        // Required empty public constructor
    }


    /**
     * onCreateView is the method that creates the view before it is accessed.
     * @param inflater A parameter for which layout to inflate.
     * @param container Used to inflate children views, in this case the list items.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_list, container, false);

        final int VIEWPAGER_HEIGHT;

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewList);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);

        /*Fills the gridlistadapter with the touchpoint objects*/
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new GridListAdapter(mItemList, gridLayoutManager, DEFAULT_SPAN_COUNT, getContext());
        mRecyclerView.setAdapter(mAdapter);

        VIEWPAGER_HEIGHT = getActivity().findViewById(R.id.viewPagerPage).getHeight();

        final ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.viewPagerPage);
        int layoutWidth = viewPager.getWidth();

        final int[] HEIGHT_OF_VIEWPAGER = new int[1];
        final int[] WIDTH_OF_VIEWPAGER = new int[1];
        final int a;


        viewPager.post(new Runnable() {
            @Override
            public void run() {
                HEIGHT_OF_VIEWPAGER[0] = viewPager.getMeasuredHeight();
                WIDTH_OF_VIEWPAGER[0] = viewPager.getMeasuredWidth();
                Log.d("Check my", "width " + viewPager.getHeight());

            }
        });
        /*Log.v("WEIGHT_OF_VIEWPAGER", String.valueOf(HEIGHT_OF_VIEWPAGER[0]));
        Log.v("HEIGHT_OF_VIEWPAGER", String.valueOf(HEIGHT_OF_VIEWPAGER[0]));
        Log.v("SIZE_OF_VIEWPAGER", String.valueOf(HEIGHT_OF_VIEWPAGER.length));
        Log.v("SIZE_OF_VIEWPAGER", String.valueOf(HEIGHT_OF_VIEWPAGER.length));

        Log.v("LASTEST HEIGHT", String.valueOf(viewPager.getMeasuredHeight()));
        Log.v("LASTEST WIDTH", String.valueOf(viewPager.getMeasuredWidth()));*/

        int height = viewPager.getMeasuredHeight();

        /*Gets height and width of screen, for the image grid*/
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        Log.v("BREDD", String.valueOf(width));

        /*Adds the grid to the list layout, 1 by 1.*/
        mAdapter.addItem(new HeaderItem("Large " + getHeaderCounter(), R.drawable.bonnstan_square600, height, width));
        mAdapter.addItem(new GridItem("Small", getGridCounter(), R.drawable.johannaiparken_square600, width/2, width/2));
        mAdapter.addItem(new GridItem("Small", getGridCounter(), R.drawable.lejonstromsbron_square600, width/2, width/2));

        return rootView;
    }

    public int getHeaderCounter() {
        mGridCounter = 0;
        return ++mHeaderCounter;
    }

    public int getGridCounter() {
        return ++mGridCounter;
    }
}
