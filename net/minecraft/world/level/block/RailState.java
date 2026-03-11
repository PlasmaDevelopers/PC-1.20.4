/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ 
/*     */ public class RailState
/*     */ {
/*     */   private final Level level;
/*     */   private final BlockPos pos;
/*     */   private final BaseRailBlock block;
/*     */   private BlockState state;
/*     */   private final boolean isStraight;
/*  19 */   private final List<BlockPos> connections = Lists.newArrayList();
/*     */   
/*     */   public RailState(Level $$0, BlockPos $$1, BlockState $$2) {
/*  22 */     this.level = $$0;
/*  23 */     this.pos = $$1;
/*  24 */     this.state = $$2;
/*  25 */     this.block = (BaseRailBlock)$$2.getBlock();
/*  26 */     RailShape $$3 = (RailShape)$$2.getValue(this.block.getShapeProperty());
/*  27 */     this.isStraight = this.block.isStraight();
/*  28 */     updateConnections($$3);
/*     */   }
/*     */   
/*     */   public List<BlockPos> getConnections() {
/*  32 */     return this.connections;
/*     */   }
/*     */   
/*     */   private void updateConnections(RailShape $$0) {
/*  36 */     this.connections.clear();
/*  37 */     switch ($$0) {
/*     */       case NORTH_SOUTH:
/*  39 */         this.connections.add(this.pos.north());
/*  40 */         this.connections.add(this.pos.south());
/*     */         break;
/*     */       case EAST_WEST:
/*  43 */         this.connections.add(this.pos.west());
/*  44 */         this.connections.add(this.pos.east());
/*     */         break;
/*     */       case ASCENDING_EAST:
/*  47 */         this.connections.add(this.pos.west());
/*  48 */         this.connections.add(this.pos.east().above());
/*     */         break;
/*     */       case ASCENDING_WEST:
/*  51 */         this.connections.add(this.pos.west().above());
/*  52 */         this.connections.add(this.pos.east());
/*     */         break;
/*     */       case ASCENDING_NORTH:
/*  55 */         this.connections.add(this.pos.north().above());
/*  56 */         this.connections.add(this.pos.south());
/*     */         break;
/*     */       case ASCENDING_SOUTH:
/*  59 */         this.connections.add(this.pos.north());
/*  60 */         this.connections.add(this.pos.south().above());
/*     */         break;
/*     */       case SOUTH_EAST:
/*  63 */         this.connections.add(this.pos.east());
/*  64 */         this.connections.add(this.pos.south());
/*     */         break;
/*     */       case SOUTH_WEST:
/*  67 */         this.connections.add(this.pos.west());
/*  68 */         this.connections.add(this.pos.south());
/*     */         break;
/*     */       case NORTH_WEST:
/*  71 */         this.connections.add(this.pos.west());
/*  72 */         this.connections.add(this.pos.north());
/*     */         break;
/*     */       case NORTH_EAST:
/*  75 */         this.connections.add(this.pos.east());
/*  76 */         this.connections.add(this.pos.north());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeSoftConnections() {
/*  82 */     for (int $$0 = 0; $$0 < this.connections.size(); $$0++) {
/*  83 */       RailState $$1 = getRail(this.connections.get($$0));
/*  84 */       if ($$1 == null || !$$1.connectsTo(this)) {
/*  85 */         this.connections.remove($$0--);
/*     */       } else {
/*  87 */         this.connections.set($$0, $$1.pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasRail(BlockPos $$0) {
/*  93 */     return (BaseRailBlock.isRail(this.level, $$0) || BaseRailBlock.isRail(this.level, $$0.above()) || BaseRailBlock.isRail(this.level, $$0.below()));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private RailState getRail(BlockPos $$0) {
/*  98 */     BlockPos $$1 = $$0;
/*  99 */     BlockState $$2 = this.level.getBlockState($$1);
/* 100 */     if (BaseRailBlock.isRail($$2)) {
/* 101 */       return new RailState(this.level, $$1, $$2);
/*     */     }
/*     */     
/* 104 */     $$1 = $$0.above();
/* 105 */     $$2 = this.level.getBlockState($$1);
/* 106 */     if (BaseRailBlock.isRail($$2)) {
/* 107 */       return new RailState(this.level, $$1, $$2);
/*     */     }
/*     */     
/* 110 */     $$1 = $$0.below();
/* 111 */     $$2 = this.level.getBlockState($$1);
/* 112 */     if (BaseRailBlock.isRail($$2)) {
/* 113 */       return new RailState(this.level, $$1, $$2);
/*     */     }
/*     */     
/* 116 */     return null;
/*     */   }
/*     */   
/*     */   private boolean connectsTo(RailState $$0) {
/* 120 */     return hasConnection($$0.pos);
/*     */   }
/*     */   
/*     */   private boolean hasConnection(BlockPos $$0) {
/* 124 */     for (int $$1 = 0; $$1 < this.connections.size(); $$1++) {
/* 125 */       BlockPos $$2 = this.connections.get($$1);
/* 126 */       if ($$2.getX() == $$0.getX() && $$2.getZ() == $$0.getZ()) {
/* 127 */         return true;
/*     */       }
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   protected int countPotentialConnections() {
/* 134 */     int $$0 = 0;
/*     */     
/* 136 */     for (Direction $$1 : Direction.Plane.HORIZONTAL) {
/* 137 */       if (hasRail(this.pos.relative($$1))) {
/* 138 */         $$0++;
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return $$0;
/*     */   }
/*     */   
/*     */   private boolean canConnectTo(RailState $$0) {
/* 146 */     return (connectsTo($$0) || this.connections.size() != 2);
/*     */   }
/*     */   
/*     */   private void connectTo(RailState $$0) {
/* 150 */     this.connections.add($$0.pos);
/*     */     
/* 152 */     BlockPos $$1 = this.pos.north();
/* 153 */     BlockPos $$2 = this.pos.south();
/* 154 */     BlockPos $$3 = this.pos.west();
/* 155 */     BlockPos $$4 = this.pos.east();
/*     */     
/* 157 */     boolean $$5 = hasConnection($$1);
/* 158 */     boolean $$6 = hasConnection($$2);
/* 159 */     boolean $$7 = hasConnection($$3);
/* 160 */     boolean $$8 = hasConnection($$4);
/*     */     
/* 162 */     RailShape $$9 = null;
/*     */     
/* 164 */     if ($$5 || $$6) {
/* 165 */       $$9 = RailShape.NORTH_SOUTH;
/*     */     }
/* 167 */     if ($$7 || $$8) {
/* 168 */       $$9 = RailShape.EAST_WEST;
/*     */     }
/* 170 */     if (!this.isStraight) {
/* 171 */       if ($$6 && $$8 && !$$5 && !$$7) {
/* 172 */         $$9 = RailShape.SOUTH_EAST;
/*     */       }
/* 174 */       if ($$6 && $$7 && !$$5 && !$$8) {
/* 175 */         $$9 = RailShape.SOUTH_WEST;
/*     */       }
/* 177 */       if ($$5 && $$7 && !$$6 && !$$8) {
/* 178 */         $$9 = RailShape.NORTH_WEST;
/*     */       }
/* 180 */       if ($$5 && $$8 && !$$6 && !$$7) {
/* 181 */         $$9 = RailShape.NORTH_EAST;
/*     */       }
/*     */     } 
/* 184 */     if ($$9 == RailShape.NORTH_SOUTH) {
/* 185 */       if (BaseRailBlock.isRail(this.level, $$1.above())) {
/* 186 */         $$9 = RailShape.ASCENDING_NORTH;
/*     */       }
/* 188 */       if (BaseRailBlock.isRail(this.level, $$2.above())) {
/* 189 */         $$9 = RailShape.ASCENDING_SOUTH;
/*     */       }
/*     */     } 
/* 192 */     if ($$9 == RailShape.EAST_WEST) {
/* 193 */       if (BaseRailBlock.isRail(this.level, $$4.above())) {
/* 194 */         $$9 = RailShape.ASCENDING_EAST;
/*     */       }
/* 196 */       if (BaseRailBlock.isRail(this.level, $$3.above())) {
/* 197 */         $$9 = RailShape.ASCENDING_WEST;
/*     */       }
/*     */     } 
/*     */     
/* 201 */     if ($$9 == null) {
/* 202 */       $$9 = RailShape.NORTH_SOUTH;
/*     */     }
/*     */     
/* 205 */     this.state = (BlockState)this.state.setValue(this.block.getShapeProperty(), (Comparable)$$9);
/* 206 */     this.level.setBlock(this.pos, this.state, 3);
/*     */   }
/*     */   
/*     */   private boolean hasNeighborRail(BlockPos $$0) {
/* 210 */     RailState $$1 = getRail($$0);
/* 211 */     if ($$1 == null) {
/* 212 */       return false;
/*     */     }
/*     */     
/* 215 */     $$1.removeSoftConnections();
/* 216 */     return $$1.canConnectTo(this);
/*     */   }
/*     */   
/*     */   public RailState place(boolean $$0, boolean $$1, RailShape $$2) {
/* 220 */     BlockPos $$3 = this.pos.north();
/* 221 */     BlockPos $$4 = this.pos.south();
/* 222 */     BlockPos $$5 = this.pos.west();
/* 223 */     BlockPos $$6 = this.pos.east();
/*     */     
/* 225 */     boolean $$7 = hasNeighborRail($$3);
/* 226 */     boolean $$8 = hasNeighborRail($$4);
/* 227 */     boolean $$9 = hasNeighborRail($$5);
/* 228 */     boolean $$10 = hasNeighborRail($$6);
/*     */     
/* 230 */     RailShape $$11 = null;
/*     */     
/* 232 */     boolean $$12 = ($$7 || $$8);
/* 233 */     boolean $$13 = ($$9 || $$10);
/* 234 */     if ($$12 && !$$13) {
/* 235 */       $$11 = RailShape.NORTH_SOUTH;
/*     */     }
/* 237 */     if ($$13 && !$$12) {
/* 238 */       $$11 = RailShape.EAST_WEST;
/*     */     }
/*     */     
/* 241 */     boolean $$14 = ($$8 && $$10);
/* 242 */     boolean $$15 = ($$8 && $$9);
/* 243 */     boolean $$16 = ($$7 && $$10);
/* 244 */     boolean $$17 = ($$7 && $$9);
/*     */     
/* 246 */     if (!this.isStraight) {
/* 247 */       if ($$14 && !$$7 && !$$9) {
/* 248 */         $$11 = RailShape.SOUTH_EAST;
/*     */       }
/* 250 */       if ($$15 && !$$7 && !$$10) {
/* 251 */         $$11 = RailShape.SOUTH_WEST;
/*     */       }
/* 253 */       if ($$17 && !$$8 && !$$10) {
/* 254 */         $$11 = RailShape.NORTH_WEST;
/*     */       }
/* 256 */       if ($$16 && !$$8 && !$$9) {
/* 257 */         $$11 = RailShape.NORTH_EAST;
/*     */       }
/*     */     } 
/* 260 */     if ($$11 == null) {
/* 261 */       if ($$12 && $$13) {
/* 262 */         $$11 = $$2;
/* 263 */       } else if ($$12) {
/* 264 */         $$11 = RailShape.NORTH_SOUTH;
/* 265 */       } else if ($$13) {
/* 266 */         $$11 = RailShape.EAST_WEST;
/*     */       } 
/*     */       
/* 269 */       if (!this.isStraight) {
/* 270 */         if ($$0) {
/* 271 */           if ($$14) {
/* 272 */             $$11 = RailShape.SOUTH_EAST;
/*     */           }
/* 274 */           if ($$15) {
/* 275 */             $$11 = RailShape.SOUTH_WEST;
/*     */           }
/* 277 */           if ($$16) {
/* 278 */             $$11 = RailShape.NORTH_EAST;
/*     */           }
/* 280 */           if ($$17) {
/* 281 */             $$11 = RailShape.NORTH_WEST;
/*     */           }
/*     */         } else {
/* 284 */           if ($$17) {
/* 285 */             $$11 = RailShape.NORTH_WEST;
/*     */           }
/* 287 */           if ($$16) {
/* 288 */             $$11 = RailShape.NORTH_EAST;
/*     */           }
/* 290 */           if ($$15) {
/* 291 */             $$11 = RailShape.SOUTH_WEST;
/*     */           }
/* 293 */           if ($$14) {
/* 294 */             $$11 = RailShape.SOUTH_EAST;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 300 */     if ($$11 == RailShape.NORTH_SOUTH) {
/* 301 */       if (BaseRailBlock.isRail(this.level, $$3.above())) {
/* 302 */         $$11 = RailShape.ASCENDING_NORTH;
/*     */       }
/* 304 */       if (BaseRailBlock.isRail(this.level, $$4.above())) {
/* 305 */         $$11 = RailShape.ASCENDING_SOUTH;
/*     */       }
/*     */     } 
/* 308 */     if ($$11 == RailShape.EAST_WEST) {
/* 309 */       if (BaseRailBlock.isRail(this.level, $$6.above())) {
/* 310 */         $$11 = RailShape.ASCENDING_EAST;
/*     */       }
/* 312 */       if (BaseRailBlock.isRail(this.level, $$5.above())) {
/* 313 */         $$11 = RailShape.ASCENDING_WEST;
/*     */       }
/*     */     } 
/*     */     
/* 317 */     if ($$11 == null) {
/* 318 */       $$11 = $$2;
/*     */     }
/*     */     
/* 321 */     updateConnections($$11);
/* 322 */     this.state = (BlockState)this.state.setValue(this.block.getShapeProperty(), (Comparable)$$11);
/*     */     
/* 324 */     if ($$1 || this.level.getBlockState(this.pos) != this.state) {
/* 325 */       this.level.setBlock(this.pos, this.state, 3);
/*     */       
/* 327 */       for (int $$18 = 0; $$18 < this.connections.size(); $$18++) {
/* 328 */         RailState $$19 = getRail(this.connections.get($$18));
/* 329 */         if ($$19 != null) {
/*     */ 
/*     */           
/* 332 */           $$19.removeSoftConnections();
/*     */           
/* 334 */           if ($$19.canConnectTo(this)) {
/* 335 */             $$19.connectTo(this);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 340 */     return this;
/*     */   }
/*     */   
/*     */   public BlockState getState() {
/* 344 */     return this.state;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RailState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */