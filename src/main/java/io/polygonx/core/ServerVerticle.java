package io.polygonx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.validation.BadRequestException;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.builder.Bodies;
import io.vertx.json.schema.SchemaParser;
import io.vertx.json.schema.SchemaRouter;
import io.vertx.json.schema.SchemaRouterOptions;
import io.vertx.json.schema.common.dsl.Keywords;
import io.vertx.json.schema.common.dsl.Schemas;

import java.util.regex.Pattern;

public class ServerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    JWTAuthOptions options = new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS256")
        .setBuffer("polygonx"));

    JWTAuth provider = JWTAuth.create(vertx, options);

    Router router = Router.router(vertx);

    SchemaParser parser = SchemaParser.createDraft201909SchemaParser(
      SchemaRouter.create(vertx, new SchemaRouterOptions())
    );

    router.route()
      .produces("application/json; charset=UTF8")
      .handler(ResponseContentTypeHandler.create())
      .handler(BodyHandler.create())
      .handler(TimeoutHandler.create())
      .handler(LoggerHandler.create())
      .failureHandler(err -> {
        Throwable failure = err.failure();
        if (failure instanceof BadRequestException) {
          err.response().setStatusCode(400).end(failure.getMessage());
        } else {
          err.next();
        }
      })
      .failureHandler(ErrorHandler.create(vertx));

    router.post("/login")
      .handler(ValidationHandler.builder(parser).body(Bodies.json(Schemas.objectSchema()
        .requiredProperty("username", Schemas.stringSchema().with(Keywords.minLength(3), Keywords.maxLength(12)))
        .requiredProperty("password", Schemas.stringSchema().with(Keywords.minLength(6), Keywords.maxLength(16)))
        .allowAdditionalProperties(false)))
        .build())
      .handler(ctx -> {
        JsonObject body = ctx.getBodyAsJson();
        body.put("loginIP", ctx.request().remoteAddress().hostAddress());
        vertx.eventBus().request("auth.login", body)
          .onSuccess(msg -> {
            JsonObject obj = (JsonObject) msg.body();
            if (obj.getString("message") == null) {
              String access_token = provider.generateToken(new JsonObject() {{
                put("username", obj.getString("username"));
              }});
              obj.put("access_token", access_token);
              ctx.response().end(obj.toString());
            } else {
              ctx.response().setStatusCode(401).end(obj.toString());
            }
          })
          .onFailure(ctx::fail);
      });

    router.post("/register")
      .handler(ValidationHandler.builder(parser).body(Bodies.json(Schemas.objectSchema()
        .requiredProperty("username", Schemas.stringSchema().with(Keywords.minLength(3), Keywords.maxLength(12)))
        .requiredProperty("email", Schemas.stringSchema().with(Keywords.pattern(Pattern.compile("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"))))
        .requiredProperty("password", Schemas.stringSchema().with(Keywords.minLength(6), Keywords.maxLength(16)))
        .allowAdditionalProperties(false)))
        .build())
      .handler(ctx -> {
        JsonObject body = ctx.getBodyAsJson();
        vertx.eventBus().request("auth.register", body)
          .onSuccess(msg -> {
            Boolean res = (Boolean) msg.body();
            if (res) {
              ctx.response().setStatusCode(201).end();
            } else {
              ctx.response().setStatusCode(409).end(new JsonObject().put("message", "username or email already exist!").toString());
            }
          })
          .onFailure(ctx::fail);
      });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .onSuccess(v -> startPromise.complete())
      .onFailure(startPromise::fail);
  }
}
