package com.shizy.news.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.shizy.news.R;
import com.shizy.news.Shakespeare;
import com.shizy.news.adapter.MainPagerAdapter;
import com.shizy.news.view.PagerSlidingTabStrip;

import java.lang.reflect.Field;

public class MainActivity extends ActionBarActivity {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	
	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (view == mTabExpand) {
				expandTabs();
			} else if (view == mTabCollapse) {
				collapseTabs();
			}
		}
	};
	
	private DrawerLayout mDrawerLayout;
	private ListView mLeftDrawer;
	private View mRightDrawer;
	private PagerSlidingTabStrip mTabStrip;
	private ViewPager mViewPager;
	private MainPagerAdapter mPagerAdapter;
	
	private ImageView mTabExpand;
	private ImageView mTabCollapse;
	private View mExpandTop;
	private View mExpandContent;

	private ActionBar mActionBar;

	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.biz_main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item != null && item.getItemId() == android.R.id.home && mDrawerToggle.isDrawerIndicatorEnabled()) {
			if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
				mDrawerLayout.closeDrawer(GravityCompat.END);
				return true;
			}
		}
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.biz_main_menu_pc:
			menuPC();
			break;
		case R.id.biz_main_menu_more:
			menuMore();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void init() {
		initView();
		initDrawer();
		initActionBar();
		initDrawerLayout();
		initViewPager();
		initExpandView();
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftDrawer = (ListView) findViewById(R.id.left_drawer);
		mRightDrawer = findViewById(R.id.right_drawer);
		mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabStrip);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mTabExpand = (ImageView) findViewById(R.id.tab_expand);
		mTabCollapse = (ImageView) findViewById(R.id.tab_collapse);
		mExpandTop = findViewById(R.id.expand_top);
		mExpandContent = findViewById(R.id.expand_content);
	}

	private void initDrawer() {
		mLeftDrawer.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Shakespeare.TITLES));
		mLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}

	private void initActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
	}

	private void initDrawerLayout() {
		mDrawerLayout.setDrawerTitle(GravityCompat.START,
				getString(R.string.drawer_title));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				Log.d(LOG_TAG, "onDrawerOpened");
				getSupportActionBar().setTitle(getTitle());
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				Log.d(LOG_TAG, "onDrawerClosed");
				getSupportActionBar().setTitle(getTitle());
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void initViewPager() {
		mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		mViewPager.setPageMargin(pageMargin);
		mTabStrip.setViewPager(mViewPager);
	}
	
	private void initExpandView() {
		mTabExpand.setOnClickListener(mOnClickListener);
		mTabCollapse.setOnClickListener(mOnClickListener);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	private void menuPC() {
		if (mDrawerToggle.isDrawerIndicatorEnabled()) {
			if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
			}
			else if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
				mDrawerLayout.closeDrawer(GravityCompat.END);
			} else {
				mDrawerLayout.openDrawer(GravityCompat.END);
			}
		}
	}
	
	private void menuMore() {
		PopupMenu menu = new PopupMenu(this, findViewById(R.id.biz_main_menu_more), Gravity.TOP);
		menu.inflate(R.menu.biz_main_sub_menu);
		menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Log.e(LOG_TAG, "onMenuItemClick--->" + arg0.getItemId());
				return true;
			}
		});
		
		try {
			//反射调用，使icon显示
			Field mPopup = menu.getClass().getDeclaredField("mPopup");
			mPopup.setAccessible(true);
			MenuPopupHelper helper = (MenuPopupHelper) mPopup.get(menu);
			helper.setForceShowIcon(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		menu.show();
	}
	
	private void expandTabs() {
		final Animation animTop = AnimationUtils.loadAnimation(this, R.anim.biz_news_column_edit_fade_in);
		final Animation animContent = AnimationUtils.loadAnimation(this, R.anim.base_slide_up_in);
		AnimationListener listener = new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if (animation == animTop) {
					mExpandTop.setVisibility(View.VISIBLE);
				} else if (animation == animContent) {
					mExpandContent.setVisibility(View.VISIBLE);
				}
			}
		};
		animTop.setAnimationListener(listener);
		animContent.setAnimationListener(listener);
		mExpandTop.clearAnimation();
		mExpandTop.startAnimation(animTop);
		mExpandContent.clearAnimation();
		mExpandContent.startAnimation(animContent);
	}
	
	private void collapseTabs() {
		final Animation animTop = AnimationUtils.loadAnimation(this, R.anim.biz_news_column_edit_fade_out);
		final Animation animContent = AnimationUtils.loadAnimation(this, R.anim.base_slide_up_out);
		AnimationListener listener = new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if (animation == animTop) {
					mExpandTop.setVisibility(View.INVISIBLE);
				} else if (animation == animContent) {
					mExpandContent.setVisibility(View.INVISIBLE);
				}
			}
		};
		animTop.setAnimationListener(listener);
		animContent.setAnimationListener(listener);
		mExpandTop.clearAnimation();
		mExpandTop.startAnimation(animTop);
		mExpandContent.clearAnimation();
		mExpandContent.startAnimation(animContent);
	}

	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			setTitle(Shakespeare.TITLES[position]);
			mDrawerLayout.closeDrawer(mLeftDrawer);
            switch (position) {
                case 0:
                    startActivity(new Intent(MainActivity.this, TestActivity.class));
                    break;
            }
		}
	}

}
