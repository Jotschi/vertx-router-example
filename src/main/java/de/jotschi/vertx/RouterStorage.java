package de.jotschi.vertx;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class RouterStorage {

	private static final Logger log = LoggerFactory.getLogger(RouterStorage.class);

	private Vertx vertx;
	private Router router;
	private Router subRouter;

	private static Set<RouterStorage> set = new HashSet<>();

	public RouterStorage(Vertx vertx) {
		this.vertx = vertx;
		set.add(this);
		setupRouters();
	}

	private void setupRouters() {
		getRouter().mountSubRouter("/dummy", getSubRouter());
	}

	public Router getSubRouter() {
		if (subRouter == null) {
			subRouter = Router.router(vertx);
		}
		return subRouter;
	}

	public Router getRouter() {
		if (router == null) {
			router = Router.router(vertx);
		}
		return router;
	}

	/**
	 * Iterate over all known router storages and update the subrouter mount.
	 * 
	 * @param name
	 */
	public static void update(String name) {
		log.info("Updating subrouter name to {" + name + "}");
		for (RouterStorage storage : set) {
			storage.getRouter().clear();
			storage.getRouter().mountSubRouter("/" + name, storage.getSubRouter());
		}

	}
}
