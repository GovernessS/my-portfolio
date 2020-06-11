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

function getFacts() {
  fetch('/data').then(response => response.json()).then((fun_facts) => {
    
    const fun_factsListElement = document.getElementById('facts-container');
    fun_factsListElement.innerHTML = '';
    fun_factsListElement.appendChild(
        createListElement('Fact One: ' + fun_facts.factOne));
    fun_factsListElement.appendChild(
        createListElement('Fact Two: ' + fun_facts.factTwo));
    fun_factsListElement.appendChild(
        createListElement('Fact Three: ' + fun_facts.factThree));
  });
}

  /** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
