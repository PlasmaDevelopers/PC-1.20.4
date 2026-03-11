package net.minecraft.util.eventlog;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import javax.annotation.Nullable;

public interface File {
  Path path();
  
  EventLogDirectory.FileId id();
  
  @Nullable
  Reader openReader() throws IOException;
  
  EventLogDirectory.CompressedFile compress() throws IOException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\EventLogDirectory$File.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */