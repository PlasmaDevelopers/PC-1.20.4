package net.minecraft.client.resources.server;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.server.packs.DownloadQueue;

public interface PackDownloader {
  void download(Map<UUID, DownloadQueue.DownloadRequest> paramMap, Consumer<DownloadQueue.BatchResult> paramConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\PackDownloader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */