/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableBiMap;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public interface WeatheringCopper
/*     */   extends ChangeOverTimeBlock<WeatheringCopper.WeatherState> {
/*  14 */   public static final Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = (Supplier<BiMap<Block, Block>>)Suppliers.memoize(() -> ImmutableBiMap.builder().put(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER).put(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER).put(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER).put(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER).put(Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER).put(Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER).put(Blocks.CHISELED_COPPER, Blocks.EXPOSED_CHISELED_COPPER).put(Blocks.EXPOSED_CHISELED_COPPER, Blocks.WEATHERED_CHISELED_COPPER).put(Blocks.WEATHERED_CHISELED_COPPER, Blocks.OXIDIZED_CHISELED_COPPER).put(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB).put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB).put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB).put(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS).put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS).put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS).put(Blocks.COPPER_DOOR, Blocks.EXPOSED_COPPER_DOOR).put(Blocks.EXPOSED_COPPER_DOOR, Blocks.WEATHERED_COPPER_DOOR).put(Blocks.WEATHERED_COPPER_DOOR, Blocks.OXIDIZED_COPPER_DOOR).put(Blocks.COPPER_TRAPDOOR, Blocks.EXPOSED_COPPER_TRAPDOOR).put(Blocks.EXPOSED_COPPER_TRAPDOOR, Blocks.WEATHERED_COPPER_TRAPDOOR).put(Blocks.WEATHERED_COPPER_TRAPDOOR, Blocks.OXIDIZED_COPPER_TRAPDOOR).put(Blocks.COPPER_GRATE, Blocks.EXPOSED_COPPER_GRATE).put(Blocks.EXPOSED_COPPER_GRATE, Blocks.WEATHERED_COPPER_GRATE).put(Blocks.WEATHERED_COPPER_GRATE, Blocks.OXIDIZED_COPPER_GRATE).put(Blocks.COPPER_BULB, Blocks.EXPOSED_COPPER_BULB).put(Blocks.EXPOSED_COPPER_BULB, Blocks.WEATHERED_COPPER_BULB).put(Blocks.WEATHERED_COPPER_BULB, Blocks.OXIDIZED_COPPER_BULB).build());
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
/*  53 */   public static final Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = (Supplier<BiMap<Block, Block>>)Suppliers.memoize(() -> ((BiMap)NEXT_BY_BLOCK.get()).inverse());
/*     */   
/*     */   static Optional<Block> getPrevious(Block $$0) {
/*  56 */     return Optional.ofNullable((Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get($$0));
/*     */   }
/*     */   
/*     */   static Block getFirst(Block $$0) {
/*  60 */     Block $$1 = $$0;
/*  61 */     Block $$2 = (Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get($$1);
/*  62 */     while ($$2 != null) {
/*  63 */       $$1 = $$2;
/*  64 */       $$2 = (Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get($$1);
/*     */     } 
/*  66 */     return $$1;
/*     */   }
/*     */   
/*     */   static Optional<BlockState> getPrevious(BlockState $$0) {
/*  70 */     return getPrevious($$0.getBlock()).map($$1 -> $$1.withPropertiesOf($$0));
/*     */   }
/*     */   
/*     */   static Optional<Block> getNext(Block $$0) {
/*  74 */     return Optional.ofNullable((Block)((BiMap)NEXT_BY_BLOCK.get()).get($$0));
/*     */   }
/*     */   
/*     */   static BlockState getFirst(BlockState $$0) {
/*  78 */     return getFirst($$0.getBlock()).withPropertiesOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   default Optional<BlockState> getNext(BlockState $$0) {
/*  83 */     return getNext($$0.getBlock()).map($$1 -> $$1.withPropertiesOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   default float getChanceModifier() {
/*  88 */     if (getAge() == WeatherState.UNAFFECTED) {
/*  89 */       return 0.75F;
/*     */     }
/*  91 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public enum WeatherState
/*     */     implements StringRepresentable
/*     */   {
/*  97 */     UNAFFECTED("unaffected"),
/*  98 */     EXPOSED("exposed"),
/*  99 */     WEATHERED("weathered"),
/* 100 */     OXIDIZED("oxidized");
/*     */     
/* 102 */     public static final Codec<WeatherState> CODEC = (Codec<WeatherState>)StringRepresentable.fromEnum(WeatherState::values);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     WeatherState(String $$0) {
/* 107 */       this.name = $$0;
/*     */     } static {
/*     */     
/*     */     }
/*     */     public String getSerializedName() {
/* 112 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */