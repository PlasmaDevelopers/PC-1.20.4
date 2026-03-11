/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public class DirectionProperty
/*    */   extends EnumProperty<Direction> {
/*    */   protected DirectionProperty(String $$0, Collection<Direction> $$1) {
/* 13 */     super($$0, Direction.class, $$1);
/*    */   }
/*    */   
/*    */   public static DirectionProperty create(String $$0) {
/* 17 */     return create($$0, $$0 -> true);
/*    */   }
/*    */   
/*    */   public static DirectionProperty create(String $$0, Predicate<Direction> $$1) {
/* 21 */     return create($$0, (Collection<Direction>)Arrays.<Direction>stream(Direction.values()).filter($$1).collect(Collectors.toList()));
/*    */   }
/*    */   
/*    */   public static DirectionProperty create(String $$0, Direction... $$1) {
/* 25 */     return create($$0, Lists.newArrayList((Object[])$$1));
/*    */   }
/*    */   
/*    */   public static DirectionProperty create(String $$0, Collection<Direction> $$1) {
/* 29 */     return new DirectionProperty($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\DirectionProperty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */