global using JavaCommons;
global using JavaCommons.Json;
global using JavaCommons.Json.Linq;
global using JavaCommons.Windows;
global using static JavaCommons.Util;
global using System.Linq;

using System;
using System.Collections.Specialized;
using System.Net;

internal static class Global
{
    static Global()
    {
        Util.Log("Initializing Global.");
    }
    public static string QIITA_TOKEN = null;
    public static QiitaAPI QIITA_API = new QiitaAPI();
}

internal class QiitaAPI
{
    internal class Status
    {
        //public HttpStatusCode status { get; set; }
        public int status_code { get; set; }
        public string status_name { get; set; }
        public string status_desc { get; set; }
        public long rate_remain { get; set; }
        public long rate_reset_epoch { get; set; }
        public DateTime rate_reset { get; set; }
        //public dynamic result { get; set; } = null;
    }
    internal dynamic Execute(out Status status2, string url, NameValueCollection query = null)
    {
        if (Global.QIITA_TOKEN == null)
        {
            throw new Exception("Please set Global.QIITA_TOKEN");
        }
        try
        {
            WebClient wc = new WebClient();
            wc.Encoding = System.Text.Encoding.UTF8;
            if (query != null) wc.QueryString = query;
            wc.Headers.Add("Authorization", "Bearer " + Global.QIITA_TOKEN);
            string json = wc.DownloadString(url);
            //var obj = Util.FromJson(json);
            status2 = new Status();
            SetResponceHeaders(status2, wc.__GetResponse());
            return Util.FromJson(json);
        }
        catch (WebException we)
        {
            status2 = new Status();
            SetResponceHeaders(status2, (HttpWebResponse)we.Response);
            return null;
        }
    }
    protected static void SetResponceHeaders(Status result, HttpWebResponse response)
    {
        long remain = long.Parse(response.GetResponseHeader("Rate-Remaining"));
        DateTime epoch = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
        DateTime resetUtc = epoch.AddSeconds(long.Parse(response.GetResponseHeader("Rate-Reset")));
        TimeZoneInfo jstZoneInfo = System.TimeZoneInfo.FindSystemTimeZoneById("Tokyo Standard Time");
        DateTime reset = TimeZoneInfo.ConvertTimeFromUtc(resetUtc, jstZoneInfo);
        TimeSpan span = reset - DateTime.Now;
        result.status_code = (int)response.StatusCode;
        result.status_name = response.StatusCode.ToString();
        result.status_desc = response.StatusDescription;
        result.rate_remain = remain;
        result.rate_reset_epoch = long.Parse(response.GetResponseHeader("Rate-Reset"));
        result.rate_reset = reset;
    }
}
