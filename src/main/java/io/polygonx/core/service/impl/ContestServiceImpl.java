package io.polygonx.core.service.impl;

import io.polygonx.core.service.ContestService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;

public class ContestServiceImpl implements ContestService {

  private final PgPool pgPool;

  public ContestServiceImpl(PgPool pgPool) {
    this.pgPool = pgPool;
  }

  @Override
  public Future<JsonObject> getCurrentContest() {
    Promise<JsonObject> promise = Promise.promise();
    pgPool.preparedQuery("select id, penalty, scoreboard_freeze_duration, name, short_name, start_time, end_time from public.contest where current_timestamp between start_time and end_time limit 1")
      .execute()
      .onSuccess(r -> {
        Row row = r.iterator().next();
        JsonObject contest = new JsonObject();
        r.columnsNames().forEach(c -> contest.put(c, row.getValue(c)));
        promise.complete(contest);
      })
      .onFailure(promise::fail);
    return promise.future();
  }
}
