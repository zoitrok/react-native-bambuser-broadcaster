/**
 * Copyright Bambuser AB 2018
 */

#import <UIKit/UIKit.h>

// import RCTComponent.h
#if __has_include(<React/RCTComponent.h>)
#import <React/RCTComponent.h>
#elif __has_include("RCTComponent.h")
#import "RCTComponent.h"
#else
#import "React/RCTComponent.h"
#endif

@class BambuserBroadcasterView;

@interface BambuserBroadcasterView : UIView

@property (nonatomic) NSString *applicatonId;
@property (nonatomic) NSString *audioQuality;
@property (nonatomic) NSString *author;
@property (nonatomic) NSString *title;
@property (nonatomic) NSString *customData;
@property (nonatomic) NSDictionary *aspect;
@property (nonatomic) BOOL localCopy;
@property (nonatomic) NSString *localCopyFilename;
@property (nonatomic) BOOL sendPosition;
@property (nonatomic) BOOL saveOnServer;
@property (nonatomic) float zoom;
@property (nonatomic) BOOL talkback;

@property (nonatomic) BOOL startBroadcast;
@property (nonatomic) BOOL stopBroadcast;
@property (nonatomic) BOOL startUplinkTest;
@property (nonatomic) BOOL takePicture;
@property (nonatomic) int acceptTalkback;
@property (nonatomic) int declineTalkback;
@property (nonatomic) BOOL endTalkback;
@property (nonatomic) BOOL switchCamera;

@property (nonatomic, copy) RCTBubblingEventBlock onCurrentViewerCountUpdate;
@property (nonatomic, copy) RCTBubblingEventBlock onTotalViewerCountUpdate;
@property (nonatomic, copy) RCTBubblingEventBlock onMessageReceived;
@property (nonatomic, copy) RCTBubblingEventBlock onBroadcastStarted;
@property (nonatomic, copy) RCTBubblingEventBlock onBroadcastStopped;
@property (nonatomic, copy) RCTBubblingEventBlock onBroadcastError;
@property (nonatomic, copy) RCTBubblingEventBlock onLocalCopySaved;
@property (nonatomic, copy) RCTBubblingEventBlock onBroadcastIdReceived;
@property (nonatomic, copy) RCTBubblingEventBlock onUplinkTestComplete;
@property (nonatomic, copy) RCTBubblingEventBlock onStreamHealthUpdate;
@property (nonatomic, copy) RCTBubblingEventBlock onPictureSaved;
@property (nonatomic, copy) RCTBubblingEventBlock onIncomingTalkbackRequest;
@property (nonatomic, copy) RCTBubblingEventBlock onTalkbackIdle;
@property (nonatomic, copy) RCTBubblingEventBlock onTalkbackPlaying;
@property (nonatomic, copy) RCTBubblingEventBlock onTalkbackAccepted;
@property (nonatomic, copy) RCTBubblingEventBlock onTalkbackNeedsAccept;
@property (nonatomic, copy) RCTBubblingEventBlock onCameraReady;
@property (nonatomic, copy) RCTBubblingEventBlock onStartBroadcastNotReady;

@end
