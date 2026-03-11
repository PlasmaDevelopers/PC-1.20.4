/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.math.Transformation;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.Util;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ import org.joml.Quaternionf;
/*    */ import org.joml.Vector3f;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class BlockMath {
/* 17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   static {
/* 19 */     VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL = (Map<Direction, Transformation>)Util.make(Maps.newEnumMap(Direction.class), $$0 -> {
/*    */           $$0.put(Direction.SOUTH, Transformation.identity());
/*    */           $$0.put(Direction.EAST, new Transformation(null, (new Quaternionf()).rotateY(1.5707964F), null, null));
/*    */           $$0.put(Direction.WEST, new Transformation(null, (new Quaternionf()).rotateY(-1.5707964F), null, null));
/*    */           $$0.put(Direction.NORTH, new Transformation(null, (new Quaternionf()).rotateY(3.1415927F), null, null));
/*    */           $$0.put(Direction.UP, new Transformation(null, (new Quaternionf()).rotateX(-1.5707964F), null, null));
/*    */           $$0.put(Direction.DOWN, new Transformation(null, (new Quaternionf()).rotateX(1.5707964F), null, null));
/*    */         });
/* 27 */     VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL = (Map<Direction, Transformation>)Util.make(Maps.newEnumMap(Direction.class), $$0 -> {
/*    */           for (Direction $$1 : Direction.values())
/*    */             $$0.put($$1, ((Transformation)VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL.get($$1)).inverse()); 
/*    */         });
/*    */   }
/*    */   
/*    */   public static final Map<Direction, Transformation> VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL;
/*    */   public static final Map<Direction, Transformation> VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL;
/*    */   
/*    */   public static Transformation blockCenterToCorner(Transformation $$0) {
/* 37 */     Matrix4f $$1 = (new Matrix4f()).translation(0.5F, 0.5F, 0.5F);
/* 38 */     $$1.mul((Matrix4fc)$$0.getMatrix());
/* 39 */     $$1.translate(-0.5F, -0.5F, -0.5F);
/* 40 */     return new Transformation($$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Transformation blockCornerToCenter(Transformation $$0) {
/* 47 */     Matrix4f $$1 = (new Matrix4f()).translation(-0.5F, -0.5F, -0.5F);
/* 48 */     $$1.mul((Matrix4fc)$$0.getMatrix());
/* 49 */     $$1.translate(0.5F, 0.5F, 0.5F);
/* 50 */     return new Transformation($$1);
/*    */   }
/*    */   
/*    */   public static Transformation getUVLockTransform(Transformation $$0, Direction $$1, Supplier<String> $$2) {
/* 54 */     Direction $$3 = Direction.rotate($$0.getMatrix(), $$1);
/* 55 */     Transformation $$4 = $$0.inverse();
/* 56 */     if ($$4 == null) {
/* 57 */       LOGGER.warn($$2.get());
/* 58 */       return new Transformation(null, null, new Vector3f(0.0F, 0.0F, 0.0F), null);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 63 */     Transformation $$5 = ((Transformation)VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL.get($$1)).compose($$4).compose(VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL.get($$3));
/*    */     
/* 65 */     return blockCenterToCorner($$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\BlockMath.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */