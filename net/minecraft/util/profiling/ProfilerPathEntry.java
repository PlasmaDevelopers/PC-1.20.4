package net.minecraft.util.profiling;

import it.unimi.dsi.fastutil.objects.Object2LongMap;

public interface ProfilerPathEntry {
  long getDuration();
  
  long getMaxDuration();
  
  long getCount();
  
  Object2LongMap<String> getCounters();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ProfilerPathEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */