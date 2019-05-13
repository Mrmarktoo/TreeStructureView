# TreeStructureView

根gradle中 dependency 加入：
```groovy
maven { url "https://dl.bintray.com/mrmarktoo/maven" }
```groovy

项目中引入依赖：
```groovy
implementation "com.marktoo.widget:cachedweb:1.0.0.0"
```groovy

控件的属性：
```groovy
<declare-styleable name="DashTreeView">
        <!--虚线还是实线-->
        <attr name="isDash" format="boolean" />
        <!--虚线每段长度-->
        <attr name="dashW" format="dimension" />
        <!--虚线间距长度-->
        <attr name="dashH" format="dimension" />
        <!--虚线宽度-->
        <attr name="lineW" format="dimension" />
        <!--颜色-->
        <attr name="lineCo" format="color" />
        <!--视图模式 主要是切换这个模式来显示不同的分支状态-->
        <attr name="mode" format="integer" />
</declare-styleable>
```groovy
mode可用的值范围：
```groovy
    /**
     * 线模式
     * 0:normal 默认一条横线
     * 1:expand T字展开
     * 2:middle son 展开项中，中间项分支向右的T字
     * 3:end son 展开项末尾项
     * 4:vertical line 第三级项设置，同时展开多个二级组时使用
     * 5:no line 没有任何显示
     */
```groovy
