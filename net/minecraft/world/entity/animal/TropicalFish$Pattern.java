/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.IntFunction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Pattern
/*     */   implements StringRepresentable
/*     */ {
/*  85 */   KOB("kob", TropicalFish.Base.SMALL, 0),
/*  86 */   SUNSTREAK("sunstreak", TropicalFish.Base.SMALL, 1),
/*  87 */   SNOOPER("snooper", TropicalFish.Base.SMALL, 2),
/*  88 */   DASHER("dasher", TropicalFish.Base.SMALL, 3),
/*  89 */   BRINELY("brinely", TropicalFish.Base.SMALL, 4),
/*  90 */   SPOTTY("spotty", TropicalFish.Base.SMALL, 5),
/*  91 */   FLOPPER("flopper", TropicalFish.Base.LARGE, 0),
/*  92 */   STRIPEY("stripey", TropicalFish.Base.LARGE, 1),
/*  93 */   GLITTER("glitter", TropicalFish.Base.LARGE, 2),
/*  94 */   BLOCKFISH("blockfish", TropicalFish.Base.LARGE, 3),
/*  95 */   BETTY("betty", TropicalFish.Base.LARGE, 4),
/*  96 */   CLAYFISH("clayfish", TropicalFish.Base.LARGE, 5);
/*     */   static {
/*  98 */     CODEC = (Codec<Pattern>)StringRepresentable.fromEnum(Pattern::values);
/*     */     
/* 100 */     BY_ID = ByIdMap.sparse(Pattern::getPackedId, (Object[])values(), KOB);
/*     */   }
/*     */   
/*     */   public static final Codec<Pattern> CODEC;
/*     */   private static final IntFunction<Pattern> BY_ID;
/*     */   private final String name;
/*     */   private final Component displayName;
/*     */   private final TropicalFish.Base base;
/*     */   private final int packedId;
/*     */   
/*     */   Pattern(String $$0, TropicalFish.Base $$1, int $$2) {
/* 111 */     this.name = $$0;
/* 112 */     this.base = $$1;
/* 113 */     this.packedId = $$1.id | $$2 << 8;
/* 114 */     this.displayName = (Component)Component.translatable("entity.minecraft.tropical_fish.type." + this.name);
/*     */   }
/*     */   
/*     */   public static Pattern byId(int $$0) {
/* 118 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   public TropicalFish.Base base() {
/* 122 */     return this.base;
/*     */   }
/*     */   
/*     */   public int getPackedId() {
/* 126 */     return this.packedId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 131 */     return this.name;
/*     */   }
/*     */   
/*     */   public Component displayName() {
/* 135 */     return this.displayName;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\TropicalFish$Pattern.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */