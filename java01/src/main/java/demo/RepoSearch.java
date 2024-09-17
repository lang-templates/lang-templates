package demo;

import common.DirModel;
import system.Waiter;

public class RepoSearch {
    public static void main(String[] args) {
        var stopWatch = new org.apache.commons.lang3.time.StopWatch();
        stopWatch.start();
        var model = new DirModel("D:\\.repo\\base14");
        stopWatch.stop();
        System.out.println(stopWatch.formatTime());
        //var now = new org.joda.time.DateTime();
        //var dt = now.plusSeconds(5);
        Waiter.waitOnConsole(5000);
        var list = model.getPathList(true);
        list.stream()
                .forEach(System.out::println);
    }
}
