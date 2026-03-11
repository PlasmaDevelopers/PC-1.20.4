/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
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
/*     */ final class UpgradeChunk
/*     */ {
/*     */   private int sides;
/* 478 */   private final ChunkPalettedStorageFix.Section[] sections = new ChunkPalettedStorageFix.Section[16];
/*     */   
/*     */   private final Dynamic<?> level;
/*     */   private final int x;
/*     */   private final int z;
/* 483 */   private final Int2ObjectMap<Dynamic<?>> blockEntities = (Int2ObjectMap<Dynamic<?>>)new Int2ObjectLinkedOpenHashMap(16);
/*     */   
/*     */   public UpgradeChunk(Dynamic<?> $$0) {
/* 486 */     this.level = $$0;
/* 487 */     this.x = $$0.get("xPos").asInt(0) << 4;
/* 488 */     this.z = $$0.get("zPos").asInt(0) << 4;
/*     */     
/* 490 */     $$0.get("TileEntities").asStreamOpt().result().ifPresent($$0 -> $$0.forEach(()));
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
/* 503 */     boolean $$1 = $$0.get("convertedFromAlphaFormat").asBoolean(false);
/*     */     
/* 505 */     $$0.get("Sections").asStreamOpt().result().ifPresent($$0 -> $$0.forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     for (ChunkPalettedStorageFix.Section $$2 : this.sections) {
/* 514 */       if ($$2 != null)
/*     */       {
/*     */ 
/*     */         
/* 518 */         for (ObjectIterator<Map.Entry<Integer, IntList>> objectIterator = $$2.toFix.entrySet().iterator(); objectIterator.hasNext(); ) { IntListIterator<Integer> intListIterator; Map.Entry<Integer, IntList> $$3 = objectIterator.next();
/* 519 */           int $$4 = $$2.y << 12;
/* 520 */           switch (((Integer)$$3.getKey()).intValue()) {
/*     */             case 2:
/* 522 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$5 = ((Integer)intListIterator.next()).intValue();
/* 523 */                 $$5 |= $$4;
/*     */                 
/* 525 */                 Dynamic<?> $$6 = getBlock($$5);
/* 526 */                 if ("minecraft:grass_block".equals(ChunkPalettedStorageFix.getName($$6))) {
/* 527 */                   String $$7 = ChunkPalettedStorageFix.getName(getBlock(relative($$5, ChunkPalettedStorageFix.Direction.UP)));
/* 528 */                   if ("minecraft:snow".equals($$7) || "minecraft:snow_layer".equals($$7)) {
/* 529 */                     setBlock($$5, ChunkPalettedStorageFix.SNOWY_GRASS);
/*     */                   }
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 3:
/* 536 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$8 = ((Integer)intListIterator.next()).intValue();
/* 537 */                 $$8 |= $$4;
/*     */                 
/* 539 */                 Dynamic<?> $$9 = getBlock($$8);
/* 540 */                 if ("minecraft:podzol".equals(ChunkPalettedStorageFix.getName($$9))) {
/* 541 */                   String $$10 = ChunkPalettedStorageFix.getName(getBlock(relative($$8, ChunkPalettedStorageFix.Direction.UP)));
/* 542 */                   if ("minecraft:snow".equals($$10) || "minecraft:snow_layer".equals($$10)) {
/* 543 */                     setBlock($$8, ChunkPalettedStorageFix.SNOWY_PODZOL);
/*     */                   }
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 110:
/* 550 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$11 = ((Integer)intListIterator.next()).intValue();
/* 551 */                 $$11 |= $$4;
/*     */                 
/* 553 */                 Dynamic<?> $$12 = getBlock($$11);
/* 554 */                 if ("minecraft:mycelium".equals(ChunkPalettedStorageFix.getName($$12))) {
/* 555 */                   String $$13 = ChunkPalettedStorageFix.getName(getBlock(relative($$11, ChunkPalettedStorageFix.Direction.UP)));
/* 556 */                   if ("minecraft:snow".equals($$13) || "minecraft:snow_layer".equals($$13)) {
/* 557 */                     setBlock($$11, ChunkPalettedStorageFix.SNOWY_MYCELIUM);
/*     */                   }
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 25:
/* 564 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$14 = ((Integer)intListIterator.next()).intValue();
/* 565 */                 $$14 |= $$4;
/* 566 */                 Dynamic<?> $$15 = removeBlockEntity($$14);
/* 567 */                 if ($$15 != null) {
/* 568 */                   String $$16 = Boolean.toString($$15.get("powered").asBoolean(false)) + Boolean.toString($$15.get("powered").asBoolean(false));
/* 569 */                   setBlock($$14, ChunkPalettedStorageFix.NOTE_BLOCK_MAP.getOrDefault($$16, ChunkPalettedStorageFix.NOTE_BLOCK_MAP.get("false0")));
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 26:
/* 575 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$17 = ((Integer)intListIterator.next()).intValue();
/* 576 */                 $$17 |= $$4;
/* 577 */                 Dynamic<?> $$18 = getBlockEntity($$17);
/* 578 */                 Dynamic<?> $$19 = getBlock($$17);
/* 579 */                 if ($$18 != null) {
/* 580 */                   int $$20 = $$18.get("color").asInt(0);
/* 581 */                   if ($$20 != 14 && $$20 >= 0 && $$20 < 16) {
/* 582 */                     String $$21 = ChunkPalettedStorageFix.getProperty($$19, "facing") + ChunkPalettedStorageFix.getProperty($$19, "facing") + ChunkPalettedStorageFix.getProperty($$19, "occupied") + ChunkPalettedStorageFix.getProperty($$19, "part");
/* 583 */                     if (ChunkPalettedStorageFix.BED_BLOCK_MAP.containsKey($$21)) {
/* 584 */                       setBlock($$17, ChunkPalettedStorageFix.BED_BLOCK_MAP.get($$21));
/*     */                     }
/*     */                   } 
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 176:
/*     */             case 177:
/* 593 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$22 = ((Integer)intListIterator.next()).intValue();
/* 594 */                 $$22 |= $$4;
/* 595 */                 Dynamic<?> $$23 = getBlockEntity($$22);
/* 596 */                 Dynamic<?> $$24 = getBlock($$22);
/* 597 */                 if ($$23 != null) {
/* 598 */                   int $$25 = $$23.get("Base").asInt(0);
/* 599 */                   if ($$25 != 15 && $$25 >= 0 && $$25 < 16) {
/* 600 */                     String $$26 = ChunkPalettedStorageFix.getProperty($$24, (((Integer)$$3.getKey()).intValue() == 176) ? "rotation" : "facing") + "_" + ChunkPalettedStorageFix.getProperty($$24, (((Integer)$$3.getKey()).intValue() == 176) ? "rotation" : "facing");
/* 601 */                     if (ChunkPalettedStorageFix.BANNER_BLOCK_MAP.containsKey($$26)) {
/* 602 */                       setBlock($$22, ChunkPalettedStorageFix.BANNER_BLOCK_MAP.get($$26));
/*     */                     }
/*     */                   } 
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 86:
/* 610 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$27 = ((Integer)intListIterator.next()).intValue();
/* 611 */                 $$27 |= $$4;
/*     */                 
/* 613 */                 Dynamic<?> $$28 = getBlock($$27);
/* 614 */                 if ("minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.getName($$28))) {
/* 615 */                   String $$29 = ChunkPalettedStorageFix.getName(getBlock(relative($$27, ChunkPalettedStorageFix.Direction.DOWN)));
/* 616 */                   if ("minecraft:grass_block".equals($$29) || "minecraft:dirt".equals($$29)) {
/* 617 */                     setBlock($$27, ChunkPalettedStorageFix.PUMPKIN);
/*     */                   }
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 140:
/* 624 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$30 = ((Integer)intListIterator.next()).intValue();
/* 625 */                 $$30 |= $$4;
/* 626 */                 Dynamic<?> $$31 = removeBlockEntity($$30);
/* 627 */                 if ($$31 != null) {
/* 628 */                   String $$32 = $$31.get("Item").asString("") + $$31.get("Item").asString("");
/* 629 */                   setBlock($$30, ChunkPalettedStorageFix.FLOWER_POT_MAP.getOrDefault($$32, ChunkPalettedStorageFix.FLOWER_POT_MAP.get("minecraft:air0")));
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 144:
/* 635 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$33 = ((Integer)intListIterator.next()).intValue();
/* 636 */                 $$33 |= $$4;
/* 637 */                 Dynamic<?> $$34 = getBlockEntity($$33);
/* 638 */                 if ($$34 != null) {
/* 639 */                   String $$38, $$35 = String.valueOf($$34.get("SkullType").asInt(0));
/* 640 */                   String $$36 = ChunkPalettedStorageFix.getProperty(getBlock($$33), "facing");
/*     */                   
/* 642 */                   if ("up".equals($$36) || "down".equals($$36)) {
/* 643 */                     String $$37 = $$35 + $$35;
/*     */                   } else {
/* 645 */                     $$38 = $$35 + $$35;
/*     */                   } 
/*     */                   
/* 648 */                   $$34.remove("SkullType");
/* 649 */                   $$34.remove("facing");
/* 650 */                   $$34.remove("Rot");
/*     */                   
/* 652 */                   setBlock($$33, ChunkPalettedStorageFix.SKULL_MAP.getOrDefault($$38, ChunkPalettedStorageFix.SKULL_MAP.get("0north")));
/*     */                 }  }
/*     */             
/*     */             
/*     */             case 64:
/*     */             case 71:
/*     */             case 193:
/*     */             case 194:
/*     */             case 195:
/*     */             case 196:
/*     */             case 197:
/* 663 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$39 = ((Integer)intListIterator.next()).intValue();
/* 664 */                 $$39 |= $$4;
/*     */                 
/* 666 */                 Dynamic<?> $$40 = getBlock($$39);
/* 667 */                 if (ChunkPalettedStorageFix.getName($$40).endsWith("_door")) {
/* 668 */                   Dynamic<?> $$41 = getBlock($$39);
/* 669 */                   if ("lower".equals(ChunkPalettedStorageFix.getProperty($$41, "half"))) {
/* 670 */                     int $$42 = relative($$39, ChunkPalettedStorageFix.Direction.UP);
/* 671 */                     Dynamic<?> $$43 = getBlock($$42);
/* 672 */                     String $$44 = ChunkPalettedStorageFix.getName($$41);
/* 673 */                     if ($$44.equals(ChunkPalettedStorageFix.getName($$43))) {
/* 674 */                       String $$45 = ChunkPalettedStorageFix.getProperty($$41, "facing");
/* 675 */                       String $$46 = ChunkPalettedStorageFix.getProperty($$41, "open");
/* 676 */                       String $$47 = $$1 ? "left" : ChunkPalettedStorageFix.getProperty($$43, "hinge");
/* 677 */                       String $$48 = $$1 ? "false" : ChunkPalettedStorageFix.getProperty($$43, "powered");
/* 678 */                       setBlock($$39, ChunkPalettedStorageFix.DOOR_MAP.get($$44 + $$44 + "lower" + $$45 + $$47 + $$46));
/* 679 */                       setBlock($$42, ChunkPalettedStorageFix.DOOR_MAP.get($$44 + $$44 + "upper" + $$45 + $$47 + $$46));
/*     */                     } 
/*     */                   } 
/*     */                 }  }
/*     */             
/*     */ 
/*     */             
/*     */             case 175:
/* 687 */               for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$49 = ((Integer)intListIterator.next()).intValue();
/* 688 */                 $$49 |= $$4;
/*     */                 
/* 690 */                 Dynamic<?> $$50 = getBlock($$49);
/* 691 */                 if ("upper".equals(ChunkPalettedStorageFix.getProperty($$50, "half"))) {
/* 692 */                   Dynamic<?> $$51 = getBlock(relative($$49, ChunkPalettedStorageFix.Direction.DOWN));
/* 693 */                   String $$52 = ChunkPalettedStorageFix.getName($$51);
/* 694 */                   if ("minecraft:sunflower".equals($$52)) {
/* 695 */                     setBlock($$49, ChunkPalettedStorageFix.UPPER_SUNFLOWER); continue;
/* 696 */                   }  if ("minecraft:lilac".equals($$52)) {
/* 697 */                     setBlock($$49, ChunkPalettedStorageFix.UPPER_LILAC); continue;
/* 698 */                   }  if ("minecraft:tall_grass".equals($$52)) {
/* 699 */                     setBlock($$49, ChunkPalettedStorageFix.UPPER_TALL_GRASS); continue;
/* 700 */                   }  if ("minecraft:large_fern".equals($$52)) {
/* 701 */                     setBlock($$49, ChunkPalettedStorageFix.UPPER_LARGE_FERN); continue;
/* 702 */                   }  if ("minecraft:rose_bush".equals($$52)) {
/* 703 */                     setBlock($$49, ChunkPalettedStorageFix.UPPER_ROSE_BUSH); continue;
/* 704 */                   }  if ("minecraft:peony".equals($$52)) {
/* 705 */                     setBlock($$49, ChunkPalettedStorageFix.UPPER_PEONY);
/*     */                   }
/*     */                 }  }
/*     */             
/*     */           }  }
/*     */       
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Dynamic<?> getBlockEntity(int $$0) {
/* 718 */     return (Dynamic)this.blockEntities.get($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Dynamic<?> removeBlockEntity(int $$0) {
/* 723 */     return (Dynamic)this.blockEntities.remove($$0); } public static int relative(int $$0, ChunkPalettedStorageFix.Direction $$1) {
/*     */     int $$2;
/*     */     int $$3;
/*     */     int $$4;
/* 727 */     switch (ChunkPalettedStorageFix.null.$SwitchMap$net$minecraft$util$datafix$fixes$ChunkPalettedStorageFix$Direction$Axis[$$1.getAxis().ordinal()]) {
/*     */       case 1:
/* 729 */         $$2 = ($$0 & 0xF) + $$1.getAxisDirection().getStep();
/* 730 */         return ($$2 < 0 || $$2 > 15) ? -1 : ($$0 & 0xFFFFFFF0 | $$2);
/*     */       case 2:
/* 732 */         $$3 = ($$0 >> 8) + $$1.getAxisDirection().getStep();
/* 733 */         return ($$3 < 0 || $$3 > 255) ? -1 : ($$0 & 0xFF | $$3 << 8);
/*     */       case 3:
/* 735 */         $$4 = ($$0 >> 4 & 0xF) + $$1.getAxisDirection().getStep();
/* 736 */         return ($$4 < 0 || $$4 > 15) ? -1 : ($$0 & 0xFFFFFF0F | $$4 << 4);
/*     */     } 
/* 738 */     return -1;
/*     */   }
/*     */   
/*     */   private void setBlock(int $$0, Dynamic<?> $$1) {
/* 742 */     if ($$0 < 0 || $$0 > 65535) {
/*     */       return;
/*     */     }
/*     */     
/* 746 */     ChunkPalettedStorageFix.Section $$2 = getSection($$0);
/*     */     
/* 748 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 752 */     $$2.setBlock($$0 & 0xFFF, $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ChunkPalettedStorageFix.Section getSection(int $$0) {
/* 757 */     int $$1 = $$0 >> 12;
/* 758 */     return ($$1 < this.sections.length) ? this.sections[$$1] : null;
/*     */   }
/*     */   
/*     */   public Dynamic<?> getBlock(int $$0) {
/* 762 */     if ($$0 < 0 || $$0 > 65535) {
/* 763 */       return ChunkPalettedStorageFix.AIR;
/*     */     }
/*     */     
/* 766 */     ChunkPalettedStorageFix.Section $$1 = getSection($$0);
/*     */     
/* 768 */     if ($$1 == null) {
/* 769 */       return ChunkPalettedStorageFix.AIR;
/*     */     }
/*     */     
/* 772 */     return $$1.getBlock($$0 & 0xFFF);
/*     */   }
/*     */   
/*     */   public Dynamic<?> write() {
/* 776 */     Dynamic<?> $$0 = this.level;
/* 777 */     if (this.blockEntities.isEmpty()) {
/* 778 */       $$0 = $$0.remove("TileEntities");
/*     */     } else {
/* 780 */       $$0 = $$0.set("TileEntities", $$0.createList(this.blockEntities.values().stream()));
/*     */     } 
/*     */     
/* 783 */     Dynamic<?> $$1 = $$0.emptyMap();
/* 784 */     List<Dynamic<?>> $$2 = Lists.newArrayList();
/* 785 */     for (ChunkPalettedStorageFix.Section $$3 : this.sections) {
/* 786 */       if ($$3 != null) {
/* 787 */         $$2.add($$3.write());
/* 788 */         $$1 = $$1.set(String.valueOf($$3.y), $$1.createIntList(Arrays.stream($$3.update.toIntArray())));
/*     */       } 
/*     */     } 
/*     */     
/* 792 */     Dynamic<?> $$4 = $$0.emptyMap();
/* 793 */     $$4 = $$4.set("Sides", $$4.createByte((byte)this.sides));
/* 794 */     $$4 = $$4.set("Indices", $$1);
/* 795 */     return $$0.set("UpgradeData", $$4).set("Sections", $$4.createList($$2.stream()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkPalettedStorageFix$UpgradeChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */