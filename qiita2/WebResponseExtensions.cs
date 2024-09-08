using System.Net;
using System.Reflection;
public static class WebResponseExtensions
{
    public static HttpWebResponse __GetResponse(this WebClient client)
    {
        FieldInfo responseField = client.GetType().GetField("m_WebResponse", BindingFlags.Instance | BindingFlags.NonPublic);
        if (responseField != null)
        {
            HttpWebResponse response = responseField.GetValue(client) as HttpWebResponse;
            return response;
        }
        return null;
    }
}
