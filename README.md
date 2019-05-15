# module
一般创建几个组件是根据项目的需求定的，我们假设把一个项目分为3个模块，分别为main页，个人中心Mine页和登录Login页。这里我们需要创建5个module，除了上面的3个以外，还应该有一个公共的，它只是一个library，不能单独运行，所有的公共资源都可以放到这里，除此之外还应该有一个单独打包的app module，这个module不写任何java代码，只是集成上面的3个module。我们看下结构

![](/img/微信截图_20190509102525.png)
![](https://github.com/sdwwld/module/blob/master/img/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20190509102525.png)


我们在项目根目录下的gradle.properties文件中设置module是否可以单独运行，

```
isModule=false
```
当isModule为true的时候上面的3个module都是可以单独运行的，当isModule为false的时候，只能运行或打包app module，也就是集成所有的module。
第三方依赖全部放在根目录的config.gradle文件中，然后在根目录下的build.gradle文件中进行引用

```
apply from: "config.gradle"
```
app module只负责集成，里面没有太多代码，只需要在app module的build.gradle添加下面代码即可

```
dependencies {
    if (!isModule.toBoolean()) {
        //如果资源文件有相同的名字，前面module的资源文件将替换后面的，Manifest会合并
        implementation project(':module_main')
        implementation project(':module_login')
        implementation project(':module_mine')
    }
}
```
这里要注意下，如果多个module集成打包的时候，有相同的资源文件名，前面的会覆盖后面的，所以为了防止出现这种问题我们可以再每个module中的build.gradle文件中都添加这样一行代码，比如在个人中心页面的module

```
resourcePrefix "mine_"
```
同样如果是在登录的module中我们可以改为

```
resourcePrefix "login_"
```
相应的每个资源文件也都要以mine_或login_开头，这样就可以避免出现重复的文件被覆盖的问题。但有一个文件AndroidManifest.xml不会被覆盖，如果多个module进行集成打包的时候，AndroidManifest.xml文件中的内容会进行合并。
module_common组件是公共的，供其他3个组件使用，所以要在使用它的时候进行引用

```
implementation project(':module_common')
```
这里要注意如果module_common组件中有引用其他第三方组件或者自己写的组件，并且还能被其他3个组件访问的话，引用必须要以api的方式，不能是implementation，比如

```
    api rootProject.ext.dependencies["eventbus"]
    api rootProject.ext.dependencies["okhttp"]
    api rootProject.ext.dependencies["okhttp-urlconnection"]
    api rootProject.ext.dependencies["retrofit"]
```
举个例子，A组件引用B组件，B组件引用C组件，如果A组件想使用C组件中的方法，那么B组件引用C组件的时候必须用api，如果用implementation ，A组件是不能访问到C组件的方法的。
其他的3个组件，究竟是一个单独的可以运行的组件还是一个library，我们可以通过前面定义的isModule来判断

```
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
相应的在需要运行组件的build.gradle文件中还有有这段代码

```
    sourceSets {
        main {
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                //集成开发模式下排除debug文件夹中的所有Java文件
                java {
                    exclude 'debug/**'
                }
            }
        }
    }
```
同时项目中也需要有对应的文件，比如我们看下个人中心Mine Module

![](/img/微信截图_20190509111503.png)

最后最重要的就是Module之间的跳转了，我们使用的是阿里巴巴的arouter，

```
    annotationProcessor rootProject.ext.dependencies["arouter-compiler"]
    annotationProcessor rootProject.ext.dependencies["glide_compiler"]
```
这个不能在公共的module中引用，必须在每个可跳转的module中引用，因为他会根据注解的类在build中生成对应的文件，比如在Mine module中有这样一段代码

```
@Route(path = ARouterPath.MODULEMINE_MINEACTIVITY)
public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_mine);
    }
}
```
那么就会在相应module生成相应的文件

![](/img/微信截图_20190509112217.png)

如果我们没有在每个注解的module中添加arouter依赖，那么在相应的module中就不会生成相应的文件，跳转永远也都不会成功。
arouter的使用比较简单，这里就不在介绍。关于页面的跳转和传递参数可以参照

```
            ARouter.getInstance().build(ARouterPath.MODULEMINE_MINEACTIVITY)
                    .withString("param", "我要到个人中心页面").navigation();
```
关于数据的接收可以参照

```
@Route(path = ARouterPath.MODULEMINE_MINEACTIVITY)
public class MineActivity extends AppCompatActivity {
    @Autowired(name = "param")
    public String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_mine);
        ARouter.getInstance().inject(this);
//        Toast.makeText(getApplicationContext(), param, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), getIntent().getExtras().getString("param"), Toast.LENGTH_LONG).show();
    }
```
这两种方式都可以获取到参数，如果想了解组件化开发的可以看下。
如果觉得有用就给个star吧
