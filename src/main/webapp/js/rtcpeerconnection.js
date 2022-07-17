/*
Copyright 2017 Google Inc.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

'use strict';

var startButton = document.getElementById('startButton');
var callButton = document.getElementById('callButton');
var hangupButton = document.getElementById('hangupButton');
callButton.disabled = true;
hangupButton.disabled = true;
startButton.onclick = start;
callButton.onclick = call;
hangupButton.onclick = hangup;

var startTime;
var localVideo = document.getElementById('localVideo');
var remoteVideo = document.getElementById('remoteVideo');

localVideo.addEventListener('loadedmetadata', function() {
    trace('Local video videoWidth: ' + this.videoWidth +
        'px,  videoHeight: ' + this.videoHeight + 'px'); // log#: 3
});

remoteVideo.addEventListener('loadedmetadata', function() {
    trace('Remote video videoWidth: ' + this.videoWidth +
        'px,  videoHeight: ' + this.videoHeight + 'px'); // log#: 48
});

remoteVideo.onresize = function() {
    trace('Remote video size changed to ' +
        remoteVideo.videoWidth + 'x' + remoteVideo.videoHeight); // log#: 46 // log#: 49
    // We'll use the first onsize callback as an indication that video has started
    // playing out.
    if (startTime) {
        var elapsedTime = window.performance.now() - startTime;
        trace('Setup time: ' + elapsedTime.toFixed(3) + 'ms'); // log#: 47
        startTime = null;
    }
};

var localStream;
var pc1;
var pc2;
var offerOptions = {
    offerToReceiveAudio: 1,
    offerToReceiveVideo: 1
};

function getName(pc) {
    return (pc === pc1) ? 'pc1' : 'pc2';
}

function getOtherPc(pc) {
    return (pc === pc1) ? pc2 : pc1;
}

function gotStream(stream) {
    trace('Received local stream'); // log#: 2
    localVideo.srcObject = stream;
    localStream = stream;
    callButton.disabled = false;
}

function start() {
    trace('Requesting local stream'); // log#: 1
    startButton.disabled = true;
    navigator.mediaDevices.getUserMedia({
        audio: true,
        video: true
    })
        .then(gotStream)
        .catch(function(e) {
            alert('getUserMedia() error: ' + e.name);
        });
}

function call() {
    callButton.disabled = true;
    hangupButton.disabled = false;
    trace('Starting call'); // log#: 4
    startTime = window.performance.now();
    var videoTracks = localStream.getVideoTracks();
    var audioTracks = localStream.getAudioTracks();
    if (videoTracks.length > 0) {
        trace('Using video device: ' + videoTracks[0].label); // log#: 5
    }
    if (audioTracks.length > 0) {
        trace('Using audio device: ' + audioTracks[0].label); // log#: 6
    }

    var servers = null;
    pc1 = new RTCPeerConnection(servers);
    trace('Created local peer connection object pc1'); // log#: 7
    pc1.onicecandidate = function(e) {
        onIceCandidate(pc1, e);
    };
    pc2 = new RTCPeerConnection(servers);
    trace('Created remote peer connection object pc2'); // log#: 8
    pc2.onicecandidate = function(e) {
        onIceCandidate(pc2, e);
    };
    pc1.oniceconnectionstatechange = function(e) {
        onIceStateChange(pc1, e);
    };
    pc2.oniceconnectionstatechange = function(e) {
        onIceStateChange(pc2, e);
    };
    pc2.onaddstream = gotRemoteStream;

    pc1.addStream(localStream);
    // pc1.stream(localStream);
    trace('Added local stream to pc1'); // log#: 9

    trace('pc1 createOffer start'); // log#: 10
    pc1.createOffer(
        offerOptions
    ).then(
        onCreateOfferSuccess,
        onCreateSessionDescriptionError
    );
}

function onCreateSessionDescriptionError(error) {
    trace('Failed to create session description: ' + error.toString());
}

function onCreateOfferSuccess(desc) {
    trace('Offer from pc1\n' + desc.sdp);  // log#: 11
    trace('pc1 setLocalDescription start');  // log#: 12
    pc1.setLocalDescription(desc).then(
        function() {
            onSetLocalSuccess(pc1);
        },
        onSetSessionDescriptionError
    );
    trace('pc2 setRemoteDescription start');  // log#: 13
    pc2.setRemoteDescription(desc).then(
        function() {
            onSetRemoteSuccess(pc2);
        },
        onSetSessionDescriptionError
    );
    trace('pc2 createAnswer start');  // log#: 14
    // Since the 'remote' side has no media stream we need
    // to pass in the right constraints in order for it to
    // accept the incoming offer of audio and video.
    pc2.createAnswer().then(
        onCreateAnswerSuccess,
        onCreateSessionDescriptionError
    );
}

function onSetLocalSuccess(pc) {
    trace(getName(pc) + ' setLocalDescription complete'); // log#: 15(pc1) // log#: 21(pc2)
}

function onSetRemoteSuccess(pc) {
    trace(getName(pc) + ' setRemoteDescription complete'); // log#: 17(pc2) // log#: 22(pc1)
}

function onSetSessionDescriptionError(error) {
    trace('Failed to set session description: ' + error.toString());
}

function gotRemoteStream(e) {
    remoteVideo.srcObject = e.stream;
    trace('pc2 received remote stream'); // log#: 16
}

function onCreateAnswerSuccess(desc) {
    trace('Answer from pc2:\n' + desc.sdp); // log#: 18
    trace('pc2 setLocalDescription start'); // log#: 19
    pc2.setLocalDescription(desc).then(
        function() {
            onSetLocalSuccess(pc2);
        },
        onSetSessionDescriptionError
    );
    trace('pc1 setRemoteDescription start'); // log#: 20
    pc1.setRemoteDescription(desc).then(
        function() {
            onSetRemoteSuccess(pc1);
        },
        onSetSessionDescriptionError
    );
}

function onIceCandidate(pc, event) {
    getOtherPc(pc).addIceCandidate(event.candidate)
        .then(
            function() {
                onAddIceCandidateSuccess(pc);
            },
            function(err) {
                onAddIceCandidateError(pc, err);
            }
        );
    trace(getName(pc) + ' ICE candidate: \n' + (event.candidate ?
        event.candidate.candidate : '(null)')); // log#: 23(pc1) // log#: 24(pc1) // log#: 25(pc1) // log#: 26(pc2) // log#: 27(pc2) // log#: 28(pc2) // log#: 42(pc2) // log#: 44(pc1)
}

function onAddIceCandidateSuccess(pc) {
    trace(getName(pc) + ' addIceCandidate success'); // log#: 29(pc1) // log#: 32(pc1) // log#: 33(pc1) // log#: 34(pc2) // log#: 37(pc2) // log#: 43(pc2) // log#: 45(pc1)
}

function onAddIceCandidateError(pc, error) {
    trace(getName(pc) + ' failed to add ICE Candidate: ' + error.toString());
}

function onIceStateChange(pc, event) {
    if (pc) {
        trace(getName(pc) + ' ICE state: ' + pc.iceConnectionState); // log#: 30(pc2) // log#: 35(pc1) // log#: 38(pc2) // log#: 40(pc1)
        console.log('ICE state change event: ', event); // log#: 31(pc2) // log#: 36(pc1) // log#: 39(pc2) // log#: 41(pc1)
    }
}

function hangup() {
    trace('Ending call'); // log#: 50
    pc1.close();
    pc2.close();
    pc1 = null;
    pc2 = null;
    hangupButton.disabled = true;
    callButton.disabled = false;
}

// logging utility
function trace(arg) {
    var now = (window.performance.now() / 1000).toFixed(3);
    console.log(now + ': ', arg);
}