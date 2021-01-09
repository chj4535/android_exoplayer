package com.example.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends Activity {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private ImaAdsLoader adsLoader;

    public static final Uri MP4_URI = Uri.parse("http://218.146.77.145:9000/static/test.mp4");
    public static final Uri AD_TAG_URI = Uri.parse("https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpremidpostlongpod&cmsid=496&vid=short_tencue&correlator=");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_view);
        adsLoader = new ImaAdsLoader(this, AD_TAG_URI);
    }

    @Override
    protected void onStart() {
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerView.setPlayer(player);
        adsLoader.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, getString(R.string.app_name)));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MP4_URI);
//        MediaSource adsMediaSource = new AdsMediaSource(mediaSource, dataSourceFactory, adsLoader, playerView);
        player.prepare(mediaSource);

        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        adsLoader.setPlayer(null);
        playerView.setPlayer(null);
        player.release();
        player = null;

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        adsLoader.release();

        super.onDestroy();
    }

}