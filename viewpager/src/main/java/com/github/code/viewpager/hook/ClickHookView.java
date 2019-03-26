package com.github.code.viewpager.hook;

import android.view.View;
import com.github.code.viewpager.adapter.OrdinaryPagerAdapter;

/**
 * @author github_chen
 * @date 2019/3/25
 */
public abstract class ClickHookView<VH extends OrdinaryPagerAdapter.ViewHolder>
    extends HookView<VH> {

  public ClickHookView(Class<VH> clz) {
    super(clz);
  }

  @Override
  protected void onHook(final View view, final VH viewHolder, final OrdinaryPagerAdapter adapter) {
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final int position = viewHolder.getAdapterPosition();
        final OrdinaryPagerAdapter.Model<?> model = adapter.getModel(position);
        if (position != -1 && model != null) {
          ClickHookView.this.onClick(view, viewHolder, position, model);
        }
      }
    });
  }

  public abstract void onClick(View v, VH holder, int position, OrdinaryPagerAdapter.Model model);
}
