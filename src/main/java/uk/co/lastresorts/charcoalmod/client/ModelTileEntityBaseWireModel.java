package uk.co.lastresorts.charcoalmod.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTileEntityBaseWireModel extends ModelBase
{
    ModelRenderer Centre;
    ModelRenderer branch1;
    ModelRenderer branch0;
    ModelRenderer branch2;
    ModelRenderer branch3;
    ModelRenderer branch4;
    ModelRenderer branch5;
  
  public ModelTileEntityBaseWireModel()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Centre = new ModelRenderer(this, 0, 0);
      Centre.addBox(-3F, -3F, -3F, 6, 6, 6);
      Centre.setRotationPoint(0F, 0F, 0F);
      Centre.setTextureSize(16, 16);
      Centre.mirror = true;
      setRotation(Centre, 0F, 0F, 0F);
      branch1 = new ModelRenderer(this, 24, 0);
      branch1.addBox(-3F, -5F, -3F, 6, 5, 6);
      branch1.setRotationPoint(0F, -3F, 0F);
      branch1.setTextureSize(16, 16);
      branch1.mirror = true;
      setRotation(branch1, 0F, 0F, 0F);
      branch0 = new ModelRenderer(this, 24, 0);
      branch0.addBox(-3F, 0F, -3F, 6, 5, 6);
      branch0.setRotationPoint(0F, 3F, 0F);
      branch0.setTextureSize(16, 16);
      branch0.mirror = true;
      setRotation(branch0, 0F, 0F, 0F);
      branch2 = new ModelRenderer(this, 0, 12);
      branch2.addBox(-3F, -3F, 0F, 6, 6, 5);
      branch2.setRotationPoint(0F, 0F, 3F);
      branch2.setTextureSize(16, 16);
      branch2.mirror = true;
      setRotation(branch2, 0F, 0F, 0F);
      branch3 = new ModelRenderer(this, 0, 12);
      branch3.addBox(-3F, -3F, -5F, 6, 6, 5);
      branch3.setRotationPoint(0F, 0F, -3F);
      branch3.setTextureSize(16, 16);
      branch3.mirror = true;
      setRotation(branch3, 0F, 0F, 0F);
      branch4 = new ModelRenderer(this, 0, 23);
      branch4.addBox(-5F, -3F, -3F, 5, 6, 6);
      branch4.setRotationPoint(-3F, 0F, 0F);
      branch4.setTextureSize(16, 16);
      branch4.mirror = true;
      setRotation(branch4, 0F, 0F, 0F);
      branch5 = new ModelRenderer(this, 0, 23);
      branch5.addBox(0F, -3F, -3F, 5, 6, 6);
      branch5.setRotationPoint(3F, 0F, 0F);
      branch5.setTextureSize(16, 16);
      branch5.mirror = true;
      setRotation(branch5, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean sidesToRender[])
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Centre.render(f5);
    if(sidesToRender[0])	branch0.render(f5);
    if(sidesToRender[1])	branch1.render(f5);
    if(sidesToRender[2])	branch3.render(f5);
    if(sidesToRender[3])	branch2.render(f5);
    if(sidesToRender[4])	branch5.render(f5);
    if(sidesToRender[5])	branch4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}
