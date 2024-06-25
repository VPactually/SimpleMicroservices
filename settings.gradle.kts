rootProject.name = "spring-micro-demo"

includeBuild("eureka-server")
includeBuild("order-service")
includeBuild("payment-service")
includeBuild("restaurant-service")
includeBuild("delivery-service")
includeBuild("api-gateway")
