import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBManager {

    private MongoCollection<Document> matchesCollection;
    private MongoCollection<Document> teamsCollection;


    public MongoDBManager() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("ballitChampions");
        matchesCollection = database.getCollection("matches");
        teamsCollection = database.getCollection("teams");

    }

    public void saveMatchResult(String team1, String team2, String winner, int blotsFirst, int plifsFirst, int advrunghsFirt, int pointsFirts, int blotsSecond, int plifSecond, int advrunghsSecond, int pointsSecond) {
        Document matches = new Document("team1", team1)
                .append("team2", team2)
                .append("winner", winner)
                .append("blotsFirst", blotsFirst)
                .append("plifsFirst", plifsFirst)
                .append("advrunghsFirts", advrunghsFirt)
                .append("pointsFirst", pointsFirts)
                .append("blotsSecond", blotsSecond)
                .append("plifsSecond", plifSecond)
                .append("advrunghsSecond", advrunghsSecond)
                .append("pointsSecond", pointsSecond);
        matchesCollection.insertOne(matches);
    }

    public void registrationMongo(String teamName, String warCry, int foundedYear){
        Document teams = new Document("team", teamName)
                .append("warCry", warCry)
                .append("foundedYear", foundedYear);
        teamsCollection.insertOne(teams);

    }
}
