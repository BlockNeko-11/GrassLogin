package io.github.blockneko11.grasslogin.schedule;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.util.Lazy;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public final class Tasks {
    public static final long TICK_INTERVAL = 20L;
    private static final List<BukkitTask> tasks = new ArrayList<>();
    private static final Lazy<AuthMessageTask> authMessage = Lazy.of(AuthMessageTask::new);

    public static void runAll() {
        tasks.add(GrassLoginPlugin.getScheduler().runTaskTimer(
                GrassLoginPlugin.getInstance(),
                authMessage.get(),
                0,
                TICK_INTERVAL * 5
        ));
    }

    public static void cancelAll() {
        tasks.forEach(BukkitTask::cancel);
        tasks.clear();
    }
}
