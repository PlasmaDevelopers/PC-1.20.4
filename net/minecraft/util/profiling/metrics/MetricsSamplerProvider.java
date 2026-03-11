package net.minecraft.util.profiling.metrics;

import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.util.profiling.ProfileCollector;

public interface MetricsSamplerProvider {
  Set<MetricSampler> samplers(Supplier<ProfileCollector> paramSupplier);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\MetricsSamplerProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */