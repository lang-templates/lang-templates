package demo;

import system.Waiter;

public class RepoSearch {
    public static void main(String[] args) {
        var stopWatch = new org.apache.commons.lang3.time.StopWatch();
        stopWatch.start();
        var model = new DirModel("D:\\.repo\\base14");
        stopWatch.stop();
        System.out.println(stopWatch.formatTime());
        var now = new org.joda.time.DateTime();
        var dt = now.plusSeconds(5);
        Waiter.waitOnConsole(dt);
        var list = model.getPathList();
        list.stream()
                .forEach(System.out::println);
    }
}
