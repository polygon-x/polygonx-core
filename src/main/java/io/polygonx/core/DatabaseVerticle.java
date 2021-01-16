package io.polygonx.core;

import io.polygonx.core.service.AuthService;
import io.polygonx.core.service.impl.AuthServiceImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public class DatabaseVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("127.0.0.1")
      .setDatabase("polygonx")
      .setUser("postgres")
      .setPassword("postgres");

    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

    PgPool client = PgPool.pool(vertx, connectOptions, poolOptions);

    AuthServiceImpl authService = AuthService.create(client);

    vertx.eventBus().localConsumer("auth.login").handler(msg -> {
      authService.login((JsonObject) msg.body())
        .onSuccess(msg::reply)
        .onFailure(Throwable::printStackTrace);
    });

    vertx.eventBus().localConsumer("auth.register").handler(msg -> {
      authService.register((JsonObject) msg.body())
        .onSuccess(v -> msg.reply(true))
        .onFailure(e -> msg.reply(false));
    });
  }
}
