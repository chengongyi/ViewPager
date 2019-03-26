package com.github.code.viewpager.hook;

import android.view.View;
import com.github.code.viewpager.adapter.OrdinaryPagerAdapter;
import java.util.List;

/**
 * @author github_chen
 * @date 2019/3/25
 */
public abstract class HookView<VH extends OrdinaryPagerAdapter.ViewHolder> {
  final Class<VH> clz;

  public HookView(Class<VH> clz) {
    this.clz = clz;
  }

  protected abstract void onHook(View view, VH viewHolder, OrdinaryPagerAdapter adapter);

  public View onHook(VH viewHolder) {
    return null;
  }

  public List<? extends View> onHookArrays(VH viewHolder) {
    return null;
  }
}
