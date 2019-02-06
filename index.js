import PropTypes from 'prop-types';
import React from 'react';
import {
  requireNativeComponent,
  View
} from 'react-native';

const AUDIO_QUALITY = {
  HIGH: 'audioQualityHigh',
  LOW: 'audioQualityLow',
  MUTE: 'audioQualityMute'
};

const propTypes = {
  applicationId: PropTypes.string,
  audioQuality: PropTypes.oneOf(Object.values(AUDIO_QUALITY)),
  author: PropTypes.string,
  title: PropTypes.string,
  customData: PropTypes.string,
  aspect: PropTypes.object,
  localCopy: PropTypes.bool,
  localCopyFilename: PropTypes.string,
  sendPosition: PropTypes.bool,
  saveOnServer: PropTypes.bool,
  zoom: PropTypes.number,
  talkback: PropTypes.bool,
  onCurrentViewerCountUpdate: PropTypes.func,
  onTotalViewerCountUpdate: PropTypes.func,
  onMessageReceived: PropTypes.func,
  onBroadcastStarted: PropTypes.func,
  onBroadcastStopped: PropTypes.func,
  onBroadcastError: PropTypes.func,
  onLocalCopySaved: PropTypes.func,
  onBroadcastIdReceived: PropTypes.func,
  onUplinkTestComplete: PropTypes.func,
  onStreamHealthUpdate: PropTypes.func,
  onPictureSaved: PropTypes.func,
  onIncomingTalkbackRequest: PropTypes.func,
  onTalkbackIdle: PropTypes.func,
  onTalkbackPlaying: PropTypes.func,
  onTalkbackAccepted: PropTypes.func,
  onTalkbackNeedsAccept: PropTypes.func,
  onCameraReady: PropTypes.func,
  ...View.propTypes
};

class RNBambuserBroadcaster extends React.Component {

  constructor(props) {
    super(props);
    this.broadcasterComponentView = null;
    this._onCurrentViewerCountUpdate = this._onCurrentViewerCountUpdate.bind(this);
    this._onTotalViewerCountUpdate = this._onTotalViewerCountUpdate.bind(this);
    this._onMessageReceived = this._onMessageReceived.bind(this);
    this._onBroadcastStarted = this._onBroadcastStarted.bind(this);
    this._onBroadcastStopped = this._onBroadcastStopped.bind(this);
    this._onBroadcastError = this._onBroadcastError.bind(this);
    this._onLocalCopySaved = this._onLocalCopySaved.bind(this);
    this._onBroadcastIdReceived = this._onBroadcastIdReceived.bind(this);
    this._onUplinkTestComplete = this._onUplinkTestComplete.bind(this);
    this._onStreamHealthUpdate = this._onStreamHealthUpdate.bind(this);
    this._onPictureSaved = this._onPictureSaved.bind(this);
    this._onIncomingTalkbackRequest = this._onIncomingTalkbackRequest.bind(this);
    this._onTalkbackIdle = this._onTalkbackIdle.bind(this);
    this._onTalkbackPlaying = this._onTalkbackPlaying.bind(this);
    this._onTalkbackAccepted = this._onTalkbackAccepted.bind(this);
    this._onTalkbackNeedsAccept = this._onTalkbackNeedsAccept.bind(this);
    this._onCameraReady = this._onCameraReady.bind(this);
  }

  startBroadcast() {
    this.broadcasterComponentView.setNativeProps({
      startBroadcast: true
    });
  }

  stopBroadcast() {
    this.broadcasterComponentView.setNativeProps({
      stopBroadcast: true
    });
  }

  switchCamera() {
    this.broadcasterComponentView.setNativeProps({
      switchCamera: true
    });
  }

  takePicture() {
    this.broadcasterComponentView.setNativeProps({
      takePicture: true
    });
  }

  startUplinkTest() {
    this.broadcasterComponentView.setNativeProps({
      startUplinkTest: true
    });
  }

  acceptTalkback(talkbackId) {
    this.broadcasterComponentView.setNativeProps({
      acceptTalkback: parseInt(talkbackId)
    });
  }

  declineTalkback(talkbackId) {
    this.broadcasterComponentView.setNativeProps({
      declineTalkback: parseInt(talkbackId)
    });
  }

  endTalkback() {
    this.broadcasterComponentView.setNativeProps({
      endTalkback: true
    });
  }

  _onCurrentViewerCountUpdate(event) {
    if (typeof this.props.onCurrentViewerCountUpdate === 'function') {
      this.props.onCurrentViewerCountUpdate(event.nativeEvent.viewers);
    }
  }

  _onTotalViewerCountUpdate(event) {
    if (typeof this.props.onTotalViewerCountUpdate === 'function') {
      this.props.onTotalViewerCountUpdate(event.nativeEvent.viewers);
    }
  }

  _onMessageReceived(event) {
    if (typeof this.props.onMessageReceived === 'function') {
      this.props.onMessageReceived(event.nativeEvent.message);
    }
  }

  _onBroadcastStarted(event) {
    if (typeof this.props.onBroadcastStarted === 'function') {
      this.props.onBroadcastStarted();
    }
  }

  _onBroadcastStopped(event) {
    if (typeof this.props.onBroadcastStopped === 'function') {
      this.props.onBroadcastStopped();
    }
  }

  _onBroadcastError(event) {
    if (typeof this.props.onBroadcastError === 'function') {
      this.props.onBroadcastError(event.nativeEvent.errorCode, event.nativeEvent.errorMessage);
    }
  }

  _onLocalCopySaved(event) {
    if (typeof this.props.onLocalCopySaved === 'function') {
      this.props.onLocalCopySaved(event.nativeEvent.filePath);
    }
  }

  _onBroadcastIdReceived(event) {
    if (typeof this.props.onBroadcastIdReceived === 'function') {
      this.props.onBroadcastIdReceived(event.nativeEvent.broadcastId);
    }
  }

  _onUplinkTestComplete(event) {
    if (typeof this.props.onUplinkTestComplete === 'function') {
      this.props.onUplinkTestComplete(event.nativeEvent.speed, event.nativeEvent.recommendation);
    }
  }

  _onStreamHealthUpdate(event) {
    if (typeof this.props.onStreamHealthUpdate === 'function') {
      this.props.onStreamHealthUpdate(event.nativeEvent.health);
    }
  }

  _onPictureSaved(event) {
    if (typeof this.props.onPictureSaved === 'function') {
      this.props.onPictureSaved(event.nativeEvent.filePath);
    }
  }

  _onIncomingTalkbackRequest(event) {
    if (typeof this.props.onIncomingTalkbackRequest === 'function') {
      this.props.onIncomingTalkbackRequest(event.nativeEvent.request, event.nativeEvent.caller, event.nativeEvent.talkbackId);
    }
  }

  _onTalkbackIdle() {
    if (typeof this.props.onTalkbackIdle === 'function') {
      this.props.onTalkbackIdle();
    }
  }

  _onTalkbackPlaying() {
    if (typeof this.props.onTalkbackPlaying === 'function') {
      this.props.onTalkbackPlaying();
    }
  }

  _onTalkbackAccepted() {
    if (typeof this.props.onTalkbackAccepted === 'function') {
      this.props.onTalkbackAccepted();
    }
  }

  _onTalkbackNeedsAccept() {
    if (typeof this.props.onTalkbackNeedsAccept === 'function') {
      this.props.onTalkbackNeedsAccept();
    }
  }

  _onCameraReady(event) {
    if (typeof this.props.onCameraReady === 'function') {
      this.props.onCameraReady(event.nativeEvent.hasZoom, event.nativeEvent.hasTorch, event.nativeEvent.canSwitchCamera);
    }
  }

  render() {
    var modifiedProps = this.props;
    if (modifiedProps.customData) {
      modifiedProps.customData = JSON.stringify(this.props.customData);
    }
    return (
      <BambuserBroadcasterView
        ref={ref => {
          this.broadcasterComponentView = ref;
        }}
        {...modifiedProps}
        onCurrentViewerCountUpdate={this._onCurrentViewerCountUpdate}
        onTotalViewerCountUpdate={this._onTotalViewerCountUpdate}
        onMessageReceived={this._onMessageReceived}
        onBroadcastStarted={this._onBroadcastStarted}
        onBroadcastStopped={this._onBroadcastStopped}
        onBroadcastError={this._onBroadcastError}
        onLocalCopySaved={this._onLocalCopySaved}
        onBroadcastIdReceived={this._onBroadcastIdReceived}
        onUplinkTestComplete={this._onUplinkTestComplete}
        onStreamHealthUpdate={this._onStreamHealthUpdate}
        onPictureSaved={this._onPictureSaved}
        onIncomingTalkbackRequest={this._onIncomingTalkbackRequest}
        onTalkbackIdle={this._onTalkbackIdle}
        onTalkbackPlaying={this._onTalkbackPlaying}
        onTalkbackAccepted={this._onTalkbackAccepted}
        onTalkbackNeedsAccept={this._onTalkbackNeedsAccept}
        onCameraReady={this._onCameraReady} />
    );
  }
}

RNBambuserBroadcaster.propTypes = propTypes;
RNBambuserBroadcaster.AUDIO_QUALITY = AUDIO_QUALITY;

const BambuserBroadcasterView = requireNativeComponent('BambuserBroadcasterView', RNBambuserBroadcaster, {
  nativeOnly: {
    startBroadcast: PropTypes.bool,
    stopBroadcast: PropTypes.bool,
    startUplinkTest: PropTypes.bool,
    takePicture: PropTypes.bool,
    acceptTalkback: PropTypes.number,
    declineTalkback: PropTypes.number,
    endTalkback: PropTypes.bool,
    switchCamera: PropTypes.bool
  }
});

module.exports = RNBambuserBroadcaster;
