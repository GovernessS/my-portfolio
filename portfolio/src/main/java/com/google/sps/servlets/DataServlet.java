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

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private ArrayList<String> funFacts = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      funFacts.add("I used to read the dictionary for fun!");
      funFacts.add("I considered going to the University of Madison (Better Dead Than Red)");
      funFacts.add("I spent over $100 on a planner in the 10th grade!");

    String json = convertToJson(funFacts);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

 /**
   * Converts a ServerStats instance into a JSON string using manual String concatentation.
   */
  private String convertToJson(ArrayList<String> funFacts) {
    String json = "{";
    json += "\"factOne\": ";
    json += "\"" + funFacts.get(0) + "\"";
    json += ", ";
    json += "\"factTwo\": ";
    json += "\"" + funFacts.get(1) + "\"";
    json += ", ";
    json += "\"factThree\": ";
    json += "\"" + funFacts.get(2) + "\"";
    json += "}";
    return json;
  }

}
