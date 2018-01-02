package de.jotschi.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class RestAPIVerticle extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(RestAPIVerticle.class);

	private String name = "dummy";

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		log.info("Starting verticle {" + this + "}");
		HttpServer server = vertx.createHttpServer();

		RouterStorage storage = new RouterStorage(vertx);

		// Test endpoint which returns some content
		storage.getSubRouter().route("/test").handler(rc -> {
			rc.response().putHeader("ContentType", "text/html")
					.end("<html><body><strong>hello world</strong><br/><a href='/" + name + "/rename?name=test'>rename</a></body></html>");
		});

		// Endpoint which can be used to rename the sub router mountpoint
		storage.getSubRouter().route("/rename").handler(rc -> {
			String name = rc.request().params().get("name");
			RouterStorage.update(name);
			this.name = name;
			rc.response().putHeader("ContentType", "text/html")
					.end("<html><body><strong>router renamed</strong><br/><a href='/" + name + "/test'>test</a></body></html>");
		});

		// start server
		server.requestHandler(storage.getRouter()::accept).listen(8888, lh -> {
			if (lh.failed()) {
				startFuture.fail(lh.cause());
			} else {
				startFuture.complete();
			}
		});

	}

}
