// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


/** Fetches comments from the server and adds them to the DOM. */
function loadComments() {
  fetch('/input').then(response => response.json()).then((comments) => {
    const commentsListElement = document.getElementById('comments-list');
    comments.forEach((comment) => {
      commentsListElement.appendChild(createTaskElement(comment));
    })
  });
}

var map;
function initMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 44.9740, lng: -93.2277}, zoom: 18});

  const trexMarker = new google.maps.Marker({
    position: {lat: 44.9740, lng: -93.2277},
    map: map,
    title: 'Go Gophers!'
  });

  const trexInfoWindow = new google.maps.InfoWindow({content: 'Go Gophers!'});
  trexInfoWindow.open(map, trexMarker);
}


/** Creates an element that represents a task, including its delete button. */
function createTaskElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = 'comments';

  const titleElement = document.createElement('span');
  titleElement.innerText = comment.text;

  commentElement.appendChild(titleElement);
  return commentElement;
}
