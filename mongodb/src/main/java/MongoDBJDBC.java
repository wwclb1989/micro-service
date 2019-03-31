import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBJDBC {
    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
            System.out.println("connection to mongodb success!");

//            mongoDatabase.createCollection("test");
//            System.out.println("集合创建成功！");

            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            System.out.println("集合选择test成功！");

            // 插入文档
//            Document document = new Document("title", "MongoDB").
//                    append("description", "database").
//                    append("likes", 100).
//                    append("by", "Fly");
//            List<Document> documents = new ArrayList<Document>();
//            documents.add(document);
//            collection.insertMany(documents);
//            System.out.println("文档插入成功");

            // 更新文档
//            collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));

            // 删除
            collection.deleteOne(Filters.eq("likes", 200));
//            collection.deleteMany(Filters.eq("likes", 200));

            // 遍历
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//喜欢你
//
//细雨带风湿透黄昏的街道
//抹去雨水双眼无故的仰望
//望向孤单的晚灯，是那伤感的记忆
//再次泛起心里无数的思念
//以往片刻欢笑仍挂在脸上
//愿你此刻可会知
//是我衷心地说声
//喜欢你，那双眼动人
//笑声更迷人
//愿再可，轻抚你
//那可爱面容
//挽手说梦话
//像昨天，你共我
//
//满带理想的我曾经多冲动
//抱怨与她相爱难有自由
//愿你此刻可会知
//是我衷心地说声
//喜欢你，那双眼动人
//笑声更迷人
//愿再可，轻抚你
//那可爱面容
//挽手说梦话
//像昨天，你共我
//
//每晚夜里自我独行
//随处荡，多冰冷
//以往为了自我挣扎
//从不知，她的痛苦
//喜欢你，那双眼动人
//笑声更迷人
//愿再可，轻抚你
//那可爱面容
//挽手说梦话
//像昨天，你共我
