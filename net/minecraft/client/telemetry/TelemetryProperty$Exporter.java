package net.minecraft.client.telemetry;

import com.mojang.authlib.minecraft.TelemetryPropertyContainer;

public interface Exporter<T> {
  void apply(TelemetryPropertyContainer paramTelemetryPropertyContainer, String paramString, T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryProperty$Exporter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */