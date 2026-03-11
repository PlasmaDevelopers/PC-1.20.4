/*     */ package net.minecraft;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import org.slf4j.Logger;
/*     */ import oshi.SystemInfo;
/*     */ import oshi.hardware.CentralProcessor;
/*     */ import oshi.hardware.GlobalMemory;
/*     */ import oshi.hardware.GraphicsCard;
/*     */ import oshi.hardware.HardwareAbstractionLayer;
/*     */ import oshi.hardware.PhysicalMemory;
/*     */ import oshi.hardware.VirtualMemory;
/*     */ 
/*     */ public class SystemReport {
/*     */   public static final long BYTES_PER_MEBIBYTE = 1048576L;
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final long ONE_GIGA = 1000000000L;
/*  25 */   private static final String OPERATING_SYSTEM = System.getProperty("os.name") + " (" + System.getProperty("os.name") + ") version " + System.getProperty("os.arch");
/*  26 */   private static final String JAVA_VERSION = System.getProperty("java.version") + ", " + System.getProperty("java.version");
/*  27 */   private static final String JAVA_VM_VERSION = System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.name") + "), " + System.getProperty("java.vm.info");
/*     */   
/*  29 */   private final Map<String, String> entries = Maps.newLinkedHashMap();
/*     */   
/*     */   public SystemReport() {
/*  32 */     setDetail("Minecraft Version", SharedConstants.getCurrentVersion().getName());
/*  33 */     setDetail("Minecraft Version ID", SharedConstants.getCurrentVersion().getId());
/*  34 */     setDetail("Operating System", OPERATING_SYSTEM);
/*  35 */     setDetail("Java Version", JAVA_VERSION);
/*  36 */     setDetail("Java VM Version", JAVA_VM_VERSION);
/*     */     
/*  38 */     setDetail("Memory", () -> {
/*     */           Runtime $$0 = Runtime.getRuntime();
/*     */           
/*     */           long $$1 = $$0.maxMemory();
/*     */           
/*     */           long $$2 = $$0.totalMemory();
/*     */           long $$3 = $$0.freeMemory();
/*     */           long $$4 = $$1 / 1048576L;
/*     */           long $$5 = $$2 / 1048576L;
/*     */           long $$6 = $$3 / 1048576L;
/*     */           return "" + $$3 + " bytes (" + $$3 + " MiB) / " + $$6 + " bytes (" + $$2 + " MiB) up to " + $$5 + " bytes (" + $$1 + " MiB)";
/*     */         });
/*  50 */     setDetail("CPUs", () -> String.valueOf(Runtime.getRuntime().availableProcessors()));
/*     */     
/*  52 */     ignoreErrors("hardware", () -> putHardware(new SystemInfo()));
/*     */     
/*  54 */     setDetail("JVM Flags", () -> {
/*     */           List<String> $$0 = Util.getVmArguments().collect((Collector)Collectors.toList());
/*     */           return String.format(Locale.ROOT, "%d total; %s", new Object[] { Integer.valueOf($$0.size()), String.join(" ", (Iterable)$$0) });
/*     */         });
/*     */   }
/*     */   
/*     */   public void setDetail(String $$0, String $$1) {
/*  61 */     this.entries.put($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setDetail(String $$0, Supplier<String> $$1) {
/*     */     try {
/*  66 */       setDetail($$0, $$1.get());
/*  67 */     } catch (Exception $$2) {
/*  68 */       LOGGER.warn("Failed to get system info for {}", $$0, $$2);
/*  69 */       setDetail($$0, "ERR");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void putHardware(SystemInfo $$0) {
/*  74 */     HardwareAbstractionLayer $$1 = $$0.getHardware();
/*  75 */     ignoreErrors("processor", () -> putProcessor($$0.getProcessor()));
/*  76 */     ignoreErrors("graphics", () -> putGraphics($$0.getGraphicsCards()));
/*  77 */     ignoreErrors("memory", () -> putMemory($$0.getMemory()));
/*     */   }
/*     */   
/*     */   private void ignoreErrors(String $$0, Runnable $$1) {
/*     */     try {
/*  82 */       $$1.run();
/*  83 */     } catch (Throwable $$2) {
/*  84 */       LOGGER.warn("Failed retrieving info for group {}", $$0, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void putPhysicalMemory(List<PhysicalMemory> $$0) {
/*  89 */     int $$1 = 0;
/*  90 */     for (PhysicalMemory $$2 : $$0) {
/*  91 */       String $$3 = String.format(Locale.ROOT, "Memory slot #%d ", new Object[] { Integer.valueOf($$1++) });
/*  92 */       setDetail($$3 + "capacity (MB)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getCapacity() / 1048576.0F) }));
/*  93 */       setDetail($$3 + "clockSpeed (GHz)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getClockSpeed() / 1.0E9F) }));
/*  94 */       Objects.requireNonNull($$2); setDetail($$3 + "type", $$2::getMemoryType);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void putVirtualMemory(VirtualMemory $$0) {
/*  99 */     setDetail("Virtual memory max (MB)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getVirtualMax() / 1048576.0F) }));
/* 100 */     setDetail("Virtual memory used (MB)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getVirtualInUse() / 1048576.0F) }));
/* 101 */     setDetail("Swap memory total (MB)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getSwapTotal() / 1048576.0F) }));
/* 102 */     setDetail("Swap memory used (MB)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getSwapUsed() / 1048576.0F) }));
/*     */   }
/*     */   
/*     */   private void putMemory(GlobalMemory $$0) {
/* 106 */     ignoreErrors("physical memory", () -> putPhysicalMemory($$0.getPhysicalMemory()));
/* 107 */     ignoreErrors("virtual memory", () -> putVirtualMemory($$0.getVirtualMemory()));
/*     */   }
/*     */   
/*     */   private void putGraphics(List<GraphicsCard> $$0) {
/* 111 */     int $$1 = 0;
/* 112 */     for (GraphicsCard $$2 : $$0) {
/* 113 */       String $$3 = String.format(Locale.ROOT, "Graphics card #%d ", new Object[] { Integer.valueOf($$1++) });
/* 114 */       Objects.requireNonNull($$2); setDetail($$3 + "name", $$2::getName);
/* 115 */       Objects.requireNonNull($$2); setDetail($$3 + "vendor", $$2::getVendor);
/* 116 */       setDetail($$3 + "VRAM (MB)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getVRam() / 1048576.0F) }));
/* 117 */       Objects.requireNonNull($$2); setDetail($$3 + "deviceId", $$2::getDeviceId);
/* 118 */       Objects.requireNonNull($$2); setDetail($$3 + "versionInfo", $$2::getVersionInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void putProcessor(CentralProcessor $$0) {
/* 123 */     CentralProcessor.ProcessorIdentifier $$1 = $$0.getProcessorIdentifier();
/*     */     
/* 125 */     Objects.requireNonNull($$1); setDetail("Processor Vendor", $$1::getVendor);
/* 126 */     Objects.requireNonNull($$1); setDetail("Processor Name", $$1::getName);
/* 127 */     Objects.requireNonNull($$1); setDetail("Identifier", $$1::getIdentifier);
/* 128 */     Objects.requireNonNull($$1); setDetail("Microarchitecture", $$1::getMicroarchitecture);
/* 129 */     setDetail("Frequency (GHz)", () -> String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$0.getVendorFreq() / 1.0E9F) }));
/*     */     
/* 131 */     setDetail("Number of physical packages", () -> String.valueOf($$0.getPhysicalPackageCount()));
/* 132 */     setDetail("Number of physical CPUs", () -> String.valueOf($$0.getPhysicalProcessorCount()));
/* 133 */     setDetail("Number of logical CPUs", () -> String.valueOf($$0.getLogicalProcessorCount()));
/*     */   }
/*     */   
/*     */   public void appendToCrashReportString(StringBuilder $$0) {
/* 137 */     $$0.append("-- ").append("System Details").append(" --\n");
/* 138 */     $$0.append("Details:");
/* 139 */     this.entries.forEach(($$1, $$2) -> {
/*     */           $$0.append("\n\t");
/*     */           $$0.append($$1);
/*     */           $$0.append(": ");
/*     */           $$0.append($$2);
/*     */         });
/*     */   }
/*     */   
/*     */   public String toLineSeparatedString() {
/* 148 */     return this.entries.entrySet().stream()
/* 149 */       .map($$0 -> (String)$$0.getKey() + ": " + (String)$$0.getKey())
/* 150 */       .collect(Collectors.joining(System.lineSeparator()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\SystemReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */