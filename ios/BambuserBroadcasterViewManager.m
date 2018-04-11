/**
 * Copyright Bambuser AB 2018
 */

#import "BambuserBroadcasterViewManager.h"
#import "BambuserBroadcasterView.h"

@implementation BambuserBroadcasterViewManager

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(onCurrentViewerCountUpdate, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onTotalViewerCountUpdate, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onMessageReceived, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onBroadcastStarted, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onBroadcastStopped, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onBroadcastError, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onLocalCopySaved, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onBroadcastIdReceived, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onUplinkTestComplete, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onStreamHealthUpdate, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onPictureSaved, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onIncomingTalkbackRequest, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onTalkbackIdle, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onTalkbackPlaying, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onTalkbackAccepted, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onTalkbackNeedsAccept, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onCameraReady, RCTBubblingEventBlock)

-(UIView *)view {
  BambuserBroadcasterView *bambuserBroadcasterView = [[BambuserBroadcasterView alloc] init];
  return bambuserBroadcasterView;
}

RCT_CUSTOM_VIEW_PROPERTY(applicationId, NSString, BambuserBroadcasterView) {
  [view setApplicatonId:json];
}

RCT_CUSTOM_VIEW_PROPERTY(audioQuality, NSString, BambuserBroadcasterView) {
  [view setAudioQuality:json];
}

RCT_CUSTOM_VIEW_PROPERTY(author, NSString, BambuserBroadcasterView) {
  [view setAuthor:json];
}

RCT_CUSTOM_VIEW_PROPERTY(title, NSString, BambuserBroadcasterView) {
  [view setTitle:json];
}

RCT_CUSTOM_VIEW_PROPERTY(customData, NSString, BambuserBroadcasterView) {
  [view setCustomData:json];
}

RCT_CUSTOM_VIEW_PROPERTY(privateMode, BOOL, BambuserBroadcasterView) {
  [view setPrivateMode:[json boolValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(aspect, NSDictionary, BambuserBroadcasterView) {
  [view setAspect:json];
}

RCT_CUSTOM_VIEW_PROPERTY(localCopy, BOOL, BambuserBroadcasterView) {
  [view setLocalCopy:[json boolValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(localCopyFilename, NSString, BambuserBroadcasterView) {
  [view setLocalCopyFilename:json];
}

RCT_CUSTOM_VIEW_PROPERTY(sendPosition, BOOL, BambuserBroadcasterView) {
  [view setSendPosition:[json boolValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(saveOnServer, BOOL, BambuserBroadcasterView) {
  [view setSaveOnServer:[json boolValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(zoom, float, BambuserBroadcasterView) {
  [view setZoom:[json floatValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(talkback, BOOL, BambuserBroadcasterView) {
  [view setTalkback:[json boolValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(startBroadcast, BOOL, BambuserBroadcasterView) {
  [view setStartBroadcast:true];
}

RCT_CUSTOM_VIEW_PROPERTY(stopBroadcast, BOOL, BambuserBroadcasterView) {
  [view setStopBroadcast:true];
}

RCT_CUSTOM_VIEW_PROPERTY(startUplinkTest, BOOL, BambuserBroadcasterView) {
  [view setStartUplinkTest:true];
}

RCT_CUSTOM_VIEW_PROPERTY(takePicture, BOOL, BambuserBroadcasterView) {
  [view setTakePicture:true];
}

RCT_CUSTOM_VIEW_PROPERTY(acceptTalkback, int, BambuserBroadcasterView) {
  [view setAcceptTalkback:[json intValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(declineTalkback, int, BambuserBroadcasterView) {
  [view setDeclineTalkback:[json intValue]];
}

RCT_CUSTOM_VIEW_PROPERTY(endTalkback, BOOL, BambuserBroadcasterView) {
  [view setEndTalkback:true];
}

RCT_CUSTOM_VIEW_PROPERTY(switchCamera, BOOL, BambuserBroadcasterView) {
  [view setSwitchCamera:true];
}

@end
