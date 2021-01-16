package io.polygonx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    CompositeFuture.all(
      vertx.deployVerticle("io.polygonx.core.ServerVerticle"),
      vertx.deployVerticle("io.polygonx.core.DatabaseVerticle")
    )
      .onSuccess(ar -> startPromise.complete())
      .onFailure(startPromise::fail);
  }
}
