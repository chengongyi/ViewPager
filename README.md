![](./images/ViewPager.gif)

## 简介
ViewPager的新式用法或者说高级用法，摒弃了传统的将业务逻辑揉在PagerAdapter中的方式，OrdinaryPagerAdapter作为一个唯一的Adapter，只接受Model的输入，所以的使用者的关注点也从传统的PagerAdapter转移到了Model中，而Model中将最小的变化量暴露给使用者，使用者只需重写getLayoutRes()去加载布局，bind()去绑定数据。关注点直接又清晰。


由于ViewPager展示的内容只来源于Model中的getLayoutRestore（），所以随着不同Model中getLayoutRes()的多样性，使得ViewPager在不讲业务逻辑侵入到OrdinaryPagerAdapter的前提下支持数据类型的多样性，因为布局和数据都是由Model具体决定的。

讲变化的点集中在最小的颗粒度 getLayoutRes() 和bind（）上，使得扩展和可维护性等都得到显著提升。方便使用者的使用


# 使用示例

图片类型的Model

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

文本类型的Model

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
  
  
#   设置给Adapter以及添加点击或则长按事件
  
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
    
