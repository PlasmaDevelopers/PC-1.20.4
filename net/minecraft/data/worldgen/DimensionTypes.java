/*     */ package net.minecraft.data.worldgen;
/*     */ 
/*     */ import java.util.OptionalLong;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.valueproviders.ConstantInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ 
/*     */ 
/*     */ public class DimensionTypes
/*     */ {
/*     */   public static void bootstrap(BootstapContext<DimensionType> $$0) {
/*  15 */     $$0.register(BuiltinDimensionTypes.OVERWORLD, new DimensionType(
/*  16 */           OptionalLong.empty(), true, false, false, true, 1.0D, true, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0F, new DimensionType.MonsterSettings(false, true, 
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
/*  33 */             (IntProvider)UniformInt.of(0, 7), 0)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     $$0.register(BuiltinDimensionTypes.NETHER, new DimensionType(
/*  39 */           OptionalLong.of(18000L), false, true, true, false, 8.0D, false, true, 0, 256, 128, BlockTags.INFINIBURN_NETHER, BuiltinDimensionTypes.NETHER_EFFECTS, 0.1F, new DimensionType.MonsterSettings(true, false, 
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
/*  56 */             (IntProvider)ConstantInt.of(7), 15)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     $$0.register(BuiltinDimensionTypes.END, new DimensionType(
/*  62 */           OptionalLong.of(6000L), false, false, false, false, 1.0D, false, false, 0, 256, 256, BlockTags.INFINIBURN_END, BuiltinDimensionTypes.END_EFFECTS, 0.0F, new DimensionType.MonsterSettings(false, true, 
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
/*  79 */             (IntProvider)UniformInt.of(0, 7), 0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     $$0.register(BuiltinDimensionTypes.OVERWORLD_CAVES, new DimensionType(
/*  86 */           OptionalLong.empty(), true, true, false, true, 1.0D, true, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0F, new DimensionType.MonsterSettings(false, true, 
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
/* 103 */             (IntProvider)UniformInt.of(0, 7), 0)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\DimensionTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */