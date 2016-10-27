SearchViewDemo
================

  Android toolbar上添加searchView进行搜索RecyclerView中的数据.<br>
  仅在一个Activity中实现，通过FrameLayout包裹RecyclerView，每当点击收缩图标时，<br>
  即显示此FramworkLayout即搜索查询结果上下文。<br>
  用道了circleimageview来显示图片。<br>
  非常简单的一个DEMO。
  
Demo
================
![demo](https://raw.githubusercontent.com/hongguangKim/SearchViewDemo/master/DEMO/1.PNG)]![demo](https://raw.githubusercontent.com/hongguangKim/SearchViewDemo/master/DEMO/2.PNG)]

Gradle
----
build.gradle相关请参考 as below:
```
dependencies {
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'de.hdodenhof:circleimageview:2.1.0'
}
```
有关'de.hdodenhof:circleimageview'可以在github中查看[circleimageview](https://github.com/hdodenhof/CircleImageView)

还有一点需要注意的是当searchView展开以后Close时OnCloseListener不起作用。可以用一下方法：
```java
public class ScrollingActivity extends MenuItemCompat.OnActionExpandListener 
```
先让Activity去实现 MenuItemCompat.OnActionExpandListener 
然后Create SearchView如下：
```java
 private void createSearchView(Menu menu) {
        mSearchView = (SearchView) menu.findItem(R.id.search_menu).getActionView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search_menu), this);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("输入您感兴趣的...");
    }
```
监听相关你可以打印log查看：
```java
 @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Log.i(TAG, "onMenuItemActionExpand");
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Log.i(TAG, "onMenuItemActionCollapse");
        return false;
    }
```
