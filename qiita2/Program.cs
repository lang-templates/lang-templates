using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Reflection;
using System.Threading;
//using LiteDB;
using SQLite;
//using static JavaCommons.Util;
using Sys = System;
//namespace CUI;
internal static class Program
{
    [STAThread]
    public static void Main(string[] args)
    {
        //redo:
        try
        {
            Sys::Console.WriteLine("using Sys::");
            Sys.Console.WriteLine("using Sys.");
            // created:2011-09-16
            DateTime dt = new DateTime(2023, 10, 20);
            Print(dt.ToString("yyyy-MM-dd"));
            DateTime startDt = new DateTime(2011, 9, 4);
            //WatitUntil(DateTime.Now.AddSeconds(5));
            //Print(Environment.Version.ToString(), "Runtime Version");
            //Print(Assembly.GetExecutingAssembly().GetName().Version.ToString(), "Assembly Version");
            //Print(args, "args");
            //Print(FullName(null));
            global::Global.QIITA_TOKEN = Environment.GetEnvironmentVariable("READ_QIITA");
            while (dt >= startDt)
            {
                ProcessDay(dt);
                dt = dt.AddDays(-1);
                Print(dt, "dt");
                WatitUntil(DateTime.Now.AddSeconds(5));
            }
            //goto redo;
#if false
            var db = TwitterCommon.GetTwitterDbConnection();
            using (db)
            {
                var @Article = TwitterCommon.colArticle(db);
                var all = @Article.FindAll().OrderBy(x => x.page_views_count).ToList();
                foreach (var x in all)
                {
                    Print(new { title = x.title, count = x.page_views_count });
                }
                Print(all.Count, "all.Count");
            }
#endif
        }
        catch (Exception e)
        {
            Log(e.ToString());
        }
    }

    private static void ProcessDay(DateTime dt)
    {
        string key = dt.ToString("yyyy-MM-dd");
        long i = 1;
        for (; ; )
        {
            QiitaAPI.Status status;
#if false
                var list = Global.QIITA_API.Execute(
                    out status,
                    "https://qiita.com/api/v2/authenticated_user/items",
                     new NameValueCollection
                     {
                         ["page"] = i.ToString(),
                         ["per_page"] = 20.ToString(),
                     });
#else
            var list = Global.QIITA_API.Execute(
                out status,
                "https://qiita.com/api/v2/items",
                 new NameValueCollection
                 {
                     ["page"] = i.ToString(),
                     ["per_page"] = 100.ToString(),
                     ["query"] = $"created:{key}"
                 });
#endif
            Print(status, "status");
            Print(status.status_name, "status.status_name");
            //if (status.status_name != "OK") break;
            if (status.status_name == "Forbidden" && status.rate_remain == 0)
            {
                WatitUntil(status.rate_reset);
                continue;
            }
            if (status.status_name != "OK") Environment.Exit(1);
            //Print(result, "result");
            //var list = result;
#if false
                var rate_reset = status.rate_reset;
                Print(rate_reset.Ticks, "rate_reset.Ticks");
                TimeSpan t = rate_reset - new DateTime(1970, 1, 1);
                long secondsSinceEpoch = (long)t.TotalSeconds;
                Print(secondsSinceEpoch, "secondsSinceEpoch");
                Print(FullName(list), "FullName(list)");
#endif
            if (list == null) Environment.Exit(1);
            Print(list.Count);
            //Print(list);
            long count = ProcessRequest(key, list);
            //long count = list.Count;
            Print(count, "count");
            if (count <= 0) break;
            i++;
        }
    }

    internal static void WatitUntil(DateTime dt)
    {
        Console.WriteLine($"{dt} までお待ちください。", dt);
        while (DateTime.Now < dt)
        {
            try
            {
                var span = dt - DateTime.Now;
                var sec = (long)span.TotalSeconds;
                Console.CursorLeft = 0;
                Console.Write($"後 {span.ToString(@"hh\:mm\:ss")} お待ちください。");
            }
            catch (Exception)
            {
            }
            Thread.Sleep(1000);
        }
        //Console.WriteLine();
        Console.WriteLine("・・・処理を再開します。");
        Thread.Sleep(1000);
    }
    internal static long ProcessRequest(string key, dynamic list)
    {
        var db = TwitterCommon.GetTwitterDbConnection();
        using (db)
        {
            //var @Article = TwitterCommon.colArticle(db);
            db.BeginTransaction();
            foreach (var x in list)
            {
                ((JObject)x).Remove("body");
                ((JObject)x).Remove("rendered_body");
                x.Remove("coediting");
                //Print(x);
                x.user = x.user.id;
                List<string> tagList = new List<string>();
                foreach (var tag in x.tags)
                {
                    tagList.Add((string)tag.name);
                }
                //x.tags = FromObject(tagList);
                x.tags = string.Join("|", tagList);
                Print(x);
                Print(x.title);
#if false
                string article_json = ToJson(x);
                var article = FromJson<Article>(article_json);
#else
                Article article = FromObject<Article>(x);
#endif
                article.key = key;
                Print(article);
                //Func<Article, bool> wrapper = (n => n.id == article.id);
                //var found = @Article.FindOne(x => x.id == article.id);
                var recordList = from s in db.Table<Article>()
                                 where s.id == article.id
                                 select s;
                if (recordList.Count() == 0)
                {
                    db.Insert(article);
                }
                else
                {
                    db.Update(article);
                }
            }
            db.Commit();
            return list.Count;
        }
    }
}

[JsonObject("Article")]
internal class Article
{
    [JsonProperty] [PrimaryKey]
    public string id { get; set; }
    [JsonProperty] [NotNull, Indexed]
    public string key { get; set; }
    [JsonProperty] [NotNull, Indexed]
    public string user { get; set; }
    [JsonProperty] [NotNull]
    public string url { get; set; }
    [JsonProperty] [NotNull]
    public string title { get; set; }
    [JsonProperty] [NotNull]
    public long comments_count { get; set; }
    [JsonProperty] [NotNull]
    public long likes_count { get; set; }
    [JsonProperty] [NotNull]
    public long stocks_count { get; set; }
    [JsonProperty] [NotNull, Indexed]
    public DateTime created_at { get; set; }
    [JsonProperty] [NotNull, Indexed]
    public DateTime updated_at { get; set; }
    [JsonProperty] [NotNull]
    public string tags { get; set; }
}
