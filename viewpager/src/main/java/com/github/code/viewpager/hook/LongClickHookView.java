package com.github.code.viewpager.hook;

import android.view.View;
import com.github.code.viewpager.adapter.OrdinaryPagerAdapter;

/**
 * @author github_chen
 * @date 2019/3/25
 */
public abstract class LongClickHookView<VH extends OrdinaryPagerAdapter.ViewHolder>
    extends ClickHookView<VH> {

  public LongClickHookView(Class<VH> clz) {
    super(clz);
  }

  @Override
  protected void onHook(final View view, final VH viewHolder, final OrdinaryPagerAdapter adapter) {
    super.onHook(view, viewHolder, adapter);
    view.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        final int position = viewHolder.getAdapterPosition();
        final OrdinaryPagerAdapter.Model<?> model = adapter.getModel(position);
        return position != -1 && model != null && LongClickHookView.this.onLongClick(view,
            viewHolder, position, model);
      }
    });
  }

  public abstract boolean onLongClick(View v, VH holder, int position,
      OrdinaryPagerAdapter.Model model);
}
