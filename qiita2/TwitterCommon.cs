using System;
using System.Collections.Generic;
//using LiteDB;
using SQLite;
internal static class TwitterCommon
{
    public static string GetTwitterDbPath()
    {
        return Environment.GetEnvironmentVariable("REPO") + @"\qiita2.db3";
    }

    public static SQLiteConnection GetTwitterDbConnection()
    {
        var connection = new SQLiteConnection(TwitterCommon.GetTwitterDbPath());
        connection.CreateTable<Article>();
        return connection;
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
