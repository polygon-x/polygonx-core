package io.polygonx.core.service;

import io.polygonx.core.service.impl.ContestServiceImpl;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;

public interface ContestService {

  static ContestServiceImpl create(PgPool pgPool) {
    return new ContestServiceImpl(pgPool);
  }

  Future<JsonObject> getCurrentContest();
}
