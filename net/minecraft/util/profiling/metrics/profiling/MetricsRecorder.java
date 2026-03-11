package net.minecraft.util.profiling.metrics.profiling;

import net.minecraft.util.profiling.ProfilerFiller;

public interface MetricsRecorder {
  void end();
  
  void cancel();
  
  void startTick();
  
  boolean isRecording();
  
  ProfilerFiller getProfiler();
  
  void endTick();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\profiling\MetricsRecorder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */