/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LevelChunkSection
/*     */ {
/*     */   public static final int SECTION_WIDTH = 16;
/*     */   public static final int SECTION_HEIGHT = 16;
/*     */   public static final int SECTION_SIZE = 4096;
/*     */   public static final int BIOME_CONTAINER_BITS = 2;
/*     */   private short nonEmptyBlockCount;
/*     */   private short tickingBlockCount;
/*     */   private short tickingFluidCount;
/*     */   private final PalettedContainer<BlockState> states;
/*     */   private PalettedContainerRO<Holder<Biome>> biomes;
/*     */   
/*     */   public LevelChunkSection(PalettedContainer<BlockState> $$0, PalettedContainerRO<Holder<Biome>> $$1) {
/*  32 */     this.states = $$0;
/*  33 */     this.biomes = $$1;
/*  34 */     recalcBlockCounts();
/*     */   }
/*     */   
/*     */   public LevelChunkSection(Registry<Biome> $$0) {
/*  38 */     this.states = new PalettedContainer<>((IdMap<BlockState>)Block.BLOCK_STATE_REGISTRY, Blocks.AIR.defaultBlockState(), PalettedContainer.Strategy.SECTION_STATES);
/*  39 */     this.biomes = new PalettedContainer($$0.asHolderIdMap(), $$0.getHolderOrThrow(Biomes.PLAINS), PalettedContainer.Strategy.SECTION_BIOMES);
/*     */   }
/*     */   
/*     */   public BlockState getBlockState(int $$0, int $$1, int $$2) {
/*  43 */     return this.states.get($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public FluidState getFluidState(int $$0, int $$1, int $$2) {
/*  47 */     return ((BlockState)this.states.get($$0, $$1, $$2)).getFluidState();
/*     */   }
/*     */   
/*     */   public void acquire() {
/*  51 */     this.states.acquire();
/*     */   }
/*     */   
/*     */   public void release() {
/*  55 */     this.states.release();
/*     */   }
/*     */   
/*     */   public BlockState setBlockState(int $$0, int $$1, int $$2, BlockState $$3) {
/*  59 */     return setBlockState($$0, $$1, $$2, $$3, true);
/*     */   }
/*     */   
/*     */   public BlockState setBlockState(int $$0, int $$1, int $$2, BlockState $$3, boolean $$4) {
/*     */     BlockState $$6;
/*  64 */     if ($$4) {
/*  65 */       BlockState $$5 = this.states.getAndSet($$0, $$1, $$2, $$3);
/*     */     } else {
/*  67 */       $$6 = this.states.getAndSetUnchecked($$0, $$1, $$2, $$3);
/*     */     } 
/*  69 */     FluidState $$7 = $$6.getFluidState();
/*  70 */     FluidState $$8 = $$3.getFluidState();
/*     */     
/*  72 */     if (!$$6.isAir()) {
/*  73 */       this.nonEmptyBlockCount = (short)(this.nonEmptyBlockCount - 1);
/*  74 */       if ($$6.isRandomlyTicking()) {
/*  75 */         this.tickingBlockCount = (short)(this.tickingBlockCount - 1);
/*     */       }
/*     */     } 
/*     */     
/*  79 */     if (!$$7.isEmpty()) {
/*  80 */       this.tickingFluidCount = (short)(this.tickingFluidCount - 1);
/*     */     }
/*     */     
/*  83 */     if (!$$3.isAir()) {
/*  84 */       this.nonEmptyBlockCount = (short)(this.nonEmptyBlockCount + 1);
/*  85 */       if ($$3.isRandomlyTicking()) {
/*  86 */         this.tickingBlockCount = (short)(this.tickingBlockCount + 1);
/*     */       }
/*     */     } 
/*     */     
/*  90 */     if (!$$8.isEmpty()) {
/*  91 */       this.tickingFluidCount = (short)(this.tickingFluidCount + 1);
/*     */     }
/*     */     
/*  94 */     return $$6;
/*     */   }
/*     */   
/*     */   public boolean hasOnlyAir() {
/*  98 */     return (this.nonEmptyBlockCount == 0);
/*     */   }
/*     */   
/*     */   public boolean isRandomlyTicking() {
/* 102 */     return (isRandomlyTickingBlocks() || isRandomlyTickingFluids());
/*     */   }
/*     */   
/*     */   public boolean isRandomlyTickingBlocks() {
/* 106 */     return (this.tickingBlockCount > 0);
/*     */   }
/*     */   
/*     */   public boolean isRandomlyTickingFluids() {
/* 110 */     return (this.tickingFluidCount > 0);
/*     */   }
/*     */   
/*     */   public void recalcBlockCounts() {
/*     */     class BlockCounter
/*     */       implements PalettedContainer.CountConsumer<BlockState> {
/*     */       public int nonEmptyBlockCount;
/*     */       public int tickingBlockCount;
/*     */       public int tickingFluidCount;
/*     */       
/*     */       public void accept(BlockState $$0, int $$1) {
/* 121 */         FluidState $$2 = $$0.getFluidState();
/*     */         
/* 123 */         if (!$$0.isAir()) {
/* 124 */           this.nonEmptyBlockCount += $$1;
/* 125 */           if ($$0.isRandomlyTicking()) {
/* 126 */             this.tickingBlockCount += $$1;
/*     */           }
/*     */         } 
/* 129 */         if (!$$2.isEmpty()) {
/* 130 */           this.nonEmptyBlockCount += $$1;
/* 131 */           if ($$2.isRandomlyTicking()) {
/* 132 */             this.tickingFluidCount += $$1;
/*     */           }
/*     */         } 
/*     */       }
/*     */     };
/*     */     
/* 138 */     BlockCounter $$0 = new BlockCounter();
/* 139 */     this.states.count($$0);
/*     */ 
/*     */     
/* 142 */     this.nonEmptyBlockCount = (short)$$0.nonEmptyBlockCount;
/* 143 */     this.tickingBlockCount = (short)$$0.tickingBlockCount;
/* 144 */     this.tickingFluidCount = (short)$$0.tickingFluidCount;
/*     */   }
/*     */   
/*     */   public PalettedContainer<BlockState> getStates() {
/* 148 */     return this.states;
/*     */   }
/*     */   
/*     */   public PalettedContainerRO<Holder<Biome>> getBiomes() {
/* 152 */     return this.biomes;
/*     */   }
/*     */   
/*     */   public void read(FriendlyByteBuf $$0) {
/* 156 */     this.nonEmptyBlockCount = $$0.readShort();
/* 157 */     this.states.read($$0);
/* 158 */     PalettedContainer<Holder<Biome>> $$1 = this.biomes.recreate();
/* 159 */     $$1.read($$0);
/* 160 */     this.biomes = $$1;
/*     */   }
/*     */   
/*     */   public void readBiomes(FriendlyByteBuf $$0) {
/* 164 */     PalettedContainer<Holder<Biome>> $$1 = this.biomes.recreate();
/* 165 */     $$1.read($$0);
/* 166 */     this.biomes = $$1;
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 170 */     $$0.writeShort(this.nonEmptyBlockCount);
/* 171 */     this.states.write($$0);
/* 172 */     this.biomes.write($$0);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 176 */     return 2 + this.states.getSerializedSize() + this.biomes.getSerializedSize();
/*     */   }
/*     */   
/*     */   public boolean maybeHas(Predicate<BlockState> $$0) {
/* 180 */     return this.states.maybeHas($$0);
/*     */   }
/*     */   
/*     */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/* 184 */     return this.biomes.get($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public void fillBiomesFromNoise(BiomeResolver $$0, Climate.Sampler $$1, int $$2, int $$3, int $$4) {
/* 188 */     PalettedContainer<Holder<Biome>> $$5 = this.biomes.recreate();
/*     */     
/* 190 */     int $$6 = 4;
/* 191 */     for (int $$7 = 0; $$7 < 4; $$7++) {
/* 192 */       for (int $$8 = 0; $$8 < 4; $$8++) {
/* 193 */         for (int $$9 = 0; $$9 < 4; $$9++) {
/* 194 */           $$5.getAndSetUnchecked($$7, $$8, $$9, $$0.getNoiseBiome($$2 + $$7, $$3 + $$8, $$4 + $$9, $$1));
/*     */         }
/*     */       } 
/*     */     } 
/* 198 */     this.biomes = $$5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\LevelChunkSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */