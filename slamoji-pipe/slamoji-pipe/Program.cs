using System;
using System.IO;
using System.Text;
using Dapper;
using MySql.Data.MySqlClient;
using Newtonsoft.Json;

namespace slamoji_pipe{
  
  class User{
    public int id { get; set; }
    public string nickname { get; set; }
    public string login_name { get; set; }
    public string pass_hash { get; set; }
  }

  class TestModel{
    public int x { get; set; }
    public int y { get; set; }
  }
  
  class Program{
    static void Main(string[] args){
      Console.WriteLine("Hello World!");
      
      StreamReader sr = new StreamReader("../../config.json", Encoding.UTF8);
      string text = sr.ReadToEnd();
      sr.Close();
      
      TestModel model = JsonConvert.DeserializeObject<TestModel>(text, new JsonSerializerSettings { DefaultValueHandling = DefaultValueHandling.Populate });
      
      Console.WriteLine(model.x);
      Console.WriteLine(model.y);
      
      string server = "localhost";
      string user = "root";
      string pass = "root";
      string database = "isucon2018";
      string connectionString = string.Format("Server={0};Database={1};Uid={2};Pwd={3}", server, database, user, pass);

      try{
        MySqlConnection connection = new MySqlConnection(connectionString);
        connection.Open();

        Console.WriteLine("MySQLに接続しました！");
        
        var users = connection.Query<User>("SELECT * FROM users limit @limit", new { limit = 10 });
        
        foreach (User hoge in users) {
          Console.WriteLine(hoge.id);
          Console.WriteLine(hoge.nickname);
        }

        connection.Close();
      }catch (MySqlException me){
        Console.WriteLine("ERROR: " + me.Message);
      }
      
    }
  }
}
