package io.polygonx.core.service.impl;

import io.polygonx.core.service.AuthService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class AuthServiceImpl implements AuthService {

  private final PgPool pgPool;

  public AuthServiceImpl(PgPool pgPool) {
    this.pgPool = pgPool;
  }

  @Override
  public Future<JsonObject> login(JsonObject loginRequest) {
    String username = loginRequest.getString("username");
    Promise<JsonObject> promise = Promise.promise();
    JsonObject object = new JsonObject();

    pgPool.preparedQuery("select password, enabled from public.user where username = $1")
      .execute(Tuple.of(username))
      .onSuccess(ar -> {
        if (ar.rowCount() > 0) {
          Row row = ar.iterator().next();
          String hashed = row.getString("password");
          String password = loginRequest.getString("password");
          if (BCrypt.checkpw(password, hashed)) {
            if (row.getBoolean("enabled")) {
              pgPool
                .withTransaction(sqlConnection ->
                  sqlConnection
                    .preparedQuery("update public.user set last_login_ip = $1, last_login_time = $2 where username = $3")
                    .execute(Tuple.of(loginRequest.getString("loginIP"), LocalDateTime.now(), username))
                )
                .onFailure(promise::fail);
            } else {
              object.put("message", "user is disabled now!");
            }
          } else {
            object.put("message", "username is not match password!");
          }
        } else {
          object.put("message", "username is not match password!");
        }
        promise.complete(object);
      })
      .onFailure(promise::fail);

    return promise.future();
  }

  @Override
  public Future<Void> register(JsonObject registerRequest) {
    String username = registerRequest.getString("username");
    String email = registerRequest.getString("email");
    String password = BCrypt.hashpw(registerRequest.getString("password"), BCrypt.gensalt());
    Promise<Void> promise = Promise.promise();

    pgPool.withTransaction(sqlConnection ->
      sqlConnection
        .preparedQuery("insert into public.user (username, email, password) values ($1, $2, $3)")
        .execute(Tuple.of(username, email, password))
        .onSuccess(v -> promise.complete())
        .onFailure(promise::fail)
    );
    return promise.future();
  }
}
