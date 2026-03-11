/*     */ package net.minecraft.world.level.portal;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.BlockUtil;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.TicketType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiRecord;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.NetherPortalBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortalForcer
/*     */ {
/*     */   private static final int TICKET_RADIUS = 3;
/*     */   private static final int SEARCH_RADIUS = 128;
/*     */   private static final int CREATE_RADIUS = 16;
/*     */   private static final int FRAME_HEIGHT = 5;
/*     */   private static final int FRAME_WIDTH = 4;
/*     */   private static final int FRAME_BOX = 3;
/*     */   private static final int FRAME_HEIGHT_START = -1;
/*     */   private static final int FRAME_HEIGHT_END = 4;
/*     */   private static final int FRAME_WIDTH_START = -1;
/*     */   private static final int FRAME_WIDTH_END = 3;
/*     */   private static final int FRAME_BOX_START = -1;
/*     */   private static final int FRAME_BOX_END = 2;
/*     */   private static final int NOTHING_FOUND = -1;
/*     */   private final ServerLevel level;
/*     */   
/*     */   public PortalForcer(ServerLevel $$0) {
/*  46 */     this.level = $$0;
/*     */   }
/*     */   
/*     */   public Optional<BlockUtil.FoundRectangle> findPortalAround(BlockPos $$0, boolean $$1, WorldBorder $$2) {
/*  50 */     PoiManager $$3 = this.level.getPoiManager();
/*  51 */     int $$4 = $$1 ? 16 : 128;
/*  52 */     $$3.ensureLoadedAndValid((LevelReader)this.level, $$0, $$4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     Optional<PoiRecord> $$5 = $$3.getInSquare($$0 -> $$0.is(PoiTypes.NETHER_PORTAL), $$0, $$4, PoiManager.Occupancy.ANY).filter($$1 -> $$0.isWithinBounds($$1.getPos())).sorted(Comparator.<PoiRecord>comparingDouble($$1 -> $$1.getPos().distSqr((Vec3i)$$0)).thenComparingInt($$0 -> $$0.getPos().getY())).filter($$0 -> this.level.getBlockState($$0.getPos()).hasProperty((Property)BlockStateProperties.HORIZONTAL_AXIS)).findFirst();
/*     */     
/*  60 */     return $$5.map($$0 -> {
/*     */           BlockPos $$1 = $$0.getPos();
/*     */           this.level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos($$1), 3, $$1);
/*     */           BlockState $$2 = this.level.getBlockState($$1);
/*     */           return BlockUtil.getLargestRectangleAround($$1, (Direction.Axis)$$2.getValue((Property)BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, ());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos $$0, Direction.Axis $$1) {
/*  71 */     Direction $$2 = Direction.get(Direction.AxisDirection.POSITIVE, $$1);
/*     */     
/*  73 */     double $$3 = -1.0D;
/*  74 */     BlockPos $$4 = null;
/*  75 */     double $$5 = -1.0D;
/*  76 */     BlockPos $$6 = null;
/*     */     
/*  78 */     WorldBorder $$7 = this.level.getWorldBorder();
/*  79 */     int $$8 = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
/*     */     
/*  81 */     BlockPos.MutableBlockPos $$9 = $$0.mutable();
/*  82 */     for (BlockPos.MutableBlockPos $$10 : BlockPos.spiralAround($$0, 16, Direction.EAST, Direction.SOUTH)) {
/*  83 */       int $$11 = Math.min($$8, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, $$10.getX(), $$10.getZ()));
/*     */ 
/*     */       
/*  86 */       int $$12 = 1;
/*  87 */       if (!$$7.isWithinBounds((BlockPos)$$10) || !$$7.isWithinBounds((BlockPos)$$10.move($$2, 1))) {
/*     */         continue;
/*     */       }
/*  90 */       $$10.move($$2.getOpposite(), 1);
/*     */       
/*  92 */       for (int $$13 = $$11; $$13 >= this.level.getMinBuildHeight(); $$13--) {
/*  93 */         $$10.setY($$13);
/*  94 */         if (canPortalReplaceBlock($$10)) {
/*     */ 
/*     */ 
/*     */           
/*  98 */           int $$14 = $$13;
/*     */           
/* 100 */           while ($$13 > this.level.getMinBuildHeight() && canPortalReplaceBlock($$10.move(Direction.DOWN))) {
/* 101 */             $$13--;
/*     */           }
/*     */ 
/*     */           
/* 105 */           if ($$13 + 4 <= $$8) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 110 */             int $$15 = $$14 - $$13;
/* 111 */             if ($$15 <= 0 || $$15 >= 3) {
/*     */ 
/*     */ 
/*     */               
/* 115 */               $$10.setY($$13);
/*     */               
/* 117 */               if (canHostFrame((BlockPos)$$10, $$9, $$2, 0)) {
/*     */                 
/* 119 */                 double $$16 = $$0.distSqr((Vec3i)$$10);
/*     */ 
/*     */                 
/* 122 */                 if (canHostFrame((BlockPos)$$10, $$9, $$2, -1) && 
/* 123 */                   canHostFrame((BlockPos)$$10, $$9, $$2, 1))
/*     */                 {
/*     */                   
/* 126 */                   if ($$3 == -1.0D || $$3 > $$16) {
/* 127 */                     $$3 = $$16;
/* 128 */                     $$4 = $$10.immutable();
/*     */                   } 
/*     */                 }
/*     */ 
/*     */                 
/* 133 */                 if ($$3 == -1.0D && ($$5 == -1.0D || $$5 > $$16)) {
/* 134 */                   $$5 = $$16;
/* 135 */                   $$6 = $$10.immutable();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 142 */     }  if ($$3 == -1.0D && $$5 != -1.0D) {
/* 143 */       $$4 = $$6;
/* 144 */       $$3 = $$5;
/*     */     } 
/*     */     
/* 147 */     if ($$3 == -1.0D) {
/*     */ 
/*     */       
/* 150 */       int $$17 = Math.max(this.level.getMinBuildHeight() - -1, 70);
/* 151 */       int $$18 = $$8 - 9;
/* 152 */       if ($$18 < $$17) {
/* 153 */         return Optional.empty();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       $$4 = (new BlockPos($$0.getX(), Mth.clamp($$0.getY(), $$17, $$18), $$0.getZ())).immutable();
/* 160 */       Direction $$19 = $$2.getClockWise();
/*     */ 
/*     */       
/* 163 */       if (!$$7.isWithinBounds($$4))
/*     */       {
/* 165 */         return Optional.empty();
/*     */       }
/*     */ 
/*     */       
/* 169 */       for (int $$20 = -1; $$20 < 2; $$20++) {
/* 170 */         for (int $$21 = 0; $$21 < 2; $$21++) {
/*     */           
/* 172 */           for (int $$22 = -1; $$22 < 3; $$22++) {
/* 173 */             BlockState $$23 = ($$22 < 0) ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.AIR.defaultBlockState();
/*     */             
/* 175 */             $$9.setWithOffset((Vec3i)$$4, $$21 * $$2
/*     */                 
/* 177 */                 .getStepX() + $$20 * $$19.getStepX(), $$22, $$21 * $$2
/*     */                 
/* 179 */                 .getStepZ() + $$20 * $$19.getStepZ());
/*     */             
/* 181 */             this.level.setBlockAndUpdate((BlockPos)$$9, $$23);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 188 */     for (int $$24 = -1; $$24 < 3; $$24++) {
/* 189 */       for (int $$25 = -1; $$25 < 4; $$25++) {
/*     */         
/* 191 */         if ($$24 == -1 || $$24 == 2 || $$25 == -1 || $$25 == 3) {
/* 192 */           $$9.setWithOffset((Vec3i)$$4, $$24 * $$2
/*     */               
/* 194 */               .getStepX(), $$25, $$24 * $$2
/*     */               
/* 196 */               .getStepZ());
/*     */           
/* 198 */           this.level.setBlock((BlockPos)$$9, Blocks.OBSIDIAN.defaultBlockState(), 3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 204 */     BlockState $$26 = (BlockState)Blocks.NETHER_PORTAL.defaultBlockState().setValue((Property)NetherPortalBlock.AXIS, (Comparable)$$1);
/*     */     
/* 206 */     for (int $$27 = 0; $$27 < 2; $$27++) {
/* 207 */       for (int $$28 = 0; $$28 < 3; $$28++) {
/* 208 */         $$9.setWithOffset((Vec3i)$$4, $$27 * $$2
/*     */             
/* 210 */             .getStepX(), $$28, $$27 * $$2
/*     */             
/* 212 */             .getStepZ());
/*     */         
/* 214 */         this.level.setBlock((BlockPos)$$9, $$26, 18);
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     return Optional.of(new BlockUtil.FoundRectangle($$4.immutable(), 2, 3));
/*     */   }
/*     */   
/*     */   private boolean canPortalReplaceBlock(BlockPos.MutableBlockPos $$0) {
/* 222 */     BlockState $$1 = this.level.getBlockState((BlockPos)$$0);
/* 223 */     return ($$1.canBeReplaced() && $$1.getFluidState().isEmpty());
/*     */   }
/*     */   
/*     */   private boolean canHostFrame(BlockPos $$0, BlockPos.MutableBlockPos $$1, Direction $$2, int $$3) {
/* 227 */     Direction $$4 = $$2.getClockWise();
/*     */     
/* 229 */     for (int $$5 = -1; $$5 < 3; $$5++) {
/* 230 */       for (int $$6 = -1; $$6 < 4; $$6++) {
/* 231 */         $$1.setWithOffset((Vec3i)$$0, $$2
/*     */             
/* 233 */             .getStepX() * $$5 + $$4.getStepX() * $$3, $$6, $$2
/*     */             
/* 235 */             .getStepZ() * $$5 + $$4.getStepZ() * $$3);
/*     */ 
/*     */         
/* 238 */         if ($$6 < 0 && !this.level.getBlockState((BlockPos)$$1).isSolid()) {
/* 239 */           return false;
/*     */         }
/* 241 */         if ($$6 >= 0 && !canPortalReplaceBlock($$1)) {
/* 242 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\portal\PortalForcer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */