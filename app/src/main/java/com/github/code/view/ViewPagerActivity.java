package com.github.code.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.github.code.R;
import com.github.code.viewpager.ScrollViewPager;
import com.github.code.viewpager.adapter.OrdinaryPagerAdapter;
import com.github.code.viewpager.hook.ClickHookView;
import java.util.Arrays;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

  private ScrollViewPager scrollViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.act_view_pager_layout);
    initView();
    init();
  }

  private void initView() {
    scrollViewPager = (ScrollViewPager) findViewById(R.id.view_pager);
  }

  private void init() {
    OrdinaryPagerAdapter adapter = new OrdinaryPagerAdapter();
    adapter.addHook(
        new ClickHookView<OrdinaryPagerAdapter.ViewHolderWrapper>(
            OrdinaryPagerAdapter.ViewHolderWrapper.class) {
          @Override
          public void onClick(View v, OrdinaryPagerAdapter.ViewHolderWrapper holder, int position,
              OrdinaryPagerAdapter.Model model) {
            if (model == null) {
              return;
            }
            if (v.getId() == R.id.img_cat) {
              Toast.makeText(ViewPagerActivity.this, "点击了猫", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.btn_test) {
              Toast.makeText(ViewPagerActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public List<? extends View> onHookArrays(
              OrdinaryPagerAdapter.ViewHolderWrapper viewHolder) {
            return Arrays.asList(viewHolder.findViewById(R.id.img_cat),
                viewHolder.findViewById(R.id.btn_test));
          }
        });
    adapter.addModel(new ImageModel());
    adapter.addModel(new TextModel());
    adapter.addModel(new ImageModel());
    adapter.addModel(new TextModel());
    scrollViewPager.setAdapter(adapter);
  }

  private final class ImageModel extends OrdinaryPagerAdapter.ModelWrapper {

    @Override
    protected int getLayoutRes() {
      return R.layout.model_image_layout;
    }

    @Override
    protected void bind(OrdinaryPagerAdapter.ViewHolderWrapper holder) {
      ImageView imgCat = holder.findViewById(R.id.img_cat);
      imgCat.setImageResource(R.drawable.icon_image_cat);
    }
  }

  private final class TextModel extends OrdinaryPagerAdapter.ModelWrapper {

    @Override
    protected int getLayoutRes() {
      return R.layout.model_text_layout;
    }

    @Override
    protected void bind(OrdinaryPagerAdapter.ViewHolderWrapper holder) {
      Button btnTest = holder.findViewById(R.id.btn_test);
      btnTest.setText("玩玩");
    }
  }
}
