/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.IntFunction;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Type
/*     */   implements StringRepresentable
/*     */ {
/*  80 */   NORMAL("normal", Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_FENCE),
/*  81 */   MESA("mesa", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE); public static final Codec<Type> CODEC;
/*     */   
/*     */   static {
/*  84 */     CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values);
/*  85 */     BY_ID = ByIdMap.continuous(Enum::ordinal, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */   }
/*     */   private static final IntFunction<Type> BY_ID; private final String name;
/*     */   private final BlockState woodState;
/*     */   private final BlockState planksState;
/*     */   private final BlockState fenceState;
/*     */   
/*     */   Type(String $$0, Block $$1, Block $$2, Block $$3) {
/*  93 */     this.name = $$0;
/*  94 */     this.woodState = $$1.defaultBlockState();
/*  95 */     this.planksState = $$2.defaultBlockState();
/*  96 */     this.fenceState = $$3.defaultBlockState();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 100 */     return this.name;
/*     */   }
/*     */   
/*     */   public static Type byId(int $$0) {
/* 104 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   public BlockState getWoodState() {
/* 108 */     return this.woodState;
/*     */   }
/*     */   
/*     */   public BlockState getPlanksState() {
/* 112 */     return this.planksState;
/*     */   }
/*     */   
/*     */   public BlockState getFenceState() {
/* 116 */     return this.fenceState;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 121 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\MineshaftStructure$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */