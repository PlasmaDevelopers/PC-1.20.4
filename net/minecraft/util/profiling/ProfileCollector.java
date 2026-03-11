package net.minecraft.util.profiling;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;

public interface ProfileCollector extends ProfilerFiller {
  ProfileResults getResults();
  
  @Nullable
  ActiveProfiler.PathEntry getEntry(String paramString);
  
  Set<Pair<String, MetricCategory>> getChartedPaths();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ProfileCollector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */