/*     */ package net.minecraft.world.scores.criteria;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ public class ObjectiveCriteria {
/*  16 */   private static final Map<String, ObjectiveCriteria> CUSTOM_CRITERIA = Maps.newHashMap();
/*  17 */   private static final Map<String, ObjectiveCriteria> CRITERIA_CACHE = Maps.newHashMap();
/*     */   
/*  19 */   public static final ObjectiveCriteria DUMMY = registerCustom("dummy");
/*  20 */   public static final ObjectiveCriteria TRIGGER = registerCustom("trigger");
/*  21 */   public static final ObjectiveCriteria DEATH_COUNT = registerCustom("deathCount");
/*  22 */   public static final ObjectiveCriteria KILL_COUNT_PLAYERS = registerCustom("playerKillCount");
/*  23 */   public static final ObjectiveCriteria KILL_COUNT_ALL = registerCustom("totalKillCount");
/*  24 */   public static final ObjectiveCriteria HEALTH = registerCustom("health", true, RenderType.HEARTS);
/*  25 */   public static final ObjectiveCriteria FOOD = registerCustom("food", true, RenderType.INTEGER);
/*  26 */   public static final ObjectiveCriteria AIR = registerCustom("air", true, RenderType.INTEGER);
/*  27 */   public static final ObjectiveCriteria ARMOR = registerCustom("armor", true, RenderType.INTEGER);
/*  28 */   public static final ObjectiveCriteria EXPERIENCE = registerCustom("xp", true, RenderType.INTEGER);
/*  29 */   public static final ObjectiveCriteria LEVEL = registerCustom("level", true, RenderType.INTEGER);
/*  30 */   public static final ObjectiveCriteria[] TEAM_KILL = new ObjectiveCriteria[] { 
/*  31 */       registerCustom("teamkill." + ChatFormatting.BLACK.getName()), registerCustom("teamkill." + ChatFormatting.DARK_BLUE.getName()), 
/*  32 */       registerCustom("teamkill." + ChatFormatting.DARK_GREEN.getName()), registerCustom("teamkill." + ChatFormatting.DARK_AQUA.getName()), 
/*  33 */       registerCustom("teamkill." + ChatFormatting.DARK_RED.getName()), registerCustom("teamkill." + ChatFormatting.DARK_PURPLE.getName()), 
/*  34 */       registerCustom("teamkill." + ChatFormatting.GOLD.getName()), registerCustom("teamkill." + ChatFormatting.GRAY.getName()), 
/*  35 */       registerCustom("teamkill." + ChatFormatting.DARK_GRAY.getName()), registerCustom("teamkill." + ChatFormatting.BLUE.getName()), 
/*  36 */       registerCustom("teamkill." + ChatFormatting.GREEN.getName()), registerCustom("teamkill." + ChatFormatting.AQUA.getName()), 
/*  37 */       registerCustom("teamkill." + ChatFormatting.RED.getName()), registerCustom("teamkill." + ChatFormatting.LIGHT_PURPLE.getName()), 
/*  38 */       registerCustom("teamkill." + ChatFormatting.YELLOW.getName()), registerCustom("teamkill." + ChatFormatting.WHITE.getName()) };
/*     */   
/*  40 */   public static final ObjectiveCriteria[] KILLED_BY_TEAM = new ObjectiveCriteria[] { 
/*  41 */       registerCustom("killedByTeam." + ChatFormatting.BLACK.getName()), registerCustom("killedByTeam." + ChatFormatting.DARK_BLUE.getName()), 
/*  42 */       registerCustom("killedByTeam." + ChatFormatting.DARK_GREEN.getName()), registerCustom("killedByTeam." + ChatFormatting.DARK_AQUA.getName()), 
/*  43 */       registerCustom("killedByTeam." + ChatFormatting.DARK_RED.getName()), registerCustom("killedByTeam." + ChatFormatting.DARK_PURPLE.getName()), 
/*  44 */       registerCustom("killedByTeam." + ChatFormatting.GOLD.getName()), registerCustom("killedByTeam." + ChatFormatting.GRAY.getName()), 
/*  45 */       registerCustom("killedByTeam." + ChatFormatting.DARK_GRAY.getName()), registerCustom("killedByTeam." + ChatFormatting.BLUE.getName()), 
/*  46 */       registerCustom("killedByTeam." + ChatFormatting.GREEN.getName()), registerCustom("killedByTeam." + ChatFormatting.AQUA.getName()), 
/*  47 */       registerCustom("killedByTeam." + ChatFormatting.RED.getName()), registerCustom("killedByTeam." + ChatFormatting.LIGHT_PURPLE.getName()), 
/*  48 */       registerCustom("killedByTeam." + ChatFormatting.YELLOW.getName()), registerCustom("killedByTeam." + ChatFormatting.WHITE.getName()) };
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final boolean readOnly;
/*     */   private final RenderType renderType;
/*     */   
/*     */   private static ObjectiveCriteria registerCustom(String $$0, boolean $$1, RenderType $$2) {
/*  56 */     ObjectiveCriteria $$3 = new ObjectiveCriteria($$0, $$1, $$2);
/*  57 */     CUSTOM_CRITERIA.put($$0, $$3);
/*  58 */     return $$3;
/*     */   }
/*     */   
/*     */   private static ObjectiveCriteria registerCustom(String $$0) {
/*  62 */     return registerCustom($$0, false, RenderType.INTEGER);
/*     */   }
/*     */   
/*     */   protected ObjectiveCriteria(String $$0) {
/*  66 */     this($$0, false, RenderType.INTEGER);
/*     */   }
/*     */   
/*     */   protected ObjectiveCriteria(String $$0, boolean $$1, RenderType $$2) {
/*  70 */     this.name = $$0;
/*  71 */     this.readOnly = $$1;
/*  72 */     this.renderType = $$2;
/*  73 */     CRITERIA_CACHE.put($$0, this);
/*     */   }
/*     */   
/*     */   public static Set<String> getCustomCriteriaNames() {
/*  77 */     return (Set<String>)ImmutableSet.copyOf(CUSTOM_CRITERIA.keySet());
/*     */   }
/*     */   
/*     */   public static Optional<ObjectiveCriteria> byName(String $$0) {
/*  81 */     ObjectiveCriteria $$1 = CRITERIA_CACHE.get($$0);
/*  82 */     if ($$1 != null) {
/*  83 */       return Optional.of($$1);
/*     */     }
/*  85 */     int $$2 = $$0.indexOf(':');
/*  86 */     if ($$2 < 0) {
/*  87 */       return Optional.empty();
/*     */     }
/*  89 */     return BuiltInRegistries.STAT_TYPE.getOptional(ResourceLocation.of($$0.substring(0, $$2), '.'))
/*  90 */       .flatMap($$2 -> getStat($$2, ResourceLocation.of($$0.substring($$1 + 1), '.')));
/*     */   }
/*     */   
/*     */   private static <T> Optional<ObjectiveCriteria> getStat(StatType<T> $$0, ResourceLocation $$1) {
/*  94 */     Objects.requireNonNull($$0); return $$0.getRegistry().getOptional($$1).map($$0::get);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  98 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 102 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   public RenderType getDefaultRenderType() {
/* 106 */     return this.renderType;
/*     */   }
/*     */   
/*     */   public enum RenderType implements StringRepresentable {
/* 110 */     INTEGER("integer"),
/* 111 */     HEARTS("hearts");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     public static final StringRepresentable.EnumCodec<RenderType> CODEC = StringRepresentable.fromEnum(RenderType::values);
/*     */     
/*     */     public static RenderType byId(String $$0) {
/* 132 */       return (RenderType)CODEC.byName($$0, INTEGER);
/*     */     }
/*     */     
/*     */     RenderType(String $$0) {
/*     */       this.id = $$0;
/*     */     }
/*     */     
/*     */     public String getId() {
/*     */       return this.id;
/*     */     }
/*     */     
/*     */     public String getSerializedName() {
/*     */       return this.id;
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\criteria\ObjectiveCriteria.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */