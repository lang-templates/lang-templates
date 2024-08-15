using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;

//using System.Windows.Forms;
using CommandLine;
using static Global.EasyObject;
namespace Exe;

class Options
{
    [Option('r', "reset", HelpText = "Reset repository", Required = false)]
    public bool ResetFlag
    {
        get;
        set;
    }
    [Option('f', "force", HelpText = "Force overwrite", Required = false)]
    public bool Force
    {
        get;
        set;
    }
    [Option('o', "output", HelpText = "<out dir>", Required = false)]
    public string OutputDir
    {
        get;
        set;
    }
    [Value(1, MetaName = "Others")]
    public IEnumerable<string> Others
    {
        get;
        set;
    }
}

public static class Program
{
    private static void Reset(Options opt, out List<DirectoryInfo> dirs, out Dictionary<string, string> dirDict)
    {
        string gitUrl = "https://github.com/lang-templates/lang-templates";
        string ltDir = ProfilePath(".lt");
        if (opt.ResetFlag)
        {
            //System.IO.Directory.Delete(ltDir, true);
            if (Directory.Exists(ltDir))
            {
                Explore(ltDir);
            }
            Environment.Exit(0);
        }
        //Log(ProfilePath());
        //Log(ltDir, "ltDir");
        bool ltDirExist = Directory.Exists(ltDir);
        //Log(ltDirExist, "ltDirExist");
        if (ltDirExist)
        {
            Log("Updating repository...");
            RunCommand("git.exe", new string[] { "pull" }, ltDir);
        }
        else
        {
            Log("Cloning repository...");
            RunCommand("git.exe", new string[] { "clone", "--recursive", gitUrl, ltDir });
        }
        //Explore(ltDir);
        var di = new DirectoryInfo(ltDir);
        dirs = di.GetDirectories().ToList();
        dirDict = new Dictionary<string, string>();
        foreach (var dir in dirs)
        {
            if (dir.Name == ".git") continue;
            //Log(dir.Name);
            //Log(dir.FullName);
            dirDict[dir.Name] = dir.FullName;
            //Echo(dir);
        }
    }
    [STAThread]
    public static void Main(string[] args)
    {
        try
        {
            List<DirectoryInfo> dirs;
            Dictionary<string, string> dirDict;
            var parseResult = Parser.Default.ParseArguments<Options>(args);
            switch (parseResult.Tag)
            {
                // パース成功
                case ParserResultType.Parsed:
                    {
                        var parsed = parseResult as Parsed<Options>;
                        Options opt = parsed.Value;
                        Reset(opt, out dirs, out dirDict);
                        //Log(opt.OutputDir, "opt.OutputDir");
                        //Log(opt.Force, "opt.Force");
                        if (opt.Others.Count() == 0)
                        {
                            foreach (var dir in dirs)
                            {
                                if (dir.Name == ".git") continue;
                                Echo(dir.Name);
                            }
                            return;
                        }
                        else if (opt.Others.Count() != 1)
                        {
                            throw new Exception("Please specify one template name.");
                        }
                        //Log(opt.Others.ElementAt(0), "opt.Others.ElementAt(0)");
                        //Log(opt.Others);
                        string name = opt.Others.ElementAt(0);
                        //Log(name, "name");
                        if (!dirDict.ContainsKey(name))
                        {
                            throw new Exception(String.Format("'{0}' not found", name));
                        }
                        if (opt.OutputDir == null) opt.OutputDir = String.Format(".\\{0}", name);
                        opt.OutputDir = Path.GetFullPath(opt.OutputDir);
                        Log(opt.OutputDir, "opt.OutputDir");
                        if (!opt.Force && new DirectoryInfo(opt.OutputDir).Exists)
                        {
                            throw new Exception(String.Format("'{0}' already exists; please use -f to overwrite.", opt.OutputDir));
                        }
                        Directory.CreateDirectory(opt.OutputDir);
                        //CopyFilesRecursively(dirDict[name], opt.OutputDir);
                        _ReplaceFilesRecursively(Path.GetFileName(opt.OutputDir), dirDict[name], opt.OutputDir);
                    }
                    break;
                // パース失敗
                case ParserResultType.NotParsed:
                    {
                        // パースの成否でパース結果のオブジェクトの方が変わる
                        //var notParsed = parseResult as NotParsed<Options>;
                        //Console.Error.WriteLine(CommandLine.Text.HelpText.AutoBuild(parseResult));
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            //Log(e.ToString());
            //Message(e.ToString(), "Exception");
            Console.Error.WriteLine(e.ToString());
        }
    }
    public static int RunCommand(string exePath, string[] args, string? cwd = null, Dictionary<string, string>? vars = null)
    {
        string argList = "";
        for (int i = 0; i < args.Length; i++)
        {
            if (i > 0) argList += " ";
            if (args[i].Contains(" "))
                argList += $"\"{args[i]}\"";
            else
                argList += args[i];
        }
        Process process = new Process();
        process.StartInfo.RedirectStandardOutput = true;
        process.StartInfo.RedirectStandardError = true;
        process.StartInfo.UseShellExecute = false;
        process.StartInfo.CreateNoWindow = true;
        process.StartInfo.FileName = exePath;
        process.StartInfo.Arguments = argList;
        if (cwd != null) process.StartInfo.WorkingDirectory = cwd;
        if (vars != null)
        {
            var keys = vars.Keys;
            foreach (var key in keys)
            {
                process.StartInfo.EnvironmentVariables[key] = vars[key];
            }
        }
        process.OutputDataReceived += (sender, e) => { Console.Error.WriteLine("[stdout] {0}", e.Data); };
        process.ErrorDataReceived += (sender, e) => { Console.Error.WriteLine("[stderr] {0}", e.Data); };
        process.Start();
        Console.CancelKeyPress += delegate (object sender, ConsoleCancelEventArgs e) { process.Kill(); };
        process.BeginOutputReadLine();
        process.BeginErrorReadLine();
        process.WaitForExit();
        process.CancelOutputRead();
        process.CancelErrorRead();
        return process.ExitCode;
    }
    public static void Explore(string dir)
    {
        System.Diagnostics.Process.Start("explorer.exe", dir);
    }
    public static string SpecialFolderPath(Environment.SpecialFolder folder)
    {
        return System.Environment.GetFolderPath(folder);
    }
    public static string ProfilePath()
    {
        string baseFolder = SpecialFolderPath(Environment.SpecialFolder.UserProfile);
        return baseFolder;
    }
    public static string ProfilePath(string orgName, string appName)
    {
        string baseFolder = SpecialFolderPath(Environment.SpecialFolder.UserProfile);
        return $"{baseFolder}{Path.DirectorySeparatorChar}{orgName}{Path.DirectorySeparatorChar}{appName}";
    }
    public static string ProfilePath(string appName)
    {
        string baseFolder = SpecialFolderPath(Environment.SpecialFolder.UserProfile);
        return $"{baseFolder}{Path.DirectorySeparatorChar}{appName}";
    }
    public static void CopyFilesRecursively(string sourcePath, string targetPath)
    {
        //Now Create all of the directories
        foreach (string dirPath in Directory.GetDirectories(sourcePath, "*", SearchOption.AllDirectories))
        {
            Directory.CreateDirectory(dirPath.Replace(sourcePath, targetPath));
        }

        //Copy all the files & Replaces any files with the same name
        foreach (string newPath in Directory.GetFiles(sourcePath, "*.*", SearchOption.AllDirectories))
        {
            File.Copy(newPath, newPath.Replace(sourcePath, targetPath), true);
        }
    }
    public static bool IsBinary(byte[] bytes)
    {
        bool isBinary = false;
        for (int i = 0; i < Math.Min(bytes.Length, 8000); i++)
        {
            if (bytes[i] == 0)
            {
                isBinary = true;
                break;
            }
        }
        return isBinary;
    }
    public static void Prepare(string dirPath)
    {
        //Echo(dirPath, "Prepare(dirPath)");
        Directory.CreateDirectory(dirPath);
    }
    public static void PrepareForFile(string filePath)
    {
        //Echo(filePath, "PrepareForFile(filePath)");
        Prepare(Path.GetDirectoryName(filePath)!);
    }
    public static void _ReplaceFilesRecursively(string programId, string sourcePath, string targetPath)
    {
        //Now Create all of the directories
        foreach (string dirPath in Directory.GetDirectories(sourcePath, "*", SearchOption.AllDirectories))
        {
            Directory.CreateDirectory(dirPath.Replace(sourcePath, targetPath));
        }

        //Copy all the files & Replaces any files with the same name
        foreach (string newPath in Directory.GetFiles(sourcePath, "*.*", SearchOption.AllDirectories))
        {
            //File.Copy(newPath, newPath.Replace(sourcePath, targetPath), true);
            byte[] bytes = File.ReadAllBytes(newPath);
            if (!IsBinary(bytes))
            {
                string text = Encoding.UTF8.GetString(bytes);
                text = text.Replace("PROGRAM", programId);
                bytes = Encoding.UTF8.GetBytes(text);
            }
            string relPath = GetRelativePath(sourcePath, newPath);
            string writePath = Path.Combine(targetPath, relPath);
            PrepareForFile(writePath);
            File.WriteAllBytes(writePath, bytes);
        }
    }
    /// <summary>
    /// Creates a relative path from one file or folder to another.
    /// </summary>
    /// <param name="fromPath">Contains the directory that defines the start of the relative path.</param>
    /// <param name="toPath">Contains the path that defines the endpoint of the relative path.</param>
    /// <returns>The relative path from the start directory to the end path.</returns>
    /// <exception cref="ArgumentNullException"><paramref name="fromPath" /> or <paramref name="toPath" /> is <c>null</c>.</exception>
    /// <exception cref="UriFormatException"></exception>
    /// <exception cref="InvalidOperationException"></exception>
    public static string GetRelativePath(string fromPath, string toPath)
    {
        // https://stackoverflow.com/questions/275689/how-to-get-relative-path-from-absolute-path
        if (string.IsNullOrEmpty(fromPath))
        {
            throw new ArgumentNullException("fromPath");
        }
        if (string.IsNullOrEmpty(toPath))
        {
            throw new ArgumentNullException("toPath");
        }
        Uri fromUri = new Uri(AppendDirectorySeparatorChar(fromPath));
        Uri toUri = new Uri(AppendDirectorySeparatorChar(toPath));
        if (fromUri.Scheme != toUri.Scheme)
        {
            return toPath;
        }
        Uri relativeUri = fromUri.MakeRelativeUri(toUri);
        string relativePath = Uri.UnescapeDataString(relativeUri.ToString());
        if (string.Equals(toUri.Scheme, Uri.UriSchemeFile, StringComparison.OrdinalIgnoreCase))
        {
            relativePath = relativePath.Replace(Path.AltDirectorySeparatorChar, Path.DirectorySeparatorChar);
        }
        return relativePath;
    }
    private static string AppendDirectorySeparatorChar(string path)
    {
        // Append a slash only if the path is a directory and does not have a slash.
        if (!Path.HasExtension(path) &&
            !path.EndsWith(Path.DirectorySeparatorChar.ToString()))
        {
            return path + Path.DirectorySeparatorChar;
        }
        return path;
    }
}
