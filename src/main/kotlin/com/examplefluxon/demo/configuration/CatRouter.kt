package com.examplefluxon.demo.configuration

import com.examplefluxon.demo.handler.CatHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration// определит поведение Spring
class CatRouter (var c:CatHandler){
    @Bean
    fun catRouter1()= router {
        accept(APPLICATION_JSON).nest {
            "/api".nest {
              //  http@ //localhost:8081/api/cat
                GET("/cat", c::getAll)
                POST("/cat", c::create)
                GET("/cat/{id}",c::getById)
                DELETE("/cat/{id}",c::deleteById)
                PUT("/cat/{id}",c::updateCat)
            }
        }
        }

}