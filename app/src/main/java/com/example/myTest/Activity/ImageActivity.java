package com.example.myTest.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.example.myTest.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ImageActivity extends Activity {

    @BindView(R.id.banner_test)
    Banner banner;


    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        ButterKnife.bind(this);
        init();
//        TokenBean bean = new TokenBean();//返回上一个activity
//        bean.setCode("asdad");
//        Intent intent = new Intent();
//        intent.putExtra("name", "22222222222222");
//        intent.putExtra("bean", bean);
//        setResult(RESULT_OK, intent);
//        this.finish();
    }

    private void init() {
        initImage();
        initBanner();
    }

    private void initImage() {
        Glide.with(ImageActivity.this).load("http://weihai.oss-cn-beijing.aliyuncs.com/4/works/c2be152f87fb1a98/image.jpg")
                .apply(bitmapTransform(new RoundedCornersTransformation(25, 3, RoundedCornersTransformation.CornerType.ALL)))
                .into(image);

    }

    private void initBanner() {

        List<String> imagePaths = new ArrayList<>();
        imagePaths.add("http://weihai.oss-cn-beijing.aliyuncs.com/4/works/c2be152f87fb1a98/image.jpg");
        imagePaths.add("http://weihai.oss-cn-beijing.aliyuncs.com/4/works/8c99d38f1cac676e/image.jpg");
        imagePaths.add("http://weihai.oss-cn-beijing.aliyuncs.com/4/works/c2be152f87fb1a98/image.jpg");
        imagePaths.add("http://weihai.oss-cn-beijing.aliyuncs.com/4/works/8c99d38f1cac676e/image.jpg");

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.LEFT)
                .setImageLoader(new BannerImageLoader())
                .setImages(imagePaths)
                .isAutoPlay(true)
                .setDelayTime(2000).start();
    }

    private class BannerImageLoader extends ImageLoader {
        @SuppressLint("CheckResult")
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            RequestOptions Rounded = new RequestOptions();
            Rounded.apply(bitmapTransform(new RoundedCornersTransformation(25, 20, RoundedCornersTransformation.CornerType.ALL)));
//            .apply(bitmapTransform(new BlurTransformation(30,2)));
//                    .centerCrop()
//                    .skipMemoryCache(true)

            RequestOptions Mask = new RequestOptions();
            Mask.apply(bitmapTransform(new MaskTransformation(R.mipmap.mask_starfish)));

            RequestOptions SketchFilter = new RequestOptions();
            SketchFilter.apply(bitmapTransform(new SketchFilterTransformation()));
//                    .apply(bitmapTransform(new RoundedCornersTransformation(25, 20, RoundedCornersTransformation.CornerType.ALL)));

            Glide.with(context).load(path)
                    .apply(Mask)
                    .transition(withCrossFade())
                    .into(imageView);
//            Transformation<Bitmap>
        }
    }
}
