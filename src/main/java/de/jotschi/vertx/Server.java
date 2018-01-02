package de.jotschi.vertx;

import io.vertx.core.Vertx;

public class Server {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		for (int i = 0; i < 10; i++) {
			vertx.deployVerticle(new RestAPIVerticle());
		}
	}

}
