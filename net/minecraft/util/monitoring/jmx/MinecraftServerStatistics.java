/*     */ package net.minecraft.util.monitoring.jmx;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.JMException;
/*     */ import javax.management.MBeanAttributeInfo;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.ObjectName;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MinecraftServerStatistics
/*     */   implements DynamicMBean
/*     */ {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private final MinecraftServer server;
/*     */   
/*     */   private MinecraftServerStatistics(MinecraftServer $$0) {
/*  34 */     this
/*     */ 
/*     */       
/*  37 */       .attributeDescriptionByName = (Map<String, AttributeDescription>)Stream.<AttributeDescription>of(new AttributeDescription[] { new AttributeDescription("tickTimes", this::getTickTimes, "Historical tick times (ms)", long[].class), new AttributeDescription("averageTickTime", this::getAverageTickTime, "Current average tick time (ms)", long.class) }).collect(Collectors.toMap($$0 -> $$0.name, Function.identity()));
/*     */ 
/*     */     
/*  40 */     this.server = $$0;
/*     */ 
/*     */ 
/*     */     
/*  44 */     MBeanAttributeInfo[] $$1 = (MBeanAttributeInfo[])this.attributeDescriptionByName.values().stream().map(AttributeDescription::asMBeanAttributeInfo).toArray($$0 -> new MBeanAttributeInfo[$$0]);
/*     */     
/*  46 */     this.mBeanInfo = new MBeanInfo(MinecraftServerStatistics.class.getSimpleName(), "metrics for dedicated server", $$1, null, null, new javax.management.MBeanNotificationInfo[0]);
/*     */   }
/*     */   private final MBeanInfo mBeanInfo; private final Map<String, AttributeDescription> attributeDescriptionByName;
/*     */   public static void registerJmxMonitoring(MinecraftServer $$0) {
/*     */     try {
/*  51 */       ManagementFactory.getPlatformMBeanServer().registerMBean(new MinecraftServerStatistics($$0), new ObjectName("net.minecraft.server:type=Server"));
/*     */ 
/*     */     
/*     */     }
/*  55 */     catch (MalformedObjectNameException|javax.management.InstanceAlreadyExistsException|javax.management.MBeanRegistrationException|javax.management.NotCompliantMBeanException $$1) {
/*  56 */       LOGGER.warn("Failed to initialise server as JMX bean", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getAverageTickTime() {
/*  61 */     return this.server.getCurrentSmoothedTickTime();
/*     */   }
/*     */   
/*     */   private long[] getTickTimes() {
/*  65 */     return this.server.getTickTimesNanos();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object getAttribute(String $$0) {
/*  71 */     AttributeDescription $$1 = this.attributeDescriptionByName.get($$0);
/*  72 */     return ($$1 == null) ? 
/*  73 */       null : 
/*  74 */       $$1.getter.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(Attribute $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList getAttributes(String[] $$0) {
/*  85 */     Objects.requireNonNull(this.attributeDescriptionByName);
/*     */ 
/*     */     
/*  88 */     List<Attribute> $$1 = (List<Attribute>)Arrays.<String>stream($$0).map(this.attributeDescriptionByName::get).filter(Objects::nonNull).map($$0 -> new Attribute($$0.name, $$0.getter.get())).collect(Collectors.toList());
/*  89 */     return new AttributeList($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList setAttributes(AttributeList $$0) {
/*  95 */     return new AttributeList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object invoke(String $$0, Object[] $$1, String[] $$2) {
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public MBeanInfo getMBeanInfo() {
/* 107 */     return this.mBeanInfo;
/*     */   }
/*     */   
/*     */   private static final class AttributeDescription {
/*     */     final String name;
/*     */     final Supplier<Object> getter;
/*     */     private final String description;
/*     */     private final Class<?> type;
/*     */     
/*     */     AttributeDescription(String $$0, Supplier<Object> $$1, String $$2, Class<?> $$3) {
/* 117 */       this.name = $$0;
/* 118 */       this.getter = $$1;
/* 119 */       this.description = $$2;
/* 120 */       this.type = $$3;
/*     */     }
/*     */     
/*     */     private MBeanAttributeInfo asMBeanAttributeInfo() {
/* 124 */       return new MBeanAttributeInfo(this.name, this.type.getSimpleName(), this.description, true, false, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\monitoring\jmx\MinecraftServerStatistics.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */