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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.input.Comments;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/input")
public class DataServlet extends HttpServlet {
  private List<String> comments = new ArrayList<String>();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "text-input", "");
    long timestamp = System.currentTimeMillis();

    comments.add(text);

    // Send information to be written to Datastore
    persistComments(text, timestamp);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println(comments);

    response.sendRedirect("/contact.html");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("UserComments").addSort("timestamp", SortDirection.DESCENDING);
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(readComments(query)));
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  private void persistComments(String text, long timestamp) {
    Entity taskEntity = new Entity("UserComments");
    taskEntity.setProperty("Text", text);
    taskEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);
  }

  private List readComments(Query query) {
    List<Comments> comments = new ArrayList<>();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String message = (String) entity.getProperty("Text");
      long timestamp = (long) entity.getProperty("timestamp");

      Comments comment = new Comments(id, message, timestamp);
      comments.add(comment);
    }

    return comments;
  }
}
