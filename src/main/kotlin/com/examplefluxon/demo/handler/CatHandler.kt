package com.examplefluxon.demo.handler

import com.examplefluxon.demo.model.Cat
import com.examplefluxon.demo.repository.CatMonRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono



//это объект чьим жизненным циклом,руковадит SPRING
//Это ззначит,SPRING создаст экземпляры класса и предоставит по месту требования
@Component
 public class CatHandler (var catRepository: CatMonRepository){
   fun getAll(serverRequest: ServerRequest): Mono<ServerResponse> {
       var cats: Flux<Cat> = catRepository.findAll()
       return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(cats)
   }
    fun getById(serverRequest: ServerRequest):Mono<ServerResponse> {
        var cat: Mono<Cat> = catRepository.findById(serverRequest.pathVariable("id"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(cat)
    }
    fun deleteById(serverRequest: ServerRequest):Mono<ServerResponse>{

      var cat2:Mono<Void> = catRepository.deleteById(serverRequest.pathVariable("id"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(cat2)
    }
    fun create(serverRequest: ServerRequest):Mono<ServerResponse>{
        var cat:Mono<Cat> = serverRequest.bodyToMono(Cat::class.java)
        val catMono = serverRequest.bodyToMono(Cat::class.java).flatMap({ cat -> catRepository.save(cat) })
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(catMono,Cat::class.java)
    }
    fun updateCat(serverRequest: ServerRequest):Mono<ServerResponse>{
        var newCat:Mono<Cat> = serverRequest.bodyToMono(Cat::class.java)
        return newCat.zipWith(catRepository.findById(serverRequest.pathVariable("id"))
                ,{newCat,OldCat->Cat(OldCat.id,newCat.name,newCat.age)})
                .flatMap (::saveAndRespond).switchIfEmpty(ServerResponse.notFound().build())

    }
    private fun saveAndRespond(cat: Cat) = ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(catRepository.save(cat), Cat::class.java)


}