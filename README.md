# 网络通信框架Volley的简单使用
Volley是一个网络通信框架，在2013年的Google I/O被推出。Volley既可以访问网络取得数据，也可以加载图片，并且在性能方面进
行了大幅度的调整。它的设计目标就是适合进行数据量不大但通信频繁的网络操作。而对于大数据量的网络操作，比如说下载文件等，Volley
的表现却非常糟糕。   
1. 添加依赖  
build.gradle文件中添加如下依赖：
```
  implementation 'com.mcxiaoke.volley:library:1.0.19'
```   
2. 在AndroidManifest.xml中添加网络权限：
```
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```   
3. Volley网络请求队列  
Volley 请求网络都是基于请求队列的，开发者只要把请求放在请求队列中就可以了，请求队列会依次进行请求。通常情况下一个应用
只需要一个请求队列，如果请求多或其他情况，也可以一个Activity对应一个请求队列。首先创建队列，如下所示：
```
  RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
```   
4. StringRequest的用法   
```
  public StringRequest(int method, String url, Listener<String> listener,
            ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }
  
  public StringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }
```   
StringRequest方法有两个构造方法，其中第一个比第二个多了请求的方法，不传的话默认为get请求。listener对请求成功时进行处理，
errorListener对请异常时进行处理。根据方法创建StringRequest对象即可。   
之后需要将常见的StringRequest对象加入到队列中   
```
  requestQueue.add(mStringRequest);
```
5. JsonRequest的用法    
JsonRequest用来解析Json数据，其中JsonObjetRequest返回的是单条Json数据，而JsonArrayRequest返回的是Json数组，用法和
StringRequest类似，可以结合Gs使用lmageLoader加载图片on对返回的数据进行解析。
6. 使用lmageLoader加载图片  
ImageLoader 的内部使用 ImageRequest 来实现，它的构造方法可以传入一个 ImageCache缓存形参，实现图片缓存的功能。同时还可以过
滤重复连接，避免重复发送请求。ImageLoader加载图片会先显示默认的图片，等待图片加载完成才会显示在ImageView上。
```
    String imgUrl = "https://c-ssl.duitang.com/uploads/item/201409/17/20140917095434_aZm2R.jpeg";
    ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
        @Override
        public Bitmap getBitmap(String url) {
            return null;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {

        }
    });
    ImageLoader.ImageListener listener = ImageLoader.getImageListener(imgView,R.drawable.ic_autorenew_black_24dp,R.drawable.ic_mood_bad_black_24dp);
    imageLoader.get(imgUrl,listener,200,200);
```  
ImageLoader.getImageListener()第一个参数表示显示图片的ImageView，第二个参数表示加载图片时显示的图片，第三个参数表示失败时显示的图片。
imageLoader.get()方法最后两个参数表示最大的宽度和高度，也可以不传参数。
