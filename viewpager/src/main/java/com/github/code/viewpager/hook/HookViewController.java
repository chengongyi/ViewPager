package com.github.code.viewpager.hook;

import android.view.View;
import com.github.code.viewpager.adapter.OrdinaryPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author github_chen
 * @date 2019/3/25
 */
public class HookViewController<VH extends OrdinaryPagerAdapter.ViewHolder> {

  private boolean isAfterHook = false;
  private OrdinaryPagerAdapter adapter;
  private final List<HookView<VH>> hookViews = new ArrayList<>();

  public HookViewController(
      OrdinaryPagerAdapter adapter) {
    this.adapter = adapter;
  }

  public void addHook(HookView<VH> hookView) {
    if (isAfterHook) {
      throw new IllegalStateException("call this method in onHook before");
    }
    if (hookView != null) {
      hookViews.add(hookView);
    }
  }

  public void onHook(OrdinaryPagerAdapter.ViewHolder holder) {
    for (HookView<VH> hookView : hookViews) {
      if (!hookView.clz.isInstance(holder)) {
        continue;
      }
      final VH vh = hookView.clz.cast(holder);
      final View view = hookView.onHook(vh);
      if (view != null) {
        hook(hookView, vh, view);
      }
      List<? extends View> views = hookView.onHookArrays(vh);
      if (views != null && !views.isEmpty()) {
        for (View v : views) {
          hook(hookView, vh, v);
        }
      }
    }
  }

  void hook(HookView<VH> hookView, VH holder, View v) {
    if (v == null || hookView == null || holder == null) {
      return;
    }
    hookView.onHook(v, holder, adapter);
    isAfterHook = true;
  }
}
