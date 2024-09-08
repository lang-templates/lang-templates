using System;
using System.Collections.Generic;
using LiteDB;
internal static class TwitterCommon
{
    public static string GetTwitterDbPath()
    {
        return Environment.GetEnvironmentVariable("REPO") + @"\qiita.ldb";
        //return Environment.GetEnvironmentVariable("REPO") + @"\t.ldb";
    }

    public static LiteDatabase GetTwitterDbConnection()
    {
        return new LiteDatabase(TwitterCommon.GetTwitterDbPath());
    }
    public static ILiteCollection<Article> colArticle(LiteDatabase db)
    {
        return db.GetCollection<Article>("Article");

    }
    /*
    public static ILiteCollection<TweetCsv> colTweetCsv(LiteDatabase db)
    {
        return db.GetCollection<TweetCsv>("TweetCsv");
    }
    public static ILiteCollection<Tweet> colTweet(LiteDatabase db)
    {
        return db.GetCollection<Tweet>("Tweet");

    }
    public static ILiteCollection<UserName> colUserName(LiteDatabase db)
    {
        return db.GetCollection<UserName>("UserName");

    }
    public static dynamic GetUserName(LiteDatabase db, string userId)
    {
        var col = colUserName(db);
        var rec = col.FindOne(x => x.Id == userId);
        if (rec == null) return "?";
        return rec.DisplayName;
    }
    public static void SetUserName(LiteDatabase db, string userId, string userName)
    {
        var col = colUserName(db);
        try
        {
            col.Insert(new UserName
            {
                Id = userId,
                DisplayName = userName
            });
            col.EnsureIndex(x => x.Id);
        }
        catch { }
    }
    */
    /*
    public static SQLiteConnection GetTwitterDbMemory()
    {
        var connection = new SQLiteConnection(":memory:");
        connection.CreateTable<Setting>();
        connection.CreateTable<Tweet>();
        return connection;
    }
    */
}
