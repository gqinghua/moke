/*
 * Copyright (c) 2014 Red Hat, Inc. and others
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.moke.vas.main.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link com.moke.vas.main.entity.User}.
 *
 * NOTE: This class has been automatically generated from the {@link com.moke.vas.main.entity.User} original class using Vert.x codegen.
 */
public class UserConverter {

  public static void fromJson(JsonObject json, User obj) {
    if (json.getValue("age") instanceof Number) {
      obj.setAge(((Number)json.getValue("age")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("phone") instanceof String) {
      obj.setPhone((String)json.getValue("phone"));
    }
    if (json.getValue("role") instanceof Number) {
      obj.setRole(((Number)json.getValue("role")).longValue());
    }
    if (json.getValue("testType") instanceof Number) {
      obj.setTestType(((Number)json.getValue("testType")).intValue());
    }
  }

  public static void toJson(User obj, JsonObject json) {
    if (obj.getAge() != null) {
      json.put("age", obj.getAge());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPhone() != null) {
      json.put("phone", obj.getPhone());
    }
    if (obj.getRole() != null) {
      json.put("role", obj.getRole());
    }
    if (obj.getTestType() != null) {
      json.put("testType", obj.getTestType());
    }
  }
}