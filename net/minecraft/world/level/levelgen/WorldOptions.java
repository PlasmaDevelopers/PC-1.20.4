/*    */ package net.minecraft.world.level.levelgen;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.OptionalLong;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class WorldOptions {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.LONG.fieldOf("seed").stable().forGetter(WorldOptions::seed), (App)Codec.BOOL.fieldOf("generate_features").orElse(Boolean.valueOf(true)).stable().forGetter(WorldOptions::generateStructures), (App)Codec.BOOL.fieldOf("bonus_chest").orElse(Boolean.valueOf(false)).stable().forGetter(WorldOptions::generateBonusChest), (App)Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(())).apply((Applicative)$$0, $$0.stable(WorldOptions::new)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final MapCodec<WorldOptions> CODEC;
/*    */ 
/*    */   
/* 21 */   public static final WorldOptions DEMO_OPTIONS = new WorldOptions("North Carolina".hashCode(), true, true);
/*    */   
/*    */   private final long seed;
/*    */   
/*    */   private final boolean generateStructures;
/*    */   private final boolean generateBonusChest;
/*    */   private final Optional<String> legacyCustomOptions;
/*    */   
/*    */   public WorldOptions(long $$0, boolean $$1, boolean $$2) {
/* 30 */     this($$0, $$1, $$2, Optional.empty());
/*    */   }
/*    */   
/*    */   public static WorldOptions defaultWithRandomSeed() {
/* 34 */     return new WorldOptions(randomSeed(), true, false);
/*    */   }
/*    */   
/*    */   private WorldOptions(long $$0, boolean $$1, boolean $$2, Optional<String> $$3) {
/* 38 */     this.seed = $$0;
/* 39 */     this.generateStructures = $$1;
/* 40 */     this.generateBonusChest = $$2;
/* 41 */     this.legacyCustomOptions = $$3;
/*    */   }
/*    */   
/*    */   public long seed() {
/* 45 */     return this.seed;
/*    */   }
/*    */   
/*    */   public boolean generateStructures() {
/* 49 */     return this.generateStructures;
/*    */   }
/*    */   
/*    */   public boolean generateBonusChest() {
/* 53 */     return this.generateBonusChest;
/*    */   }
/*    */   
/*    */   public boolean isOldCustomizedWorld() {
/* 57 */     return this.legacyCustomOptions.isPresent();
/*    */   }
/*    */   
/*    */   public WorldOptions withBonusChest(boolean $$0) {
/* 61 */     return new WorldOptions(this.seed, this.generateStructures, $$0, this.legacyCustomOptions);
/*    */   }
/*    */   
/*    */   public WorldOptions withStructures(boolean $$0) {
/* 65 */     return new WorldOptions(this.seed, $$0, this.generateBonusChest, this.legacyCustomOptions);
/*    */   }
/*    */   
/*    */   public WorldOptions withSeed(OptionalLong $$0) {
/* 69 */     return new WorldOptions($$0.orElse(randomSeed()), this.generateStructures, this.generateBonusChest, this.legacyCustomOptions);
/*    */   }
/*    */   
/*    */   public static OptionalLong parseSeed(String $$0) {
/* 73 */     $$0 = $$0.trim();
/*    */     
/* 75 */     if (StringUtils.isEmpty($$0)) {
/* 76 */       return OptionalLong.empty();
/*    */     }
/*    */     
/*    */     try {
/* 80 */       return OptionalLong.of(Long.parseLong($$0));
/* 81 */     } catch (NumberFormatException $$1) {
/*    */       
/* 83 */       return OptionalLong.of($$0.hashCode());
/*    */     } 
/*    */   }
/*    */   
/*    */   public static long randomSeed() {
/* 88 */     return RandomSource.create().nextLong();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\WorldOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */