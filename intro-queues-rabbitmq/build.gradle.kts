plugins {
    id("java")
}

group = "com.rabbitmq.queues"
version = "unspecified"

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.0")
    implementation("org.springframework.boot:spring-boot-devtools:3.0.0")
    implementation("org.springframework.amqp:spring-rabbit:3.0.0")
    implementation("com.rabbitmq:amqp-client:5.9.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}