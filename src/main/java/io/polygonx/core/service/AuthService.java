package io.polygonx.core.service;

import io.polygonx.core.service.impl.AuthServiceImpl;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;

public interface AuthService {

  static AuthServiceImpl create(PgPool pgPool) {
    return new AuthServiceImpl(pgPool);
  }

  Future<JsonObject> login(JsonObject loginRequest);

  Future<Void> register(JsonObject registerRequest);
}
