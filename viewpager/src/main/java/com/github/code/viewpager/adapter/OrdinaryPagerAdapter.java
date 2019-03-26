package com.github.code.viewpager.adapter;

import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.code.viewpager.hook.HookView;
import com.github.code.viewpager.hook.HookViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author github_chen
 * @date 2019/3/25
 */
public class OrdinaryPagerAdapter extends PagerAdapter {
  private final ModelList models = new ModelList();
  private final HookViewController hookViewController = new HookViewController(this);

  @Override
  public int getCount() {
    return models.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    Model<?> model = models.get(position);
    ViewHolder viewHolder = model.getViewHolder();
    if (viewHolder != null) {
      viewHolder.unBind(model);
      container.removeView(viewHolder.itemView);
    }
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    Model<?> model = models.get(position);
    View child;
    if (model.getViewHolder() == null || model.getViewHolder().itemView == null) {
      child = LayoutInflater.from(container.getContext())
          .inflate(model.getLayoutRes(), null, false);

      ViewHolder viewHolder = model.getViewHolderCreator().create(child, position);
      viewHolder.setAdapterPosition(position);
      viewHolder.bind(model);
      hookViewController.onHook(model.holder);
    } else {
      child = model.getViewHolder().itemView;
    }
    container.addView(child);
    return child;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    Model<?> model = models.get(position);
    return model.getPageTitle();
  }

  public void addModel(Model<?> model) {
    if (model != null) {
      models.add(model);
      notifyDataSetChanged();
    }
  }

  public void addModels(Collection<? extends Model<?>> c) {
    if (c != null && !c.isEmpty()) {
      models.addAll(c);
      notifyDataSetChanged();
    }
  }

  public void addModel(int index, Model<?> model) {
    if (index >= 0 && index < models.size() && model != null) {
      models.add(index, model);
      notifyDataSetChanged();
    }
  }

  public int indexOf(Model<?> model) {
    if (model != null) {
      return models.indexOf(model);
    }
    return -1;
  }

  public void insertBeforeModel(Model<?> model, Model<?> target) {
    final int index = indexOf(target);
    if (index != -1) {
      addModel(index, model);
      notifyDataSetChanged();
    }
  }

  public void insertAfterModel(Model<?> model, Model<?> target) {
    final int index = indexOf(target);
    if (index != -1 && index < models.size()) {
      addModel(index, model);
      notifyDataSetChanged();
    }
  }

  public Model<?> remove(int index) {
    if (index >= 0 && index < models.size()) {
      Model<?> remove = models.remove(index);
      notifyDataSetChanged();
      return remove;
    }
    return null;
  }

  public boolean remove(Model<?> model) {
    if (model != null) {
      boolean remove = models.remove(model);
      notifyDataSetChanged();
      return remove;
    }
    return false;
  }

  public List<Model<?>> getModels() {
    return models;
  }

  public Model<?> getModel(int position) {
    if (position < 0 || position >= models.size()) {
      return null;
    }
    return models.get(position);
  }

  public <VH extends ViewHolder> void addHook(HookView<VH> hookView) {
    //noinspection unchecked
    hookViewController.addHook(hookView);
  }

  private static final class ModelList extends ArrayList<Model<?>> {
  }

  public static abstract class Model<VH extends ViewHolder> {

    private ViewHolder holder;

    protected abstract int getLayoutRes();

    protected abstract IViewHolderCreator getViewHolderCreator();

    protected void bind(VH holder) {

    }

    protected void unBind(VH holder) {
    }

    protected CharSequence getPageTitle() {
      return null;
    }

    public ViewHolder getViewHolder() {
      return holder;
    }

    void bindViewHolder(ViewHolder holder) {
      this.holder = holder;
    }
  }

  public static abstract class ModelWrapper extends Model<ViewHolderWrapper> {
    @Override
    protected IViewHolderCreator getViewHolderCreator() {
      return new IViewHolderCreator<ViewHolderWrapper>() {
        @Override
        public ViewHolderWrapper create(View view, int position) {
          return new ViewHolderWrapper(view);
        }
      };
    }
  }

  public static class ViewHolder {
    protected final View itemView;
    private int position = -1;

    ViewHolder(View itemView) {
      this.itemView = itemView;
    }

    public int getAdapterPosition() {
      return position;
    }

    void setAdapterPosition(int position) {
      this.position = position;
    }

    void bind(Model model) {
      if (model != null) {
        //noinspection unchecked
        model.bindViewHolder(this);
        //noinspection unchecked
        model.bind(this);
      }
    }

    void unBind(Model model) {
      if (model != null) {
        //noinspection unchecked
        model.unBind(this);
      }
    }
  }

  public static class ViewHolderWrapper extends ViewHolder {
    private final SparseArray<View> views = new SparseArray<>();

    public ViewHolderWrapper(View itemView) {
      super(itemView);
    }

    public <V extends View> V findViewById(@IdRes int id) {
      View view = views.get(id);
      if (view == null) {
        view = itemView.findViewById(id);
        if (view != null) {
          views.put(id, view);
        }
      }
      //noinspection unchecked
      return (V) view;
    }
  }

  public interface IViewHolderCreator<VH extends ViewHolder> {
    VH create(View view, int position);
  }
}
