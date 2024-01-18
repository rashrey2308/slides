package demo.app.slides

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
class SlidesApplication

fun main(args: Array<String>) {
//	servletRegistrationBean()
	runApplication<SlidesApplication>(*args)
}


