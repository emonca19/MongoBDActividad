/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package banco.itson.edu.actividadmongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author elimo
 */
public class ActividadMongo {

    public static void main(String[] args) {
        // URL de conexión a la base de datos MongoDB
        ConnectionString cadenaConexion = new ConnectionString("mongodb://localhost");

        MongoClientSettings clientsSettings = MongoClientSettings.builder()
                .applyConnectionString(cadenaConexion)
                .build();

        // Crea un cliente MongoDB utilizando los ajustes configurados
        MongoClient dbServer = MongoClients.create(clientsSettings);

        // Obtener una referencia a la base de datos "test"
        MongoDatabase database = dbServer.getDatabase("admin");

        // Obtener una referencia a la colección "personas"
        MongoCollection<Document> collection = database.getCollection("alumnos");

        // Lee todas las personas de la colección, ordénalas por edad y nombre, y filtra que sean mayores o igual a 18
        FindIterable<Document> filteredPersons = collection.find(new Document("edad", new Document("$gte", 18))).sort(new Document("edad", 1).append("nombre", 1));

        // Mostrar las personas en la consola
        System.out.println("Personas mayores de 18 ordenadas por edad y nombre:");
        for (Document person : filteredPersons) {
            System.out.println(person);
        }

//         Crea un documento en la colección
        Document nuevoDocumento = new Document("nombre", "Juan")
                .append("edad", 25)
                .append("ciudad", "Guaymas");
        collection.insertOne(nuevoDocumento);
        System.out.println("Documento insertado: " + nuevoDocumento);

        // Actualiza un documento (el que gustes)
        Document filtroActualizar = new Document("nombre", "Juan");
        Document actualizacion = new Document("$set", new Document("edad", 18).append("nombre", "Gustavo"));
        collection.updateOne(filtroActualizar, actualizacion);
        System.out.println("Documento actualizado: " + actualizacion);

        // Elimina un documento (el que gustes)
        Document filtroEliminar = new Document("nombre", "Gustavo");
        collection.deleteOne(filtroEliminar);
        System.out.println("Documento eliminado: " + filtroEliminar);

        // Cerrar la conexión con MongoDB
        dbServer.close();
    }
}
