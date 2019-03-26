package com.github.code.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author github_chen
 * @date 2019/3/25
 */
public class ScrollViewPager extends ViewPager {

  private boolean enableScroll;

  public ScrollViewPager(Context context) {
    this(context, null);
  }

  public ScrollViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    setEnableScroll(true);
  }

  @Override
  public boolean canScrollVertically(int direction) {
    return isEnableScroll() && super.canScrollVertically(direction);
  }

  public boolean isEnableScroll() {
    return enableScroll;
  }

  public void setEnableScroll(boolean enableScroll) {
    this.enableScroll = enableScroll;
  }
}
