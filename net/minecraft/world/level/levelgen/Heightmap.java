/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.BitStorage;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SimpleBitStorage;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Heightmap
/*     */ {
/*  25 */   private static final Logger LOGGER = LogUtils.getLogger(); static final Predicate<BlockState> NOT_AIR; static {
/*  26 */     NOT_AIR = ($$0 -> !$$0.isAir());
/*  27 */   } static final Predicate<BlockState> MATERIAL_MOTION_BLOCKING = BlockBehaviour.BlockStateBase::blocksMotion; private final BitStorage data; private final Predicate<BlockState> isOpaque;
/*     */   private final ChunkAccess chunk;
/*     */   
/*  30 */   public enum Usage { WORLDGEN,
/*  31 */     LIVE_WORLD,
/*  32 */     CLIENT; }
/*     */ 
/*     */   
/*     */   public enum Types
/*     */     implements StringRepresentable {
/*  37 */     WORLD_SURFACE_WG("WORLD_SURFACE_WG", Heightmap.Usage.WORLDGEN, (String)Heightmap.NOT_AIR),
/*  38 */     WORLD_SURFACE("WORLD_SURFACE", Heightmap.Usage.CLIENT, (String)Heightmap.NOT_AIR),
/*  39 */     OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Heightmap.Usage.WORLDGEN, (String)Heightmap.MATERIAL_MOTION_BLOCKING),
/*  40 */     OCEAN_FLOOR("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, (String)Heightmap.MATERIAL_MOTION_BLOCKING), MOTION_BLOCKING("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, (String)Heightmap.MATERIAL_MOTION_BLOCKING), MOTION_BLOCKING_NO_LEAVES("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, (String)Heightmap.MATERIAL_MOTION_BLOCKING); public static final Codec<Types> CODEC; private final String serializationKey; static {
/*  41 */       MOTION_BLOCKING = new Types("MOTION_BLOCKING", 4, "MOTION_BLOCKING", Heightmap.Usage.CLIENT, $$0 -> ($$0.blocksMotion() || !$$0.getFluidState().isEmpty()));
/*  42 */       MOTION_BLOCKING_NO_LEAVES = new Types("MOTION_BLOCKING_NO_LEAVES", 5, "MOTION_BLOCKING_NO_LEAVES", Heightmap.Usage.LIVE_WORLD, $$0 -> (($$0.blocksMotion() || !$$0.getFluidState().isEmpty()) && !($$0.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock)));
/*     */     }
/*     */     private final Heightmap.Usage usage; private final Predicate<BlockState> isOpaque;
/*     */     static {
/*  46 */       CODEC = (Codec<Types>)StringRepresentable.fromEnum(Types::values);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Types(String $$0, Heightmap.Usage $$1, Predicate<BlockState> $$2) {
/*  53 */       this.serializationKey = $$0;
/*  54 */       this.usage = $$1;
/*  55 */       this.isOpaque = $$2;
/*     */     }
/*     */     
/*     */     public String getSerializationKey() {
/*  59 */       return this.serializationKey;
/*     */     }
/*     */     
/*     */     public boolean sendToClient() {
/*  63 */       return (this.usage == Heightmap.Usage.CLIENT);
/*     */     }
/*     */     
/*     */     public boolean keepAfterWorldgen() {
/*  67 */       return (this.usage != Heightmap.Usage.WORLDGEN);
/*     */     }
/*     */     
/*     */     public Predicate<BlockState> isOpaque() {
/*  71 */       return this.isOpaque;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/*  76 */       return this.serializationKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Heightmap(ChunkAccess $$0, Types $$1) {
/*  85 */     this.isOpaque = $$1.isOpaque();
/*  86 */     this.chunk = $$0;
/*  87 */     int $$2 = Mth.ceillog2($$0.getHeight() + 1);
/*  88 */     this.data = (BitStorage)new SimpleBitStorage($$2, 256);
/*     */   }
/*     */   
/*     */   public static void primeHeightmaps(ChunkAccess $$0, Set<Types> $$1) {
/*  92 */     int $$2 = $$1.size();
/*  93 */     ObjectArrayList objectArrayList = new ObjectArrayList($$2);
/*  94 */     ObjectListIterator<Heightmap> $$4 = objectArrayList.iterator();
/*     */     
/*  96 */     int $$5 = $$0.getHighestSectionPosition() + 16;
/*  97 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/*  98 */     for (int $$7 = 0; $$7 < 16; $$7++) {
/*  99 */       for (int $$8 = 0; $$8 < 16; $$8++) {
/* 100 */         for (Types $$9 : $$1) {
/* 101 */           objectArrayList.add($$0.getOrCreateHeightmapUnprimed($$9));
/*     */         }
/*     */         
/* 104 */         for (int $$10 = $$5 - 1; $$10 >= $$0.getMinBuildHeight(); $$10--) {
/* 105 */           $$6.set($$7, $$10, $$8);
/* 106 */           BlockState $$11 = $$0.getBlockState((BlockPos)$$6);
/* 107 */           if (!$$11.is(Blocks.AIR)) {
/*     */ 
/*     */             
/* 110 */             while ($$4.hasNext()) {
/* 111 */               Heightmap $$12 = (Heightmap)$$4.next();
/* 112 */               if ($$12.isOpaque.test($$11)) {
/* 113 */                 $$12.setHeight($$7, $$8, $$10 + 1);
/* 114 */                 $$4.remove();
/*     */               } 
/*     */             } 
/* 117 */             if (objectArrayList.isEmpty()) {
/*     */               break;
/*     */             }
/* 120 */             $$4.back($$2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public boolean update(int $$0, int $$1, int $$2, BlockState $$3) {
/* 127 */     int $$4 = getFirstAvailable($$0, $$2);
/* 128 */     if ($$1 <= $$4 - 2)
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     if (this.isOpaque.test($$3)) {
/*     */       
/* 135 */       if ($$1 >= $$4) {
/* 136 */         setHeight($$0, $$2, $$1 + 1);
/* 137 */         return true;
/*     */       }
/*     */     
/*     */     }
/* 141 */     else if ($$4 - 1 == $$1) {
/* 142 */       BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 143 */       for (int $$6 = $$1 - 1; $$6 >= this.chunk.getMinBuildHeight(); $$6--) {
/* 144 */         $$5.set($$0, $$6, $$2);
/* 145 */         if (this.isOpaque.test(this.chunk.getBlockState((BlockPos)$$5))) {
/* 146 */           setHeight($$0, $$2, $$6 + 1);
/* 147 */           return true;
/*     */         } 
/*     */       } 
/* 150 */       setHeight($$0, $$2, this.chunk.getMinBuildHeight());
/* 151 */       return true;
/*     */     } 
/*     */     
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   public int getFirstAvailable(int $$0, int $$1) {
/* 158 */     return getFirstAvailable(getIndex($$0, $$1));
/*     */   }
/*     */   
/*     */   public int getHighestTaken(int $$0, int $$1) {
/* 162 */     return getFirstAvailable(getIndex($$0, $$1)) - 1;
/*     */   }
/*     */   
/*     */   private int getFirstAvailable(int $$0) {
/* 166 */     return this.data.get($$0) + this.chunk.getMinBuildHeight();
/*     */   }
/*     */   
/*     */   private void setHeight(int $$0, int $$1, int $$2) {
/* 170 */     this.data.set(getIndex($$0, $$1), $$2 - this.chunk.getMinBuildHeight());
/*     */   }
/*     */   
/*     */   public void setRawData(ChunkAccess $$0, Types $$1, long[] $$2) {
/* 174 */     long[] $$3 = this.data.getRaw();
/* 175 */     if ($$3.length == $$2.length) {
/* 176 */       System.arraycopy($$2, 0, $$3, 0, $$2.length);
/*     */       return;
/*     */     } 
/* 179 */     LOGGER.warn("Ignoring heightmap data for chunk " + $$0.getPos() + ", size does not match; expected: " + $$3.length + ", got: " + $$2.length);
/* 180 */     primeHeightmaps($$0, EnumSet.of($$1));
/*     */   }
/*     */   
/*     */   public long[] getRawData() {
/* 184 */     return this.data.getRaw();
/*     */   }
/*     */   
/*     */   private static int getIndex(int $$0, int $$1) {
/* 188 */     return $$0 + $$1 * 16;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Heightmap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */