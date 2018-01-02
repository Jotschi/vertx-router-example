# Vert.x Multi Verticle Router Example

This example shows how to apply the Multi-Reactor Pattern when using Vert.x for a REST API. Each of the 10 deployed verticles will setup their own router. Routers should and will not be shared by the verticles. Instead the `RouterStorage` class will manage all router instances and apply changes to all created routers.