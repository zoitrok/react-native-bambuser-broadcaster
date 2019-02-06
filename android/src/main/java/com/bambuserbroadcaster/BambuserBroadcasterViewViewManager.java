package com.bambuserbroadcaster;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.bambuser.broadcaster.Broadcaster;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

/**
 * Copyright Bambuser AB 2018
 */

public class BambuserBroadcasterViewViewManager extends ViewGroupManager<BambuserBroadcasterView> {

    private static Map<String, Broadcaster.AudioSetting> AUDIO_SETTING = MapBuilder.of(
        "audioQualityHigh", Broadcaster.AudioSetting.HIGH_QUALITY,
        "audioQualityLow", Broadcaster.AudioSetting.NORMAL_QUALITY,
        "audioQualityMute", Broadcaster.AudioSetting.OFF
    );

    public static final String REACT_CLASS = "BambuserBroadcasterView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected BambuserBroadcasterView createViewInstance(ThemedReactContext reactContext) {
        return new BambuserBroadcasterView(reactContext, this);
    }

    void pushEvent(ThemedReactContext context, ViewGroup view, String name, WritableMap event) {
        context.getJSModule(RCTEventEmitter.class).receiveEvent(view.getId(), name, event);
    }

    @ReactProp(name = "applicationId")
    public void setApplicationId(BambuserBroadcasterView view, String applicationId) {
        view.setApplicationId(applicationId);
    }

    @ReactProp(name = "audioQuality")
    public void setAudioQuality(BambuserBroadcasterView view, String audioQuality) {
        Broadcaster.AudioSetting audioSetting = AUDIO_SETTING.get(audioQuality);
        view.setAudioQuality(audioSetting);
    }

    @ReactProp(name = "author")
    public void setAuthor(BambuserBroadcasterView view, String author) {
        view.setAuthor(author);
    }

    @ReactProp(name = "title")
    public void setTitle(BambuserBroadcasterView view, String title) {
        view.setTitle(title);
    }

    @ReactProp(name = "customData")
    public void setCustomData(BambuserBroadcasterView view, String customData) {
        view.setCustomData(customData);
    }

    @ReactProp(name = "aspect")
    public void setAspect(BambuserBroadcasterView view, ReadableMap aspect) {
        view.setAspect(aspect);
    }

    @ReactProp(name = "localCopy")
    public void setLocalCopy(BambuserBroadcasterView view, Boolean localCopy) {
        view.setLocalCopy(localCopy);
    }

    @ReactProp(name = "localCopyFilename")
    public void setLocalCopy(BambuserBroadcasterView view, String localCopyFilename) {
        view.setLocalCopyFilename(localCopyFilename);
    }

    @ReactProp(name = "sendPosition")
    public void setSendPosition(BambuserBroadcasterView view, Boolean sendPosition) {
        view.setSendPosition(sendPosition);
    }

    @ReactProp(name = "saveOnServer")
    public void setSaveOnServer(BambuserBroadcasterView view, Boolean saveOnServer) {
        view.setSaveOnServer(saveOnServer);
    }

    @ReactProp(name = "zoom")
    public void setZoom(BambuserBroadcasterView view, float zoom) {
        view.setZoom(zoom);
    }

    @ReactProp(name = "talkback")
    public void setTalkback(BambuserBroadcasterView view, Boolean talkback) {
        view.setTalkback(talkback);
    }

    @ReactProp(name = "startBroadcast")
    public void setStartBroadcast(BambuserBroadcasterView view, Boolean startBroadcast) {
        view.startBroadcast();
    }

    @ReactProp(name = "stopBroadcast")
    public void setStopBroadcast(BambuserBroadcasterView view, Boolean stopBroadcast) {
        view.stopBroadcast();
    }

    @ReactProp(name = "startUplinkTest")
    public void setStartUplinkTest(BambuserBroadcasterView view, Boolean startUplinkTest) {
        view.startUplinkTest();
    }

    @ReactProp(name = "takePicture")
    public void setTakePicture(BambuserBroadcasterView view, Boolean takePicture) {
        view.takePicture();
    }

    @ReactProp(name = "acceptTalkback")
    public void setAcceptTalkback(BambuserBroadcasterView view, int acceptTalkback) {
        view.acceptTalkback((int)acceptTalkback);
    }

    @ReactProp(name = "declineTalkback")
    public void setDeclineTalkback(BambuserBroadcasterView view, int declineTalkback) {
        view.declineTalkback();
    }

    @ReactProp(name = "endTalkback")
    public void setEndTalkback(BambuserBroadcasterView view, Boolean endTalkback) {
        view.endTalkback();
    }

    @ReactProp(name = "switchCamera")
    public void setSwitchCamera(BambuserBroadcasterView view, Boolean switchCamera) {
        view.switchCamera();
    }

    @Override
    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        Map<String, Map<String, String>> map = MapBuilder.of(
            "onCurrentViewerCountUpdate", MapBuilder.of("registrationName", "onCurrentViewerCountUpdate"),
            "onTotalViewerCountUpdate", MapBuilder.of("registrationName", "onTotalViewerCountUpdate"),
            "onMessageReceived", MapBuilder.of("registrationName", "onMessageReceived"),
            "onBroadcastStarted", MapBuilder.of("registrationName", "onBroadcastStarted"),
            "onBroadcastStopped", MapBuilder.of("registrationName", "onBroadcastStopped"),
            "onBroadcastError", MapBuilder.of("registrationName", "onBroadcastError"),
            "onLocalCopySaved", MapBuilder.of("registrationName", "onLocalCopySaved")
        );

        map.putAll(MapBuilder.of(
            "onBroadcastIdReceived", MapBuilder.of("registrationName", "onBroadcastIdReceived"),
            "onUplinkTestComplete", MapBuilder.of("registrationName", "onUplinkTestComplete"),
            "onStreamHealthUpdate", MapBuilder.of("registrationName", "onStreamHealthUpdate"),
            "onPictureSaved", MapBuilder.of("registrationName", "onPictureSaved"),
            "onTotalViewerCountUpdate", MapBuilder.of("registrationName", "onTotalViewerCountUpdate"),
            "onIncomingTalkbackRequest", MapBuilder.of("registrationName", "onIncomingTalkbackRequest"),
            "onTalkbackIdle", MapBuilder.of("registrationName", "onTalkbackIdle")
        ));

        map.putAll(MapBuilder.of(
            "onTalkbackPlaying", MapBuilder.of("registrationName", "onTalkbackPlaying"),
            "onTalkbackAccepted", MapBuilder.of("registrationName", "onTalkbackAccepted"),
            "onTalkbackNeedsAccept", MapBuilder.of("registrationName", "onTalkbackNeedsAccept"),
            "onCameraReady", MapBuilder.of("registrationName", "onCameraReady"),
            "onStartBroadcastNotReady", MapBuilder.of("registrationName", "onStartBroadcastNotReady")
        ));

        return map;
    }

}
