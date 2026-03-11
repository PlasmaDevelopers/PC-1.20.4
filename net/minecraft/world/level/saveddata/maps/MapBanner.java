/*     */ package net.minecraft.world.level.saveddata.maps;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ 
/*     */ 
/*     */ public class MapBanner
/*     */ {
/*     */   private final BlockPos pos;
/*     */   
/*     */   public MapBanner(BlockPos $$0, DyeColor $$1, @Nullable Component $$2) {
/*  22 */     this.pos = $$0;
/*  23 */     this.color = $$1;
/*  24 */     this.name = $$2;
/*     */   } private final DyeColor color; @Nullable
/*     */   private final Component name;
/*     */   public static MapBanner load(CompoundTag $$0) {
/*  28 */     BlockPos $$1 = NbtUtils.readBlockPos($$0.getCompound("Pos"));
/*  29 */     DyeColor $$2 = DyeColor.byName($$0.getString("Color"), DyeColor.WHITE);
/*  30 */     MutableComponent mutableComponent = $$0.contains("Name") ? Component.Serializer.fromJson($$0.getString("Name")) : null;
/*  31 */     return new MapBanner($$1, $$2, (Component)mutableComponent);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MapBanner fromWorld(BlockGetter $$0, BlockPos $$1) {
/*  36 */     BlockEntity $$2 = $$0.getBlockEntity($$1);
/*  37 */     if ($$2 instanceof BannerBlockEntity) { BannerBlockEntity $$3 = (BannerBlockEntity)$$2;
/*  38 */       DyeColor $$4 = $$3.getBaseColor();
/*  39 */       Component $$5 = $$3.hasCustomName() ? $$3.getCustomName() : null;
/*  40 */       return new MapBanner($$1, $$4, $$5); }
/*     */     
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPos() {
/*  47 */     return this.pos;
/*     */   }
/*     */   
/*     */   public DyeColor getColor() {
/*  51 */     return this.color;
/*     */   }
/*     */   
/*     */   public MapDecoration.Type getDecoration() {
/*  55 */     switch (this.color) {
/*     */       case WHITE:
/*  57 */         return MapDecoration.Type.BANNER_WHITE;
/*     */       case ORANGE:
/*  59 */         return MapDecoration.Type.BANNER_ORANGE;
/*     */       case MAGENTA:
/*  61 */         return MapDecoration.Type.BANNER_MAGENTA;
/*     */       case LIGHT_BLUE:
/*  63 */         return MapDecoration.Type.BANNER_LIGHT_BLUE;
/*     */       case YELLOW:
/*  65 */         return MapDecoration.Type.BANNER_YELLOW;
/*     */       case LIME:
/*  67 */         return MapDecoration.Type.BANNER_LIME;
/*     */       case PINK:
/*  69 */         return MapDecoration.Type.BANNER_PINK;
/*     */       case GRAY:
/*  71 */         return MapDecoration.Type.BANNER_GRAY;
/*     */       case LIGHT_GRAY:
/*  73 */         return MapDecoration.Type.BANNER_LIGHT_GRAY;
/*     */       case CYAN:
/*  75 */         return MapDecoration.Type.BANNER_CYAN;
/*     */       case PURPLE:
/*  77 */         return MapDecoration.Type.BANNER_PURPLE;
/*     */       case BLUE:
/*  79 */         return MapDecoration.Type.BANNER_BLUE;
/*     */       case BROWN:
/*  81 */         return MapDecoration.Type.BANNER_BROWN;
/*     */       case GREEN:
/*  83 */         return MapDecoration.Type.BANNER_GREEN;
/*     */       case RED:
/*  85 */         return MapDecoration.Type.BANNER_RED;
/*     */     } 
/*     */     
/*  88 */     return MapDecoration.Type.BANNER_BLACK;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component getName() {
/*  94 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  99 */     if (this == $$0) {
/* 100 */       return true;
/*     */     }
/* 102 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 103 */       return false;
/*     */     }
/* 105 */     MapBanner $$1 = (MapBanner)$$0;
/* 106 */     return (Objects.equals(this.pos, $$1.pos) && this.color == $$1.color && Objects.equals(this.name, $$1.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Objects.hash(new Object[] { this.pos, this.color, this.name });
/*     */   }
/*     */   
/*     */   public CompoundTag save() {
/* 115 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/* 117 */     $$0.put("Pos", (Tag)NbtUtils.writeBlockPos(this.pos));
/* 118 */     $$0.putString("Color", this.color.getName());
/*     */     
/* 120 */     if (this.name != null) {
/* 121 */       $$0.putString("Name", Component.Serializer.toJson(this.name));
/*     */     }
/*     */     
/* 124 */     return $$0;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 128 */     return "banner-" + this.pos.getX() + "," + this.pos.getY() + "," + this.pos.getZ();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapBanner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */