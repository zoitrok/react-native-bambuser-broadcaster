package com.bambuserbroadcaster;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.bambuser.broadcaster.BroadcastStatus;
import com.bambuser.broadcaster.Broadcaster;
import com.bambuser.broadcaster.CameraError;
import com.bambuser.broadcaster.ConnectionError;
import com.bambuser.broadcaster.SurfaceViewWithAutoAR;
import com.bambuser.broadcaster.TalkbackState;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;

import java.io.File;

/**
 * Copyright Bambuser AB 2018
 */

public class BambuserBroadcasterView extends RelativeLayout implements LifecycleEventListener {

    BambuserBroadcasterViewViewManager manager;
    RelativeLayout mWrapperLayout;
    SurfaceViewWithAutoAR mVideoSurfaceView;
    Broadcaster mBroadcaster;
    String _applicationId;
    Broadcaster.AudioSetting _audioQuality = Broadcaster.AudioSetting.HIGH_QUALITY;
    String _author;
    String _title;
    String _customData;
    ReadableMap _aspect;
    Boolean _localCopy = false;
    String _localCopyFilename;
    Boolean _sendPosition = false;
    Boolean _saveOnServer = true;
    float _zoom = 0;
    Boolean _broadcasting = false;
    Boolean _talkback = false;

    public BambuserBroadcasterView(ThemedReactContext context, BambuserBroadcasterViewViewManager manager) {
        super(context);
        context.addLifecycleEventListener(this);
        this.manager = manager;
        init(null, 0);
    }

    public BambuserBroadcasterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mWrapperLayout = new RelativeLayout(getContext());
        mWrapperLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mWrapperLayout.setBackgroundColor(Color.parseColor("#000000"));
        addView(mWrapperLayout);
        mVideoSurfaceView = new SurfaceViewWithAutoAR(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mWrapperLayout.addView(mVideoSurfaceView, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWrapperLayout.layout(0, 0, right, bottom);
        if (indexOfChild(mWrapperLayout) != 0) {
            removeView(mWrapperLayout);
            addView(mWrapperLayout, 0);
        }
        if (mBroadcaster != null && !_broadcasting) {
            Activity activity = getActivity();
            if (activity != null) {
                int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                if (_aspect != null) {
                    if (_aspect.hasKey("width") && _aspect.hasKey("height")) {
                        int aspectWidth = _aspect.getInt("width");
                        int aspectHeight = _aspect.getInt("height");
                        mBroadcaster.setRotation(rotation, rotation, aspectWidth, aspectHeight);
                    } else {
                        mBroadcaster.setRotation(rotation, rotation);
                    }
                } else {
                    mBroadcaster.setRotation(rotation, rotation);
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mBroadcaster == null) {
            Activity activity = getActivity();
            if (activity != null) {
                mBroadcaster = new Broadcaster(activity, _applicationId, mBroadcasterObserver);
                mBroadcaster.setViewerCountObserver(mBroadcastViewerCountObserver);
                mBroadcaster.setCameraSurface(mVideoSurfaceView);
                mBroadcaster.setUplinkSpeedObserver(mUplinkSpeedObserver);
            }
        }
        mBroadcaster.onActivityResume();
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBroadcaster != null) {
            mBroadcaster.onActivityPause();
        }
    }

    void setApplicationId(String applicationId) {
        if (applicationId != null) {
            _applicationId = applicationId;
        }
    }

    void setAudioQuality(Broadcaster.AudioSetting audioQuality) {
        if (audioQuality != null) {
            _audioQuality = audioQuality;
        }
        if (mBroadcaster != null) {
            mBroadcaster.setAudioQuality(_audioQuality);
        }
    }

    void setAuthor(String author) {
        _author = author;
        if (mBroadcaster != null) {
            mBroadcaster.setAuthor(_author);
        }
    }

    void setTitle(String title) {
        _title = title;
        if (mBroadcaster != null) {
            mBroadcaster.setTitle(_title);
        }
    }

    void setCustomData(String customData) {
        _customData = customData;
        if (mBroadcaster != null) {
            mBroadcaster.setCustomData(_title);
        }
    }

    void setAspect(ReadableMap aspect) {
        if (aspect != null) {
            _aspect = aspect;
        }
    }

    void setLocalCopy(Boolean localCopy) {
        _localCopy = localCopy;
    }

    void setLocalCopyFilename(String localCopyFilename) {
        _localCopyFilename = localCopyFilename;
    }

    void setSendPosition(Boolean sendPosition) {
        _sendPosition = sendPosition;
        if (mBroadcaster != null) {
            mBroadcaster.setSendPosition(_sendPosition);
        }
    }

    void setSaveOnServer(Boolean saveOnServer) {
        _saveOnServer = saveOnServer;
        if (mBroadcaster != null) {
            mBroadcaster.setSaveOnServer(_saveOnServer);
        }
    }

    void setZoom(float zoom) {
        _zoom = zoom;
        if (mBroadcaster.getZoomRatios() != null) {
            int minZoom = mBroadcaster.getZoomRatios().get(0);
            int maxZoom = mBroadcaster.getZoomRatios().get(mBroadcaster.getZoomRatios().size() - 1);
            float zoomCalc = ((maxZoom - minZoom) * _zoom) + minZoom;
            if (mBroadcaster != null) {
                mBroadcaster.setZoom((int)zoomCalc);
            }
        }
    }

    void setTalkback(Boolean talkback) {
        _talkback = talkback;
        if (mBroadcaster != null) {
            if (talkback) {
                mBroadcaster.setTalkbackObserver(mTalkbackObserver);
            } else {
                mBroadcaster.setTalkbackObserver(null);
            }
        }
    }

    void startBroadcast() {
        if (mBroadcaster != null && mBroadcaster.canStartBroadcasting()) {
            mBroadcaster.setAudioQuality(_audioQuality);
            mBroadcaster.setAuthor(_author);
            mBroadcaster.setTitle(_title);
            mBroadcaster.setCustomData(_customData);
            mBroadcaster.setSendPosition(_sendPosition);
            mBroadcaster.setSaveOnServer(_saveOnServer);
            if (_localCopy) {
                File filename = null;
                if (_localCopyFilename == null) {
                    long ms = System.currentTimeMillis();
                    filename = new File(getContext().getCacheDir(), "broadcast-" + String.valueOf(ms) + ".mp4");
                } else {
                    filename = new File(getContext().getCacheDir(), _localCopyFilename);
                }
                if (filename != null) {
                    mBroadcaster.storeLocalMedia(filename, mLocalMediaObserver);
                }
            }
            if (_talkback) {
                mBroadcaster.setTalkbackObserver(mTalkbackObserver);
            } else {
                mBroadcaster.setTalkbackObserver(null);
            }
            mBroadcaster.startBroadcast();
        } else {
          sendEvent(event, "onStartBroadcastNotReady");
        }
    }

    void stopBroadcast() {
        if (mBroadcaster != null) {
            mBroadcaster.stopBroadcast();
        }
    }

    void startUplinkTest() {
        if (mBroadcaster != null) {
            mBroadcaster.startUplinkTest();
        }
    }

    void takePicture() {
        if (mBroadcaster != null) {
            long ms = System.currentTimeMillis();
            File file = new File(getContext().getCacheDir(), "image-" + String.valueOf(ms) + ".jpeg");
            mBroadcaster.takePicture(file, null, mPictureObserver);
        }
    }

    void acceptTalkback(int acceptTalkback) {
        if (mBroadcaster != null) {
            mBroadcaster.acceptTalkback(acceptTalkback);
        }
    }

    void declineTalkback() {
        if (mBroadcaster != null) {
            mBroadcaster.stopTalkback();
        }
    }

    void endTalkback() {
        if (mBroadcaster != null) {
            mBroadcaster.stopTalkback();
        }
    }

    void switchCamera() {
        if (mBroadcaster != null) {
            mBroadcaster.switchCamera();
        }
    }

    private Broadcaster.Observer mBroadcasterObserver = new Broadcaster.Observer() {
        @Override
        public void onConnectionStatusChange(BroadcastStatus broadcastStatus) {
            WritableMap event = new WritableNativeMap();
            switch (broadcastStatus) {
                case IDLE:
                    sendEvent(event, "onBroadcastStopped");
                    _broadcasting = false;
                    break;
                case PREPARED:
                    break;
                case STARTING:
                    break;
                case CAPTURING:
                    sendEvent(event, "onBroadcastStarted");
                    _broadcasting = true;
                    break;
                case FINISHING:
                    break;
            }
        }
        @Override
        public void onStreamHealthUpdate(int i) {
            WritableMap event = new WritableNativeMap();
            event.putString("health", String.valueOf(i));
            sendEvent(event, "onStreamHealthUpdate");
        }
        @Override
        public void onConnectionError(ConnectionError connectionError, String s) {
            WritableMap event = new WritableNativeMap();
            event.putString("errorCode", String.valueOf(connectionError));
            event.putString("message", s);
            sendEvent(event, "onBroadcastError");
        }
        @Override
        public void onCameraError(CameraError cameraError) {
            WritableMap event = new WritableNativeMap();
            event.putString("errorCode", String.valueOf(cameraError));
            sendEvent(event, "onBroadcastError");
        }
        @Override
        public void onChatMessage(String s) {
            WritableMap event = new WritableNativeMap();
            event.putString("message", s);
            sendEvent(event, "onMessageReceived");
        }
        @Override
        public void onResolutionsScanned() {
            requestLayout();
        }
        @Override
        public void onCameraPreviewStateChanged() {
            WritableMap event = new WritableNativeMap();
            event.putBoolean("hasZoom", mBroadcaster.hasZoom());
            event.putBoolean("hasTorch", mBroadcaster.hasTorch());
            event.putBoolean("canSwitchCamera", (mBroadcaster.getCameraCount() > 1));
            sendEvent(event, "onCameraReady");
        }
        @Override
        public void onBroadcastInfoAvailable(String s, String s1) {
        }
        @Override
        public void onBroadcastIdAvailable(String s) {
            WritableMap event = new WritableNativeMap();
            event.putString("broadcastId", s);
            sendEvent(event, "onBroadcastIdReceived");
        }
    };

    private Broadcaster.LocalMediaObserver mLocalMediaObserver = new Broadcaster.LocalMediaObserver() {
      @Override
      public void onLocalMediaClosed(String filePath) {
          WritableMap event = new WritableNativeMap();
          event.putString("filePath", filePath);
          sendEvent(event, "onPictureSaved");
      }
      @Override
      public void onLocalMediaError() {
          WritableMap event = new WritableNativeMap();
          event.putString("message", "Failed to store local media");
          sendEvent(event, "onBroadcastError");
      }
    };

    private Broadcaster.PictureObserver mPictureObserver = new Broadcaster.PictureObserver() {
        @Override
        public void onPictureStored(File file) {
            WritableMap event = new WritableNativeMap();
            event.putString("filePath", file.getPath());
            sendEvent(event, "onPictureSaved");
        }
    };

    private Broadcaster.TalkbackObserver mTalkbackObserver = new Broadcaster.TalkbackObserver() {
        @Override
        public void onTalkbackStateChanged(TalkbackState state, int id, String caller, String request) {
            WritableMap event = new WritableNativeMap();
            switch (state) {
                case IDLE:
                    sendEvent(event, "onTalkbackIdle");
                    break;
                case READY:
                    break;
                case PLAYING:
                    sendEvent(event, "onTalkbackPlaying");
                    break;
                case ACCEPTED:
                    sendEvent(event, "onTalkbackAccepted");
                    break;
                case NEEDS_ACCEPT:
                    sendEvent(event, "onTalkbackNeedsAccept");
                    WritableMap incomingTalkbackEvent = new WritableNativeMap();
                    incomingTalkbackEvent.putString("request", request);
                    incomingTalkbackEvent.putString("caller", caller);
                    incomingTalkbackEvent.putString("talkbackId", String.valueOf(id));
                    sendEvent(incomingTalkbackEvent, "onIncomingTalkbackRequest");
                    break;
            }
        }
    };

    private Broadcaster.UplinkSpeedObserver mUplinkSpeedObserver = new Broadcaster.UplinkSpeedObserver() {
        @Override
        public void onUplinkTestComplete(long bitrate, boolean recommendation) {
            WritableMap event = new WritableNativeMap();
            event.putString("speed", String.valueOf(bitrate));
            event.putString("recommendation", String.valueOf(recommendation));
            sendEvent(event, "onUplinkTestComplete");
        }
    };

    private Broadcaster.ViewerCountObserver mBroadcastViewerCountObserver = new Broadcaster.ViewerCountObserver() {
        @Override
        public void onCurrentViewersUpdated(long l) {
            WritableMap event = new WritableNativeMap();
            event.putString("viewers", String.valueOf(l));
            sendEvent(event, "onCurrentViewerCountUpdate");
        }

        @Override
        public void onTotalViewersUpdated(long l) {
            WritableMap event = new WritableNativeMap();
            event.putString("viewers", String.valueOf(l));
            sendEvent(event, "onTotalViewerCountUpdate");
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    private void sendEvent(WritableMap event, String name) {
        if (manager != null) {
            manager.pushEvent((ThemedReactContext) getContext(), this, name, event);
        }
    }

    @Override
    public void onHostResume() {
        if (mBroadcaster != null) {
            mBroadcaster.onActivityResume();
        }
    }

    @Override
    public void onHostPause() {
        if (mBroadcaster != null) {
            mBroadcaster.onActivityPause();
        }
    }

    @Override
    public void onHostDestroy() {
        if (mBroadcaster != null) {
            mBroadcaster.onActivityDestroy();
        }
    }

}
