/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public class TreeDecoratorType<P extends TreeDecorator> {
/*  8 */   public static final TreeDecoratorType<TrunkVineDecorator> TRUNK_VINE = register("trunk_vine", TrunkVineDecorator.CODEC);
/*  9 */   public static final TreeDecoratorType<LeaveVineDecorator> LEAVE_VINE = register("leave_vine", LeaveVineDecorator.CODEC);
/* 10 */   public static final TreeDecoratorType<CocoaDecorator> COCOA = register("cocoa", CocoaDecorator.CODEC);
/* 11 */   public static final TreeDecoratorType<BeehiveDecorator> BEEHIVE = register("beehive", BeehiveDecorator.CODEC);
/* 12 */   public static final TreeDecoratorType<AlterGroundDecorator> ALTER_GROUND = register("alter_ground", AlterGroundDecorator.CODEC);
/* 13 */   public static final TreeDecoratorType<AttachedToLeavesDecorator> ATTACHED_TO_LEAVES = register("attached_to_leaves", AttachedToLeavesDecorator.CODEC);
/*    */   
/*    */   private static <P extends TreeDecorator> TreeDecoratorType<P> register(String $$0, Codec<P> $$1) {
/* 16 */     return (TreeDecoratorType<P>)Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, $$0, new TreeDecoratorType<>($$1));
/*    */   }
/*    */   
/*    */   private final Codec<P> codec;
/*    */   
/*    */   private TreeDecoratorType(Codec<P> $$0) {
/* 22 */     this.codec = $$0;
/*    */   }
/*    */   
/*    */   public Codec<P> codec() {
/* 26 */     return this.codec;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\TreeDecoratorType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */