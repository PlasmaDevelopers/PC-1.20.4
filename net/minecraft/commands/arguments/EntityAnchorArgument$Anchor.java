/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Anchor
/*    */ {
/*    */   FEET, EYES;
/*    */   static final Map<String, Anchor> BY_NAME;
/*    */   private final String name;
/*    */   private final BiFunction<Vec3, Entity, Vec3> transform;
/*    */   
/*    */   static {
/* 60 */     FEET = new Anchor("FEET", 0, "feet", ($$0, $$1) -> $$0);
/* 61 */     EYES = new Anchor("EYES", 1, "eyes", ($$0, $$1) -> new Vec3($$0.x, $$0.y + $$1.getEyeHeight(), $$0.z));
/*    */   }
/*    */   static {
/* 64 */     BY_NAME = (Map<String, Anchor>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */           for (Anchor $$1 : values()) {
/*    */             $$0.put($$1.name, $$1);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   Anchor(String $$0, BiFunction<Vec3, Entity, Vec3> $$1) {
/* 74 */     this.name = $$0;
/* 75 */     this.transform = $$1;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Anchor getByName(String $$0) {
/* 80 */     return BY_NAME.get($$0);
/*    */   }
/*    */   
/*    */   public Vec3 apply(Entity $$0) {
/* 84 */     return this.transform.apply($$0.position(), $$0);
/*    */   }
/*    */   
/*    */   public Vec3 apply(CommandSourceStack $$0) {
/* 88 */     Entity $$1 = $$0.getEntity();
/* 89 */     if ($$1 == null) {
/* 90 */       return $$0.getPosition();
/*    */     }
/* 92 */     return this.transform.apply($$0.getPosition(), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\EntityAnchorArgument$Anchor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */