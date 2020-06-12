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

/** Creates an element that represents a task, including its delete button. */
function createTaskElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = 'comments';

  const titleElement = document.createElement('span');
  titleElement.innerText = comment.text;

  commentElement.appendChild(titleElement);
  return commentElement;
}