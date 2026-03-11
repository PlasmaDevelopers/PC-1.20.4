/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum MergeStrategy
/*     */   implements StringRepresentable
/*     */ {
/* 140 */   REPLACE("replace")
/*     */   {
/*     */     public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 143 */       $$1.set($$0, (Tag)Iterables.getLast($$2));
/*     */     }
/*     */   },
/* 146 */   APPEND("append")
/*     */   {
/*     */     public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 149 */       List<Tag> $$3 = $$1.getOrCreate($$0, ListTag::new);
/* 150 */       $$3.forEach($$1 -> {
/*     */             
/*     */             if ($$1 instanceof ListTag) {
/*     */               $$0.forEach(());
/*     */             }
/*     */           });
/*     */     }
/*     */   },
/* 158 */   MERGE("merge")
/*     */   {
/*     */     public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 161 */       List<Tag> $$3 = $$1.getOrCreate($$0, CompoundTag::new);
/* 162 */       $$3.forEach($$1 -> {
/*     */             if ($$1 instanceof CompoundTag) {
/*     */               $$0.forEach(());
/*     */             }
/*     */           });
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   public static final Codec<MergeStrategy> CODEC;
/*     */   private final String name;
/*     */   
/*     */   static {
/* 175 */     CODEC = (Codec<MergeStrategy>)StringRepresentable.fromEnum(MergeStrategy::values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MergeStrategy(String $$0) {
/* 182 */     this.name = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 187 */     return this.name;
/*     */   }
/*     */   
/*     */   public abstract void merge(Tag paramTag, NbtPathArgument.NbtPath paramNbtPath, List<Tag> paramList) throws CommandSyntaxException;
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNbtFunction$MergeStrategy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */