/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.random.Weight;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
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
/*     */ public class SpawnerData
/*     */   extends WeightedEntry.IntrusiveBase
/*     */ {
/*     */   public static final Codec<SpawnerData> CODEC;
/*     */   public final EntityType<?> type;
/*     */   public final int minCount;
/*     */   public final int maxCount;
/*     */   
/*     */   static {
/*  73 */     CODEC = ExtraCodecs.validate(RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(()), (App)Weight.CODEC.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::getWeight), (App)ExtraCodecs.POSITIVE_INT.fieldOf("minCount").forGetter(()), (App)ExtraCodecs.POSITIVE_INT.fieldOf("maxCount").forGetter(())).apply((Applicative)$$0, SpawnerData::new)), $$0 -> ($$0.minCount > $$0.maxCount) ? DataResult.error(()) : DataResult.success($$0));
/*     */   }
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
/*     */   public SpawnerData(EntityType<?> $$0, int $$1, int $$2, int $$3) {
/*  90 */     this($$0, Weight.of($$1), $$2, $$3);
/*     */   }
/*     */   
/*     */   public SpawnerData(EntityType<?> $$0, Weight $$1, int $$2, int $$3) {
/*  94 */     super($$1);
/*  95 */     this.type = ($$0.getCategory() == MobCategory.MISC) ? EntityType.PIG : $$0;
/*  96 */     this.minCount = $$2;
/*  97 */     this.maxCount = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return "" + EntityType.getKey(this.type) + "*(" + EntityType.getKey(this.type) + "-" + this.minCount + "):" + this.maxCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MobSpawnSettings$SpawnerData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */