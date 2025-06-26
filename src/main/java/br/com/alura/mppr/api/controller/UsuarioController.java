package br.com.alura.mppr.api.controller;

import java.util.Collections;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api")
public class UsuarioController {

    // Injeta o MongoTemplate para interagir com o banco de dados
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("teste")
    public String teste() {
        return "Bem vindo a API";
    }

    @RequestMapping("usuario")
    public String usuario() {
        return "Pegou um usuario";
    }
    
    @GetMapping("/status-mongo")
    public ResponseEntity<Map<String, String>> verificarStatusMongo() {
        try {
            // Executa o comando 'ping' como um Document BSON.
            // O comando { "ping": 1 } é o padrão do MongoDB para verificar a conexão.
            mongoTemplate.getDb().runCommand(new Document("ping", 1));

            Map<String, String> responseBody = Collections.singletonMap("status", "Conexão com o MongoDB está OK!");
            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            Map<String, String> errorBody = Collections.singletonMap("status", "Falha na conexão com o MongoDB: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }
}
