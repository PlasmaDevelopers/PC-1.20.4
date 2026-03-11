/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.RandomizableContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.decoration.ItemFrame;
/*     */ import net.minecraft.world.entity.monster.Shulker;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class EndCityPieces {
/*     */   static EndCityPiece addPiece(StructureTemplateManager $$0, EndCityPiece $$1, BlockPos $$2, String $$3, Rotation $$4, boolean $$5) {
/*  35 */     EndCityPiece $$6 = new EndCityPiece($$0, $$3, $$1.templatePosition(), $$4, $$5);
/*  36 */     BlockPos $$7 = $$1.template().calculateConnectedPosition($$1.placeSettings(), $$2, $$6.placeSettings(), BlockPos.ZERO);
/*  37 */     $$6.move($$7.getX(), $$7.getY(), $$7.getZ());
/*     */     
/*  39 */     return $$6;
/*     */   }
/*     */   private static final int MAX_GEN_DEPTH = 8;
/*     */   
/*     */   public static class EndCityPiece extends TemplateStructurePiece { public EndCityPiece(StructureTemplateManager $$0, String $$1, BlockPos $$2, Rotation $$3, boolean $$4) {
/*  44 */       super(StructurePieceType.END_CITY_PIECE, 0, $$0, makeResourceLocation($$1), $$1, makeSettings($$4, $$3), $$2);
/*     */     }
/*     */     
/*     */     public EndCityPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/*  48 */       super(StructurePieceType.END_CITY_PIECE, $$1, $$0, $$1 -> makeSettings($$0.getBoolean("OW"), Rotation.valueOf($$0.getString("Rot"))));
/*     */     }
/*     */     
/*     */     private static StructurePlaceSettings makeSettings(boolean $$0, Rotation $$1) {
/*  52 */       BlockIgnoreProcessor $$2 = $$0 ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
/*  53 */       return (new StructurePlaceSettings()).setIgnoreEntities(true).addProcessor((StructureProcessor)$$2).setRotation($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ResourceLocation makeTemplateLocation() {
/*  58 */       return makeResourceLocation(this.templateName);
/*     */     }
/*     */     
/*     */     private static ResourceLocation makeResourceLocation(String $$0) {
/*  62 */       return new ResourceLocation("end_city/" + $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  67 */       super.addAdditionalSaveData($$0, $$1);
/*     */       
/*  69 */       $$1.putString("Rot", this.placeSettings.getRotation().name());
/*  70 */       $$1.putBoolean("OW", (this.placeSettings.getProcessors().get(0) == BlockIgnoreProcessor.STRUCTURE_BLOCK));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/*  75 */       if ($$0.startsWith("Chest")) {
/*  76 */         BlockPos $$5 = $$1.below();
/*     */         
/*  78 */         if ($$4.isInside((Vec3i)$$5)) {
/*  79 */           RandomizableContainer.setBlockEntityLootTable((BlockGetter)$$2, $$3, $$5, BuiltInLootTables.END_CITY_TREASURE);
/*     */         }
/*  81 */       } else if ($$4.isInside((Vec3i)$$1) && Level.isInSpawnableBounds($$1)) {
/*  82 */         if ($$0.startsWith("Sentry")) {
/*  83 */           Shulker $$6 = (Shulker)EntityType.SHULKER.create((Level)$$2.getLevel());
/*  84 */           if ($$6 != null) {
/*  85 */             $$6.setPos($$1.getX() + 0.5D, $$1.getY(), $$1.getZ() + 0.5D);
/*  86 */             $$2.addFreshEntity((Entity)$$6);
/*     */           } 
/*  88 */         } else if ($$0.startsWith("Elytra")) {
/*  89 */           ItemFrame $$7 = new ItemFrame((Level)$$2.getLevel(), $$1, this.placeSettings.getRotation().rotate(Direction.SOUTH));
/*  90 */           $$7.setItem(new ItemStack((ItemLike)Items.ELYTRA), false);
/*  91 */           $$2.addFreshEntity((Entity)$$7);
/*     */         } 
/*     */       } 
/*     */     } }
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
/*     */   public static void startHouseTower(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, List<StructurePiece> $$3, RandomSource $$4) {
/* 106 */     FAT_TOWER_GENERATOR.init();
/* 107 */     HOUSE_TOWER_GENERATOR.init();
/* 108 */     TOWER_BRIDGE_GENERATOR.init();
/* 109 */     TOWER_GENERATOR.init();
/*     */     
/* 111 */     EndCityPiece $$5 = addHelper($$3, new EndCityPiece($$0, "base_floor", $$1, $$2, true));
/* 112 */     $$5 = addHelper($$3, addPiece($$0, $$5, new BlockPos(-1, 0, -1), "second_floor_1", $$2, false));
/* 113 */     $$5 = addHelper($$3, addPiece($$0, $$5, new BlockPos(-1, 4, -1), "third_floor_1", $$2, false));
/* 114 */     $$5 = addHelper($$3, addPiece($$0, $$5, new BlockPos(-1, 8, -1), "third_roof", $$2, true));
/*     */     
/* 116 */     recursiveChildren($$0, TOWER_GENERATOR, 1, $$5, null, $$3, $$4);
/*     */   }
/*     */   
/*     */   static EndCityPiece addHelper(List<StructurePiece> $$0, EndCityPiece $$1) {
/* 120 */     $$0.add($$1);
/* 121 */     return $$1;
/*     */   }
/*     */   
/*     */   static boolean recursiveChildren(StructureTemplateManager $$0, SectionGenerator $$1, int $$2, EndCityPiece $$3, BlockPos $$4, List<StructurePiece> $$5, RandomSource $$6) {
/* 125 */     if ($$2 > 8) {
/* 126 */       return false;
/*     */     }
/*     */     
/* 129 */     List<StructurePiece> $$7 = Lists.newArrayList();
/* 130 */     if ($$1.generate($$0, $$2, $$3, $$4, $$7, $$6)) {
/*     */       
/* 132 */       boolean $$8 = false;
/* 133 */       int $$9 = $$6.nextInt();
/* 134 */       for (StructurePiece $$10 : $$7) {
/* 135 */         $$10.setGenDepth($$9);
/* 136 */         StructurePiece $$11 = StructurePiece.findCollisionPiece($$5, $$10.getBoundingBox());
/* 137 */         if ($$11 != null && $$11.getGenDepth() != $$3.getGenDepth()) {
/* 138 */           $$8 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 142 */       if (!$$8) {
/* 143 */         $$5.addAll($$7);
/* 144 */         return true;
/*     */       } 
/*     */     } 
/* 147 */     return false;
/*     */   }
/*     */   
/* 150 */   static final SectionGenerator HOUSE_TOWER_GENERATOR = new SectionGenerator()
/*     */     {
/*     */       public void init() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 157 */         if ($$1 > 8) {
/* 158 */           return false;
/*     */         }
/*     */         
/* 161 */         Rotation $$6 = $$2.placeSettings().getRotation();
/* 162 */         EndCityPieces.EndCityPiece $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$2, $$3, "base_floor", $$6, true));
/*     */         
/* 164 */         int $$8 = $$5.nextInt(3);
/* 165 */         if ($$8 == 0) {
/* 166 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "base_roof", $$6, true));
/* 167 */         } else if ($$8 == 1) {
/* 168 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 0, -1), "second_floor_2", $$6, false));
/* 169 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 8, -1), "second_roof", $$6, false));
/*     */           
/* 171 */           EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_GENERATOR, $$1 + 1, $$7, null, $$4, $$5);
/* 172 */         } else if ($$8 == 2) {
/* 173 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 0, -1), "second_floor_2", $$6, false));
/* 174 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "third_floor_2", $$6, false));
/* 175 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 8, -1), "third_roof", $$6, true));
/*     */           
/* 177 */           EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_GENERATOR, $$1 + 1, $$7, null, $$4, $$5);
/*     */         } 
/* 179 */         return true;
/*     */       }
/*     */     };
/*     */   
/* 183 */   static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList((Object[])new Tuple[] { new Tuple(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)) });
/*     */   
/*     */   private static interface SectionGenerator {
/*     */     void init();
/*     */     
/*     */     boolean generate(StructureTemplateManager param1StructureTemplateManager, int param1Int, EndCityPieces.EndCityPiece param1EndCityPiece, BlockPos param1BlockPos, List<StructurePiece> param1List, RandomSource param1RandomSource); }
/*     */   
/* 190 */   static final SectionGenerator TOWER_GENERATOR = new SectionGenerator()
/*     */     {
/*     */       public void init() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 197 */         Rotation $$6 = $$2.placeSettings().getRotation();
/* 198 */         EndCityPieces.EndCityPiece $$7 = $$2;
/* 199 */         $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(3 + $$5.nextInt(2), -3, 3 + $$5.nextInt(2)), "tower_base", $$6, true));
/* 200 */         $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(0, 7, 0), "tower_piece", $$6, true));
/*     */         
/* 202 */         EndCityPieces.EndCityPiece $$8 = ($$5.nextInt(3) == 0) ? $$7 : null;
/*     */         
/* 204 */         int $$9 = 1 + $$5.nextInt(3);
/* 205 */         for (int $$10 = 0; $$10 < $$9; $$10++) {
/* 206 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(0, 4, 0), "tower_piece", $$6, true));
/* 207 */           if ($$10 < $$9 - 1 && $$5.nextBoolean()) {
/* 208 */             $$8 = $$7;
/*     */           }
/*     */         } 
/*     */         
/* 212 */         if ($$8 != null) {
/* 213 */           for (Tuple<Rotation, BlockPos> $$11 : EndCityPieces.TOWER_BRIDGES) {
/* 214 */             if ($$5.nextBoolean()) {
/*     */               
/* 216 */               EndCityPieces.EndCityPiece $$12 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, (BlockPos)$$11.getB(), "bridge_end", $$6.getRotated((Rotation)$$11.getA()), true));
/* 217 */               EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_BRIDGE_GENERATOR, $$1 + 1, $$12, null, $$4, $$5);
/*     */             } 
/*     */           } 
/*     */           
/* 221 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "tower_top", $$6, true));
/*     */         }
/* 223 */         else if ($$1 == 7) {
/* 224 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "tower_top", $$6, true));
/*     */         } else {
/* 226 */           return EndCityPieces.recursiveChildren($$0, EndCityPieces.FAT_TOWER_GENERATOR, $$1 + 1, $$7, null, $$4, $$5);
/*     */         } 
/*     */         
/* 229 */         return true;
/*     */       }
/*     */     };
/*     */   
/* 233 */   static final SectionGenerator TOWER_BRIDGE_GENERATOR = new SectionGenerator()
/*     */     {
/*     */       public boolean shipCreated;
/*     */       
/*     */       public void init() {
/* 238 */         this.shipCreated = false;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 243 */         Rotation $$6 = $$2.placeSettings().getRotation();
/* 244 */         int $$7 = $$5.nextInt(4) + 1;
/*     */         
/* 246 */         EndCityPieces.EndCityPiece $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$2, new BlockPos(0, 0, -4), "bridge_piece", $$6, true));
/* 247 */         $$8.setGenDepth(-1);
/* 248 */         int $$9 = 0;
/* 249 */         for (int $$10 = 0; $$10 < $$7; $$10++) {
/* 250 */           if ($$5.nextBoolean()) {
/* 251 */             $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(0, $$9, -4), "bridge_piece", $$6, true));
/* 252 */             $$9 = 0;
/*     */           } else {
/* 254 */             if ($$5.nextBoolean()) {
/* 255 */               $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(0, $$9, -4), "bridge_steep_stairs", $$6, true));
/*     */             } else {
/* 257 */               $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(0, $$9, -8), "bridge_gentle_stairs", $$6, true));
/*     */             } 
/* 259 */             $$9 = 4;
/*     */           } 
/*     */         } 
/*     */         
/* 263 */         if (this.shipCreated || $$5.nextInt(10 - $$1) != 0) {
/* 264 */           if (!EndCityPieces.recursiveChildren($$0, EndCityPieces.HOUSE_TOWER_GENERATOR, $$1 + 1, $$8, new BlockPos(-3, $$9 + 1, -11), $$4, $$5)) {
/* 265 */             return false;
/*     */           }
/*     */         } else {
/*     */           
/* 269 */           EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(-8 + $$5.nextInt(8), $$9, -70 + $$5.nextInt(10)), "ship", $$6, true));
/* 270 */           this.shipCreated = true;
/*     */         } 
/*     */ 
/*     */         
/* 274 */         $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(4, $$9, 0), "bridge_end", $$6.getRotated(Rotation.CLOCKWISE_180), true));
/* 275 */         $$8.setGenDepth(-1);
/*     */         
/* 277 */         return true;
/*     */       }
/*     */     };
/*     */   
/* 281 */   static final List<Tuple<Rotation, BlockPos>> FAT_TOWER_BRIDGES = Lists.newArrayList((Object[])new Tuple[] { new Tuple(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   static final SectionGenerator FAT_TOWER_GENERATOR = new SectionGenerator()
/*     */     {
/*     */       public void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 296 */         Rotation $$6 = $$2.placeSettings().getRotation();
/*     */         
/* 298 */         EndCityPieces.EndCityPiece $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$2, new BlockPos(-3, 4, -3), "fat_tower_base", $$6, true));
/* 299 */         $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(0, 4, 0), "fat_tower_middle", $$6, true));
/* 300 */         for (int $$8 = 0; $$8 < 2 && 
/* 301 */           $$5.nextInt(3) != 0; $$8++) {
/*     */ 
/*     */           
/* 304 */           $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(0, 8, 0), "fat_tower_middle", $$6, true));
/*     */           
/* 306 */           for (Tuple<Rotation, BlockPos> $$9 : EndCityPieces.FAT_TOWER_BRIDGES) {
/* 307 */             if ($$5.nextBoolean()) {
/*     */               
/* 309 */               EndCityPieces.EndCityPiece $$10 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, (BlockPos)$$9.getB(), "bridge_end", $$6.getRotated((Rotation)$$9.getA()), true));
/* 310 */               EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_BRIDGE_GENERATOR, $$1 + 1, $$10, null, $$4, $$5);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 315 */         $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-2, 8, -2), "fat_tower_top", $$6, true));
/* 316 */         return true;
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\EndCityPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */